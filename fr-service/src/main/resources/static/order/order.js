angular.module('market').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8193/';
    $scope.orderList = false;

    $scope.loadOrders = function (id = 1) {
        $http.get(contextPath + 'core/api/v1/orders')
            .then(function (response) {
                $scope.orders = response.data;
        });
    }

    $scope.loadOrderProducts = function (id = 1) {
        $scope.orderId = id;
        $scope.items = $scope.orders[id - 1].items;
        $scope.orderTotalPrice = $scope.orders[id - 1].totalPrice;
        $scope.orderList = true;
    }

    $scope.hideOrderProducts = function () {
        $scope.orderList = false;
    }

    $scope.loadOrders();
});