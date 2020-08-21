var app = angular.module("ACTUAL_STATISTICS",[]);

app.controller("ACTUAL_CONTROLLER",function($scope, $http, $filter){

    $scope.coupleCurrency = ["USD/RUB", "EUR/RUB", "EUR/USD"];
    $scope.urlActualValue = "/currency/actual/";
    $scope.urlActualDate = "/currency/last_data/";
    $scope.oneClick = 0;

    $scope.outputValue = [];
    $scope.outputDate = [];

    $scope.date = new Date();
    $scope.date = $filter('date')($scope.date, 'yyyy-MM-dd');

    $scope.actualValue = function () {
        if ($scope.oneClick === 0) {
            dataFunction();
        }
        $scope.oneClick++;
    }

    dataFunction = function () {

        update1().then(function () {
            update2().then(function () {
                update3().then(function () {
                    gettingDateValue().then(function () {
                        gettingValue()
                    })
                })
            })
        });
    }

        gettingValue = async function () {
            var urlRequest1 = $scope.urlActualValue + $scope.coupleCurrency[0];
            $http({
                url: urlRequest1,
                method: "GET"
            }).then(function (response) {
                $scope.outputValue.push(response.data)
                var urlRequest2 = $scope.urlActualValue + $scope.coupleCurrency[1];
                $http({
                    url: urlRequest2,
                    method: "GET"
                }).then(function (response) {
                    $scope.outputValue.push(response.data)
                    var urlRequest3 = $scope.urlActualValue + $scope.coupleCurrency[2];
                    $http({
                        url: urlRequest3,
                        method: "GET"
                    }).then(function (response) {
                        $scope.outputValue.push(response.data)
                    })
                })
            })
        }

        gettingDateValue = async function () {
            var urlRequestDate1 = $scope.urlActualDate + $scope.coupleCurrency[0];
            $http({
                url: urlRequestDate1,
                method: "GET"
            }).then(function (response) {
                $scope.outputDate.push(response.data.slice(0, 10))
                var urlRequestDate2 = $scope.urlActualDate + $scope.coupleCurrency[1];
                $http({
                    url: urlRequestDate2,
                    method: "GET"
                }).then(function (response) {
                    $scope.outputDate.push(response.data.slice(0, 10))
                    var urlRequestDate3 = $scope.urlActualDate + $scope.coupleCurrency[2];
                    $http({
                        url: urlRequestDate3,
                        method: "GET"
                    }).then(function (response) {
                        $scope.outputDate.push(response.data.slice(0, 10))
                    })
                })
            })
        }

    update1 = async function () {
        return $http({
            url: '/currency/update',
            method: 'POST',
            data: {
                dateEntryClient: $scope.date,
                base: "EUR",
                symbols: "USD"
            }
        })
    }

    update2 = async function () {
        return $http({
            url: '/currency/update',
            method: 'POST',
            data: {
                dateEntryClient: $scope.date,
                base: "USD",
                symbols: "RUB"
            }
        })
    }

    update3 = async function () {
        return $http({
            url: '/currency/update',
            method: 'POST',
            data: {
                dateEntryClient: $scope.date,
                base: "EUR",
                symbols: "RUB"
            }
        })
    }


});