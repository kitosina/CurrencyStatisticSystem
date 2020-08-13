var app = angular.module("REGISTRATION",[]);

app.controller("REGISTRATION_CONTROLLER",function($scope, $http){

    $scope.newUser = {
        username: "",
        password: "",
        email: "",
    }


    $scope.createNewUser = function () {
        if(validForm()) {
            $http({
                url: '/user/registration',
                method: 'POST',
                data: {
                    username: $scope.newUser.username,
                    password: $scope.newUser.password,
                    email: $scope.newUser.email
                }
            }).then(function (response) {
                if (response.data === 1) {
                    alert("Username is taken")
                }
                if (response.data === 2) {
                    alert("Email is taken")
                }
                if (response.data === 3) {
                    alert("Username and Email is taken")
                }
                if (response.data === 0) {
                    alert("Successful registration")
                }
            });
        }
    }

    validForm = function () {
        alertLabel = "";
        var flag = true;
        if ($scope.newUser.username === "") {
            alertLabel += "1) please input username \n"
            flag = false;
        }
        if ($scope.newUser.password === "") {
            alertLabel += "2) please input password \n"
            flag = false;
        }
        if ($scope.newUser.email === "") {
            alertLabel += "3) please input email"
            flag = false;
        }
        alert(alertLabel);
        return flag;
    }


});