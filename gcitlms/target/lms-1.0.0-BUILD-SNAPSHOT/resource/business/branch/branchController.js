libraryApp.controller("branchCtrl", function ($scope, $http, commonService, Pagination, toaster) {
    commonService.getData("http://localhost:8080/searchBranches").then(function (data) {
        $scope.branches = data;
        $scope.pagination = Pagination.getNew(8);
        $scope.pagination.numPages = Math.ceil($scope.branches.length / $scope.pagination.perPage);
    });
    $scope.searchBranch = function () {
        commonService.getData("http://localhost:8080/searchBranches?searchString=" + $scope.searchString).then(function (data) {
            $scope.branches = data;
            $scope.pagination = Pagination.getNew(8);
            $scope.pagination.numPages = Math.ceil($scope.branches.length / $scope.pagination.perPage);
        })
    };
    $scope.addNewBranch = function () {
        commonService.postData("http://localhost:8080/addBranch", $scope.branch).then(function (data) {
            $scope.editBranchModal = false;
            toaster.pop('success', "Add Branch Success", '', 5000, 'trustedHtml');
            $scope.branches = data;
        })
    };
    $scope.showAvaiBookCopyModal = function (branch) {
        $scope.branch = branch;
        commonService.getData("http://localhost:8080/getAvaiBookCopy/" + $scope.branch.branchId).then(function (data) {
            console.log(data);
            $scope.avaiBookCopies = data;
            $scope.avaiBookCopyModal = true;
            $scope.paginationInNewCopy = Pagination.getNew(8);
            $scope.paginationInNewCopy.numPages = Math.ceil($scope.avaiBookCopies.length / $scope.paginationInNewCopy.perPage);
        })
    };
    $scope.addNewCopy = function (bookId) {
        $scope.bookCopy = {
            book: {
                bookId: bookId
            },
            branch: {
                branchId: $scope.branch.branchId
            },
            noOfCopies: 0
        };
        commonService.postData("http://localhost:8080/addBookCopy", $scope.bookCopy).then(function (data) {
            $scope.avaiBookCopies = data;
            toaster.pop('success', "Add New Copy Success", '', 5000, 'trustedHtml');
            $scope.paginationInNewCopy = Pagination.getNew(8);
            $scope.paginationInNewCopy.numPages = Math.ceil($scope.avaiBookCopies.length / $scope.paginationInNewCopy.perPage);
        })
    };
    $scope.updateBranch = function () {
        commonService.putData("http://localhost:8080/updateBranch", $scope.branch).then(function (data) {
            $scope.editBranchModal = false;
            toaster.pop('success', "Update Branch Success", '', 5000, 'trustedHtml');
            $scope.branches = data;
        })
    };

    $scope.deleteBranch = function (branchId) {
        if (confirm("Are you sure to delete?")) {
            commonService.deleteData("http://localhost:8080/deleteBranch/" + branchId).then(function (data) {
                $scope.branches = data;
                toaster.pop('success', "Delete Branch Success", '', 5000, 'trustedHtml');
            })
        } else {
            return false;
        }
    };

    $scope.showEditBranchModal = function (branchId) {
        commonService.getData("http://localhost:8080/getSingleBranch/"+ branchId).then(function (data) {
            $scope.branch = data;
            $scope.editBranchModal = true;
        })
    };
    $scope.showEditBookCopyModal = function (branch) {
        $scope.branch = branch;
        commonService.getData("http://localhost:8080/getBookCopiesInBranch/" + $scope.branch.branchId).then(function (data) {
            $scope.booksInBranch = data;
            $scope.selectedBranchId = $scope.branch.branchId;
            $scope.paginationInBookCopy = Pagination.getNew(6);
            $scope.paginationInBookCopy.numPages = Math.ceil($scope.booksInBranch.length / $scope.paginationInBookCopy.perPage);
            $scope.editBookCopyModal = true;
        })
    };
    $scope.searchBookCopy = function () {
        commonService.getData("http://localhost:8080/getBookCopiesInBranch/" + $scope.selectedBranchId + "?searchString=" + $scope.searchBookCopies).then(function (data) {
            $scope.booksInBranch = data;
            $scope.paginationInBookCopy = Pagination.getNew(6);
            $scope.paginationInBookCopy.numPages = Math.ceil($scope.booksInBranch.length / $scope.paginationInBookCopy.perPage);
        })
    };

    $scope.editBookCopy = function (bookCopy) {
        commonService.postData("http://localhost:8080/updateBookCopy", bookCopy).then(function (data) {
            $scope.booksInBranch = data;
            toaster.pop('success', "Update Book Copy Success", '', 3000, 'trustedHtml');
        })
    };
    $scope.closeBookCopyModal = function () {
        $scope.editBookCopyModal = false;
    };
    $scope.closeBranchModal = function () {
        $scope.editBranchModal = false;
    };
    $scope.closeAddCopyModal = function () {
        $scope.avaiBookCopyModal = false;
    }

});
