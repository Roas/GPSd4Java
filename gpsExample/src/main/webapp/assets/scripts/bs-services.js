/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var bsServices = angular.module('bsServices', ['ngResource']);

// Actions
bsServices.factory('Action', ['$resource',
    function ($resource)
    {
        return $resource('api/Action/:id', { actionId: '@id' },
        {
            update: {method: 'PUT'}
        });
    }]);

// Beacons
bsServices.factory('Beacon', ['$resource',
    function ($resource)
    {
        return $resource('api/Beacon/:id', { actionId: '@id' },
            {
                update: {method: 'PUT'}
            });
    }]);

// (User) Groups
bsServices.factory('Group', ['$resource',
    function ($resource)
    {
        return $resource('api/UserGroup/:id', { actionId: '@id' },
            {
                update: {method: 'PUT'}
            });
    }]);

// Permissions
bsServices.factory('Permission', ['$resource',
    function ($resource)
    {
        return $resource('api/Permission/:id', { actionId: '@id' },
            {
                update: {method: 'PUT'}
            });
    }]);

// Roles
bsServices.factory('Role', ['$resource',
    function ($resource)
    {
        return $resource('api/Role/:id', { actionId: '@id' },
            {
                update: {method: 'PUT'}
            });
    }]);

// Rules
bsServices.factory('Rule', ['$resource',
    function ($resource)
    {
        return $resource('api/Rule/:id', { actionId: '@id' },
            {
                update: {method: 'PUT'}
            });
    }]);

// Users
bsServices.factory('User', ['$resource',
    function ($resource)
    {
        return $resource('api/User/:id', { actionId: '@id' },
            {
                update: {method: 'PUT'},
                query: {isArray: true}
            });
    }]);
