angular.module('market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //запрос списка продуктов из корзины
    $scope.loadProductsCart = function () {
        $http.get(contextPath + 'cart/api/v1/cart/' + $localStorage.marketGuestCartId)
             .then(function (response) {
                  console.log(response);
                  $scope.Cart = response.data;
        });
    }

    //удаление продукта из корзины по id
    $scope.deleteProductCart = function (productId) {
        $http.delete(contextPath + 'cart/api/v1/cart/' + $localStorage.marketGuestCartId + '/' + productId)
             .then(function (response) {
                $scope.loadProductsCart();
             });
    }

    //изменение количества продуктов в корзине по id
    $scope.changeProductQuantityInCart = function (productId, delta) {
        $http({
            url: contextPath + 'cart/api/v1/cart/' + $localStorage.marketGuestCartId,
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
        $http.delete(contextPath + 'cart/api/v1/cart/' + $localStorage.marketGuestCartId)
            .then(function (response) {
                $scope.loadProductsCart();
        });
    }

    //оформить заказ
    $scope.createOrder = function () {
        $scope.phone = $scope.frontOrder.phone;
        $scope.address = $scope.frontOrder.country + ", г." + $scope.frontOrder.city + ", ул." + $scope.frontOrder.street + ", дом " + $scope.frontOrder.home + ", квартира " + $scope.frontOrder.flat;

        $http({
            url: contextPath + 'core/api/v1/orders',
            method: 'POST',
            params: {
                phone : $scope.phone,
                address : $scope.address
            }
        }).then(function successCallback(response) {
                alert('Заказ оформлен');
                $scope.clearCart();
                $location.path('/order');
            }, function errorCallback(response) {
                if (response.status == 400) {
                    alert(response.data.errorMessage);
                }
            });
    }

    $scope.loadProductsCart();
});