$(document).ready(function(){			
			
			function getParameterByName(name) {
			    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
			    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			        results = regex.exec(location.search);
			    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
			}			
			var cate = getParameterByName('category');
			
			
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
			
			 $('#pb00').click(function(){   //전체 선택시
				 
					$(this).addClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');					
			        $(".pb01pb").css('display','block');
			        $(".pb02pb").css('display','block');
			        $(".pb03pb").css('display','block');
			        $(".pb04pb").css('display','block');
			        $(".pb05pb").css('display','block');
			        $(".pb06pb").css('display','block');
			        $(".pb07pb").css('display','block');
		        });			
								
			 $('#pb01').click(function(){  
				 
					$(this).addClass('active');
					$('#pb00').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');					
			        $(".pb01pb").css('display','block');
			        $(".pb02pb").css('display','none');
			        $(".pb03pb").css('display','none');
			        $(".pb04pb").css('display','none');
			        $(".pb05pb").css('display','none');
			        $(".pb06pb").css('display','none');
			        $(".pb07pb").css('display','none');

		        });
			 
			 $('#pb02').click(function(){  	 	 			
					$(this).addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');

			        $(".pb01pb").css('display','none');
			        $(".pb02pb").css('display','block');
			        $(".pb03pb").css('display','none');
			        $(".pb04pb").css('display','none');
			        $(".pb05pb").css('display','none');
			        $(".pb06pb").css('display','none');
			        $(".pb07pb").css('display','none');
		        });
			 $('#pb03').click(function(){
					$(this).addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');
				 				 
			        $(".pb01pb").css('display','none');
			        $(".pb02pb").css('display','none');
			        $(".pb03pb").css('display','block');
			        $(".pb04pb").css('display','none');
			        $(".pb05pb").css('display','none');
			        $(".pb06pb").css('display','none');
			        $(".pb07pb").css('display','none');
		        });
			 $('#pb04').click(function(){   
					$(this).addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');
					
			        $(".pb01pb").css('display','none');
			        $(".pb02pb").css('display','none');
			        $(".pb03pb").css('display','none');
			        $(".pb04pb").css('display','block');
			        $(".pb05pb").css('display','none');
			        $(".pb06pb").css('display','none');
			        $(".pb07pb").css('display','none');
		        });
			 $('#pb05').click(function(){   
					$(this).addClass('active');
					$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb06').removeClass('active');
					$('#pb07').removeClass('active');				 
				 
			        $(".pb01pb").css('display','none');
			        $(".pb02pb").css('display','none');
			        $(".pb03pb").css('display','none');
			        $(".pb04pb").css('display','none');
			        $(".pb05pb").css('display','block');
			        $(".pb06pb").css('display','none');
			        $(".pb07pb").css('display','none');
		        });
			 $('#pb06').click(function(){   
	      			$(this).addClass('active');
	      			$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb07').removeClass('active');
				 
			        $(".pb01pb").css('display','none');
			        $(".pb02pb").css('display','none');
			        $(".pb03pb").css('display','none');
			        $(".pb04pb").css('display','none');
			        $(".pb05pb").css('display','none');
			        $(".pb06pb").css('display','block');
			        $(".pb07pb").css('display','none');
		        });
			 $('#pb07').click(function(){
				 	$(this).addClass('active');
				 	$('#pb00').removeClass('active');
					$('#pb01').removeClass('active');
					$('#pb02').removeClass('active');
					$('#pb03').removeClass('active');
					$('#pb04').removeClass('active');
					$('#pb05').removeClass('active');
					$('#pb06').removeClass('active');
					
			        $(".pb01pb").css('display','none');
			        $(".pb02pb").css('display','none');
			        $(".pb03pb").css('display','none');
			        $(".pb04pb").css('display','none');
			        $(".pb05pb").css('display','none');
			        $(".pb06pb").css('display','none');
			        $(".pb07pb").css('display','block');
		        });
			 
			 
			 $('#gh00').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');					
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','block');
			        $(".gh02gh").css('display','block');
			        $(".gh03gh").css('display','block');
			        $(".gh04gh").css('display','block');
			        $(".gh05gh").css('display','block');
			        $(".gh06gh").css('display','block');
			        $(".gh07gh").css('display','block');
			        $(".gh08gh").css('display','block');
		        });	
			 $('#gh01').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');					
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','block');
			        $(".gh02gh").css('display','none');
			        $(".gh03gh").css('display','none');
			        $(".gh04gh").css('display','none');
			        $(".gh05gh").css('display','none');
			        $(".gh06gh").css('display','none');
			        $(".gh07gh").css('display','none');
			        $(".gh08gh").css('display','none');
		        });
			 $('#gh02').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');					
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','none');
			        $(".gh02gh").css('display','block');
			        $(".gh03gh").css('display','none');
			        $(".gh04gh").css('display','none');
			        $(".gh05gh").css('display','none');
			        $(".gh06gh").css('display','none');
			        $(".gh07gh").css('display','none');
			        $(".gh08gh").css('display','none');
		        });
			 $('#gh03').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');					
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','none');
			        $(".gh02gh").css('display','none');
			        $(".gh03gh").css('display','block');
			        $(".gh04gh").css('display','none');
			        $(".gh05gh").css('display','none');
			        $(".gh06gh").css('display','none');
			        $(".gh07gh").css('display','none');
			        $(".gh08gh").css('display','none');
		        });
			 $('#gh04').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');					
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','none');
			        $(".gh02gh").css('display','none');
			        $(".gh03gh").css('display','none');
			        $(".gh04gh").css('display','block');
			        $(".gh05gh").css('display','none');
			        $(".gh06gh").css('display','none');
			        $(".gh07gh").css('display','none');
			        $(".gh08gh").css('display','none');
		        });
			 $('#gh05').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');					
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','none');
			        $(".gh02gh").css('display','none');
			        $(".gh03gh").css('display','none');
			        $(".gh04gh").css('display','none');
			        $(".gh05gh").css('display','block');
			        $(".gh06gh").css('display','none');
			        $(".gh07gh").css('display','none');
			        $(".gh08gh").css('display','none');
		        });
			 $('#gh06').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh07').removeClass('active');					
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','none');
			        $(".gh02gh").css('display','none');
			        $(".gh03gh").css('display','none');
			        $(".gh04gh").css('display','none');
			        $(".gh05gh").css('display','none');
			        $(".gh06gh").css('display','block');
			        $(".gh07gh").css('display','none');
			        $(".gh08gh").css('display','none');
		        });
			 $('#gh07').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');					
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh08').removeClass('active');					
			        $(".gh01gh").css('display','none');
			        $(".gh02gh").css('display','none');
			        $(".gh03gh").css('display','none');
			        $(".gh04gh").css('display','none');
			        $(".gh05gh").css('display','none');
			        $(".gh06gh").css('display','none');
			        $(".gh07gh").css('display','block');
			        $(".gh08gh").css('display','none');
		        });
			 $('#gh08').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#gh00').removeClass('active');
					$('#gh01').removeClass('active');					
					$('#gh02').removeClass('active');
					$('#gh03').removeClass('active');
					$('#gh04').removeClass('active');
					$('#gh05').removeClass('active');
					$('#gh06').removeClass('active');
					$('#gh07').removeClass('active');					
			        $(".gh01gh").css('display','none');
			        $(".gh02gh").css('display','none');
			        $(".gh03gh").css('display','none');
			        $(".gh04gh").css('display','none');
			        $(".gh05gh").css('display','none');
			        $(".gh06gh").css('display','none');
			        $(".gh07gh").css('display','none');
			        $(".gh08gh").css('display','block');
		        });
			 
			 
			 $('#pe00').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#pe01').removeClass('active');
					$('#pe02').removeClass('active');										
			        $(".pe01pe").css('display','block');
			        $(".pe02pe").css('display','block');			        			       
		        });			
			 $('#pe01').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#pe00').removeClass('active');
					$('#pe02').removeClass('active');										
			        $(".pe01pe").css('display','block');
			        $(".pe02pe").css('display','none');
		        });
			 $('#pe02').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#pe00').removeClass('active');
					$('#pe01').removeClass('active');										
			        $(".pe01pe").css('display','none');
			        $(".pe02pe").css('display','block');
		        });
			 
			 
			 $('#lh00').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#lh01').removeClass('active');
					$('#lh02').removeClass('active');										
			        $(".lh01lh").css('display','block');
			        $(".lh02lh").css('display','block');			        			       
		        });			
			 $('#lh01').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#lh00').removeClass('active');
					$('#lh02').removeClass('active');										
			        $(".lh01lh").css('display','block');
			        $(".lh02lh").css('display','none');
		        });
			 $('#lh02').click(function(){   //전체 선택시				 
					$(this).addClass('active');
					$('#lh00').removeClass('active');
					$('#lh01').removeClass('active');										
			        $(".lh01lh").css('display','none');
			        $(".lh02lh").css('display','block');
		        });
			
			
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