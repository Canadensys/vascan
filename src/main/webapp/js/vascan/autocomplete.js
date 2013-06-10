/****************************
Copyright (c) 2013 Canadensys
Script to handle autocomplete
****************************/
/*global VASCAN, $, window*/
VASCAN.autocomplete = (function(){

	'use strict';

	var _private = {
		init: function(){
			this.typeahead();
		},
		typeahead: function(){
			$('.typeahead').typeahead([
				{
					name: 'scientific',
					remote: VASCAN.common.baseURL+'/search.json?q=%QUERY&t=taxon',
					header: '<h3 class="taxontype-name">Scientific Names</h3>'
				},
				{
					name: 'vernacular',
					remote: VASCAN.common.baseURL+'/search.json?q=%QUERY&t=vernacular',
					header: '<h3 class="taxontype-name">Vernacular Names</h3>'
				}
				]).on('typeahead:selected', this.dropdown_selected);
		},
		dropdown_selected: function(){
			//TODO: capture language in session and redirect to proper i18n version of result page
			window.location.href = VASCAN.common.baseURL+'/name/'+encodeURIComponent($(this).val());
		}
	};
	return {
		init: function() {
			_private.init();
		}
	};
}());