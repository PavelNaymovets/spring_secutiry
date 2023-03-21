(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order', {
                templateUrl: 'order/order.html',
                controller: 'ordersController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/userInfo', {
                templateUrl: 'user/info/userInfo.html',
                controller: 'userInfoController'
            })
            .when('/product', {
                templateUrl: 'product/product.html',
                controller: 'productController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springWebUser) {
            try {
                let jwt = $localStorage.springWebUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);

                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.springWebUser;
                    $http.defaults.headers.common.Authorization = '';
                }

            } catch (e) {
            }

            //подставляю авторизационный токен из локал стораджа в хедер при каждом запросе
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }

        if (!$localStorage.marketGuestCartId) {
            $http.get('http://localhost:8193/cart/api/v1/cart/uuid')
                .then(function successCallback(response) {
                    $localStorage.marketGuestCartId = response.data.value;
                });
        }
    }
})();

angular.module('market').controller('indexController', function($scope, $rootScope, $http, $location, $localStorage){
    const contextPath = 'http://localhost:8193/';

    //аутентификация
    $scope.tryToAuth = function () {
        $http.post(contextPath + 'auth/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token, isAdmin: response.data.isAdmin};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $location.path('/');
                }
            }, function errorCallback(response) {
            });
    }

    //выход с сайта
    $scope.tryToLogout = function () {
        $scope.clearUser();
        $location.path('/');
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    }

    //удаление токена из локал стораджа
    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    }

    //уведомление об авторизации
    $scope.showCurrentUserInfo = function () {
        $http.get(contextPath + 'auth/api/v1/profile')
            .then(function successCallback(response) {
                alert('Имя пользователя: ' + response.data.username);
            }, function errorCallback(response) {
                alert('Не авторизованный пользователь');
            });
    }

    //проверка авторизации
    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    }

    //пользователь имеет права администратора
    $rootScope.isAdmin = function () {
        if ($localStorage.springWebUser == null) {
            return false;
        } else if ($localStorage.springWebUser.isAdmin == true) {
            return true;
        } else {
            return false;
        }
    }


});