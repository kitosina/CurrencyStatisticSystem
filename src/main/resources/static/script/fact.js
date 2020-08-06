var app = angular.module("FACTS",[]);

app.controller("FACT_CONTROLLER",function($scope, $http){

    $scope.nameCurrency = "";
    $scope.listFact = [];

    $scope.selectCurrency = function () {
        console.log($scope.nameCurrency);
        if ($scope.nameCurrency!=="") {
            var url = "/fact/" + $scope.nameCurrency;
            $http({
                url: url,
                method: "GET"
            }).then(function (response) {
                $scope.listFact = response.data.listFact;
            })

        } else alert("Please input currency!");
    }

});