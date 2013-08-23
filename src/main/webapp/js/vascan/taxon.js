/****************************
Copyright (c) 2013 Canadensys
Script to handle taxon pages
****************************/
/*global VASCAN, $, window*/
VASCAN.taxon = (function(){

  'use strict';

  var _private = {
    init: function(){
      $('.reference').tooltip({showURL: false});
      $('ul.buttons a:first').on('click', function(e) {
        e.preventDefault();
        $('#map_result').show(350);
        $('#list_result').hide(350);
        $('ul.buttons').find('a').toggleClass('selected');
      });
      $('ul.buttons a:last').on('click', function(e) {
        e.preventDefault();
        $('#map_result').hide(350);
        $('#list_result').show(350);
        $('ul.buttons').find('a').toggleClass('selected');
      });
    }
  };
  return {
    init: function() {
      _private.init();
    }
  };
}());