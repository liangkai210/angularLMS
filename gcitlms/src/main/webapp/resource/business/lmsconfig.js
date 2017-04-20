libraryApp.config(["$routeProvider", function ($routeProvider) {
    return $routeProvider.when("/", {
        redirectTo: "/home"
    }).when("/home", {
        templateUrl: "welcome.html"
    }).when("/admin", {
        templateUrl: "/user/admin.html"
    }).when("/viewbooks", {
        templateUrl: "/book/books.html"
    }).when("/viewauthors", {
        templateUrl: "/author/authors.html"
    }).when("/viewgenres", {
        templateUrl: "/genre/genres.html"
    }).when("/viewpublishers", {
        templateUrl: "/publisher/publishers.html"
    }).when("/viewbranches", {
        templateUrl: "/branch/branches.html"
    }).when("/viewborrowers", {
        templateUrl: "/borrower/borrowers.html"
    }).when("/login", {
        templateUrl: "login.html"
    }).when("/borrowBook", {
        templateUrl: "/user/borrowBook.html"
    }).when("/librarian", {
        templateUrl: "/user/librarian.html"
    })
}]);

// libraryApp.config(function ($stateProvider, $urlRouterProvider) {
//     $urlRouterProvider.otherwise('/index');
//     $stateProvider
//         .state('index', {
//             url: "/index",
//             views: {
//                 '': {
//                     templateUrl: 'welcome.html'
//                 },
//             }
//         })
//     // .state('admin', {
//     //     url: '/admin',
//     //     views: {
//     //         'admin@menus': {
//     //             templateUrl: 'admin.html'
//     //         },
//     //         'content@content': {
//     //             templateUrl: '/book/books.html'
//     //         }
//     //     }
//     // })