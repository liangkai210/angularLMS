libraryApp.controller("loanController", function ($scope, $http, $rootScope, $window, $cookieStore, commonService, Pagination, toaster) {
    var span = "<span class='caret'></span>";
    var cardNo = $cookieStore.get("cardNo");
    if (cardNo == undefined) {
        $window.location.href = "#/login";
        toaster.pop('error', "Please Login First", '', 3000, 'trustedHtml');
    }
    $scope.fillTables = function () {
        commonService.getData("http://localhost:8080/possLoan/" + $scope.selectedBranch.branchId + "/" + cardNo).then(function (data) {
            $scope.possBooks = data;
            $scope.pagination = Pagination.getNew(5);
            $scope.pagination.numPages = Math.ceil($scope.possBooks.length / $scope.pagination.perPage);
        });

        commonService.getData("http://localhost:8080/loansFromBorInBranch/" + $scope.selectedBranch.branchId + "/" + cardNo).then(function (data) {
            console.log(data);
            $scope.loans = data;
            $scope.loanPagination = Pagination.getNew(5);
            $scope.loanPagination.numPages = Math.ceil($scope.loans.length / $scope.loanPagination.perPage);
        });
    };

    $scope.searchLoanBook = function () {
        commonService.getData("http://localhost:8080/loansFromBorInBranch/" + $scope.selectedBranch.branchId + "/" + cardNo + "?searchString=" + $scope.searchString1).then(function (data) {
            $scope.loans = data;
            $scope.loanPagination = Pagination.getNew(5);
            $scope.loanPagination.numPages = Math.ceil($scope.loans.length / $scope.loanPagination.perPage);
        })
    };
    $scope.searchPossBook = function () {
        commonService.getData("http://localhost:8080/possLoan/" + $scope.selectedBranch.branchId + "/" + cardNo + "?searchString=" + $scope.searchString).then(function (data) {
            $scope.possBooks = data;
            $scope.pagination = Pagination.getNew(5);
            $scope.pagination.numPages = Math.ceil($scope.possBooks.length / $scope.pagination.perPage);
        })
    };


    $scope.fillBranches = function () {
        commonService.getData("http://localhost:8080/searchBranches").then(function (data) {
            $scope.branches = data;
        })

    };

    $scope.fillBranches();

    $scope.selectBranch = function (branch) {
        $scope.selectedBranch = branch;
        $('#branchButton').html(branch.branchName + span);
        $scope.fillTables();
    };

    $scope.checkOutBook = function (bookId) {
        var dateOut = new Date();
        var dueDate = new Date(dateOut.getTime() + 7 * 24 * 3600 * 1000);
        $scope.loan = {
            book: {
                bookId: bookId
            },
            borrower: {
                cardNo: cardNo
            },
            branch: {
                branchId: $scope.selectedBranch.branchId
            },
            dateOut: dateOut,
            dueDate: dueDate
        };
        commonService.postData("http://localhost:8080/loanBook", $scope.loan).then(function (data) {
            // $scope.possBooks = data;
            $scope.fillTables();
            toaster.pop('success', "Borrow Book Success", '', 3000, 'trustedHtml');
        });
        // $scope.showBookLoanList();
    };
    $scope.returnBook = function (loan) {
        commonService.postData("http://localhost:8080/returnBook", loan).then(function (data) {
            // $scope.showBookLoanList();
            $scope.fillTables();
            toaster.pop('success', "Return Book Success", '', 3000, 'trustedHtml');
        })
    };

    $scope.logOff = function () {
        commonService.getData("http://localhost:8080/logOff").then(function (data) {
            $cookieStore.remove("cardNo");
            toaster.pop('warning', "Bye! Have a good day!", '', 3000, 'trustedHtml');
            $window.location.href = "#/login"
        })
    }
    $scope.showBookLoanList = function () {
        $scope.fillTables();
    }
});