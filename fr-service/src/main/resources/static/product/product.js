angular.module('market').controller('productController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8193/';

    //добавить новый продукт
    $scope.addProduct = function () {
        $http.post(contextPath + 'core/api/v1/products', $scope.newProduct)
             .then(function successCallback(response) {
                 alert("Продукт успешно добавлен");
             }, function errorCallback(response) {
                 if (response.status == 400) {
                     alert(response.data.errorMessage);
                 }
             });
    }

    //запрос списка категорий
    $scope.getProductCategories = function () {
        $http.get(contextPath + 'core/api/v1/products/category')
            .then(function (response) {
                $scope.categoryList = response.data;
        });
    }

    $scope.getProductCategories();
});