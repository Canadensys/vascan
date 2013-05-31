var autocomplete  = (function($){
	// Setup map component
	function setup() {
		$('.typeahead').typeahead([
		{
			name: 'vernacular',
			remote: 'search.json?q=%QUERY&t=vernacular',
			header: '<h3 class="type-name">Vernacular Names</h3>'
		},
		{
			name: 'nhl-teams',
			remote: 'search.json?q=%QUERY&t=taxon',
			header: '<h3 class="type-name">Scientific Names</h3>'
		}
		]);
	}
	
//Public methods
  return {
	  setup : setup
	};
}(jQuery));