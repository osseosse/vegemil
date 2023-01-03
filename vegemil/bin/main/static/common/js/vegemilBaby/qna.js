$(document).ready(function(){				
	
			function getParameterByName(name) {
			    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
			    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			        results = regex.exec(location.search);
			    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
			}			
			var cate = getParameterByName('category');
							
			    if(cate =="sk"){
			        $("#LcateSk").addClass('active');  			       
			    }
			    else if(cate =="gh"){
			        $("#LcateGh").addClass('active');     
			    }
			    else if(cate =="bo"){
			        $("#LcateBo").addClass('active');
			    }
			    else if(cate =="di"){
			        $("#LcateDi").addClass('active');
			    }
			    else{
			        $("#LcateAll").addClass('active');
			    }
			    
			    
});



			  