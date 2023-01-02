$(document).ready(function(){			
			
			function getParameterByName(name) {
			    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
			    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			        results = regex.exec(location.search);
			    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
			}			
			var cate = getParameterByName('category');
			var subCate = getParameterByName('subCategory');

			
				$("#pb").css('display','none');
			    $("#gh").css('display','none');
			    $("#pe").css('display','none');
			    $("#lh").css('display','none');
			 
			    if(cate =="pb"){
			        $("#pb").css('display','block'); /* 하위카테고리 */
			        $("#LcatePb").addClass('active'); /* 카테고리이미지 */   			       
			    }
			    else if(cate =="gh"){
			        $("#gh").css('display','block');
			        $("#LcateGh").addClass('active');     
			    }
			    else if(cate =="pe"){
			        $("#pe").css('display','block');
			        $("#LcatePe").addClass('active');
			    }
			    else if(cate =="lh"){
			        $("#lh").css('display','block');
			        $("#LcateLh").addClass('active');
			    }
			    else{
			        $("#LcateAll").addClass('active');
			    }
			    
			    
			    if(subCate =="pb01"){
			    	$('#pb00').removeClass('active');
					$('#pb01').addClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');				    	
			    }else if(subCate =="pb02"){
			    	$('#pb02').addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');			    	
			    }else if(subCate =="pb03"){
			    	$('#pb03').addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');			    	
			    }else if(subCate =="pb04"){
			    	$('#pb04').addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');			    	
			    }else if(subCate =="pb05"){
			    	$('#pb05').addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');			    	
			    }else if(subCate =="pb06"){
			    	$('#pb06').addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb07').removeClass('active');			    	
			    }else if(subCate =="pb07"){
			    	$('#pb07').addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');			    	
			    }
			    
			    if(subCate =="gh01"){
			    	$('#gh00').removeClass('active');
					$('#gh01').addClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');
					$('#gh08').removeClass('active');
			    }else if(subCate =="gh02"){
			    	$('#gh02').addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');
					$('#gh08').removeClass('active');
			    }else if(subCate =="gh03"){
			    	$('#gh03').addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');
					$('#gh08').removeClass('active');
			    }else if(subCate =="gh04"){
			    	$('#gh04').addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');
					$('#gh08').removeClass('active');
			    }else if(subCate =="gh05"){
			    	$('#gh05').addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');
					$('#gh08').removeClass('active');
			    }else if(subCate =="gh06"){
			    	$('#gh06').addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh07').removeClass('active');
					$('#gh08').removeClass('active');
			    }else if(subCate =="gh07"){
			    	$('#gh07').addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');	
					$('#gh08').removeClass('active');
			    }else if(subCate =="gh08"){
			    	$('#gh08').addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');
			    }			    
			    if(subCate =="pe01"){
					$('#pe01').addClass('active');
			    	$('#pe00').removeClass('active');
					$('#pe02').removeClass('active');					
			    }else if(subCate =="pe02"){
			    	$('#pe02').addClass('active');
					$('#pe00').removeClass('active');
					$('#pe01').removeClass('active');					
			    }
			    
			    if(subCate =="lh01"){
					$('#lh01').addClass('active');
			    	$('#lh00').removeClass('active');
					$('#lh02').removeClass('active');					
			    }else if(subCate =="lh02"){
			    	$('#lh02').addClass('active');
					$('#lh00').removeClass('active');
					$('#lh01').removeClass('active');					
			    }
			 
			
			
		    // Slicknav menu
		    $('.navbar-nav').slicknav({
		        allowParentLinks: true,
		        label: "",
		        closedSymbol: '<i class="fa fa-chevron-down" aria-hidden="true"></i>',
		        openedSymbol :'<i class="fa fa-chevron-up" aria-hidden="true"></i>'
		    });
		  
		  // menu click event
		  $('.slicknav_btn').click(function() {
		    $(this).toggleClass('act');
		      if($(this).hasClass('act')) {
		        $('.slicknav_menu').addClass('act');
		      }
		      else {
		        $('.slicknav_menu').removeClass('act');
		      }
		
		  });
		  
		   ////
		    
		  /*  if('<%=mCategory %>' != '') selectMCategory('<%=mCategory %>');
		    
		    if('<%=newsCount %>' == 0) $('#content404').show()*/
		});

		function selectMCategory(category){
			
		    var cnt;
		    
		    if(category.substr(0,2) == 'pb') cnt=7;
		    else if(category.substr(0,2) == 'gh') cnt=8;
		    else if(category.substr(0,2) == 'pe') cnt=2;
		    else if(category.substr(0,2) == 'lh') cnt=2;
		    
		    for(var i=0;i<=cnt;i++){
		        $("#" + category.substr(0,2) + "0" + i).attr('class','');
		    }
		    
		    $("#" + category).attr('class','active');
		    
		    if(category.substr(2,2) == "00"){
		        $("." + category.substr(0,2)).css('display','block');
		    }
		    else{
		        $("." + category.substr(0,2)).css('display','none');
		        $("." + category).css('display','block');
		    }
		}