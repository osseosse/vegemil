/*================================
 * 	 PC Menu
 *================================
$('#gnb > ul > li > a').on("mouseenter focusin",function(e){
		$('#gnb > ul > li').removeClass('on');

		$(this).parent('li').addClass('on')
		$('#header').addClass('open');
		
		var hd = $(this).next('.depth02_menu_wrap').stop().innerHeight();
		var num = hd + 91;
		
		$('#header').stop().animate({height: num}, 340);
		e.preventDefault();
	})

	$('.gnb_depth2').find('a').on("focusin",function(e){
		$('#gnb > ul > li').removeClass('on');

		$(this).parents('#gnb > ul > li').addClass('on')
		$('#header').addClass('open');
		
		var hd = $(this).parents('.depth02_menu_wrap').stop().innerHeight();
		var num = hd + 101;
		
		$('#header').stop().animate({height: num}, 340);
	})

	// 

	if (matchMedia("screen and (min-width: 800px)").matches) {
		$('#header, .all_search_pop').on("mouseleave",function(){
			gnb_close();
		})
	}
	
	$('#gnb').find("a").last().on("focusout",function(){
		gnb_close();
	});

	function gnb_close(){
		$('#header').stop().css({height: 90}); 
		$('#gnb > ul > li').removeClass('on');
		$('#header').removeClass('open');
	}

	


/*================================
 * 	 FullMenu
 *================================*/


	/* Open */
	function openNav() {
	  document.getElementById("myNav").style.height = "100%";
	}

	/* Close */
	function closeNav() {
	  document.getElementById("myNav").style.height = "0%";
	}

/*================================
 * 	 모바일 FullMenu
 *================================*/

	/* mobileAside Open */
	function openAside() {
	  document.getElementById("mobileAside").style.width = "70%";
	}

	/* mobileAside Close */
	function closeAside() {
	  document.getElementById("mobileAside").style.width = "0%";
	}



/*================================
 * 	 Mobile Menu // 활성화시 플로팅메뉴 오류. 개발시 활성화로 변경하고, mainHeader에 스크립트 삭제하고 오류체크해볼것
 *================================

$("#verticalType01").collapse({
	  accordion: true, 
	  open: function() {
		this.addClass("open");
		this.css({ height: this.children().outerHeight() });
	  },
	  close: function() {
		this.css({ height: "0px" });
		this.removeClass("open");
	  }
	});

/*================================
 * 	 main > 자주묻는질문+SNS
 *================================

$(".verticalType02").collapse({
	  accordion: true, 
	  open: function() {
		this.addClass("open");
		this.css({ height: this.children().outerHeight() });
	  },
	  close: function() {
		this.css({ height: "0px" });
		this.removeClass("open");
	  }
	});

/*================================
 * 	 Main > Floating
 *================================*/
// jQuery Load
(function($){
	$(document).ready(function() {
		
		//2022-02-18 주문 > 결제금액 계산기
		if (!$('#flotingPage').length){return false}; //#contents -> wrap으로 변경 
		var limit =  $(window).height() - ($('.floting').height()+00);
		//alert(limit);
		if (limit > 0){
			$('.floting').addFloating({  
				targetRight: 10,  
				targetTop: 40,  //스크롤시 높이설정 
				snap: true  
			});  
		} else {
			$('.floting').addFloating({  
				targetRight: 10,  
				targetTop: 40,  
				snap: true  
			});  
		}	// end - 2022-02-18 주문 > 결제금액 계산기


	});
})(jQuery);


/*================================
 * 	 slide (pp)
 *================================*/
 $(document).ready(function(){
	/********** 메인 > 빅배너 **********/
	$('#slider0 .slider').prrpleSlider({
		autoPlay:true,	
		autoPlayInterval:4000,	
	}); 
	/********** 메인 > 추천 **********/
	$('#slider1 .slider').prrpleSlider({
		multiple:2,
		autoPlay:true,	
		autoPlayInterval:4000,	
	});  

	/********** 메인 > 후기 **********/
	$('#slider2 .slider').prrpleSlider({
		multiple:2,
		autoPlay:true,	
		autoPlayInterval:4000,	
	});  
	/********** 제품리스트 > 상단배너 **********/
	$('#slider3 .slider').prrpleSlider({
		multiple:3,
		autoPlay:true,	
		autoPlayInterval:4000,	
	});  
	
	
	

});






/*================================
 * 	 main > Tab
 *================================*/
