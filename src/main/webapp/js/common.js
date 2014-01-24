function updateScopeSheet(sheet, $scope){
	angular.forEach(sheet.headers, function(header,index){
		if(header.value){
			header.total = 0;
		}
	});
	computeTotal(sheet.lines, sheet.headers);
}
/**Parses string formatted as YYYY-MM-DD to a Date object.
 * If the supplied string does not match the format, an 
 * invalid Date (value NaN) is returned.
 * @param {string} dateStringInRange format YYYY-MM-DD, with year in
 * range of 0000-9999, inclusive.
 * @return {Date} Date object representing the string.
 */
function parseISO8601(dateStringInRange) {
  var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/,
      date = new Date(NaN), month,
      parts = isoExp.exec(dateStringInRange);

  if(parts) {
    month = +parts[2];
    date.setFullYear(parts[1], month - 1, parts[3]);
    if(month != date.getMonth() + 1) {
      date.setTime(NaN);
    }
  }
  return date;
}
/**
 * Show a loading message
 * @param text
 */
function showLoadingDiv(text){
	$("#loadingText").text(text);
	$("#loadingDiv").show();
}
function limit(text, maxLength){
	console.log(text.length);
	console.log(maxLength);
	if(text.length <= maxLength){
		return text;
	}else{
		//trim the string to the maximum length
		var trimmedString = text.substr(0, maxLength);

		//re-trim if we are in the middle of a word
		trimmedString = trimmedString.substr(0, Math.min(trimmedString.length, trimmedString.lastIndexOf(" ")));
		console.log(trimmedString);
		return trimmedString + "...";
	}
}
/**
 * Display an error message within content with specified code error
 * @param code
 * @param content
 */
function errorMessage(code,content){
	if(!$("#errorDiv [data-error-code="+code+"]").is("*")){
		$("#errorDiv")
				.append(
					$("<div />")
						.attr("data-error-code",code)
						.addClass(".error")
						.click(function(){
							$(this).remove();
							if($("#errorDiv").is(":empty")){
								$("#errorDiv").fadeOut(300);
							}
						})
						.append($('<span />').addClass("ui-icon ui-icon-alert"))
						.append(content));
	}else{
		$("#errorDiv").effect("highlight", {color : "#FF9999"});
		$("#errorDiv [data-error-code="+code+"]").effect("highlight");
	}
	if(!$("#errorDiv").is(":visible")){
		$("#errorDiv").fadeIn(300);
	}
}
/**
 * Remove an error message within code specified
 * @param code
 * @param content
 */
function removeErrorMessage(code){
	if($("#errorDiv [data-error-code="+code+"]").is("*")){
		$("#errorDiv [data-error-code="+code+"]").remove();
		if($("#errorDiv").is(":empty")){
			$("#errorDiv").fadeOut(300);
		}
	}
}
/**
 * Hide loading div
 */
function hideLoadingDiv(){
	$("#loadingDiv").hide();
}
/**
 * Check if o is an empty object (if array is empty, or string is empty)
 * @param o
 * @returns
 */
function isEmpty(o){
	if( Object.prototype.toString.call( o ) === '[object Array]' && o.length == 1){
		return $.isEmptyObject(o[0]);
	}else{
		return $.isEmptyObject(o);
	}
}
function formatTimestamp(timestamp){
	var date = new Date(timestamp * 1000),
		datevalues = [
	       date.getFullYear()
	      ,date.getMonth()+1
	      ,date.getDate()
	      ,date.getHours()
	      ,date.getMinutes()
	      ,date.getSeconds()
	   ];
	return datevalues;
}
/**
 * Check if list contains e
 * @param list
 * @param e
 * @returns {Boolean}
 */
function containsInStringList(list, e){
	if(isEmpty(list)){
		return false;
	}
	var check = getRegExMatcher(e);
	return list.match(check[0]) != null || list.match(check[1]) != null;
}
/**
 * Build a regex to match occurence of e
 * in a StringList
 * @param e
 */
function getRegExMatcher(e){
	var regExMatcher = new Array();
	regExMatcher[0] = new RegExp(_separator_+e+"$|^"+e+"("+_separator_+")?");
	regExMatcher[1] = new RegExp(_separator_+e+_separator_);
	return regExMatcher;
}
/**
 * Remove e from StringList
 * @param list
 * @param e
 * @returns
 */
function removeFromStringList(list, e){
	if(!isEmpty(list)){
		var check = getRegExMatcher(e);
		list = list.replace(check[0],"");
		list = list.replace(check[1],_separator_);
	}
	return list;
}
/**
 * Add an element e to the StringList list 
 * @param list
 * @param e
 * @returns
 */
function addToStringList(list, e){
	if(isEmpty(list)){
		list = list + e;
	}else{
		list = list + _separator_ + e;
	}
	return list;
}
/**
 * Find line element by its id
 * @param list
 * @param id
 */
function findLineIndex(list, id){
	var found = false;
	var lineIndex = null;
	for (var i=0; i<list.length && !found; i++) {
		if(list[i].id == id){
			found = true;
			lineIndex = i; 
		}
	}
	return lineIndex;
}
/**
 * Compute all totals values from lines
 * @param lines
 * @param totals
 */
function computeTotal(lines,headers){
	angular.forEach(headers,function(header){
		if(header.value){
			header.total = 0;
		}
	});
	angular.forEach(lines,function(line){
		if(line.selected){
			angular.forEach(headers,function(header,index){
				if(header.value){
					header.total = parseInt(header.total) + parseInt(line.data[index].value);
				}
			});
		}
	});
}
/**
 * Find line element by its id
 * @param list
 * @param id
 */
function findLine(list, id){
	var found = false;
	var line = null;
	for (var i=0; i<list.length && !found; i++) {
		if(list[i].id == id){
			found = true;
			line = list[i]; 
		}
	}
	return line;
}
/**
 * Protect window.console method calls, e.g. console is not defined on IE
 * unless dev tools are open, and IE doesn't define console.debug
 */
(function() {
  if (!window.console) {
    window.console = {};
  }
  // union of Chrome, FF, IE, and Safari console methods
  var m = [
    "log", "info", "warn", "error", "debug", "trace", "dir", "group",
    "groupCollapsed", "groupEnd", "time", "timeEnd", "profile", "profileEnd",
    "dirxml", "assert", "count", "markTimeline", "timeStamp", "clear"
  ];
  // define undefined methods as noops to prevent errors
  for (var i = 0; i < m.length; i++) {
    if (!window.console[m[i]]) {
      window.console[m[i]] = function() {};
    }    
  } 
})();
function safeApply(fn){
	var phase = this.$root.$$phase;
	if(phase == '$apply' || phase == '$digest') {
		if(fn && (typeof(fn) === 'function')) {
			fn();
		}
	} else {
		this.$apply(fn);
	}
}