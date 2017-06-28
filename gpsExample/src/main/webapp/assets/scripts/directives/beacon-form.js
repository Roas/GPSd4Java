bsApp.directive('beaconForm', function ($compile) {
    return {
        restrict: 'E',
        scope: {
            beacon: '='
        },
        templateUrl: 'assets/scripts/directives/beacon-form.html'
    };
});