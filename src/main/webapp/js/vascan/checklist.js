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
			$("#selection_button").click(function() {
				$(this).addClass('selected');
				$("#display_button").removeClass('selected');
				$("#display_criteria").slideUp(500);
				$("#selection_criteria").slideDown(500);
				$("#checklist_box").attr("action","checklist");
			});
			$('#display_button').click(function() {
				$(this).addClass('selected');
				$("#selection_button").removeClass('selected');
				$("#selection_criteria").slideUp(500);
				$("#display_criteria").slideDown(500);
				$("#checklist_box").attr("action","checklist#");
			});
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
				try {
					paths = svg.getElementById(checkbox_value).getElementsByTagName("path");
					if($(this).prop("checked")) {
						$.each(paths, function() {
							this.setAttributeNS(null, 'fill', 'rgb(168,36,0)');
						});
					} else {
						$.each(paths, function() {
							this.setAttributeNS(null, 'fill', self.get_region_color(checkbox_value));
						});
					}
				} catch(e) {}
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
			if(document.location.href.indexOf("#") != -1){
				$("#display_button").addClass('selected');
				$("#selection_button").removeClass('selected');
				$("#display_criteria").show();
				$("#selection_criteria").hide();
			}
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