'use strict';

function orderSheet(sheet){
	var orderedSheet = [];
	angular.forEach(sheet.lines,function(line, index){
		if(!angular.isUndefined(line.section)){
			if( index == 0
				(index > 0 && (isEmpty(sheet.lines[index-1].section) ||
						line.section.id  == sheet.lines[index-1].section.id) )){
				orderedSheet.push({"isSection" : true, "name" : line.section.name });
			}
			orderedSheet.push(line);
		}
	});
}

/* Services */

var whichOnesServices = angular.module('whichOnesServices', ['ngResource'])
	.factory('WhichOnesLines', ['$resource',
		function($resource){
			return $resource('rest/sheet/line/:lineId', {lineId: '@lineId' }, {
				remove : {method: 'POST', url: 'rest/sheet/remove/line/:lineId',params : { lineId : "@lineId" }}
			});
		}
	])
	.factory('WhichOnesData', ['$resource',
	   function($resource){
			return $resource('rest/sheet/:tokenId', {tokenId: '@tokenId' }, {
				auth: {method:'POST', params:{password:'@password'}, isArray:false},
				getNewSheet: {method:'GET', url:'data/newSheet.json', isArray:false},		
				getSample1: {method:'GET', url:'data/sample1.json', isArray:false},
				getSample2: {method:'GET', url:'data/sample2.json', isArray:false},
				getSample3: {method:'GET', url:'data/sample3.json', isArray:false},
				create: {method: 'POST', url: 'rest/sheet/create', isArray: false},
			});
	   }
	])
	.service('WhichOnesSheetService', ['$rootScope', 'WhichOnesData', 'WhichOnesLines',
        function($rootScope, WhichOnesData, WhichOnesLines){
			return {
				sheet : null,
				getSheet : function (tokenId){
					if(tokenId == null){
						this.sheet = WhichOnesData.getNewSheet();
					}else if(tokenId == "sample1"){
						this.sheet = WhichOnesData.getSample1();
					}else if(tokenId == "sample2"){
						this.sheet = WhichOnesData.getSample2();
					}else if(tokenId == "sample3"){
						this.sheet = WhichOnesData.getSample3();
					}else{
						this.sheet = WhichOnesData.get({"tokenId" : tokenId});
					}
					return this.sheet;
				},
				getOrderedLine :  function(){
					var orderedSheet = new Array();
					var sheet = this.sheet;
					angular.forEach(sheet.lines,function(line, index){
						if(!isEmpty(line.section)){
							console.log(index == 0 ? "none" :sheet.lines[index-1].section);
							if( index == 0 ||
								(index > 0 && 
										(isEmpty(sheet.lines[index-1].section) ||
										line.section.id  != sheet.lines[index-1].section.id) 
								)
							){
								orderedSheet.push({"isSection" : true, "section" : { "name" : line.section.name , "id" : line.section.id } });
							}
						}
						orderedSheet.push(line);
					});
					return orderedSheet;
				},
				mapSections : function(){
					var sections = {};
					var sheet = this.sheet;
					angular.forEach(sheet.lines,function(line, index){
						console.log(line,line.section);
						if(!isEmpty(line.section)){
							if(angular.isUndefined(sections[line.section.id])){
								sections[line.section.id] = line.section;
							}
							line.section = sections[line.section.id] ;
						}
					});
					return sections;
				},
				saveSheet: function(){
					console.log(this.sheet);
					this.prepareSheet();
				},
				saveLine: function(id){
					var line = findLine(this.sheet.lines, id);
					if(isEmpty(line.sheet) || isEmpty(line.sheet.id)){
						line.sheet = { "id" : this.sheet.id };
					}
					if(line.$resolved){
						line.$save().then(function(newLine){
							$rootScope.$broadcast( 'sheet.update' );
						});
					}else{
						line = WhichOnesLines.save(line);
						line.$promise.then(function(newLine){
							$rootScope.$broadcast( 'sheet.update' );
						});
					}
					console.log(line);
				},
				saveSection: function(section){
					console.log(section);
					$rootScope.$broadcast( 'sheet.update' );
				},
				controleNewSheet: function(){
					this.sheet.$promise.then(function(sheet){
						console.log(sheet.token, sheet);
						if(!angular.isUndefined(sheet.token)){
							$rootScope.$broadcast( 'sheet.created' );
						}
						$rootScope.$broadcast( 'sheet.available' );
					});
				},
				createSheet: function(sheet){
					sheet.id = null;
					angular.forEach(sheet.lines,function(line){
						line.id = null;
					});
					var newSheet = WhichOnesData.create(sheet);
					console.log(newSheet);
					this.sheet = newSheet;
					return this.sheet;
				},
				prepareSheet: function(){
					this.sheet.$promise.then(function(sheet){
						$rootScope.$broadcast( 'sheet.available' );
					});
				},
				deleteLine: function(line){
					var index = findLineIndex(this.sheet.lines, line.id);
					var $lines = this.sheet.lines;
					WhichOnesLines.remove({lineId : line.id}).$promise.then(function(){
						$lines.splice(index,1);
						$rootScope.$broadcast( 'sheet.update' );
					});
				},
				addLineAfter: function(line){
					var newLine = {
							"data" : new Array(), 
							"selected" : false, 
							"index" : line.index+1,
							"sheet" : { "id" : this.sheet.id }
						};
					if(!isEmpty(line.section)){
						newLine.section = { 
								"name" : line.section.name,  
								"id" : line.section.id 
							};
					}
					angular.forEach(line.data,function(datum){
						newLine.data.push({"value" : "", "newValue" : true });
					});
					newLine = WhichOnesLines.save(newLine);
					var $lines = this.sheet.lines;
					newLine.$promise.then(function(newLine){
						$lines.push(newLine);
						$rootScope.$broadcast( 'sheet.available' );
					});
				},
				addColumn: function(){
					this.sheet.headers.push({ "name" : "", "type" : "s1" , "newValue" : true });
					angular.forEach(this.sheet.lines, function(line){
						line.data.push({"value" : "", "newValue" : true});
					});
					console.log(this.sheet);
					$rootScope.$broadcast( 'sheet.update' );
				},
				removeSectionForLine: function(line){
					var index = findLineIndex(this.sheet.lines, line.id);
					delete this.sheet.lines[index].section;
					$rootScope.$broadcast( 'sheet.update' );
				}
			};
		}
	]);