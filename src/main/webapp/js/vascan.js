;(function(vc, $, window, document, undefined) {

	vc.common = {
		baseURL: "/vascan"
	};

/* DESIGN TEMPLATE FOR MODULES IN VASCAN */
/*
	VASCAN.myModule = (function() {
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
*/

  //global initializer
	$(function() {
		$.each(vc, function() {
			if(this.init) { this.init(); }
		});
	});

}(window.VASCAN = window.VASCAN || {}, jQuery, window, document));