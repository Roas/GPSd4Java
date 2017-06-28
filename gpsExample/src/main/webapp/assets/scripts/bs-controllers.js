/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var bsControllers = angular.module('bsControllers', []);
// query()  = LIST
// save()   = CREATE
// get()    = READ
// update() = UPDATE
// remove() = DELETE

// --- Dashboard ---
bsControllers.controller('DashboardCtrl', ['$scope', 'Beacon',
    function ($scope, Beacon) {
        
    }]);
// --- Actions ---
bsControllers.controller('ActionListCtrl', ['$scope', 'Action',
    function ($scope, Action) {
        $scope.actions = Action.query();
    }]);
bsControllers.controller('ActionCreateCtrl', ['$scope', '$location', 'Action',
    function ($scope, $location, Action) {
        $scope.action = new Action();
        $scope.create = function () {
            Action.save($scope.action, function (data) {
                console.log(data);
                $location.path('/actions');
            });
        };
    }]);
bsControllers.controller('ActionReadCtrl', ['$scope', '$routeParams', 'Action',
    function ($scope, $routeParams, Action) {
        $scope.action = Action.get({
            id: $routeParams.actionId
        });
    }]);
bsControllers.controller('ActionUpdateCtrl', ['$scope', '$location', '$routeParams', 'Action',
    function ($scope, $location, $routeParams, Action) {
        $scope.action = Action.get({
            id: $routeParams.actionId
        });
        $scope.update = function () {
            Action.update({id: $scope.action.id}, $scope.action, function (data) {
                console.log(data);
                $location.path('/actions');
            });
        };
    }]);
bsControllers.controller('ActionDeleteCtrl', ['$scope', '$location', '$routeParams', 'Action',
    function ($scope, $location, $routeParams, Action) {
        $scope.action = Action.get({
            id: $routeParams.actionId
        });
        $scope.delete = function () {
            Action.remove({id: $scope.action.id}, function (data) {
                console.log(data);
                $location.path('/actions');
            }, function() {
                $scope.removeError = true;
            });
        };
    }]);
// --- Beacons ---
bsControllers.controller('BeaconListCtrl', ['$scope', 'Beacon',
    function ($scope, Beacon) {
        $scope.beacons = Beacon.query();
    }]);
bsControllers.controller('BeaconCreateCtrl', ['$scope', '$location', 'Beacon',
    function ($scope, $location, Beacon) {
        $scope.beacon = new Beacon();
        $scope.create = function () {
            Beacon.save($scope.beacon, function (data) {
                console.log(data);
                $location.path('/beacons');
            });
        };
    }]);
bsControllers.controller('BeaconReadCtrl', ['$scope', '$routeParams', 'Beacon',
    function ($scope, $routeParams, Beacon) {
        $scope.beacon = Beacon.get({
            id: $routeParams.beaconId
        });
    }]);
bsControllers.controller('BeaconUpdateCtrl', ['$scope', '$location', '$routeParams', 'Beacon',
    function ($scope, $location, $routeParams, Beacon) {
        $scope.beacon = Beacon.get({
            id: $routeParams.beaconId
        });
        $scope.update = function () {
            Beacon.update({id: $scope.beacon.id}, $scope.beacon, function (data) {
                console.log(data);
                $location.path('/beacons');
            });
        };
    }]);
bsControllers.controller('BeaconDeleteCtrl', ['$scope', '$location', '$routeParams', 'Beacon',
    function ($scope, $location, $routeParams, Beacon) {
        $scope.beacon = Beacon.get({
            id: $routeParams.beaconId
        });
        $scope.delete = function () {
            Beacon.remove({id: $scope.beacon.id}, function (data) {
                console.log(data);
                $location.path('/beacons');
            }, function() {
                $scope.removeError = true;
            });
        };
    }]);
// --- (User) Groups ---
bsControllers.controller('GroupListCtrl', ['$scope', 'Group',
    function ($scope, Group) {
        $scope.groups = Group.query();
    }]);
bsControllers.controller('GroupCreateCtrl', ['$scope', '$location', 'Group',
    function ($scope, $location, Group) {
        $scope.group = new Group();
        $scope.create = function () {
            Group.save($scope.group, function (data) {
                console.log(data);
                $location.path('/groups');
            });
        };
    }]);
