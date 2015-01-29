/****************************
Copyright (c) 2013 Canadensys
Script to handle autocomplete
****************************/
/*global VASCAN, $, window, Bloodhound*/
VASCAN.search = (function(){

  'use strict';

  var _private = {
    data_sources: { scientific : {}, vernacular : {} },
    tt_input: $('.typeahead'),
    init: function() {
      this.bloodhound();
      this.typeahead();
      this.track_search();
    },
    bloodhound: function() {
      this.data_sources.scientific = this.create_bloodhound('taxon');
      this.data_sources.vernacular = this.create_bloodhound('vernacular');
      this.data_sources.scientific.initialize();
      this.data_sources.vernacular.initialize();
    },
    create_bloodhound: function(type) {
      return new Bloodhound({
        datumTokenizer : function(d) { return Bloodhound.tokenizers.whitespace(d.name); },
        queryTokenizer : Bloodhound.tokenizers.whitespace,
        limit : 10,
        remote : {
          url : VASCAN.common.baseURL+'/search.json?q=%QUERY&t='+type,
          filter : function(r) { return $.map(r, function(v) { return { 'name' : v }; }); }
        }
      });
    },
    typeahead: function(){
      this.tt_input.typeahead({
          minLength: 3,
          highlight: true
        },
        {
          name: 'scientific',
          source : this.data_sources.scientific.ttAdapter(),
          displayKey : 'name',
          templates : {
            header: '<h3 class="taxontype-name">'+VASCAN.common.getLanguageResource("autocomplete_title1")+'</h3>'
          }
        },
        {
          name: 'vernacular',
          source: this.data_sources.vernacular.ttAdapter(),
          displayKey : 'name',
          templates : {
            header: '<h3 class="taxontype-name">'+VASCAN.common.getLanguageResource("autocomplete_title2")+'</h3>'
          }
        }).on('typeahead:selected', this.dropdown_selected).focus().select();
    },
    dropdown_selected: function(){
      var lang = VASCAN.common.getParameterByName("lang"), param = (lang !== "") ? "?lang=" + lang : "";
      window.location.href = VASCAN.common.baseURL+'/name/'+encodeURIComponent($(this).val())+param;
    },
    track_search: function() {
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