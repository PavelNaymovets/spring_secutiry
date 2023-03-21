angular.module('market').controller('storeController', function ($scope, $rootScope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //запрос списка продуктов из репозитория
    $scope.loadProducts = function (pageIndex = 1) {
        $scope.page = pageIndex;
        if (pageIndex <= 1) {
            $scope.page = 1;
        } else if (pageIndex > $scope.totalPages) {
            $scope.page = $scope.totalPages;
        }
        $http({
              url: contextPath + 'core/api/v1/products',
              method: 'GET',
              params: {
                  p : $scope.page,
                  name_part : $scope.product ? $scope.product.name_part : null,
                  min_price : $scope.product ? $scope.product.min_price : null,
                  max_price : $scope.product ? $scope.product.max_price : null
              }
        }).then(function (response) {
              console.log(response);
              $scope.totalPages = response.data.totalPages;

              $scope.ProductsList = response.data.items;
              $scope.generatePageList(response.data.totalPages);
        });
    }

    //подсчет количества страниц с продуктами
    $scope.generatePageList = function(totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
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
                $scope.loadProducts($scope.page);
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
            $scope.loadProducts($scope.page);
        });
    }

    //добавление продукта в корзину по id
    $scope.addProductCart = function (productId) {
        $http.post(contextPath + 'cart/api/v1/cart/' + $localStorage.marketGuestCartId + '/' + productId)
             .then(function (response) {
                console.log(response);
             });
    }

    $scope.loadProducts();
});