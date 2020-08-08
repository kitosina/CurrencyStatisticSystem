var app = angular.module("CONVERTER",[]);

app.controller("CONVERTER_CONTROLLER",function($scope, $http){

    $scope.nameCurrency = "";
    $scope.listFact = [];
    $scope.url = "/fact/";

    $scope.selectCurrency = function () {
        console.log($scope.nameCurrency);
        if ($scope.nameCurrency!=="") {
            var urlRequest = $scope.url + $scope.nameCurrency;
            $http({
                url: urlRequest,
                method: "GET"
            }).then(function (response) {
                $scope.listFact = response.data.listFact;
            })

        } else alert("Please input currency!");
    }

});