/****************************
Copyright (c) 2013 Canadensys
Script to handle autocomplete
****************************/
/*global VASCAN, $, window*/
VASCAN.search = (function(){

  'use strict';

  var _private = {
    tt_input: $('.typeahead'),
    init: function() {
      this.typeahead();
      this.trackSearch();
    },
    typeahead: function(){
      this.tt_input.typeahead([
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
        ]).on('typeahead:selected', this.dropdown_selected).focus().select();
    },
    dropdown_selected: function(){
      var lang = VASCAN.common.getParameterByName("lang"), param = (lang) ? "?lang=" + lang : "";
      window.location.href = VASCAN.common.baseURL+'/name/'+encodeURIComponent($(this).val())+param;
    },
    trackSearch: function() {
      var self = this;
      if(typeof _gaq !== 'undefined') {
        $('#search_button').on('click', function() {
          _gaq.push(['_trackEvent', 'Search', 'Submitted', self.tt_input.val()]);
        });
      }
    }
  };
  return {
    init: function() {
      _private.init();
    }
  };
}());