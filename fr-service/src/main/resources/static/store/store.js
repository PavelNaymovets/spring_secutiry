angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //запрос списка продуктов из репозитория
    $scope.loadProducts = function (pageIndex = 1) {
        $http({
              url: contextPath + 'core/api/v1/products',
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

    //сбросить фильтр
    $scope.reload = function () {
        $scope.product = null;
        $scope.loadProducts();
    }

    //удаление продукта из репозитория по id
    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + 'core/api/v1/products/' + productId)
             .then(function (response) {
                $scope.loadProducts();
             });
    }

    //изменение количества продуктов по id
    $scope.changeQuantity = function (productId, delta) {
        $http({
            url: contextPath + 'core/api/v1/products',
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
        $http.post(contextPath + 'core/api/v1/products', $scope.newProduct)
             .then(function(response) {
                $scope.loadProducts();
             });
    }

    //добавление продукта в корзину по id
    $scope.addProductCart = function (productId) {
        $http.post(contextPath + 'cart/api/v1/cart/' + productId)
             .then(function (response) {
                console.log(response);
             });
    }

    $scope.loadProducts();
});