bsControllers.controller('GroupReadCtrl', ['$scope', '$routeParams', 'Group', 'User',
    function ($scope, $routeParams, Group, User)
    {
        $scope.group = Group.get(
                {
                    id: $routeParams.groupId
                });
        $scope.currentGroupUsers = [];
        $scope.usersNotInGroup = [];
        $scope.addUserToGroup = function(user, group)
        {
            user.userGroup.push(group);
            User.update({id: user.id}, user, function (data)
            {
                console.log(data);
                $scope.currentGroupUsers.push(user);
                $scope.usersNotInGroup.splice($scope.usersNotInGroup.indexOf(user), 1);
            });
        }
        $scope.removeUserFromGroup = function(user, group)
        {
            var index = user.userGroup.forEach(function(g)
            {
                if(g.id === group.id)
                {
                    var index = user.userGroup.indexOf(g);
                    user.userGroup.splice(index, 1);
                }
            });

            User.update({id: user.id}, user, function (data)
            {
                console.log(data);
                $scope.currentGroupUsers.splice($scope.currentGroupUsers.indexOf(user), 1);
                $scope.usersNotInGroup.push(user);
            });
        }
        User.query(function (results)
        {
            angular.forEach(results, function (user)
            {
                var addUserToList = false;
                user.userGroup.forEach(function(g)
                {
                    if(g.id === $scope.group.id)
                    {
                        addUserToList = true;
                    }
                });

                if(addUserToList)
                {
                    $scope.currentGroupUsers.push(user);
                }
                else
                {
                    $scope.usersNotInGroup.push(user);
                }
            });
        });
    }]);
bsControllers.controller('GroupUpdateCtrl', ['$scope', '$location', '$routeParams', 'Group',
    function ($scope, $location, $routeParams, Group) {
        $scope.group = Group.get({
            id: $routeParams.groupId
        });
        $scope.update = function () {
            Group.update({id: $scope.group.id}, $scope.group, function (data) {
                console.log(data);
                $location.path('/groups');
            });
        };
    }]);
bsControllers.controller('GroupDeleteCtrl', ['$scope', '$location', '$routeParams', 'Group',
    function ($scope, $location, $routeParams, Group) {
        $scope.group = Group.get({
            id: $routeParams.groupId
        });
        $scope.delete = function () {
            Group.remove({id: $scope.group.id}, function (data) {
                console.log(data);
                $location.path('/groups');
            }, function() {
                $scope.removeError = true;
            });
        };
    }]);
// --- Roles ---
bsControllers.controller('RoleListCtrl', ['$scope', 'Role',
    function ($scope, Role) {
        $scope.roles = Role.query();
    }]);
bsControllers.controller('RoleCreateCtrl', ['$scope', '$location', 'Role', 'Permission',
    function ($scope, $location, Role, Permission) {
        $scope.role = new Role();
        $scope.permissions = Permission.query();
        $scope.create = function () {
            Role.save($scope.role, function (data) {
                console.log(data);
                $location.path('/roles');
            });
        };
    }]);
bsControllers.controller('RoleReadCtrl', ['$scope', '$routeParams', 'Role',
    function ($scope, $routeParams, Role) {
        $scope.role = Role.get({
            id: $routeParams.roleId
        });
    }]);
bsControllers.controller('RoleUpdateCtrl', ['$scope', '$location', '$routeParams', 'Role', 'Permission',
    function ($scope, $location, $routeParams, Role, Permission) {
        $scope.role = Role.get({
            id: $routeParams.roleId
        });
        $scope.permissions = Permission.query();
        $scope.update = function () {
            Role.update({id: $scope.role.id}, $scope.role, function (data) {
                console.log(data);
                $location.path('/roles');
            });
        };
    }]);
bsControllers.controller('RoleDeleteCtrl', ['$scope', '$location', '$routeParams', 'Role',
    function ($scope, $location, $routeParams, Role) {
        $scope.role = Role.get({
            id: $routeParams.roleId
        });
        $scope.delete = function () {
            Role.remove({id: $scope.role.id}, function (data) {
                console.log(data);
                $location.path('/roles');
            }, function() {
                $scope.removeError = true;
            });
        };
    }]);
// --- Rules ---
bsControllers.controller('RuleListCtrl', ['$scope', 'Rule',
    function ($scope, Rule) {
        $scope.rules = Rule.query();
    }]);
bsControllers.controller('RuleCreateCtrl', ['$scope','Beacon', 'Rule','Action','Group', '$location',
    function ($scope,Beacon, Rule,Action,Group,$location) {
        $scope.beacons = Beacon.query();
        $scope.actions = Action.query();
        $scope.groups = Group.query();
        $scope.rule = new Rule();
        $scope.selectedActions = [];

        $scope.create = function ()
        {
            $scope.rule.action = $scope.selectedActions;
            Rule.save($scope.rule, function (data)
            {
                console.log(data);
                $location.path('/rules');
            });
        };

        $scope.dragularOptions = {
            containersModel: $scope.selectedActions,
            classes: {
                mirror: 'custom-green-mirror'
            },
            nameSpace: 'common' // just connecting left and right container
        };
    }]);

bsControllers.controller('RuleCtrl', ['$scope', '$routeParams', '$http',
    function ($scope, $routeParams, $http) {
        $http.get('api/Rule/' + $routeParams.ruleId).success(function (data) {
            $scope.rule = data;
        });
    }]);
