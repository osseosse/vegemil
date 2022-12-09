

// gnb
function chk(){
		if(cc == 1){
		$("#gnb ul li ul").slideDown(200);
		$(".gnbBG").slideDown(200);
		//$("#gnb .gnb_bg").slideDown(100);
	}else{
		$("#gnb ul li ul").slideUp(250);
		$(".gnbBG").slideUp(250);
		//$("#gnb .gnb_bg").slideUp(250);
	}
}


$(function(){
	$('#gnb>ul>li').mouseover(function(){
		setTimeout(chk, 100);
		cc = 1;
		$(this).addClass('active');
	});
	$('#gnb').mouseout(function(){
		setTimeout(chk, 400);
		cc = 0;
		$('#gnb ul li').removeClass('active');
	});
	$('#gnb ul li a').focus(function(){
		setTimeout(chk, 100);
		cc = 1;
		$(this).parent().addClass('active');
	});
	$('#gnb ul li a').blur(function(){
		setTimeout(chk, 100);
		cc = 0;
		$('#gnb ul li').removeClass('active');
	});
});


// 모바일 GNB
function openNav() {
  document.getElementById("myNav").style.width = "100%";
}

function closeNav() {
  document.getElementById("myNav").style.width = "0%";
}


// 그래프 위치이동
$(document).ready(function(){
      
	function example(){
		var w = $(window).width();

		if(w<752){ //tablet&모바일이라면
		   $('.bar-graph-m').after( $('.move_contents'));

		}else{ //pc라면
			$('.bar-graph-web').after( $('.move_contents'));
		}
	}

	//초기실행
	example();


	$(window).resize(function(){
		example();
	});

});




	

/*====================================================== 
	BROOK > main.js
=======================================================*/
    (function ($) {
        'use strict';

		/*=========================== 
			09. Slick Activation
		============================*/
        // Check if element exists
        $.fn.elExists = function () {
            return this.length > 0;
        };
        // Variables
        var $html = $('html'),
            $elementCarousel = $('.brook-element-carousel');

        function customPagingNumb($pagingOptions){
            var i = ($pagingOptions.currentSlide ? $pagingOptions.currentSlide : 0) + 1;
            var $current = i.toString().padStart(2, '0');
            var $total = $pagingOptions.slick.slideCount.toString().padStart(2, '0');
            $pagingOptions.selector.html('<span class="current">'+$current+'</span>/<span class="total">'+$total+'</span>');
        }

        if ($elementCarousel.elExists()) {
            var slickInstances = [];
            $elementCarousel.each(function (index, element) {
                var $this = $(this);
                var $parent = $(this).parent()[0];
                var $status = $($parent).find('.custom-paging');
                // Carousel Options
                var $options = typeof $this.data('slick-options') !== 'undefined' ? $this.data('slick-options') : '';
                var $spaceBetween = $options.spaceBetween ? parseInt($options.spaceBetween) : 0,
                    $spaceBetween_xl = $options.spaceBetween_xl ? parseInt($options.spaceBetween_xl) : 0,
                    $isCustomArrow = $options.isCustomArrow ? $options.isCustomArrow : false,
                    $customPaging = $options.customPaging ? $options.customPaging : false,
                    $customPrev = $isCustomArrow === true ? ($options.customPrev ? $options.customPrev : '') : '',
                    $customNext = $isCustomArrow === true ? ($options.customNext ? $options.customNext : '') : '',
                    $vertical = $options.vertical ? $options.vertical : false,
                    $focusOnSelect = $options.focusOnSelect ? $options.focusOnSelect : false,
                    $asNavFor = $options.asNavFor ? $options.asNavFor : '',
                    $fade = $options.fade ? $options.fade : false,
                    $autoplay = $options.autoplay ? $options.autoplay : false,
                    $autoplaySpeed = $options.autoplaySpeed ? $options.autoplaySpeed : 5000,
                    $swipe = $options.swipe ? $options.swipe : false,
                    $adaptiveHeight = $options.adaptiveHeight ? $options.adaptiveHeight : false,

                    $arrows = $options.arrows ? $options.arrows : false,
                    $dots = $options.dots ? $options.dots : false,
                    $infinite = $options.infinite ? $options.infinite : false,
                    $centerMode = $options.centerMode ? $options.centerMode : false,
                    $centerPadding = $options.centerPadding ? $options.centerPadding : '',
                    $speed = $options.speed ? parseInt($options.speed) : 1000,
                    $prevArrow = $arrows === true ? ($options.prevArrow ? '<span class="' + $options.prevArrow.buttonClass + '"><i class="' + $options.prevArrow.iconClass + '"></i></span>' : '<button class="slick-prev">previous</span>') : '',
                    $nextArrow = $arrows === true ? ($options.nextArrow ? '<span class="' + $options.nextArrow.buttonClass + '"><i class="' + $options.nextArrow.iconClass + '"></i></span>' : '<button class="slick-next">next</span>') : '',
                    $slidesToShow = $options.slidesToShow ? parseInt($options.slidesToShow, 10) : 1,
                    $slidesToScroll = $options.slidesToScroll ? parseInt($options.slidesToScroll, 10) : 1;

                /*Responsive Variable, Array & Loops*/
                var $responsiveSetting = typeof $this.data('slick-responsive') !== 'undefined' ? $this.data('slick-responsive') : '',
                    $responsiveSettingLength = $responsiveSetting.length,
                    $responsiveArray = [];
                for (var i = 0; i < $responsiveSettingLength; i++) {
                    $responsiveArray[i] = $responsiveSetting[i];

                }

                // Adding Class to instances
                $this.addClass('slick-carousel-' + index);
                $this.parent().find('.slick-dots').addClass('dots-' + index);
                $this.parent().find('.slick-btn').addClass('btn-' + index);

                if ($spaceBetween != 0) {
                    $this.addClass('slick-gutter-' + $spaceBetween);
                }
                if ($spaceBetween_xl != 0) {
                    $this.addClass('slick-gutter-xl-' + $spaceBetween_xl);
                }
                var $slideCount = null;
                $this.on('init', function(event, slick){
                    $slideCount = slick.slideCount;
                    if($slideCount <= $slidesToShow){
                        $this.children('.slick-dots').hide();	
                    }
                    if($customPaging == true){
                        var $current = '01';
                        var $total = $slideCount.toString().padStart(2, '0');
                        $status.html('<span class="current">'+$current+'</span>/<span class="total">'+$total+'</span>');
                    }
                });

                $this.slick({
                    slidesToShow: $slidesToShow,
                    slidesToScroll: $slidesToScroll,
                    asNavFor: $asNavFor,
                    autoplay: $autoplay,
                    autoplaySpeed: $autoplaySpeed,
                    speed: $speed,
                    infinite: $infinite,
                    arrows: $arrows,
                    dots: $dots,
                    vertical: $vertical,
                    focusOnSelect: $focusOnSelect,
                    centerMode: $centerMode,
                    centerPadding: $centerPadding,
                    fade: $fade,
                    adaptiveHeight: $adaptiveHeight,
                    prevArrow: $prevArrow,
                    nextArrow: $nextArrow,
                    responsive: $responsiveArray,
                });

                if ($isCustomArrow === true) {
                    $($customPrev).on('click', function () {
                        $this.slick('slickPrev');
                    });
                    $($customNext).on('click', function () {
                        $this.slick('slickNext');
                    });
                }
                $this.on('init reInit afterChange', function (event, slick, currentSlide, nextSlide) {
                    var $pagingOptions = {
                        event: event,
                        slick: slick,
                        currentSlide: currentSlide,
                        nextSlide: nextSlide,
                        selector: $status
                    }
                    if($customPaging == true){
                        customPagingNumb($pagingOptions);
                    }
                });
            });

            // Updating the sliders in tab
            $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                $elementCarousel.slick('setPosition');
            });
        }


	

 
    /*=====================================
    	25. Portfolio Masonry Activation // 전체제품
    =========================================*/

    $(window).load(function () {
        $('.bk-masonary-wrapper').imagesLoaded(function () {

            // filter items on button click
            $('.messonry-button , .mesonary-button-active').on('click', 'button', function () {
                var filterValue = $(this).attr('data-filter');
                $(this).siblings('.is-checked').removeClass('is-checked');
                $(this).addClass('is-checked');
                $grid.isotope({
                    filter: filterValue
                });
            });

            // init Isotope
            var $grid = $('.mesonry-list').isotope({
                percentPosition: true,
                transitionDuration: '0.7s',
                layoutMode: 'masonry',
                masonry: {
                    columnWidth: '.resizer',
                }
            });
        });
    })

	 /*=====================================
    	25. Portfolio Masonry Activation // 제품검색
    =========================================*/

    $(window).load(function () {
        $('.bk-masonary-wrapper').imagesLoaded(function () {

            // filter items on button click
            $('.messonry-search , .mesonary-button-active').on('click', 'button', function () {
                var filterValue = $(this).attr('data-filter');
                $(this).siblings('.is-checked').removeClass('is-checked');
                $(this).addClass('is-checked');
                $grid.isotope({
                    filter: filterValue
                });
            });

            // init Isotope
            var $grid = $('.mesonry-list').isotope({
                percentPosition: true,
                transitionDuration: '0.7s',
                layoutMode: 'masonry',
                masonry: {
                    columnWidth: '.resizer',
                }
            });
        });
    })



	


	
})(jQuery);
/*====================================================== 
	// BROOK > main.js
=======================================================*/


