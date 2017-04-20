libraryApp.controller("borrowerCtrl", function ($scope, $http, commonService, Pagination, toaster) {
    commonService.getData("http://localhost:8080/searchBorrowers").then(function (data) {
        $scope.borrowers = data;
        console.log(data);
        $scope.pagination = Pagination.getNew(8);
        $scope.pagination.numPages = Math.ceil($scope.borrowers.length / $scope.pagination.perPage);
    });
    $scope.searchBorrower = function () {
        commonService.getData("http://localhost:8080/searchBorrowers?searchString=" + $scope.searchString).then(function (data) {
            $scope.borrowers = data;
            $scope.pagination = Pagination.getNew(8);
            $scope.pagination.numPages = Math.ceil($scope.borrowers.length / $scope.pagination.perPage);
        })
    };
    $scope.addNewBorrower = function () {
        commonService.postData("http://localhost:8080/addBorrower", $scope.borrower).then(function (data) {
            $scope.editBorrowerModal = false;
            $scope.borrowers = data;
        })
    };

    $scope.updateBorrower = function () {
        commonService.putData("http://localhost:8080/updateBorrower", $scope.borrower).then(function (data) {
            $scope.editBorrowerModal = false;
            toaster.pop('success', "Update Borrower Success", '', 5000, 'trustedHtml');
            $scope.borrowers = data;
        })
    };

    $scope.deleteBorrower = function (cardNo) {
        if (confirm("Are you sure to delete?")) {
            commonService.postData("http://localhost:8080/deleteBorrower/" + cardNo).then(function (data) {
                $scope.borrowers = data;
                toaster.pop('success', "Delete Borrower Success", '', 5000, 'trustedHtml');
            })
        } else {
            return false;
        }
    };

    $scope.showEditBorrowerModal = function (cardNo) {
        commonService.getData("http://localhost:8080/getSingleBorrower/" + cardNo).then(function (data) {
            $scope.borrower = data;
            $scope.editBorrowerModal = true;
        })
    };

    $scope.showEditBookLoanModal = function (cardNo) {
        commonService.getData("http://localhost:8080/loansFromBorrower/" + cardNo).then(function (data) {
            $scope.loansFromBorrower = data;
            $scope.selectedCardNo = cardNo;
            $scope.paginationInBookLoan = Pagination.getNew(6);
            $scope.paginationInBookLoan.numPages = Math.ceil($scope.loansFromBorrower.length / $scope.paginationInBookLoan.perPage);
            $scope.editBookLoanModal = true;
        })
    };
    $scope.searchBookLoan = function () {
        commonService.getData("http://localhost:8080/loansFromBorrower/" + $scope.selectedCardNo + "?searchString=" + $scope.searchBookLoans).then(function (data) {
            $scope.loansFromBorrower = data;
            $scope.paginationInBookLoan = Pagination.getNew(6);
            $scope.paginationInBookLoan.numPages = Math.ceil($scope.loansFromBorrower.length / $scope.paginationInBookLoan.perPage);
        })
    };
    $scope.editBookLoan = function (bookLoan) {
        commonService.postData("http://localhost:8080/updateBookLoan/", bookLoan).then(function (data) {
            $scope.loansFromBorrower = data;
            toaster.pop('success', "Update Loan Success", '', 5000, 'trustedHtml');
        })
    };

    $scope.closeBorrowerModal = function () {
        $scope.editBorrowerModal = false;
    };
    $scope.closeBookLoanModal = function () {
        $scope.editBookLoanModal = false;
    };

});
