/*
Copyright (c) 2011 Canadensys
Script to handle the search component
*/

jQuery(document).ready(function($){

	/**
	 * Give focus to the text field
	 */
	$("#search_term").focus();
	
	/**
	 * dropdown function
	 * will highlight the next or previous <li> on arrow down or arrow up key press
	 */
	var dropdown = function(event){
		// return on any non system keys (letters, numbers...)
		if(event.keyCode == 0)
			return;
		
		// objects for highlighting
		var noSelection = false;
		var currentSelectedItem;
		var nextSibling;
		var previousSibling;

		// find currently selected row, it predecessor and follower
		currentSelectedItem = $("#search_dropdown > li:not(.dropdown_header)").filter('.selected');
		nextSibling = currentSelectedItem.nextAll('li:not(.dropdown_header)').eq(0);
		previousSibling = currentSelectedItem.prevAll('li:not(.dropdown_header)').eq(0);

		// if no rows found, first li is automatically selected
		if(currentSelectedItem.length == 0){
			noSelection = true;
			currentSelectedItem = $("#search_dropdown li:not(.dropdown_header)").eq(0);
			nextSibling = currentSelectedItem.nextAll('li:not(.dropdown_header)').eq(0);
		}
		
		// if no follower, lock to user in place (alway select current row, which is the last one)
		if(nextSibling.length == 0)
			nextSibling = currentSelectedItem;
		
		// if no predecessor, lock to user in place (alway select current row, which is the first one)	
		if(previousSibling.length == 0)
			previousSibling = currentSelectedItem;
		
		// up
		if(event.keyCode == 38){
			currentSelectedItem.removeClass('selected');
			previousSibling.addClass('selected');
			
			if(previousSibling == currentSelectedItem){
				previousSibling.removeClass('selected');
				$("#search_term").focus();
				el = $("#search_term").get(0);
				pos = $("#search_term").val().length;
				if(el.setSelectionRange){
					el.setSelectionRange(pos,pos);
				} else if (el.createTextRange) {
			      var range = el.createTextRange();
			      range.collapse(true);
			      range.moveEnd('character', pos);
			      range.moveStart('character', pos);
			      range.select();
			    }
			}
		}
		// down
		else if(event.keyCode == 40){
			if($("#search_dropdown").is(":visible") == false){
				event.keyCode = 0;
				query(event);
			}
			if(noSelection)
				currentSelectedItem.addClass('selected');
			else{
				currentSelectedItem.removeClass('selected');
				nextSibling.addClass('selected');
			}
		}

		// enter
		else if(event.keyCode == 13){
			// if nothing is highlighted or if it's a enter on header "Search for xxxxx", submit form
			if(!currentSelectedItem.hasClass('selected') || currentSelectedItem.hasClass('dropdown_continue_search'))
				document.forms[0].submit();
			// else link to details page
			else{
				document.location.href = currentSelectedItem.find('a').attr('href');
			}
		}
	};
		
	/**
	 * 
	 */
	var query = function(event){
		
		//for every other key, execute ajax lookup
		if(event.keyCode == 40 || event.keyCode == 38){
			//dropdown(event);
			return;
		}
		
		// abort on multiple request (kill the current running query and start another one) 
		try{ajaxRequest.abort();$("#search_dropdown").show();}catch(e){}
   
		// if there is some key words in the search field, execute ajax call
		if($("#search_term").val() != ""){
		
			ajaxRequest = $.ajax({
				//async:true,
				url: '/vascan/_ajax/search.jsp?k=' + $.trim($("#search_term").val()) + '&p=1',
				dataType:'json',
				success:function(data, textStatus, XMLHttpRequest){
					$("#search_dropdown").empty();

					//json data
					var items = data.d;
				   
					//for each item, create a new row
					$.each(items,function(){
						var row = $("<li>");
						var a = $("<a>");
						var name = "";
						
						a.html(this.n);
						if(french == true)
							this.n += '?lang=fr';
						a.attr("href","/vascan/name/" + this.n);
						a.appendTo(row);
						row.unbind('click').bind('click',function(){document.location.href=a.attr('href')});
						row.hover(function(){$(this).addClass('selected')},function(){$(this).removeClass('selected');})														
						row.appendTo("#search_dropdown")
					});
					
							
					//append last row "Search for (keyword)"
					var row = $("<li>");
					var link = '/vascan/search/?q='+$("#search_term").val(); 
					if(french)
						this.link += '&lang=fr';
					
					row.unbind('click').bind('click',function(){document.location.href=link});
					var a = $("<a>");
					a.html(searchfor + " \"" + $("#search_term").val() +"\"");
					a.attr("href",link);
					a.appendTo(row);
					row.addClass("dropdown_continue_search");
					row.appendTo($("#search_dropdown"));

					row.hover(function(event){row.addClass('selected');},function(event){row.removeClass('selected')});
					
					//display dropdown
					$("#search_dropdown").show();
				},
				error:function(){
					$("#search_dropdown").hide();
				}
			});
		}
		else
			$("#search_dropdown").hide();
	}
	
	//request handle
	var ajaxRequest;
	
	
	// strings displayed base on lang;
	var search_en = "Search for";
	var search_fr = "Recherchez";
	var searchfor = search_en;
	var french = false;
	if(document.location.href.match(new RegExp("lang=fr"))){
		french = true;
		searchfor = search_fr;
	}

	//bind change event to keywords textbox;
	$("#search_term").unbind("focusout").bind("focusout",function(){
		setTimeout(function(){$("#search_dropdown").hide()},250);
	});

	//bind keyup listener to search box ; will handle every other key
	$("#search_term").unbind("keyup").bind("keyup",query);

	
	//bind keypress listener to search box ; will handle up and down arrow
	$("#search_term").unbind("keypress").bind("keypress",dropdown);
	

	// hide the dropdown ul 
	$("#search_dropdown").hide();  		
});