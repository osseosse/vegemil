$(document).ready(function(){	
	 		
			$("#btnTest").click(function(){	
				$(".portfolio-33-33").attr('style', "display:none");
				$('[id="s8 s3"]').attr('style', "display:block");
	 			$('[id="s2 s13"]').attr('style', "display:block");
			});
	
	 	 	$('.button').click(function(){	 	 		
	 	 		$('button[type="button"]').removeClass('is-checked')
	 		    $(this).addClass('is-checked');
	 	 		
		 		var filter = $(this).attr('data-filter');
		 		alert(filter);
				
		 		if(filter == '*') {
		 			$(".portfolio-33-33").attr('style', "display:block");
		 		} else if(filter == '.s1') { /* 베스트 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			/*$(".s1 s3").attr('style', "display:block");*/
		 			$(".col-12 col-md-6 col-lg-4 portfolio portfolio_style--1 portfolio-33-33  s1 s3").show();
		 			/*$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s1 s3"]').attr('style', "display:block");	*/
		 		}else if(filter == '.s2') { /* 추천 */		 			
		 			$(".ggg").attr('style', "display:none");
		 			$('[id="s8 s3"]').attr('style', "display:block");
		 			$('[id="s2 s13"]').attr('style', "display:block");
		 		}else if(filter == '.s3') { /* 일반영양식 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s1 s3"]').attr('style', "display:block");
		 			$('[id="s3 s4"]').attr('style', "display:block");
		 			$('[id="s3"]').attr('style', "display:block");
		 		}else if(filter == '.s4') { /* 어린이 일반영양식 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s3 s4"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s5') { /* 당뇨 환자 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s5 s11"]').attr('style', "display:block");	 			
		 			$('[id="s5 s15 s11"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s6') { /* 신장질환자 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s6"]').attr('style', "display:block");	 			
		 			$('[id="s6 s15 s11"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s7') { /* 민감한 장 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s7 s14 s11"]').attr('style', "display:block");	 			
		 			$('[id="s7 s14"]').attr('style', "display:block");	 			
		 			$('[id="s7 s11"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s8') { /* 건강식품 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s8 s2"]').attr('style', "display:block");	 			
		 			$('[id="s8 s13"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s9') { /* 외상 및 수술 환자 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s8 s2"]').attr('style', "display:block");	 			
		 			$('[id="s9 s11 s15"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s10') { /* 고단백식 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s10 s13"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s11') { /* 경관급식 전용 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s5 s11"]').attr('style', "display:block");	 			
		 			$('[id="s5 s15 s11"]').attr('style', "display:block");	 			
		 			$('[id="s6 s15 s11"]').attr('style', "display:block");	 			
		 			$('[id="s7 s14 s11"]').attr('style', "display:block");	 			
		 			$('[id="s7 s11"]').attr('style', "display:block");	 			
		 			$('[id="s7 s15 s11"]').attr('style', "display:block");	 			
		 			$('[id="s9 s11 s15"]').attr('style', "display:block");	 			
		 			$('[id="s11"]').attr('style', "display:block");	 			
		 			$('[id="s11 s15"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s12') { /* 점도증진 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s12"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s13') { /* 담백질 보충 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s13"]').attr('style', "display:block");	 			
		 			$('[id="s10 s13"]').attr('style', "display:block");	 			
		 			$('[id="s8 s13"]').attr('style', "display:block");	 			
		 			$('[id="s2 s13"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s14') { /* 화이바 */
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s7 s14 s11"]').attr('style', "display:block");	 			
		 			$('[id="s7 s14"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s15') { /* RTH*/
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s5 s15 s11"]').attr('style', "display:block");	 			
		 			$('[id="s6 s15 s11"]').attr('style', "display:block");	 			
		 			$('[id="s7 s15 s11"]').attr('style', "display:block");	 			
		 			$('[id="s9 s11 s15"]').attr('style', "display:block");	 			
		 			$('[id="s11 s15"]').attr('style', "display:block");	 			
		 		}else if(filter == '.s16') { /*암환자용*/
		 			$(".portfolio-33-33").attr('style', "display:none");
		 			$('[id="s10 s16"]').attr('style', "display:block");	 			
		 			$ 			
		 		} 
	 		});	 		
	 		
			$('#btnSearch').click(function(){
				var word = $('#searchKeyword').val();
				if( word == "" || word == null){
					alert("검색어를 입력하세요.");
					return false;
				}
			});
			
			function onKeydownEnterKey(obj) {
			    if(event.keycode == 13){
				    self.focus();
				    obj.click();				
				    return false;
				}
			}
		});