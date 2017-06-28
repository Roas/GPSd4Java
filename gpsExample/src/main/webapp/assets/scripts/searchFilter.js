angular.module('staticSelect', [])
 .controller('searchFilter', ['$scope', function($scope) {
   $scope.data = {
    singleSelect: "5000",

   };

   $scope.filter = function() {
     $scope.data.singleSelect = 'nonsense';
   };
}]);