/*====================================================== 
	전체제품 
=======================================================*/
$(function(){
	$('.ico_pc_01').on('click',function(){
		$('.t01').css('display','none');
		$('.t02').css('display','none');
		$('.t03').css('display','none');
		$('.t04').css('display','none');
	});
	$('.ico_pc_02').on('click',function(){
		$('.t01').css('display','block');
		$('.t02').css('display','none');
		$('.t03').css('display','none');
		$('.t04').css('display','none');
	});
	$('.ico_pc_03').on('click',function(){
		$('.t01').css('display','none');
		$('.t02').css('display','block');
		$('.t03').css('display','none');
		$('.t04').css('display','none');
	});
	$('.ico_pc_04').on('click',function(){
		$('.t01').css('display','none');
		$('.t02').css('display','none');
		$('.t03').css('display','block');
		$('.t04').css('display','none');
	});
	$('.ico_pc_05').on('click',function(){
		$('.t01').css('display','none');
		$('.t02').css('display','none');
		$('.t03').css('display','none');
		$('.t04').css('display','block');
	});

});


/*====================================================== 
	경장영양식이란 
=======================================================*/
$(function(){
	$('.ent001').on('mouseover',function(){
		$('.entbg01 > ul').css('background','#fff');
	});
	$('.ent001').on('mouseout',function(){
		$('.entbg01 > ul').css('background','#eef0f2');
	});
	
	$('.ent002').on('mouseover',function(){
		$('.entbg02 > ul').css('background','#fff');
	});
	$('.ent002').on('mouseout',function(){
		$('.entbg02 > ul').css('background','#eef0f2');
	});

});







/*====================================================== 
	경장영양식이란 > slide
=======================================================*/

$.fn.basicTabs2 = function(options){ /* basicTabs2 변경 */
var settings = $.extend({
  active_class: "current",
  list_class: "tabs2", /* tabe2 변경*/
  content_class: "tab_content2", /* tab_content2 */
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



