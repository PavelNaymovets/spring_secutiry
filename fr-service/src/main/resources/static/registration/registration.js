angular.module('market').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    $scope.functionRegistration = function () {
        $http.post(contextPath + 'auth/registration', $scope.reguser)
            .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.springWebUser = {username: $scope.reguser.username, token: response.data.token, isAdmin: response.data.isAdmin};

                        $scope.reguser = null;

                        $location.path('/');
                    }
                }, function errorCallback(response) {
                    if (response.status == 400) {
                        alert(response.data.message);
                    }
            });
    }
});