bsControllers.controller('RuleReadCtrl', ['$scope', '$routeParams', 'Rule', 'Beacon',
    function ($scope, $routeParams, Rule, Beacon) {
        $scope.rule = Rule.get({
            id: $routeParams.ruleId
        }, function(data) {
            $scope.beacon = Beacon.get({
                id: data.beaconId
            });
        });
    }]);
bsControllers.controller('RuleUpdateCtrl', ['$scope', '$routeParams','$location','Action', 'Rule','Group','Beacon',
    function ($scope, $routeParams, $location, Action, Rule, Group, Beacon) {
        $scope.rule = Rule.get({
            id: $routeParams.ruleId
        }, function(response)
        {
            $scope.beacons = Beacon.query(function(results)
            {
                angular.forEach(results, function (b)
                {
                    if(b.id == response.beaconId) {
                        response.beacon = b;
                        $scope.rule.beacon = b;
                    }
                });
            });
            Action.query(function(results)
            {
                angular.forEach(results, function (a)
                {
                    for(var i = 0; i < response.action.length; i++)
                    {
                        if (response.action[i].id === a.id)
                        {
                            $scope.selectedActions.push(a);
                            return;
                        }
                    }
                    $scope.actions.push(a);
                });
            });
        });
        $scope.actions = [];
        $scope.groups = Group.query();
        $scope.selectedActions = [];
        $scope.update = function () {
            $scope.rule.action = $scope.selectedActions;
            Rule.update({id: $scope.rule.id}, $scope.rule, function (data) {
                console.log(data);
                $location.path('/rules');
            });
        };

        $scope.dragularOptions = {
            containersModel: $scope.selectedActions,
            classes: {
                mirror: 'custom-green-mirror'
            },
            nameSpace: 'common' // just connecting left and right container
        };
    }]);

bsControllers.controller('RuleDeleteCtrl', ['$scope', '$routeParams','$location', 'Rule',
    function ($scope, $routeParams, $location, Rule) {
        $scope.rule = Rule.get({
            id: $routeParams.ruleId
        });
        $scope.delete = function () {
            Rule.remove({id: $scope.rule.id}, $scope.rule, function (data) {
                console.log(data);
                $location.path('/rules');
            });
        };
    }]);
// --- Users ---
bsControllers.controller('UserListCtrl', ['$scope', 'User',
    function ($scope, User) {
        $scope.users = User.query();
    }]);
bsControllers.controller('UserCreateCtrl', ['$scope', '$location', 'User', 'Role', 'Group',
    function ($scope, $location, User, Role, Group) {
        $scope.user = new User();
        $scope.roles = Role.query();
        $scope.groups = Group.query();
        $scope.create = function () {
            User.save($scope.user, function (data) {
                console.log(data);
                $location.path('/users');
            });
        };
    }]);
bsControllers.controller('UserReadCtrl', ['$scope', '$routeParams', 'User',
    function ($scope, $routeParams, User) {
        $scope.user = User.get({
            id: $routeParams.userId
        });
    }]);
bsControllers.controller('UserUpdateCtrl', ['$scope', '$location', '$routeParams', 'User', 'Role', 'Group',
    function ($scope, $location, $routeParams, User, Role, Group) {
        $scope.user = User.get({
            id: $routeParams.userId
        });
        $scope.roles = Role.query();
        $scope.groups = Group.query();
        $scope.update = function () {
            User.update({id: $scope.user.id}, $scope.user, function (data) {
                console.log(data);
                $location.path('/users');
            });
        };
    }]);
bsControllers.controller('UserDeleteCtrl', ['$scope', '$location', '$routeParams', 'User',
    function ($scope, $location, $routeParams, User) {
        $scope.user = User.get({
            id: $routeParams.userId
        });
        $scope.delete = function () {
            User.remove({id: $scope.user.id}, function (data) {
                console.log(data);
                $location.path('/users');
            });
        };
    }]);
// --- Login ---
bsControllers.controller('LoginCtrl', ['$scope', '$location', '$http',
    function ($scope, $location, $http) {
        $scope.invalidLogin = false;
        $scope.userlogin = {username: null, password: null};
        $scope.login = function () {
            $http.post('login', $scope.userlogin).success(function () {
                $location.path('/dashboard');
            }).error(function () {
                $scope.invalidLogin = true;
            });
        };
    }]);
// --- Settings ---
bsControllers.controller('SettingsCtrl', ['$scope', '$http', 'User',
    function ($scope, $http, User) {
        $scope.saved = false;
        $scope.saveProfile = function()
        {
            if($scope.user != null)
            {
                User.update({id: $scope.user.id}, $scope.user, function (data) {
                    console.log(data);
                    $scope.saved = true;
                });
            }
        }

        $http.get('api/User/currentUser').success(function (data) {
            $scope.user = data;
        });
    }]);