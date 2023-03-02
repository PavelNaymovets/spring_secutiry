angular.module('app', ['ngStorage']).controller('indexController', function($scope, $rootScope, $http, $localStorage){
    const contextPath = 'http://localhost:8190/market-core';
    const contextPathToCartService = 'http://localhost:8191/market-carts';

    //подставляю авторизационный токен из локал стораджа в хедер при каждом запросе
    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    //АУТЕНТИФИКАЦИЯ

    //аутентификация
    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token, isAdmin: response.data.isAdmin};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    }

    //выход с сайта
    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
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
        if ($localStorage.springWebUser.isAdmin == true) {
            return true;
        } else {
            return false;
        }
    }

    //уведомление об авторизации
    $scope.showCurrentUserInfo = function () {
        $http.get(contextPath + '/api/v1/profile')
            .then(function successCallback(response) {
                alert('Имя пользователя: ' + response.data.username);
            }, function errorCallback(response) {
                alert('Не авторизованный пользователь');
            });
    }

    //удаление токена из локал стораджа
    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    }

    //РАБОТА С ТОВАРАМИ

    //запрос списка продуктов из репозитория
    $scope.loadProducts = function (pageIndex = 1) {
        $http({
              url: contextPath + '/api/v1/products',
              method: 'GET',
              params: {
                  name_part : $scope.product ? $scope.product.name_part : null,
                  min_price : $scope.product ? $scope.product.min_price : null,
                  max_price : $scope.product ? $scope.product.max_price : null
              }
        }).then(function (response) {
              console.log(response);
              $scope.ProductsList = response.data.content;
        });
    }

    //запрос списка пользователей
    $scope.loadUsers = function () {
        $http.get(contextPath + '/api/v1/admin')
             .then(function (response) {
                  console.log(response);
                  $scope.UserList = response.data;
        });
    }

    //сбросить фильтр
    $scope.reload = function () {
        $scope.product = null;
        $scope.loadProducts();
    }

    //удаление продукта из репозитория по id
    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/api/v1/products/' + productId)
             .then(function (response) {
                $scope.loadProducts();
             });
    }

    //изменение количества продуктов по id
    $scope.changeQuantity = function (productId, delta) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'PUT',
            params: {
                id: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    //добавить новый продукт
    $scope.addProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
             .then(function(response) {
                $scope.loadProducts();
             });
    }

    //запрос списка продуктов из корзины
    $scope.loadProductsCart = function () {
        $http.get(contextPathToCartService + '/api/v1/cart')
             .then(function (response) {
                  console.log(response);
                  $scope.Cart = response.data;
        });
    }

    //удаление продукта из корзины по id
    $scope.deleteProductCart = function (productId) {
        $http.delete(contextPathToCartService + '/api/v1/cart/' + productId)
             .then(function (response) {
                $scope.loadProductsCart();
             });
    }

    //добавление продукта в корзину по id
    $scope.addProductCart = function (productId) {
        $http.post(contextPathToCartService + '/api/v1/cart/' + productId)
             .then(function (response) {
                $scope.loadProductsCart();
             });
    }

    //изменение количества продуктов в корзине по id
    $scope.changeProductQuantityInCart = function (productId, delta) {
        $http({
            url: contextPathToCartService + '/api/v1/cart',
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
        $http.delete(contextPathToCartService + '/api/v1/cart')
            .then(function (response) {
                $scope.loadProductsCart();
        });
    }

    //оформить заказ
    $scope.createOrder = function () {
        $http.post(contextPath + '/api/v1/orders')
            .then(function (response) {
                alert('Заказ оформлен');
        });
    }


    $scope.loadUsers();
    $scope.loadProducts();
    $scope.loadProductsCart();
});