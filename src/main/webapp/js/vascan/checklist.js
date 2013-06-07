/****************************
Copyright (c) 2013 Canadensys
Script to handle checklist creation
****************************/
VASCAN.checklist = (function(){
	var _private = {
		region_color: {},
		set_region_colors: function() {
			var color = this.region_color;
			$.each(["AB", "SK", "MB"], function() { color[this] = "rgb(195,195,195)"; });
			$.each(["ON", "QC"], function() { color[this] = "rgb(165,165,165)"; });
			$.each(["NB", "PE", "NS", "NL_N", "NL_L", "PM"], function() { color[this] = "rgb(135,135,135)"; });
			$.each(["BC"], function() { color[this] = "rgb(225, 225, 225)"; })
			$.each(["YT", "NU", "NT", "GL"], function() { color[this] = "rgb(255,255,255)";});
		},
		get_region_color: function(region) {
			return this.region_color[region];
		},
		toggle_display_criteria: function() {
			var self = this;

			$("#selection_button").click(function() {
				self.show_criteria_panel("selection");
			});
			$('#display_button').click(function() {
				self.show_criteria_panel("display");
			});
		},
		show_criteria_panel: function(selected) {
			var opposite = "selection";

			if(!selected) { return false; }
			$('#'+selected+'_button').addClass('selected');
			$("#"+selected+"_criteria").slideDown(500);
			if(selected === "selection") { opposite = "display"; }
			$("#"+opposite+"_button").removeClass('selected');
			$("#"+opposite+"_criteria").slideUp(500);
			$("#criteria_panel").val(selected);
		},
		bind_region_checkboxes: function() {
			var self = this;
			$('#canada').change(function() {
				var checkboxes = $('input[type="checkbox"]:not([value="GL"],[value="PM"])', '#checklist_distribution');
				if($(this).prop("checked")) {
					checkboxes.prop("checked", true);
					self.uncheck_outside_canada();
				} else {
					checkboxes.prop("checked", false);
				} 
				self.process_map();
			});
			$('.region').change(function() {
				var checkboxes = $('input[type="checkbox"]', $(this).closest("ul"));
				($(this).prop("checked")) ? checkboxes.prop("checked", true) : checkboxes.prop("checked", false);
				self.process_map();
			});
			$('.province').change(function() {
				var region = $(this).closest("ul");
				if(!$(this).prop("checked")) { $(".region", region).prop("checked", false); }
				if($('.province:checked', region).length === $('.province', region).length) {
					$(".region", region).prop("checked", true);
				}
				self.process_map();
			});
		},
		uncheck_outside_canada: function() {
			$.each(["GL", "PM"], function() {
				$('input[type="checkbox"][value="'+this+'"]').prop("checked", false).closest("ul").find(".region").prop("checked", false);
			});
		},
		process_map: function() {
			var	self = this,
					svg = $('#map')[0].getSVGDocument(),
					checkboxes = $('input[type="checkbox"]', '#checklist_distribution'),
					paths = [];

			$.each(checkboxes, function() {
				var checkbox_value = $(this).val();
				paths = svg.getElementById(checkbox_value);
				if($(paths).length > 0) {
					paths = paths.getElementsByTagName("path");
					if($(this).prop("checked")) {
						$.each(paths, function() {
							this.setAttributeNS(null, 'fill', 'rgb(168,36,0)');
						});
					} else {
						$.each(paths, function() {
							this.setAttributeNS(null, 'fill', self.get_region_color(checkbox_value));
						});
					}
				}
			});
		},
		bind_rank_checkboxes: function() {
			$.each(["main_rank", "sub_rank"], function() {
				var self = this;
				$('.'+this).click(function() {
					($('.'+self+':checked').length === $('.'+self).length) ? $('#'+self).prop("checked", true) : $('#'+self).prop("checked", false);
				});
				$('#'+this).click(function() {
					($(this).prop("checked")) ? $('.'+self).prop("checked", true) : $('.'+self).prop("checked", false);
				});
			});
		},
		set_default_display: function() {
			var panel = VASCAN.common.getParameterByName("criteria_panel");
			this.process_map();
			//$('body').scrollTo($('#custom_results_table'));
			this.show_criteria_panel(panel);
		},
		init: function() {
			_private.set_region_colors();
			_private.toggle_display_criteria();
			_private.bind_region_checkboxes();
			_private.bind_rank_checkboxes();
			_private.set_default_display();
		}
	};
	return {
		init: function() {
			_private.init();
		}
	};
}());