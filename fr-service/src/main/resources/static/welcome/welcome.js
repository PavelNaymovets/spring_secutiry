angular.module('market').controller('welcomeController', function ($scope, $rootScope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //запрос списка пользователей
    $scope.loadUsers = function () {
        $http.get(contextPath + 'auth/api/v1/admin')
             .then(function (response) {
                  console.log(response);
                  $scope.UserList = response.data;
        });
    }

    $scope.loadUsers();
});