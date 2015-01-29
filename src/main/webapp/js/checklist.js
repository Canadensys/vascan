/****************************
Copyright (c) 2013 Canadensys
Script to handle checklist creation
****************************/
/*global VASCAN, $*/
VASCAN.checklist = (function(){

  'use strict';

  var _private = {
    region_color: {},
    set_region_colors: function() {
      var color = this.region_color;
      $.each(["AB", "SK", "MB"], function() { color[this] = "rgb(195,195,195)"; });
      $.each(["ON", "QC"], function() { color[this] = "rgb(165,165,165)"; });
      $.each(["NB", "PE", "NS", "NL_N", "NL_L", "PM"], function() { color[this] = "rgb(135,135,135)"; });
      $.each(["BC"], function() { color[this] = "rgb(225, 225, 225)"; });
      $.each(["YT", "NU", "NT", "GL"], function() { color[this] = "rgb(255,255,255)";});
    },
    get_region_color: function(region) {
      return this.region_color[region];
    },
    toggle_display_criteria: function() {
      var self = this;

      $("#selection_button").on('click', function() {
        self.show_criteria_panel("selection");
      });
      $('#display_button').on('click', function() {
        self.show_criteria_panel("display");
      });
    },
    show_criteria_panel: function(selected, scroll) {
      var self = this, opposite = "selection";

      if(!selected) { return false; }
      $('#'+selected+'_button').addClass('selected');
      $("#"+selected+"_criteria").slideDown(500);
      if(selected === "selection") { opposite = "display"; }
      $("#"+opposite+"_button").removeClass('selected');
      $("#"+opposite+"_criteria").slideUp(500, function() {
        if(scroll) { self.scroll_results(); }
      });
      $("#criteria_panel").val(selected);
    },
    scroll_results: function() {
      $('html, body').animate({
        scrollTop: $('#custom_results_table').prev().offset().top
      }, 2000);
    },
    bind_region_checkboxes: function() {
      var self = this;

      $('#canada').on('change', function() {
        var checkboxes = $('input[type="checkbox"]:not([value="GL"],[value="PM"])', '#checklist_distribution');
        if($(this).prop("checked")) {
          checkboxes.prop("checked", true);
          self.uncheck_outside_canada();
        } else {
          checkboxes.prop("checked", false);
        } 
        self.process_map();
      });
      $('.region').on('change', function() {
        var checkboxes = $('input[type="checkbox"]', $(this).closest("ul"));
        if($(this).prop("checked")) {
          checkboxes.prop("checked", true);
        } else {
          checkboxes.prop("checked", false);
        }
        self.set_checked_canada();
        self.process_map();
      });
      $('.province').on('change', function() {
        self.set_checked_regions(this);
        self.set_checked_canada();
        self.process_map();
      });
    },
    set_checked_regions: function(province) {
      var region = $(province).closest("ul");
      if(!$(province).prop("checked")) { $(".region", region).prop("checked", false); }
      if($('.province:checked', region).length === $('.province', region).length) {
        $(".region", region).prop("checked", true);
      }
    },
    set_checked_canada: function() {
      var canada = $('#canada');

      if($(".province:checked", "#checklist_distribution").length === 14 && 
        $('input[type="checkbox"][value="PM"]').prop("checked") === false && 
        $('input[type="checkbox"][value="GL"]').prop("checked") === false) {
          canada.prop("checked", true);
        } else {
          canada.prop("checked", false);
        }
    },
    uncheck_outside_canada: function() {
      $.each(["GL", "PM"], function() {
        $('input[type="checkbox"][value="'+this+'"]').prop("checked", false).closest("ul").find(".region").prop("checked", false);
      });
    },
    process_map: function(seq) {
      var self = this,
          svg = $('#map_selector').svg('get'),
          checkboxes = $('input[type="checkbox"]', '#checklist_distribution');

      if(seq === "init") {
        $.each(checkboxes, function() {
          var checkbox = $(this);
          $('#'+checkbox.val(), svg.root()).css("cursor", "pointer").on("click", function() {
            if(checkbox.prop("checked")) { checkbox.prop("checked", false); } else { checkbox.prop("checked", true); }
            self.toggle_province_highlight(checkbox, $(this));
          });
        });
      }

      $.each(checkboxes, function() {
        var checkbox = $(this),
            paths = $('#'+checkbox.val(), svg.root());

        if(paths.length > 0) {
          self.toggle_province_highlight(checkbox, paths);
        }
      });
    },
    toggle_province_highlight: function(checkbox, paths) {
      var self = this;

      if(checkbox.prop("checked")) {
        $.each(paths, function() {
          $(this).css("fill", "rgb(168,36,0)");
        });
      } else {
        $.each(paths, function() {
          $(this).css("fill", self.get_region_color(checkbox.val()));
        });
      }
    },
    bind_rank_checkboxes: function() {
      $.each(["main_rank", "sub_rank"], function() {
        var self = this;
        $('.'+this).on('click', function() {
          if($('.'+self+':checked').length === $('.'+self).length) {
            $('#'+self).prop("checked", true);
          } else {
            $('#'+self).prop("checked", false);
          }
        });
        $('#'+this).on('click', function() {
          if($(this).prop("checked")) {
            $('.'+self).prop("checked", true);
          } else {
            $('.'+self).prop("checked", false);
          }
        });
      });
    },
    set_default_options: function() {
      var self = this, panel = VASCAN.common.getParameterByName("criteria_panel") || "selection", scroll = false;
      $.each($('.province'), function() {
        self.set_checked_regions(this);
      });
      this.set_checked_canada();
      if($('#custom_results_table').length > 0) { scroll = true; }
      this.show_criteria_panel(panel, scroll);
    },
    load_map: function() {
      var self = this;
      $('#map_selector').svg({
        loadURL: VASCAN.common.baseURL + "/assets/images/distribution_checklist.svg",
        onLoad: function() { self.process_map("init"); }
      });
    },
    init: function() {
      _private.set_region_colors();
      _private.toggle_display_criteria();
      _private.bind_region_checkboxes();
      _private.bind_rank_checkboxes();
      _private.set_default_options();
      _private.load_map();
    }
  };
  return {
    init: function() {
      _private.init();
    }
  };
}());