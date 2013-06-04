/*
Copyright (c) 2011 Canadensys
Script to handle the checklist component
*/
/*globals*/
var fill = [];
var all = ["BC","AB","SK","MB","ON","QC","NB","PE","NS","NL_N","NL_L","YT","NT","NU","GL","PM"];
var canada =   ["BC","AB","SK","MB","ON","QC","NB","PE","NS","NL_N","NL_L","YT","NT","NU"];
var pacific =  ["BC"];
var prairies = ["AB","SK","MB"];
var central =  ["ON","QC"];
var atlantic = ["NB","PE","NS","NL_N","NL_L","PM"];
var arctic =   ["YT","NT","NU","GL"];
var regions = [];

$(document).ready(function(){
	// fill colors for svg map
	fill["BC"] = "rgb(225,225,225)";
	fill["AB"] = "rgb(195,195,195)";
	fill["SK"] = "rgb(195,195,195)";
	fill["MB"] = "rgb(195,195,195)";
	fill["ON"] = "rgb(165,165,165)";
	fill["QC"] = "rgb(165,165,165)";
	fill["NB"] = "rgb(135,135,135)";
	fill["PE"] = "rgb(135,135,135)";
	fill["NS"] = "rgb(135,135,135)";
	fill["NL_N"] = "rgb(135,135,135)";
	fill["NL_L"] = "rgb(135,135,135)";
	fill["YT"] = "rgb(255,255,255)";
	fill["NU"] = "rgb(255,255,255)";
	fill["NT"] = "rgb(255,255,255)";
	fill["GL"] = "rgb(255,255,255)";
	fill["PM"] = "rgb(135,135,135)";
		
	// regions groupings
	regions["pacific"] = pacific;
	regions["prairies"] = prairies;
	regions["central"] = central;
	regions["atlantic"] = atlantic;
	regions["arctic"] = arctic;
	regions["canada"] = canada;
	
	// bind click event to regions checkboxes; (checks or unchecks child checkboxes and call change events on those checkboxes)
    $("#canada").bind("click",{parent:"canada"},processCheckbox);
    $("#pacific").bind("click",{parent:"pacific"},processCheckbox);
    $("#prairies").bind("click",{parent:"prairies"},processCheckbox);
    $("#central").bind("click",{parent:"central"},processCheckbox);
    $("#atlantic").bind("click",{parent:"atlantic"},processCheckbox);
    $("#arctic").bind("click",{parent:"arctic"},processCheckbox);
    
    // bind change event on provinces checkboxes; (changes svg map when checkbox clicked)
    $.each(all,function(){
        svgTerritory = this;
        checkbox = $("#"+this); 
        checkbox.bind("change",{element:checkbox},processMap);
    });
    
    // on initial page load look at checked chekboxes :
	// highlight mapaccordingly
	// turn on or off region checkboxes (ex. turn on central if ON and QC are checked)
    for(var region in regions){
    	checks=true;
        for(var province = 0; province < regions[region].length; province++){
            if(!$("#"+regions[region][province]).prop("checked")){
                checks=false;
            }
            else{
               $("#"+regions[region][province]).prop("checked",true);
               $("#"+regions[region][province]).trigger("change",[false]);
            }
        }
        if(checks == true){
           // if all provinces for a region are checked, check region
           $("#"+region).attr("checked","checked");
        }
    }
    
    
    // display criterias
	$(".main_rank, .sub_rank").bind("click",function(event){
        var allChecked = "checked";
        $("." + event.target.className).each(function(){
            if(this.checked != true)
                allChecked = "";
        });
        $("#" + event.target.className).attr("checked",allChecked);
	});
	
	$("#main_rank , #sub_rank").bind("click",function(event){
		$("." + event.target.id).attr("checked",event.target.checked);
	});
	
	if(document.location.href.indexOf("#") != -1){
        $("#display_button").addClass('selected');
        $("#selection_button").removeClass('selected');
        $("#display_criteria").show();
        $("#selection_criteria").hide();
    }
});

// if parent region checkbox is checked, check all province children and trigger change event to highlight map
function processCheckbox(event){
	var _checked = $("#"+event.data.parent).prop('checked');
     $.each(eval(event.data.parent),function(){
        $("#"+this).prop('checked',_checked);
        $("#"+this).trigger('change');
    });
}

// highlight or return to base fill color map based on checked satus of checkbox
function processMap(event,checkGroups){
	var color = "";
	if($(event.data.element).prop('checked'))
		color = "rgb(168,36,0)";
	else
		color = fill[event.data.element.attr("id")];
	try{
		var map = document.getElementById('map');
		var svg = map.getSVGDocument();
		var paths = svg.getElementById(event.data.element.attr("id")).getElementsByTagName("path");
		for(i = 0; i < paths.length; i++){
			paths[i].setAttributeNS(null,'fill',color);
			if(event.data.element.attr("id") == "PM")
			   paths[i].setAttributeNS(null,'stroke',color);
		}
	}
	catch(e){}

    if(checkGroups != false){
        for(var region in regions){
             checks=true;
             for(var province = 0; province < regions[region].length; province++){
                 if($("#"+regions[region][province]).prop('checked') != true){
                     checks=false;
                 }
             }
             // if all provinces for a region are checked, check region
             $("#"+region).prop('checked',checks);
         }						
     }		
}

// verify if all provinces are checked for a region, check region 
function checkGrouping(){
    for(var region in regions){
         checks=true;
         for(var province = 0; province < regions[region].length; province++){
             if($("#"+regions[region][province]).prop('checked') != true){
                 checks=false;
             }
             else{
                $("#"+regions[region][province]).prop('checked',true);
                $("#"+regions[region][province]).trigger("change",[false]);
             }
         }
         
         if(checks == true){
            // if all provinces for a region are checked, check region
            $("#"+region).prop('checked',true);
         }
     }
}

function toggleDisplay(target){
	if(target == 'display_criteria'){
		$("#display_button").addClass('selected');
		$("#selection_button").removeClass('selected');
		$("#display_criteria").slideDown(500);
		$("#selection_criteria").slideUp(500);
		$("#checklist_box").attr("action","checklist#");
	}
	else{
		$("#display_button").removeClass('selected');
		$("#selection_button").addClass('selected');
		$("#display_criteria").slideUp(500);
		$("#selection_criteria").slideDown(500);
		$("#checklist_box").attr("action","checklist");
	}
}