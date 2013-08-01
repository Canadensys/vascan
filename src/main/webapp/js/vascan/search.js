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
      this.autofocus(); //must be after typeahead
      this.clickable_results();
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
      $('#search_term').focusout(function() {
        setTimeout(function() {
          $(this).focus().select();
        }, 0);
      }).focus().select();
    },
    clickable_results: function() {
      $('#search_list').on('click', 'li', function() {
        window.location.href = $(this).find('a').slice(0,1).attr('href');
      });
    }
  };
  return {
    init: function() {
      _private.init();
    }
  };
}());