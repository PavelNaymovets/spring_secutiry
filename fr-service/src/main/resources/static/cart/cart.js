angular.module('market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //запрос списка продуктов из корзины
    $scope.loadProductsCart = function () {
        $http.get(contextPath + 'cart/api/v1/cart')
             .then(function (response) {
                  console.log(response);
                  $scope.Cart = response.data;
        });
    }

    //удаление продукта из корзины по id
    $scope.deleteProductCart = function (productId) {
        $http.delete(contextPath + 'cart/api/v1/cart/' + productId)
             .then(function (response) {
                $scope.loadProductsCart();
             });
    }

    //изменение количества продуктов в корзине по id
    $scope.changeProductQuantityInCart = function (productId, delta) {
        $http({
            url: contextPath + 'cart/api/v1/cart',
            method: 'PUT',
            params: {
                id: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProductsCart();
        });
    }

    //очистить корзину
    $scope.clearCart = function () {
        $http.delete(contextPath + 'cart/api/v1/cart')
            .then(function (response) {
                $scope.loadProductsCart();
        });
    }

    //оформить заказ
    $scope.createOrder = function () {
        $http.post(contextPath + 'core/api/v1/orders')
            .then(function (response) {
                alert('Заказ оформлен');
                $scope.clearCart();
        });
    }

    $scope.loadProductsCart();
});