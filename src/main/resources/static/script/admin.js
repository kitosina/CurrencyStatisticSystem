var app = angular.module("ADMIN",[]);

app.controller("ADMIN_CONTROLLER",function($scope, $http){

    $scope.username = "";
    $scope.url = "/administration"

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
            console.log(urlRequest);
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
                console.log(response);
                $scope.usersObject = response.data;
                console.log($scope.usersObject)
            })
    }

    $scope.clearDb = function () {
        if($scope.codeClearDb === "Yes") {
            $scope.delete = async function () {
                $http({
                    url: '/administration/clear',
                    method: 'DELETE',
                }).then(function (response) {
                    console.log("Delete")
                })
            }
        } else alert("Not Operation")
    }

});