var libraryApp = angular.module("libraryApp", ["ngRoute", "ui.bootstrap.modal", "toaster", "ngAnimate", "simplePagination", "ngCookies"]);

libraryApp.directive("bookCountDirective", function () {
    return {
        templateUrl: "/book/count.html"
    }
});
libraryApp.directive("authorCountDirective", function () {
    return {
        templateUrl: "/author/count.html"
    }
});
libraryApp.directive("genreCountDirective", function () {
    return {
        templateUrl: "/genre/count.html"
    }
});
libraryApp.directive("publisherCountDirective", function () {
    return {
        templateUrl: "/publisher/count.html"
    }
});
libraryApp.directive("branchCountDirective", function () {
    return {
        templateUrl: "/branch/count.html"
    }
});
libraryApp.directive("borrowerCountDirective", function () {
    return {
        templateUrl: "/borrower/count.html"
    }
});
libraryApp.directive("ensureUnique", ["$http", function ($http) {
    return {
        require: "ngModel",
        link: function (scope, ele, attrs, c) {
            scope.$watch(attrs.ngModel, function () {
                $http({
                    method: "GET",
                    url: "http://localhost:8080/validateBorrower/" + scope.borrower.username,
                }).success(function (data, status, headers, cfg) {
                    if (data === "true") {
                        c.$setValidity('unique', false);
                    } else {
                        c.$setValidity('unique', true);
                    }
                }).error(function (data, status, headers, cfg) {
                    c.$setValidity('unique', false);
                });
            });
        }
    }
}]);
// libraryApp.directive('ngUnique', ['$http', function ($http) {
//     return {
//         require: 'ngModel',
//         link: function (scope, elem, attrs, ctrl) {
//             elem.on('blur', function (evt) {
//                 scope.$apply(function () {
//                     $http({
//                         method: 'GET',
//                         url: 'http://localhost:8080/validateBorrower/' + elem.val(),
//                         data: {
//                             username: elem.val(),
//                             dbField: attrs.ngUnique
//                         }
//                     }).success(function (data, status, headers, config) {
//                         if (data) {
//                             ctrl.$setValidity('unique', data.status);
//                         } else {
//                             ctrl.$setValidity('unique', false);
//                         }
//                     })
//                 });
//             })
//         }
//     }
// }]);


libraryApp.directive("adminMenu", function () {
    return {
        templateUrl: "/user/admin.html"
    }
});




