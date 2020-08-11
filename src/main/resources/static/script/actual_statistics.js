var app = angular.module("ACTUAL_STATISTICS",[]);

app.controller("ACTUAL_CONTROLLER",function($scope, $http){

    $scope.coupleCurrency = ["USD/RUB", "EUR/RUB", "EUR/USD"];
    $scope.urlActualValue = "/currency/actual/";
    $scope.urlActualDate = "/currency/last_data/";
    $scope.oneClick = 0;

    $scope.outputValue = [];
    $scope.outputDate = "";


    dataFunction = function () {
        $scope.outputValue = [];
        $scope.outputDate = "";
        var flag = true;

        if(flag) {
            flag = false;
            urlRequest1 = $scope.urlActualValue + $scope.coupleCurrency[0];
            $http({
                url: urlRequest1,
                method: "GET"
            }).then(function (response) {
                console.log(response.data);
                $scope.outputValue.push(response.data);
                flag = true;
            })
        }

        if(flag) {
            flag = false;
            urlRequest2 = $scope.urlActualValue + $scope.coupleCurrency[1];
            $http({
                url: urlRequest2,
                method: "GET"
            }).then(function (response) {
                console.log(response);
                $scope.outputValue.push(response.data);
                flag = true;
            })
        }

        if(flag) {
            flag = false;
            urlRequest3 = $scope.urlActualValue + $scope.coupleCurrency[2];
            $http({
                url: urlRequest3,
                method: "GET"
            }).then(function (response) {
                console.log(response);
                $scope.outputValue.push(response.data);
                flag = true;
            })
        }



        urlRequestLastData = $scope.urlActualDate + $scope.coupleCurrency[0];
        $http({
            url: urlRequestLastData,
            method: "GET"
        }).then(function (response) {
            console.log(response);
            $scope.outputDate = (response.data.slice(0, 10));
        })
    }
        // console.log($scope.outputDate);

    $scope.actualValue = function () {
        if ($scope.oneClick === 0) {
            dataFunction();
        }
        $scope.oneClick++;
    }

});