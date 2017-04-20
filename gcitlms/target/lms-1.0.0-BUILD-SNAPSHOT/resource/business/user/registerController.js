libraryApp.controller("registerCtrl", function ($scope, $http, $rootScope, $window, $cookieStore, commonService, toaster) {
    $scope.submitForm = function () {
        commonService.postData("http://localhost:8080/addBorrower", $scope.borrower).then(function (data) {
            $scope.registerBorrowerModal = false;
        })
    };

    $scope.login = function () {
        $scope.username = $('#username').val();

        commonService.postData("http://localhost:8080/loginBorrower/" + $scope.username + "/" + $scope.password).then(function (data) {
            if (data == -1) {
                toaster.pop('error', "Invalide Login Information", '', 5000, 'trustedHtml');
            } else {
                $cookieStore.put("cardNo", data);
                $window.location.href = "#/borrowBook";
                toaster.pop('success', "Welcome", '', 5000, 'trustedHtml');
            }
        })
    };

    $scope.showRegisterModal = function () {
        $scope.registerBorrowerModal = true;
    };

    $scope.closeRegisterModal = function () {
        $scope.registerBorrowerModal = false;
    };

    // $scope.checkAvailability = function () {
    //     commonService.getData("http://localhost:8080/validateBorrower/" + $scope.borrower.username).then(function (data) {
    //         console.log(data);
    //         if (data) {
    //             $scope.availability = true;
    //         } else {
    //             $scope.availability = false;
    //         }
    //     })
    // }
});