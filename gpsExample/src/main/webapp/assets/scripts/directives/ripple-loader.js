bsApp.directive('rippleLoader', function () {
        return {
            restrict: 'E',
            template: '<div class="ripple-container"><img src="assets/images/ripple.gif" class="ripple-loader" ng-show="loading"></div>',
            link: function (scope, element) {
               scope.loading = true;
            }
        };
    });