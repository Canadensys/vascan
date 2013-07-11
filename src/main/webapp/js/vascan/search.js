/****************************
Copyright (c) 2013 Canadensys
Script to handle autocomplete
****************************/
/*global VASCAN, $, window*/
VASCAN.search = (function(){

	'use strict';

	var _private = {
		init: function(){
			this.typeahead();
			this.autofocus();
		},
		typeahead: function(){
			$('.typeahead').typeahead([
				{
					name: 'scientific',
					remote: VASCAN.common.baseURL+'/search.json?q=%QUERY&t=taxon',
					header: '<h3 class="taxontype-name">'+VASCAN.common.getLanguageResource("autocomplete_title1")+'</h3>'
				},
				{
					name: 'vernacular',
					remote: VASCAN.common.baseURL+'/search.json?q=%QUERY&t=vernacular',
					header: '<h3 class="taxontype-name">'+VASCAN.common.getLanguageResource("autocomplete_title2")+'</h3>'
				}
				]).on('typeahead:selected', this.dropdown_selected);
		},
		dropdown_selected: function(){
			var lang = VASCAN.common.getParameterByName("lang"), param = (lang) ? "?lang=" + lang : "";
			window.location.href = VASCAN.common.baseURL+'/name/'+encodeURIComponent($(this).val())+param;
		},
		autofocus: function(){
		  $('#search_term').focus();
		}
	};
	return {
		init: function() {
			_private.init();
		}
	};
}());