angular.module('market').controller('productController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //Добавить новый продукт
    $scope.addProduct = function () {
        $http.post(contextPath + 'core/api/v1/products', $scope.newProduct)
             .then(function successCallback(response) {
                 alert("Продукт успешно добавлен");
                 $scope.newProduct = null;
             }, function errorCallback(response) {
                 if (response.status == 400) {
                     alert(response.data.errorMessage);
                 }
             });
    }

    //Обновить продукт
    $scope.updateProductData = function () {
        $scope.updateProduct.title = $scope.oldTitle + ',' + $scope.newTitle;

        $http.put(contextPath + 'core/api/v1/products/update', $scope.updateProduct)
            .then(function (response) {
                alert("Продукт успешно обновлен");
                $scope.updateProduct = null;
                $scope.oldTitle = null;
                $scope.newTitle = null;
                $scope.getProductList();
            });
    }

    //запрос списка категорий
    $scope.getProductCategories = function () {
        $http.get(contextPath + 'core/api/v1/products/category')
            .then(function (response) {
                $scope.categoryList = response.data;
        });
    }

    //Запрос всего списка продуктов
    $scope.getProductList = function () {
        $http.get(contextPath + 'core/api/v1/products/list')
            .then(function (response) {
                $scope.productList = response.data;
        });
    }

    $scope.getProductList();
    $scope.getProductCategories();
});