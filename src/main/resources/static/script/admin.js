var app = angular.module("ADMIN",[]);

app.controller("ADMIN_CONTROLLER",function($scope, $http){

    $scope.username = "";
    $scope.url = "/administration"
    var flagClickOfClearDb = true;

    $scope.usersObject = [{
        id: null ,
        username: "",
        password: "",
        email: "",
        roles: ""
    }]

    $scope.codeClearDb = "";

    $scope.deleteUser = function () {
        var urlDelete = $scope.url + "/delete/by/";
        if ($scope.username !== "") {
            var urlRequest = urlDelete + $scope.username;
            $http({
                url: urlRequest,
                method: "DELETE"
            }).then(function (response) {
                $scope.getAllUser();
                console.log("OK!");
            })
        } else alert("Please input username")
    }

    $scope.getAllUser = function () {
        var urlRequest = $scope.url;
            $http({
                url: urlRequest,
                method: "GET"
            }).then(function (response) {
                $scope.usersObject = response.data;
            })
    }

    clear = function () {
        flagClickOfClearDb = false;
        if($scope.codeClearDb === "Yes") {
                $http({
                    url: '/administration/clear',
                    method: 'DELETE'
                }).then(function (response) {
                    flagClickOfClearDb = true;
                    console.log("Delete")
                })
            } else alert("Not Operation")
    }

    $scope.clearDb = function () {
        if(flagClickOfClearDb) clear();
    }

});