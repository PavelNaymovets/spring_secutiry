angular.module('market').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8193/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'core/api/v1/orders').then(function (response) {
            $scope.orders = response.data;
        });
    }

    $scope.loadOrders();
});