(function( $ ) {
  $.fn.basicTabs1 = function(options){ /* basicTabs1 변경 */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs1", /* tabe2 변경*/
      content_class: "tab_content1", /* tab_content1 */
      starting_tab: 1
    }, options );
    var $content = $('.' + settings.content_class);
    var $list = $('.' + settings.list_class);
    $content.find('div').hide();
    $content.find("div:nth-child(" + settings.starting_tab + ")").show();
    $list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

    $("." + settings.list_class + ' li a').click(function(e){
        $list.find("li").removeClass(settings.active_class);
        $("." + settings.content_class + " > div").hide();
        $(this).parent().addClass(settings.active_class);
        var currentTab = $(this).attr('href');
        $(currentTab).show();
        e.preventDefault();
    });
  };
}( jQuery ));


(function( $ ) {
  $.fn.basicTabs2 = function(options){ /* basicTabs2 변경 */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs2", /* tabe2 변경*/
      content_class: "tab_content2", /* tab_content2 */
      starting_tab: 1
    }, options );
    var $content = $('.' + settings.content_class);
    var $list = $('.' + settings.list_class);
    $content.find('tab_content2 > div').hide();
    $content.find("div:nth-child(" + settings.starting_tab + ")").show();
    $list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

    $("." + settings.list_class + ' li a').click(function(e){
        $list.find("li").removeClass(settings.active_class);
        $("." + settings.content_class + " > div").hide();
        $(this).parent().addClass(settings.active_class);
        var currentTab = $(this).attr('href');
        $(currentTab).show();
        e.preventDefault();
    });
  };
}( jQuery ));


/*================================
 * 	 제품진열 > 카테고리 Tab
 *================================*/
(function( $ ) {
  $.fn.basicTabs3 = function(options){ /* basicTabs3 변경 */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs3", /* tabe3 변경*/
      content_class: "tab_content3", /* tab_content3 */
      starting_tab: 1
    }, options );
    var $content = $('.' + settings.content_class);
    var $list = $('.' + settings.list_class);
    $content.find('tab_content3 > div').hide();
    $content.find("div:nth-child(" + settings.starting_tab + ")").show();
    $list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

    $("." + settings.list_class + ' li a').click(function(e){
        $list.find("li").removeClass(settings.active_class);
        $("." + settings.content_class + " > div").hide();
        $(this).parent().addClass(settings.active_class);
        var currentTab = $(this).attr('href');
        $(currentTab).show();
        e.preventDefault();
    });
  };
}( jQuery ));


/*================================
 * 	 제품상세 > 상단 썸네일 Tab
 *================================*/
(function( $ ) {
  $.fn.basicTabs4 = function(options){ /* basicTabs4 변경 */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs4", /* tabe4 변경*/
      content_class: "tab_content4", /* tab_content4 */
      starting_tab: 1
    }, options );
    var $content = $('.' + settings.content_class);
    var $list = $('.' + settings.list_class);
    $content.find('tab_content4 > div').hide();
    $content.find("div:nth-child(" + settings.starting_tab + ")").show();
    $list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

    $("." + settings.list_class + ' li a').click(function(e){
        $list.find("li").removeClass(settings.active_class);
        $("." + settings.content_class + " > div").hide();
        $(this).parent().addClass(settings.active_class);
        var currentTab = $(this).attr('href');
        $(currentTab).show();
        e.preventDefault();
    });
  };
}( jQuery ));

/*================================
 * 	 커뮤니케이션 > 공정거래 > 자율준수 운영현황
 *================================*/
(function( $ ) {
  $.fn.basicTabs5 = function(options){ /* basicTabs5 변경 */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs5", /* tabe5 변경*/
      content_class: "tab_content5", /* tab_content5 */
      starting_tab: 1
    }, options );
    var $content = $('.' + settings.content_class);
    var $list = $('.' + settings.list_class);
    $content.find('tab_content5 > div').hide();
    $content.find("div:nth-child(" + settings.starting_tab + ")").show();
    $list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

    $("." + settings.list_class + ' li a').click(function(e){
        $list.find("li").removeClass(settings.active_class);
        $("." + settings.content_class + " > div").hide();
        $(this).parent().addClass(settings.active_class);
        var currentTab = $(this).attr('href');
        $(currentTab).show();
        e.preventDefault();
    });
  };
}( jQuery ));


/*================================
 * 	 브랜드 > 아코디언
 *================================*/
$(function() {
	window.setTimeout(function() {
		$('ul.prd_acd').css('opacity', '1');
	}, 0);

	$('li').addClass('default');
	
		$('li').on('mouseover', function() {
	
		var e = $('ul.prd_acd > li');
			if(e.hasClass('expand')){
				 e.removeClass('expand');
				$(this).addClass('expand');
			} else { $(this).addClass('expand'); }
		})


		$('li').on('mouseout', function() {
	
		var e = $('ul.prd_acd > li');
			if(e.removeClass('expand')){
				 e.removeClass('expand');
				$(this).removeClass('expand');
			} else { $(this).removeClass('expand'); }
		})
})