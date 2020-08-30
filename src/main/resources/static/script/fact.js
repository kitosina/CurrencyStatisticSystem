var app = angular.module("FACTS",[]);

app.controller("FACT_CONTROLLER",function($scope, $https, $filter){

    $scope.nameCurrency = "";
    $scope.listFact = [];
    $scope.url = "/fact";

    $scope.selectAllFact = async function () {
        var urlRequest = $scope.url;
        $https({
            url: urlRequest,
            method: "GET"
        }).then(function (response) {
            if (response.data.length === 0) {
                loadFacts().then(function () {
                    selectFactForCurrency()
                })
            } else selectFactForCurrency()
        })
    }

    selectFactForCurrency = async function () {
        if ($scope.nameCurrency!=="") {
            var urlRequest = $scope.url + "/" + $scope.nameCurrency;
            $http({
                url: urlRequest,
                method: "GET"
            }).then(function (response) {
                $scope.listFact = response.data
            })

        } else alert("Please input currency!");
    }

    loadFacts = async function () {
            alert("loading facts in DB")
            var urlRequest = $scope.url + "/" + "load";
            $https({
                url: urlRequest,
                method: "GET",
            }).then(function (response) {
                console.log("OK");
            })
    }


});