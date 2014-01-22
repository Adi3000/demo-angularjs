'use strict';

/* Controllers */
angular.module('whichOnesControllers', ['whichOnesServices', 'ngRoute'])
	.controller('WhichOnesSheetController', ['$scope', '$rootScope', '$location', 'WhichOnesSheetService',
        function($scope, $rootScope, $location, WhichOnesSheetService){
			var sheetToken = angular.isUndefined($location.search().sheet) ? null : $location.search().sheet;
			console.log(sheetToken);
			$scope.role = { "editor" : true };
			$scope.totals = {};
			//Load that sheet
			$scope.sheet = WhichOnesSheetService.getSheet(sheetToken);
			WhichOnesSheetService.prepareSheet();
			$scope.$on( 'sheet.available', function( event ) {
				$scope.sections = WhichOnesSheetService.mapSections();
				$scope.sheet = WhichOnesSheetService.sheet;
				$scope.orderedLines = WhichOnesSheetService.getOrderedLine();
				updateScopeSheet($scope.sheet, $scope);
				$rootScope.$broadcast('sheet.ready');
			});
			$scope.$on( 'sheet.update', function( event ) {
				$scope.orderedLines = WhichOnesSheetService.getOrderedLine();
				updateScopeSheet($scope.sheet, $scope);
			});
			$scope.saveSheet = function(){
				console.log($scope.sheet);
			};
		}
	])
	.controller("tutoSteps", ['$scope', '$location',
	    function($scope, $location){
			$scope.step = $location.search().step;
		}
	]);


