/****************************
Copyright (c) 2013 Canadensys
Script to handle name pages
****************************/
/*global VASCAN, $, window*/
VASCAN.namepage = (function(){

	'use strict';

	var _private = {
		init: function(){
			$('.reference').tooltip({showURL: false});
		},
	};
	return {
		init: function() {
			_private.init();
		}
	};
}());