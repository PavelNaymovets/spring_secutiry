angular.module('market').controller('userInfoController', function ($scope, $rootScope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //запрос списка пользователей
    $scope.loadUsers = function () {
        $http.get(contextPath + 'auth/api/v1/admin')
             .then(function (response) {
                  console.log(response);
                  $scope.UserList = response.data;
        });
    }

    //добавить роль пользователю
    $scope.addRole = function () {
        $http.put(contextPath + 'auth/api/v1/admin/role/add', $scope.updateUser)
             .then(function successCallback(response) {
                    $scope.loadUsers();
                }, function errorCallback(response) {
                    if (response.status == 400) {
                        alert(response.data.message);
                    }
                });
    }

    //убрать роль пользователя
    $scope.deleteRole = function () {
        $http.put(contextPath + 'auth/api/v1/admin/role/delete', $scope.updateUser)
             .then(function(response) {
                $scope.loadUsers();
             });
    }

    $scope.loadUsers();
});