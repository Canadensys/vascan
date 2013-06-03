VASCAN.autocomplete = (function(){
	var _private = {
		init: function(){
			this.typeahead();
		},
		typeahead: function(){
			$('.typeahead').typeahead([
				{
					name: 'scientific',
					remote: '../search.json?q=%QUERY&t=taxon',
					header: '<h3 class="taxontype-name">Scientific Names</h3>'
				},
				{
					name: 'vernacular',
					remote: '../search.json?q=%QUERY&t=vernacular',
					header: '<h3 class="taxontype-name">Vernacular Names</h3>'
				}
				]).on('typeahead:selected', this.dropdown_selected);
		},
		dropdown_selected: function(){
			//TODO: capture language in session and redirect to proper i18n version of result page
			window.location.href = '/vascan/name/'+$(this).val().replace(/\s/g,"+");
		}
	};
	return {
		params: {},
		init: _private.init()
	};
}());