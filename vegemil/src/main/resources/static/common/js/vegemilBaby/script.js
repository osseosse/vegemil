/*
$(document).ready(function(){
    

    var delay=0, setTimeoutConst;
    $('.site-navigation:not(.onclick) .navbar-nav>li.dropdown, .site-navigation:not(.onclick) li.dropdown>ul>li.dropdown').hover(
    function(){
      var $this = $(this);
      setTimeoutConst = setTimeout(function(){
        $this.addClass('open').slideDown();
        $this.find('.dropdown-toggle').addClass('disabled');
      }, delay);

    },  function(){ 
      clearTimeout(setTimeoutConst );
      $(this).removeClass('open');
      $(this).find('.dropdown-toggle').removeClass('disabled');
    });


    $('.navbar-nav').slicknav({
        allowParentLinks: true,
        label: "",
        closedSymbol: '<i class="fa fa-chevron-down" aria-hidden="true"></i>',
        openedSymbol :'<i class="fa fa-chevron-up" aria-hidden="true"></i>'
    });
  

  $('.slicknav_btn').click(function() {
    $(this).toggleClass('act');
      if($(this).hasClass('act')) {
        $('.slicknav_menu').addClass('act');
      }
      else {
        $('.slicknav_menu').removeClass('act');
      }
  });
});

/* ------------------------------------------------------
		***** slider리뉴얼 *****
--------------------------------------------------------
$(window).load(function(){
	

	$('#slider1 .slider').prrpleSlider({
		autoPlay:true,				
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
});


/* ------------------------------------------------------
		***** 메인 circular *****
--------------------------------------------------------
$(function(){ 
    $('.rotating-slider').rotatingSlider({
		slideHeight : Math.min(340, window.innerWidth -80),
		slideWidth : Math.min(1200, window.innerWidth -80),
	});
});


/* ------------------------------------------------------------------------------------------------------------
		***** 메인rotating-slider --> tab *****
--------------------------------------------------------------------------------------------------------------
(function( $ ) {
  $.fn.basicTabs1 = function(options){ 
	var settings = $.extend({
	  active_class: "current",
	  list_class: "tabs1", 
	  content_class: "tab_content1", 
	  starting_tab: 1
	}, options );
	var $content = $('.' + settings.content_class);
	var $list = $('.' + settings.list_class);
	$content.find('dl').hide();
	$content.find("dl:nth-child(" + settings.starting_tab + ")").show();
	$list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

	$("." + settings.list_class + ' li a').click(function(e){
		$list.find("li").removeClass(settings.active_class);
		$("." + settings.content_class + " > dl").hide();
		$(this).parent().addClass(settings.active_class);
		var currentTab = $(this).attr('href');
		$(currentTab).show();
		e.preventDefault();
	});
  };
}( jQuery ));

/* --------------------------------------------------------------------------------------
		***** subMenu  > select1, select2  *****
----------------------------------------------------------------------------------------*/
$(document).ready(function() {
$(".view-btn_01").click(function(){  //클릭시
		if($(this).attr("class")=="view-btn_01"){
			$(this).addClass("open");  //클래스 값 추가
			$(".list_open_01").slideDown("fast"); // language_open을  slideDown 하시오
			$(".view-btn_02").removeClass("open");  // 다른버튼클릭시 닫김
			$(".list_open_02").slideUp("fast");  // 다른버튼클릭시 닫김
		}else{
			$(this).removeClass("open");   // 클래스값 제거 
			$(".list_open_01").slideUp("fast");  // language_open을  slideUp 하시오
		}
		return false;
	});
});


$(document).ready(function() {
$(".view-btn_02").click(function(){  //클릭시
		if($(this).attr("class")=="view-btn_02"){
			$(this).addClass("open");  //클래스 값 추가
			$(".list_open_02").slideDown("fast"); // language_open을  slideDown 하시오
			$(".view-btn_01").removeClass("open");  // 다른버튼클릭시 닫김
			$(".list_open_01").slideUp("fast");  // 다른버튼클릭시 닫김
		}else{
			$(this).removeClass("open");   // 클래스값 제거 
			$(".list_open_02").slideUp("fast");  // language_open을  slideUp 하시오
		}
		return false;
	});
});


