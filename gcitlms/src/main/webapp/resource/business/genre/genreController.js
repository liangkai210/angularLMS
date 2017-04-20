libraryApp.controller("genreCtrl", function ($scope, $http, commonService, Pagination, toaster) {
    commonService.getData("http://localhost:8080/searchGenres").then(function (data) {
        $scope.genres = data;
        $scope.pagination = Pagination.getNew(8);
        $scope.pagination.numPages = Math.ceil($scope.genres.length / $scope.pagination.perPage);
    });
    $scope.searchGenre = function () {
        commonService.getData("http://localhost:8080/searchGenres?searchString=" + $scope.searchString).then(function (data) {
            $scope.genres = data;
            $scope.pagination = Pagination.getNew(8);
            $scope.pagination.numPages = Math.ceil($scope.genres.length / $scope.pagination.perPage);
        })
    };
    $scope.addnewgenre = function () {
        commonService.postData("http://localhost:8080/addGenre", $scope.genre).then(function (data) {
            $scope.editgenremodal = false;
            toaster.pop('success', "Add Genre Success", '', 5000, 'trustedHtml');
            $scope.genres = data;
        })
    };

    $scope.updategenre = function () {
        commonService.putData("http://localhost:8080/updateGenre", $scope.genre).then(function (data) {
            $scope.editgenremodal = false;
            toaster.pop('success', "Update Genre Success", '', 5000, 'trustedHtml');
            $scope.genres = data;
        })
    };

    $scope.deletegenre = function (genre_id) {
        if (confirm("Are you sure to delete?")) {
            commonService.deleteData("http://localhost:8080/deleteGenre/" + genre_id).then(function (data) {
                $scope.genres = data;
                toaster.pop('success', "Delete Genre Success", '', 5000, 'trustedHtml');
            })
        } else {
            return false;
        }
    };
    commonService.getData("http://localhost:8080/getBooks").then(function (data) {
        $scope.allbooks = data.books;
    });

    $scope.showEditGenreModal = function (genre_id) {
        commonService.getData("http://localhost:8080/getSingleGenre/" + genre_id).then(function (data) {
            $scope.genre = data;
            $scope.editgenremodal = true;
        })
    };
    $scope.closegenremodal = function () {
        $scope.editgenremodal = false;
    };

});
