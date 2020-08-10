var app = angular.module("CONVERTER",[]);

app.controller("CONVERTER_CONTROLLER",function($scope, $http){

    $scope.coupleCurrency = "";
    $scope.url = "/currency/converter/";
    $scope.inputValue = null;
    $scope.outputValue = null;
    $scope.nameUserForm = "";


    $scope.convertCurrency = function () {
        console.log($scope.coupleCurrency);
        console.log($scope.inputValue);
        if (($scope.coupleCurrency !== "") && ($scope.inputValue !== null)){
            urlRequest = $scope.url + $scope.coupleCurrency;
            $http({
                url: urlRequest,
                method: "GET"
            }).then(function (response) {
                $scope.nameUserForm = $scope.coupleCurrency.split("/");
                $scope.outputValue = $scope.inputValue * response.data + " " + $scope.nameUserForm[1];
            })
        } else alert("Please input form");





        // if ($scope.nameCurrency!=="") {
        //     var urlRequest = $scope.url + $scope.nameCurrency;
        //     $http({
        //         url: urlRequest,
        //         method: "GET"
        //     }).then(function (response) {
        //         $scope.listFact = response.data.listFact;
        //     })
        //
        // } else alert("Please input currency!");
    }

});

// function checkCurrencyEntry(nameCurrency1, nameCurrency2) {
//     if (nameCurrency1 =)
// }