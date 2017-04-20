libraryApp.controller("publisherCtrl", function ($scope, $http, commonService, Pagination, toaster) {
    commonService.getData("http://localhost:8080/searchPubs").then(function (data) {
        $scope.publishers = data;
        $scope.pagination = Pagination.getNew(8);
        $scope.pagination.numPages = Math.ceil($scope.publishers.length / $scope.pagination.perPage);
    });
    $scope.searchPublisher = function () {
        commonService.getData("http://localhost:8080/searchPubs?searchString=" + $scope.searchString).then(function (data) {
            $scope.publishers = data;
            $scope.pagination = Pagination.getNew(8);
            $scope.pagination.numPages = Math.ceil($scope.publishers.length / $scope.pagination.perPage);
        })
    };
    $scope.addNewPublisher = function () {
        commonService.postData("http://localhost:8080/addPub", $scope.publisher).then(function (data) {
            $scope.editPublisherModal = false;
            toaster.pop('success', "Add Publisher Success", '', 5000, 'trustedHtml');
            $scope.publishers = data;
        })
    };

    $scope.updatePublisher = function () {
        commonService.putData("http://localhost:8080/updatePub", $scope.publisher).then(function (data) {
            $scope.editPublisherModal = false;
            toaster.pop('success', "Update Publisher Success", '', 5000, 'trustedHtml');
            $scope.publishers = data;
        })
    };

    $scope.deletePublisher = function (publisherId) {
        if (confirm("Are you sure to delete?")) {
            commonService.deleteData("http://localhost:8080/deletePub/" + publisherId).then(function (data) {
                $scope.publishers = data;
                toaster.pop('success', "Delete Publisher Success", '', 5000, 'trustedHtml');
            })
        } else {
            return false;
        }
    };
    commonService.getData("http://localhost:8080/getBooks").then(function (data) {
        $scope.allbooks = data.books;
    });

    $scope.showEditPublisherModal = function (publisherId) {
        commonService.getData("http://localhost:8080/getSinglePub/" + publisherId).then(function (data) {
            $scope.publisher = data;
            $scope.editPublisherModal = true;
        })
    };
    $scope.closePublisherModal = function () {
        $scope.editPublisherModal = false;
    };

});
