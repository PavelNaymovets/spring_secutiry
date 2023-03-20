angular.module('market').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8193/';

    $scope.loadOrders = function (id = 1) {
        $http.get(contextPath + 'core/api/v1/orders')
            .then(function (response) {
                $scope.orders = response.data;
                $scope.orderId = id;

                $scope.items = response.data[id - 1].items;
                $scope.orderTotalPrice = response.data[id - 1].totalPrice;
        });
    }

    $scope.loadOrders();
});