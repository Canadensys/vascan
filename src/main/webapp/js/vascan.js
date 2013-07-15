/****************************
Copyright (c) 2011 Canadensys
Boostrapper for modules

DESIGN TEMPLATE FOR MODULES IN VASCAN

  VASCAN.myModule = (function() {
    "use strict";
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
****************************/
/*global window*/
;(function(vc, $, window, document, undefined) {
  
  "use strict";

  vc.common = {

    baseURL: "/vascan",

    languageResources: {},
    
    setLanguageResources: function(resources) {
      if(typeof resources !== 'object') { return false; }
      $.extend(true, this.languageResources, resources);
    },
    
    getLanguageResource: function(resource) {
      if(this.languageResources.hasOwnProperty(resource)) {
        return this.languageResources[resource];
      }
      return null;
    },

    getParameterByName: function(name) {
      var cname   = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]"),
          regexS  = "[\\?&]" + cname + "=([^&#]*)",
          regex   = new RegExp(regexS),
          results = regex.exec(window.location.href);

      if(results === null) { return ""; }
      return decodeURIComponent(results[1].replace(/\+/g, " "));
    }
  };

  //global initializer
  $(function() {
    $.each(vc, function() {
      if(this.init) { this.init(); }
    });
  });

}(window.VASCAN = window.VASCAN || {}, jQuery, window, document));