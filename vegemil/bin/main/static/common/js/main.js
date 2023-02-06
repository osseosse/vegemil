


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
 * 	 ����� FullMenu
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
 * 	 Mobile Menu // Ȱ��ȭ�� �÷��ø޴� ����. ���߽� Ȱ��ȭ�� �����ϰ�, mainHeader�� ��ũ��Ʈ �����ϰ� ����üũ�غ���
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
 * 	 main > ���ֹ�������+SNS
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
 * 	 Main > Floatin
 *================================*/
// jQuery Load
(function($){
	$(document).ready(function() {
		

		if (!$('#flotingPage').length){return false}; //#contents -> wrap���� ���� 
		var limit =  $(window).height() - ($('.floting').height()+00);
		//alert(limit);
		if (limit > 0){
			$('.floting').addFloating({  
				targetRight: 10,  
				targetTop: 40,  //��ũ�ѽ� ���̼��� 
				snap: true  
			});  
		} else {
			$('.floting').addFloating({  
				targetRight: 10,  
				targetTop: 40,  
				snap: true  
			});  
		}	// 


	});
})(jQuery);


/*================================
 * 	 slide (pp)
 *================================*/
 $(document).ready(function(){
	/********** ���� > ���� **********/
	$('#slider0 .slider').prrpleSlider({
		autoPlay:true,	
		autoPlayInterval:4000,	
	}); 
	/********** ���� > ��õ **********/
	$('#slider1 .slider').prrpleSlider({
		multiple:2,
		autoPlay:true,	
		autoPlayInterval:4000,	
	});  

	/********** ���� > �ı� **********/
	$('#slider2 .slider').prrpleSlider({
		multiple:2,
		autoPlay:true,	
		autoPlayInterval:4000,	
	});  
	/********** ��ǰ����Ʈ > ��ܹ�� **********/
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
  $.fn.basicTabs1 = function(options){ /* basicTabs1 ���� */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs1", /* tabe2 ����*/
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
  $.fn.basicTabs2 = function(options){ /* basicTabs2 ���� */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs2", /* tabe2 ����*/
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
 * 	 ��ǰ���� > ī�װ� Tab
 *================================*/
(function( $ ) {
  $.fn.basicTabs3 = function(options){ /* basicTabs3 ���� */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs3", /* tabe3 ����*/
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
 * 	 ��ǰ�� > ��� ����� Tab
 *================================*/
(function( $ ) {
  $.fn.basicTabs4 = function(options){ /* basicTabs4 ���� */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs4", /* tabe4 ����*/
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
 * 	 Ŀ�´����̼� > �����ŷ� > �����ؼ� ���Ȳ
	 ȸ��Ұ� > ���� 
 *================================*/
(function( $ ) {
  $.fn.basicTabs5 = function(options){ /* basicTabs5 ���� */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs5", /* tabe5 ����*/
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
 * 	 �̵�� (TVCF, �μ�, ETC, ����)
 *================================*/
(function( $ ) {
  $.fn.basicTabs6 = function(options){ /* basicTabs6 ���� */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs6", /* tabe6 ����*/
      content_class: "tab_content6", /* tab_content6 */
      starting_tab: 1
    }, options );
    var $content = $('.' + settings.content_class);
    var $list = $('.' + settings.list_class);
    $content.find('tab_content6 > div').hide();
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
 * 	 �귣�� > ���ڵ��
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

/*================================
 * 	 FAQ ��ƼĮ
 *================================*/

