/****************************
Copyright (c) 2013 Canadensys
Script to handle download page
****************************/
/*global VASCAN, $, window*/
VASCAN.download = (function(){

  'use strict';

  var _private = {
    on_error: function(){
      $(".error").show();
      $(".ready").hide();
      $(".busy").hide();
    },
    on_success: function(){
      $(".ready").show();
      $(".busy").hide();
      $(".error").hide();
    },
    generate: function(url){
      $.ajax({url:url,cache:false,data:document.location.search.substr(1),success:_private.on_success,error:_private.on_error});
    },

    init: function(){
      setTimeout(function(){_private.generate(VASCAN.common.baseURL + "/generate")},1000);
    },
  }
  return {
    init: function() {
      _private.init();
    }
  };
}());