/****************************
Copyright (c) 2013 Canadensys
Script to handle vernacular pages
****************************/
/*global VASCAN, $, window*/
VASCAN.vernacular = (function(){

  'use strict';

  var _private = {
    init: function(){
      $('.reference').tooltip({showURL: false});
    }
  };
  return {
    init: function() {
      _private.init();
    }
  };
}());