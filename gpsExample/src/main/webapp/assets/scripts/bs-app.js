/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var bsApp = angular.module('bsApp', ['ngRoute', 'bsControllers', 'bsServices', 'staticSelect', 'angular.filter', 'dragularModule']);

bsApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                // --- Dashboard ---
                when('/dashboard', {
                    templateUrl: 'assets/partials/dashboard.html',
                    controller: 'DashboardCtrl'
                }).
                // --- Actions ---
                when('/actions', {
                    templateUrl: 'assets/partials/actions/list.html',
                    controller: 'ActionListCtrl'
                }).
                when('/actions/add', {
                    templateUrl: 'assets/partials/actions/create.html',
                    controller: 'ActionCreateCtrl'
                }).
                when('/actions/:actionId', {
                    templateUrl: 'assets/partials/actions/read.html',
                    controller: 'ActionReadCtrl'
                }).
                when('/actions/:actionId/edit', {
                    templateUrl: 'assets/partials/actions/update.html',
                    controller: 'ActionUpdateCtrl'
                }).
                when('/actions/:actionId/remove', {
                    templateUrl: 'assets/partials/actions/delete.html',
                    controller: 'ActionDeleteCtrl'
                }).
                // --- Beacons (by Wilco) ---
                when('/beacons', {
                    templateUrl: 'assets/partials/beacons/list.html',
                    controller: 'BeaconListCtrl'
                }).
                when('/beacons/add', {
                    templateUrl: 'assets/partials/beacons/create.html',
                    controller: 'BeaconCreateCtrl'
                }).
                when('/beacons/:beaconId', {
                    templateUrl: 'assets/partials/beacons/read.html',
                    controller: 'BeaconReadCtrl'
                }).
                when('/beacons/:beaconId/edit', {
                    templateUrl: 'assets/partials/beacons/update.html',
                    controller: 'BeaconUpdateCtrl'
                }).
                when('/beacons/:beaconId/remove', {
                    templateUrl: 'assets/partials/beacons/delete.html',
                    controller: 'BeaconDeleteCtrl'
                }).
                // --- (User) Groups ---
                when('/groups', {
                    templateUrl: 'assets/partials/groups/list.html',
                    controller: 'GroupListCtrl'
                }).
                when('/groups/add', {
                    templateUrl: 'assets/partials/groups/create.html',
                    controller: 'GroupCreateCtrl'
                }).
                when('/groups/:groupId', {
                    templateUrl: 'assets/partials/groups/read.html',
                    controller: 'GroupReadCtrl'
                }).
                when('/groups/:groupId/edit', {
                    templateUrl: 'assets/partials/groups/update.html',
                    controller: 'GroupUpdateCtrl'
                }).
                when('/groups/:groupId/remove', {
                    templateUrl: 'assets/partials/groups/delete.html',
                    controller: 'GroupDeleteCtrl'
                }).
                // --- Roles (by Wilco) ---
                when('/roles', {
                    templateUrl: 'assets/partials/roles/list.html',
                    controller: 'RoleListCtrl'
                }).
                when('/roles/add', {
                    templateUrl: 'assets/partials/roles/create.html',
                    controller: 'RoleCreateCtrl'
                }).
                when('/roles/:roleId', {
                    templateUrl: 'assets/partials/roles/read.html',
                    controller: 'RoleReadCtrl'
                }).
                when('/roles/:roleId/edit', {
                    templateUrl: 'assets/partials/roles/update.html',
                    controller: 'RoleUpdateCtrl'
                }).
                when('/roles/:roleId/remove', {
                    templateUrl: 'assets/partials/roles/delete.html',
                    controller: 'RoleDeleteCtrl'
                }).
                // --- Rules ---
                when('/rules', {
                    templateUrl: 'assets/partials/rules/list.html',
                    controller: 'RuleListCtrl'
                }).
                when('/rules/add', {
                    templateUrl: 'assets/partials/rules/create.html',
                    controller: 'RuleCreateCtrl'
                }).
                when('/rules/:ruleId', {
                    templateUrl: 'assets/partials/rules/read.html',
                    controller: 'RuleReadCtrl'
                }).
                when('/rules/:ruleId/edit', {
                    templateUrl: 'assets/partials/rules/update.html',
                    controller: 'RuleUpdateCtrl'
                }).
                when('/rules/:ruleId/remove', {
                    templateUrl: 'assets/partials/rules/delete.html',
                    controller: 'RuleDeleteCtrl'
                }).
                // --- Users ---
                when('/users', {
                    templateUrl: 'assets/partials/users/list.html',
                    controller: 'UserListCtrl'
                }).
                when('/users/add', {
                    templateUrl: 'assets/partials/users/create.html',
                    controller: 'UserCreateCtrl'
                }).
                when('/users/:userId', {
                    templateUrl: 'assets/partials/users/read.html',
                    controller: 'UserReadCtrl'
                }).
                when('/users/:userId/edit', {
                    templateUrl: 'assets/partials/users/update.html',
                    controller: 'UserUpdateCtrl'
                }).
                when('/users/:userId/remove', {
                    templateUrl: 'assets/partials/users/delete.html',
                    controller: 'UserDeleteCtrl'
                }).
                // --- Settings (by Wilco) ---
                when('/settings', {
                    templateUrl: 'assets/partials/settings.html',
                    controller: 'SettingsCtrl'
                }).
                otherwise({
                    redirectTo: '/dashboard'
                });
    }]);
