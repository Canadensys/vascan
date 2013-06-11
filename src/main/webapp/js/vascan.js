/****************************
Copyright (c) 2011 Canadensys
Boostrapper for modules

DESIGN TEMPLATE FOR MODULES IN VASCAN

	VASCAN.myModule = (function() {
		"use strict";
		var _private = {
			init: function() {
		 	//do stuff
			}
		}
		//return some private methods, init will be automatically executed
		return {
			init: _private.init;
		};
	}());
****************************/
;(function(vc, $, window, document, undefined) {

	vc.common = {

		baseURL: "/vascan",

		getParameterByName: function(name) {
	    var cname   = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]"),
	        regexS  = "[\\?&]" + cname + "=([^&#]*)",
	        regex   = new RegExp(regexS),
	        results = regex.exec(window.location.href);

	    if(results === null) { return ""; }
	    return decodeURIComponent(results[1].replace(/\+/g, " "));
	  }
	};

  //global initializer
	$(function() {
		$.each(vc, function() {
			if(this.init) { this.init(); }
		});
	});

}(window.VASCAN = window.VASCAN || {}, jQuery, window, document));