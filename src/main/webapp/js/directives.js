'use strict';

function selectLine(lineTag,sheet){
	var selectedLine = findLine(sheet.lines, lineTag.attr("data-line-id")); 
	if(selectedLine.selected){
		selectedLine.selected = false;
		$("input.checkbox",lineTag).prop("checked",false);
	}else{
		selectedLine.selected = true;
		$("input.checkbox",lineTag).prop("checked",true);
	}
	computeTotal(sheet.lines,sheet.headers);
}

var sectionEvenClass = "sectionEven";
var sectionOddClass = "sectionOdd";
var evenSection = true;
/* Directives */
var whichOnesDirectives = angular.module('whichOnesDirectives', ['whichOnesControllers', 'pascalprecht.translate'])
	.directive('manageSheet', ['WhichOnesSheetService', function(WhichOnesSheetService){
		return {
			restrict: 'A',
			templateUrl : 'template/manage-sheet.html',
			scope : true,
			controller: function($scope, $element, $compile){
	        	$scope.role = $scope.$parent.role;
				$scope.addColumn = function(){
					WhichOnesSheetService.addColumn($scope.line); 
				};
			},
			link: function(scope,$element, attrs){
				$(".ui-icon",$element).click(function(e){
					e.stopPropagation();
				});
			}
		};
	}])
	.directive('button',function(){
		return {
			restrict: 'A',
			link: function(scope,$element, attrs){
				$element.click(function(e){
					e.stopPropagation();
				});
			}
		};
	})
	.directive('manageLine', ['WhichOnesSheetService', function(WhichOnesSheetService){
		return {
			restrict: 'A',
			templateUrl : 'template/manage-line.html',
			scope : true,
			controller: function($scope, $element, $compile, $translate){
	        	$scope.role = $scope.$parent.role;
				$scope.add = function(){
					WhichOnesSheetService.addLineAfter($scope.line); 
				};
				$scope.remove = function(){
					if(confirm($translate("MSG_DELETE_CONFIRM"))){
						WhichOnesSheetService.deleteLine($scope.line); 
						console.log($scope);
					}
				};
				$scope.removeSection = function(){
					WhichOnesSheetService.removeSectionForLine($scope.line); 
					console.log($element.parent("tr").removeClass("sectionOdd sectionEven"));
				};
			},
			link: function(scope,$element, attrs){
				if(scope.$parent.line.selected){
					$("input.checkbox",$element).prop("checked",true);
				}
				$(".ui-icon",$element).click(function(e){
					e.stopPropagation();
				});
			}
		};
	}])
	.directive('editable',[ 'WhichOnesSheetService', function(WhichOnesSheetService, $translate){
		return {
			restrict: 'A',
			scope:{
				value : "=editable",
				modelToEdit : "@editable",
				initEditing:"=",
				mandatory: "=",
				placeholder: "@"
			},
			templateUrl: 'template/editable.html',
			controller: function($scope, $element){
				$scope.editing = $scope.initEditing;
				$scope.role = $scope.$parent.role;
				$scope.editingValue = $scope.value;
				$scope.edit = function() {
					$scope.editing = true;
					$scope.editingValue = $scope.value;
				};
				
				$scope.close = function() {
					$scope.editing = false;
					$scope.editingValue = $scope.value;
				};
				
				$scope.save = function(editingValue) {
					if(!isEmpty(editingValue) || !($scope.mandatory)){
						$scope.value = editingValue;
						if($scope.initEditing){
							delete $scope.initEditing;
							console.log($scope);
						}
						$scope.$watch($scope.modelToEdit,function(){
							if($element.hasClass("line")){
								WhichOnesSheetService.saveLine($element.parent("tr").attr("data-line-id"));
							}else{
								WhichOnesSheetService.saveSheet();
							}
						});
						$scope.close();
					}else{
						alert($translate("MSG_MANDATORY"));
					}
				};
			}
		};
		
	}])
	.directive('editableZone',function(){
		return {
			restrict: 'A',
			link: function(scope,$element, attrs){
				var editableZone = $(".ui-icon, input",$element);
				editableZone
				.click(function(e){
					e.stopPropagation();
				});
				$("input.editField",$element).keydown(function (e){
					if(e.keyCode == 13){
						$(this).siblings(".save").click();
					}
				});
			}
		};
	})
	.directive('line',function($timeout){
		return {
			restrict: 'A',
			scope: { line : '='},
			link: function(scope,$element, attrs){
				var line = scope.line;
				if(line.isSection){
					evenSection = !evenSection;
					$element.addClass(evenSection ? sectionEvenClass : sectionOddClass);
				}else{
					if(!isEmpty(line.section)){
						$element.addClass(evenSection ? sectionEvenClass : sectionOddClass);
					}
					$timeout(function(){
						$element.mouseenter(function(){$(this).addClass("lineHover");});
						$element.mouseleave(function(){$(this).removeClass("lineHover");});
					});
				}
			}
		};
	})
	.directive('create', function(WhichOnesSheetService){
		return {
			restrict: 'A',
			scope: { sheet : '=create'},
			controller: function($scope,$element){
				$scope.create = function(){
					WhichOnesSheetService.selectLine($scope.sheet);
				};
			}
		};
	})
	.directive('selectable', function(){
		return {
			restrict: 'A',
			controller: function($scope,$element){
				$scope.select = function(){
					selectLine($element, $scope.sheet);
				};
			}
		};
	});