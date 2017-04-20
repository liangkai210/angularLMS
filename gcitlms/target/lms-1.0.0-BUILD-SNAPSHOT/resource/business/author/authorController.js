libraryApp.controller("authorCtrl", function ($scope, $http, commonService, Pagination, toaster) {
    commonService.getData("http://localhost:8080/searchAuthors").then(function (data) {
        $scope.authors = data;
        $scope.pagination = Pagination.getNew(8);
        $scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
    });
    $scope.searchAuthor = function () {
        commonService.getData("http://localhost:8080/searchAuthors?searchString=" + $scope.searchString).then(function (data) {
            $scope.authors = data;
            $scope.pagination = Pagination.getNew(8);
            $scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
        })
    };
    $scope.addnewauthor = function () {
        commonService.postData("http://localhost:8080/addAuthor", $scope.author).then(function (data) {
            $scope.editauthormodal = false;
            toaster.pop('success', "Add Author Success", '', 5000, 'trustedHtml');
            $scope.authors = data;
        })
    };

    $scope.updateauthor = function () {
        commonService.putData("http://localhost:8080/updateAuthor", $scope.author).then(function (data) {
            $scope.editauthormodal = false;
            toaster.pop('success', "Update Author Success", '', 5000, 'trustedHtml');
            $scope.authors = data;
        })
    };

    $scope.deleteauthor = function (authorId) {
        if (confirm("Are you sure to delete?")) {
            commonService.deleteData("http://localhost:8080/deleteAuthor/" + authorId).then(function (data) {
                $scope.authors = data;
                toaster.pop('success', "Delete Author Success", '', 5000, 'trustedHtml');
            })
        } else {
            return false;
        }
    };
    commonService.getData("http://localhost:8080/getBooks").then(function (data) {
        $scope.allbooks = data.books;
    });

    $scope.showEditAuthorModal = function (authorId) {
        commonService.getData("http://localhost:8080/getSingleAuthor/" + authorId).then(function (data) {
            $scope.author = data;
            $scope.editauthormodal = true;
        })
    };
    $scope.closeauthormodal = function () {
        $scope.editauthormodal = false;
    };

});
