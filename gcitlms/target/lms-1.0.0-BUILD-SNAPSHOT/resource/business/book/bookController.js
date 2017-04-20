libraryApp.controller("bookCtrl", function ($scope, $http, $window, $location, commonService, Pagination, toaster) {
    commonService.getData("http://localhost:8080/searchBooks").then(function (data) {
        $scope.books = data;
        $scope.pagination = Pagination.getNew(8);
        $scope.pagination.numPages = Math.ceil($scope.books.length / $scope.pagination.perPage);
    });
    $scope.searchBook = function () {
        commonService.getData("http://localhost:8080/searchBooks?searchString=" + $scope.searchString).then(function (data) {
            $scope.books = data;
            $scope.pagination = Pagination.getNew(8);
            $scope.pagination.numPages = Math.ceil($scope.books.length / $scope.pagination.perPage);
        })
    };
    $scope.addNewBook = function () {
        commonService.postData("http://localhost:8080/addBook", $scope.book).then(function (data) {
            $scope.editbookmodal = false;
            toaster.pop('success', "Add Book Success", '', 3000, 'trustedHtml');
            $scope.books = data;
        })
    };
    $scope.updateBook = function () {
        commonService.postData("http://localhost:8080/updateBook", $scope.book).then(function (data) {
            $scope.editbookmodal = false;
            toaster.pop('success', "Update Book Success", '', 3000, 'trustedHtml');
            $scope.books = data;
        })
    };
    $scope.deleteBook = function (bookId) {
        if (confirm("Are you sure to delete?")) {
            commonService.postData("http://localhost:8080/deleteBook/" + bookId).then(function (data) {
                $scope.books = data;
                toaster.pop('success', "Delete Book Success", '', 3000, 'trustedHtml');
            })
        } else {
            return false;
        }
    };
    commonService.getData("http://localhost:8080/getAllInfos").then(function (data) {
        $scope.allauthors = data.authors;
        $scope.allgenres = data.genres;
        $scope.allpublishers = data.publishers;
    });
    $scope.showEditBookModal = function (bookId) {
        commonService.getData("http://localhost:8080/getSingleBook/" + bookId).then(function (data) {
            $scope.book = data;
            $scope.editbookmodal = true;
        })
    };
    $scope.closebookmodal = function () {
        $scope.editbookmodal = false;
    };
});