/* ------------------------------------------------------------------------------------------------------------
		***** 메인 > 모바일 메뉴 tab *****
--------------------------------------------------------------------------------------------------------------*/
$(function(){
	var duration = 300;
	var $aside = $('.page-main > aside');
	var $asideButton = $aside.find('button')
		.on('click', function(){
			$aside.toggleClass('open');
			if($aside.hasClass('open')) {
				$aside.stop(true).animate({left:'-70px'}, duration, 'swing');
				$asideButton.find('img').attr('src','img/btn_close.png');
			} else{
				$aside.stop(true).animate({left:'-350px'}, duration, 'swing');
				$asideButton.find('img').attr('src','img/btn_open.png');
			};

		});
});



/* ------------------------------------------------------------------------------------------------------------
		***** 브랜드 > 영양 tab *****
--------------------------------------------------------------------------------------------------------------*/
(function( $ ) {
  $.fn.basicTabs = function(options){ /* basicTabs 변경 */
	var settings = $.extend({
	  active_class: "current",
	  list_class: "tabs2", /* tabe2 변경*/
	  content_class: "tab_content_brand", /* tab_content_brand */
	  starting_tab: 1 /* 베스트후기 개발로 인한 2로선정 */
	}, options );
	var $content = $('.' + settings.content_class);
	var $list = $('.' + settings.list_class);
	$content.find('dl').hide();
	$content.find("dl:nth-child(" + settings.starting_tab + ")").show();
	$list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

	$("." + settings.list_class + ' li a').click(function(e){
		$list.find("li").removeClass(settings.active_class);
		$("." + settings.content_class + " > dl").hide();
		$(this).parent().addClass(settings.active_class);
		var currentTab = $(this).attr('href');
		$(currentTab).show();
		e.preventDefault();
	});
  };
}( jQuery ));



/* ------------------------------------------------------
		***** 달력아기모델 > Select *****
--------------------------------------------------------*/
$(document).ready(function() { var selectTarget = $('.selectbox select'); selectTarget.change(function(){ var select_name = $(this).children('option:selected').text(); $(this).siblings('label').text(select_name); }); });



/* ------------------------------------------------------
		***** 브랜드 > TV 광고  *****
		썸네일 마우스오버시 오버랩애니메이션
--------------------------------------------------------*/
$(function(){
	//
	var duration = 300;
	// images
	var $images = $('.thumb-over .t_img');
		//images 세 번째 이미지의 캡션 표현 부문
		$images.filter('.t_img')
			.on('mouseover', function(){
            $(this).find('strong').stop(true).animate({bottom:'0px'}, duration);
			$(this).find('img').stop(true).animate({top:'0%'}, duration *1.3);
        })

		  .on('mouseout', function(){
            $(this).find('strong').stop(true).animate({bottom:'-480px'}, duration);
			$(this).find('img').stop(true).animate({top:'0px'}, duration );
		})
});




/* ------------------------------------------------------
		***** 브랜드 > TV 광고  *****
		popup
--------------------------------------------------------*/
(function ( $ ) {

	$.fn.grtyoutube = function( options ) {

		return this.each(function() {

			// Get video ID
			var getvideoid = $(this).attr("youtubeid");

			// Default options
			var settings = $.extend({
				videoID: getvideoid,
				autoPlay: true,
				theme: "dark"
			}, options );

			// Convert some values
			if(settings.autoPlay === true) { settings.autoPlay = 1 } else if(settings.autoPlay === false)  { settings.autoPlay = 0 }
			if(settings.theme === "dark") { settings.theme = "grtyoutube-dark-theme" } else if(settings.theme === "light")  { settings.theme = "grtyoutube-light-theme" }

			// Initialize on click
			if(getvideoid) {
				$(this).on( "click", function() {
					 $("body").append('<div class="grtyoutube-popup '+settings.theme+'">'+
								'<div class="grtyoutube-popup-content">'+
									'<span class="grtyoutube-popup-close"></span>'+
									'<iframe class="grtyoutube-iframe" src="https://www.youtube.com/embed/'+settings.videoID+'?rel=0&wmode=transparent&autoplay='+settings.autoPlay+'&iv_load_policy=3" allowfullscreen frameborder="0" allow="autoplay; fullscreen"></iframe>'+
								'</div>'+
							'</div>');
				});
			}

			// Close the box on click or escape
			$(this).on('click', function (event) {
				event.preventDefault();
				$(".grtyoutube-popup-close, .grtyoutube-popup").click(function(){
					$(".grtyoutube-popup").remove();
				});
			});

			$(document).keyup(function(event) {
				if (event.keyCode == 27){
					$(".grtyoutube-popup").remove();
				}
			});
		});
	};

}( jQuery ));


