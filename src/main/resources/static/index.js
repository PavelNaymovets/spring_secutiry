angular.module('app', []).controller('indexController', function($scope, $http){
    const contextPath = 'http://localhost:8190/app';

    //запрос списка продуктов из репозитория
    $scope.loadProducts = function (pageIndex = 1) {
        $http({
              url: contextPath + '/api/v1/products',
              method: 'GET',
              params: {
                  name_part : $scope.product ? $scope.product.name_part : null,
                  min_price : $scope.product ? $scope.product.min_price : null,
                  max_price : $scope.product ? $scope.product.max_price : null
              }
        }).then(function (response) {
              console.log(response);
              $scope.ProductsList = response.data.content;
        });
    }

    //запрос списка продуктов из корзины
    $scope.loadProductsCart = function () {
        $http.get(contextPath + '/api/v1/products/cart')
             .then(function (response) {
                  console.log(response);
                  $scope.ProductsListCart = response.data;
        });
    }

    //сбросить фильтр
    $scope.reload = function () {
        $scope.product = null;
        $scope.loadProducts();
    }

    //удаление продукта из репозитория по id
    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/api/v1/products/' + productId)
             .then(function (response) {
                $scope.loadProducts();
             });
    }

    //удаление продукта из корзины по id
    $scope.deleteProductCart = function (productId) {
        $http.delete(contextPath + '/api/v1/products/cart/' + productId)
             .then(function (response) {
                $scope.loadProductsCart();
             });
    }

    //добавление продукта в корзину по id
    $scope.addProductCart = function (productId) {
        $http.post(contextPath + '/api/v1/products/cart/' + productId)
             .then(function (response) {
                $scope.loadProductsCart();
             });
    }

    //изменение количества продуктов по id
    $scope.changeQuantity = function (productId, delta) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'PUT',
            params: {
                id: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    //добавить новый продукт
    $scope.addProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
             .then(function(response) {
                $scope.loadProducts();
             });
    }

    $scope.loadProducts();
    $scope.loadProductsCart();
});