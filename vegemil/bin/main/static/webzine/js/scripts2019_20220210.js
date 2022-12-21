
//   all ------------------
function initPopuga() {
    "use strict";
    //   loader ------------------
    $(".loader").fadeOut(500, function () {
        $("#main").animate({
            opacity: "1"
        }, 500);
    });
    //   Background image ------------------
    var a = $(".bg");
    a.each(function (a) {
        if ($(this).attr("data-bg")) $(this).css("background-image", "url(" + $(this).data("bg") + ")");
    });

	 $(".to-top").on("click", function (a) {
        a.preventDefault();
        $("html, body").animate({
            scrollTop: 0
        }, 800);
        return false;
    });


 	//  scrollToFixed------------------
    $(".scroll-nav-wrap ").scrollToFixed({
        minWidth: 1068,
        zIndex: 112,
        marginTop: 90,
    });
    $(".boxed-filter").scrollToFixed({
        minWidth: 1068,
        zIndex: 112,
        marginTop: 60,
    });
    $(".fix-tab").scrollToFixed({
        minWidth: 1068,
        zIndex: 112,
        marginTop: 170,
        removeOffsets: true,
        limit: function () {
            var a = $(".limit-box").offset().top - $(".fix-tab").outerHeight(true);
            return a;
        }
    });
    $(".fix-aside").scrollToFixed({
        minWidth:1258,
        zIndex: 112,
        marginTop: 110,
        removeOffsets: true,
        limit: function () {
            var a = $(".limit-box").offset().top - $(".fix-aside").outerHeight(true);
            return a;
        }
    });
    //   Isotope------------------
    function n() {
        if ($(".gallery-items").length) {
            var a = $(".gallery-items").isotope({
                singleMode: true,
                columnWidth: ".grid-sizer, .grid-sizer-second, .grid-sizer-three",
                itemSelector: ".gallery-item, .gallery-item-second, .gallery-item-three"
            });
            a.imagesLoaded(function () {
                a.isotope("layout");
            });
            $(".gallery-filters").on("click touchstart", "a.gallery-filter", function (b) {
                b.preventDefault();
                var c = $(this).attr("data-filter"),
                    d = $(this).text();
                a.isotope({
                    filter: c
                });
                $(".gallery-filters a").removeClass("gallery-filter-active");
                $(this).addClass("gallery-filter-active");
            });
        }
        $(".gallery-items").isotope("on", "layoutComplete", function (a, b) {
            var b = a.length;
            $(".num-album").html(b);
        });
        var b = $(".gallery-item").length;
        $(".all-album , .num-album").html(b);
    }
    n();
    $(window).on("load", function () {
        n();
    });
    $(".gallery-item").on('touchstart', function () {
        $(this).trigger('hover');
    }).on('touchend', function () {
        $(this).trigger('hover');
    });
    //   Swiper------------------
    if ($(".portfolio-wrap").length > 0) {
        var prwrap = new Swiper(".portfolio-wrap .swiper-container", {
            slidesPerView: "auto",
            centeredSlides: false,
            spaceBetween: 10,
            grabCursor: true,
            freeMode: true,
            mousewheelControl: true,
            mousewheelReleaseOnEdges: true,
            mousewheelSensitivity: 1,
            preloadImages: true,
            updateOnImagesReady: true,
            scrollbar: '.swiper-scrollbar',
            pagination: '.swiper-pagination',
            paginationType: 'fraction',
            scrollbarHide: false,
            nextButton: ".swiper-button-next",
            prevButton: ".swiper-button-prev"
        });
    }
    if ($(".fs-gallery-wrap").length > 0) {
        var h = $(".fs-gallery-wrap").data("autoplayslider"),
            i = $(".fs-gallery-wrap").data("slidereffect");
        var j = new Swiper(".fs-gallery-wrap .swiper-container", {
            autoplay: h,
            autoplayDisableOnInteraction: false,
            pagination: ".swiper-pagination",
            paginationClickable: true,
            effect: i,
            speed: 1000,
            grabCursor: true,
            nextButton: ".swiper-button-next",
            prevButton: ".swiper-button-prev",
            loop: true,
            mousewheelControl: false,
            mousewheelReleaseOnEdges: true,
            mousewheelSensitivity: 1,
        });
    }
    var m = new Swiper(".single-slider .swiper-container", {
        pagination: ".swiper-pagination",
        paginationType: "fraction",
        effect: $(".single-slider").data("effects"),
        loop: true,
        grabCursor: true,
        autoHeight: true,
        nextButton: ".swiper-button-next",
        prevButton: ".swiper-button-prev"
    });
    var swiper = new Swiper('.inline-carousel .swiper-container', {
        pagination: '.swiper-pagination',
        slidesPerView: 3,
        paginationClickable: true,
        spaceBetween: 10,
        nextButton: ".inline-car-control .swiper-button-next",
        prevButton: ".inline-car-control .swiper-button-prev"

    });
    //   lightGallery------------------
    $(".image-popup , .single-popup-image").lightGallery({
        selector: "this",
        cssEasing: "cubic-bezier(0.25, 0, 0.25, 1)",
        download: false,
        counter: false
    });
    var o = $(".lightgallery"),
        p = o.data("looped");
    o.lightGallery({
        selector: ".lightgallery a.popup-image , .lightgallery  a.popgal",
        cssEasing: "cubic-bezier(0.25, 0, 0.25, 1)",
        download: false,
        loop: p,
        counter: false
    });

    $(".filter-button").on("click touchstart", function () {
        $(".hid-filter").slideToggle(500);
    });
    //   appear------------------
    $(".stats").appear(function () {
        $(".num").countTo();
    });
    $(".inline-facts").append("<span class='dec-counter'></span>");
    $(".dec-counter").each(function () {
        var numdec = $(this).parents(".inline-facts").find("div.num").data("num");
        $(this).text(numdec)
    });
    $(".piechart-holder").appear(function () {
        $(this).find(".chart").each(function () {
            $(".chart").easyPieChart({
                barColor: "#F9C413",
                trackColor: "#404040",
                scaleColor: false,
                size: "150",
                lineWidth: "40",
                lineCap: "butt",
                animate: 3500,
                easing: "easeInBounce",
                onStep: function (a, b, c) {
                    $(this.el).find(".percent").text(Math.round(c));
                }
            });
        });
    });
    $(".skillbar-box").appear(function () {
        $(this).find("div.skillbar-bg").each(function () {
            $(this).find(".custom-skillbar").delay(600).animate({
                width: $(this).attr("data-percent")
            }, 1500);
        });
    });
 	//   perfectScrollbar------------------
    $('.nav-holder-wrap , .twitter-widget-wrap').perfectScrollbar();
    //   share------------------
    var shrcn = $(".share-container");
    function showShare() {
        shrcn.animate({
            top: 0
        }, 550);
    }
    function hideShare() {
        shrcn.animate({
            top: -150 + "px"
        }, 550);
    }
    $(".show-share").on("click touchstart", function (a) {
        showShare();
        return false;
    });
    $(".closeshare").on("click touchstart", function (a) {
        a.preventDefault();
        hideShare();
        return false;
    });
    var u = $(".share-container");
    u.share({
        networks: ["facebook", "pinterest", "googleplus", "twitter", "linkedin"]
    });
    //   tabs------------------
    $("ul.tabs li").on("click", function () {
        var a = $(this).attr("data-tab"),
            b = $("ul.tabs li");
        b.removeClass("current");
        $(".tab-content").removeClass("current");
        $(this).addClass("current");
        $("#" + a).addClass("current");
        return false;
    });
    //   scroll to------------------
    $(".custom-scroll-link").on("click", function () {
        var a = 70;
        if (location.pathname.replace(/^\//, "") == this.pathname.replace(/^\//, "") || location.hostname == this.hostname) {
            var b = $(this.hash);
            b = b.length ? b : $("[name=" + this.hash.slice(1) + "]");
            if (b.length) {
                $("html,body").animate({
                    scrollTop: b.offset().top - a
                }, {
                    queue: false,
                    duration: 1200,
                    easing: "easeInOutExpo"
                });
                return false;
            }
        }
    });
    $(".scroll-nav  ul").singlePageNav({
        filter: ":not(.external)",
        updateHash: false,
        offset: 140,
        threshold: 140,
        speed: 1200,
        currentClass: "act-link"
    });
   
    //   Contact form------------------
    $("#contactform").submit(function () {
        var a = $(this).attr("action");
        $("#message").slideUp(750, function () {
            $("#message").hide();
            $("#submit").attr("disabled", "disabled");
            $.post(a, {
                name: $("#name").val(),
                email: $("#email").val(),
                comments: $("#comments").val()
            }, function (a) {
                document.getElementById("message").innerHTML = a;
                $("#message").slideDown("slow");
                $("#submit").removeAttr("disabled");
                if (null != a.match("success")) $("#contactform").slideDown("slow");
            });
        });
        return false;
    });
    $("#contactform input, #contactform textarea").keyup(function () {
        $("#message").slideUp(1500);
    });
    //   mailchimp------------------
    $("#subscribe").ajaxChimp({
        language: "eng",
        url: "http://kwst.us9.list-manage1.com/subscribe/post?u=992ebe1f14864e841317ca145&id=163340d9c8"
    });
    $.ajaxChimp.translations.eng = {
        submit: "Submitting...",
        0: '<i class="fa fa-check"></i> We will be in touch soon!',
        1: '<i class="fa fa-warning"></i> You must enter a valid e-mail address.',
        2: '<i class="fa fa-warning"></i> E-mail address is not valid.',
        3: '<i class="fa fa-warning"></i> E-mail address is not valid.',
        4: '<i class="fa fa-warning"></i> E-mail address is not valid.',
        5: '<i class="fa fa-warning"></i> E-mail address is not valid.'
    };
    //   Video------------------
    var v = $(".background-youtube").data("vid");
    var f = $(".background-youtube").data("mv");
    $(".background-youtube").YTPlayer({
        fitToBackground: true,
        videoId: v,
        pauseOnScroll: true,
        mute: f,
        callback: function () {
            var a = $(".background-video").data("ytPlayer").player;
        }
    });
    var w = $(".background-vimeo").data("vim");
    $(".background-vimeo").append('<iframe src="//player.vimeo.com/video/' + w + '?background=1"  frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen ></iframe>');
    $(".video-holder").height($(".media-container").height());
    if ($(window).width() > 1024) {
        if ($(".video-holder").size() > 0)
            if ($(".media-container").height() / 9 * 16 > $(".media-container").width()) {
                $(".background-vimeo iframe ").height($(".media-container").height()).width($(".media-container").height() / 9 * 16);
                $(".background-vimeo iframe ").css({
                    "margin-left": -1 * $("iframe").width() / 2 + "px",
                    top: "-75px",
                    "margin-top": "0px"
                });
            }
        else {
            $(".background-vimeo iframe ").width($(window).width()).height($(window).width() / 16 * 9);
            $(".background-vimeo iframe ").css({
                "margin-left": -1 * $("iframe").width() / 2 + "px",
                "margin-top": -1 * $("iframe").height() / 2 + "px",
                top: "50%"
            });
        }
    } else if ($(window).width() < 760) {
        $(".video-holder").height($(".media-container").height());
        $(".background-vimeo iframe ").height($(".media-container").height());
    } else {
        $(".video-holder").height($(".media-container").height());
        $(".background-vimeo iframe ").height($(".media-container").height());
    }
    $(".video-container").css("width", $(window).width() + "px");
    $(".video-container ").css("height",  720 / 1280 * $(window).width()) + "px";
    if ($(".video-container").height() < $(window).height()) {
        $(".video-container ").css("height", $(window).height() + "px");
        $(".video-container").css("width",  1280 / 720 * $(window).height()) + "px";
    }
    $(".nav-holder nav li a.act-link").closest("li").addClass("actli");
    $(".nav-holder nav li ul").parent("li").append('<span class="nav-dec"></span>');
    $(".nav-holder nav li").on("click", function () {
        $(this).each(function () {
            $(this).children("ul").stop().slideToggle(400);
        });
    });
//  menu  ------------------

	function hideMenu() {
		$(".nav-holder-wrap").animate({
			right: -450 + "px"
		}, 550);
		$(".nav-button-wrap").addClass("vis-menbut");
	}
	function showMenu() {
		$(".nav-holder-wrap").animate({
			right: 0
		}, 550);
		$(".nav-button-wrap").removeClass("vis-menbut");
	}
    $(".nav-button").on("click touchstart", function() {
        if ($(this).parent(".nav-button-wrap").hasClass("vis-menbut")) showMenu(); else hideMenu();
        return false;
    });
 	$(".filter-header").on("click", function() {
		if($(window).width()<1258){
		 		$(".fixed-filter .gallery-filters").slideToggle(400);
			}

        return false;
    });
    var sfw = $(".search-form-wrap");
    function showSearch() {
        sfw.addClass("visserch");
        hideTwit();
    }
    function hideSearch() {
        sfw.removeClass("visserch");
    }
    $(".act-search").on("click", function () {
        showSearch();
    });
    $(".close-search").on("click", function () {
        hideSearch();
    });
    // twitter ------------------
    var tww = $(".twitter-widget-wrap"),
        abt = $(".active-twitter");

    function showTwitter() {
        hideSearch();
        tww.addClass("vistwitt");
        abt.removeClass("twittact");
    }
    function hideTwit() {
        tww.removeClass("vistwitt");
        abt.addClass("twittact");
    }
    abt.on("click", function () {
        if ($(this).hasClass("twittact")) showTwitter();
        else hideTwit();
    });
    if ($("#header-twitts").length > 0) {
        var config1 = {
            "profile": {
                "screenName": 'katokli3mmm'
            },
            "domId": 'header-twitts',
            "maxTweets": 5,
            "enableLinks": true,
            "showImages": false
        };
        twitterFetcher.fetch(config1);
    }
}
// parallax ------------------
function initparallax() {
    var a = {
        Android: function () {
            return navigator.userAgent.match(/Android/i);
        },
        BlackBerry: function () {
            return navigator.userAgent.match(/BlackBerry/i);
        },
        iOS: function () {
            return navigator.userAgent.match(/iPhone|iPad|iPod/i);
        },
        Opera: function () {
            return navigator.userAgent.match(/Opera Mini/i);
        },
        Windows: function () {
            return navigator.userAgent.match(/IEMobile/i);
        },
        any: function () {
            return a.Android() || a.BlackBerry() || a.iOS() || a.Opera() || a.Windows();
        }
    };
    trueMobile = a.any();
    if (null == trueMobile) {
        var b = new Scrollax();
        b.reload();
        b.init();
    }
    if (trueMobile) $(".background-video").remove();
}
$(function () {
    initPopuga();
    initparallax();
});


// 메뉴슬라이드 ------------------
$(function(){
	var duration = 300;
	var $aside = $('.page-main > aside');
	var $asideButton = $aside.find('button')
		.on('click', function(){
			$aside.toggleClass('open');
			if($aside.hasClass('open')) {
				$aside.stop(true).animate({left:'0px'}, duration, 'swing'); /* right*/
				$asideButton.find('button.list img').attr('src','./images/common/gnb01.png'); //이미지로 on,off 제어가능
				$('button.list').css('background','#f15c5c'); //on 배경
			} else{
				$aside.stop(true).animate({left:'-190px'}, duration, 'swing'); /* right*/
				$asideButton.find('button.list img').attr('src','./images/common/gnb01.png'); //이미지로 on,off 제어가능
				$('button.list').css('background','none'); //off 배경 
			};
		});
});

//$(function(){
	//$('.btn_gnb_close').css({
	//		backgroundColor: './images/common/btn_close.png'

//	})
//});

// 메인 > 썸네일오버  ------------------
$(function(){
	// 
	var duration = 300;

	// images ----------------------------------------
	 var $images = $('.hover-effect p');
	// images 세번째 이미지
	 $images.filter('p')
		.on('mouseover', function(){
			$(this).find('strong').stop(true).animate({bottom: '0px'}, duration);
			$(this).find('span').stop(true).animate({opacity: 1}, duration);
			$(this).find('img').stop(true).animate({top: '-20px'}, duration * 1.3);
		})
		.on('mouseout', function(){
			$(this).find('strong').stop(true).animate({bottom: '-100px'}, duration);
			$(this).find('span').stop(true).animate({opacity: 0}, duration);
			$(this).find('img').stop(true).animate({top: '0px'}, duration);
		});
	});



// Q4_2019/sub05 슬라이드 ------------------
$(function(){
	$('#slider3 .slider').prrpleSlider({
		autoPlay:true,				//play slider automatically?  //false 사용시 멈춤
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
});

// Q1_2020/sub07 슬라이드 ------------------
$(function(){
	$('#slider4 .slider').prrpleSlider({
		autoPlay:true,				//play slider automatically?  //false 사용시 멈춤
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
});


// Q3_2020/인덱스 슬라이드 ------------------
$(function(){
	$('#slider5 .slider').prrpleSlider({
		autoPlay:true,				//play slider automatically?  //false 사용시 멈춤
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
});


// Q1_2021/sub01 슬라이드 ------------------
$(function(){
	$('#slider6 .slider').prrpleSlider({
		autoPlay:true,				//play slider automatically?  //false 사용시 멈춤
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
});

// 2022 메인 리뉴얼 > 건강레시피 슬라이드 ------------------
$(function(){
	$('.slider8 .slider').prrpleSlider({
		autoPlay:true,				//play slider automatically?  //false 사용시 멈춤
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
});


// 2022 메인 리뉴얼 > 여행 슬라이드 ------------------
$(function(){
	$('#slider9 .slider').prrpleSlider({
		multiple:2,
		autoPlay:true,				//play slider automatically?  //false 사용시 멈춤
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
});

// 스페셜페이지 PC~모바일 전체 
$(function(){
	let multiple
		function isMobile(){
		var UserAgent = navigator.userAgent; console.log(UserAgent)
		if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
		{
			return true;

		}else{

			return false;
		} 
	}
	

	if(isMobile()){
			multiple=2; //MO용 페이지
	}else{
			multiple=4; //PC용 페이지
	}
 $('#slider7 .slider').prrpleSlider({
		loop:true,
		autoPlay:true,	
		autoPlayInterval:	4000,	
		multiple:multiple,
		transitionTime:250,
		csstransforms:	false,
		richSwiping:false
  });
});



/* ------------------------------------------------------
		***** 스페셜페이지 tab  *****
--------------------------------------------------------*/






/* ------------------------------------------------------
		***** 메인비쥬얼 > TV 광고  *****
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

									//'<iframe class="grtyoutube-iframe" src="https://www.youtube.com/embed/'+settings.videoID+'?rel=0&wmode=transparent&autoplay='+settings.autoPlay+'&iv_load_policy=3&loop=1;playlist=amd7xMTGRS0" allowfullscreen frameborder="0" allow="autoplay; fullscreen"></iframe>'+
									'<iframe class="grtyoutube-iframe" src="https://www.youtube.com/embed/'+settings.videoID+'?rel=0&wmode=transparent&autoplay='+settings.autoPlay+'&iv_load_policy=3&loop=1;" allowfullscreen frameborder="0" allow="autoplay; fullscreen"></iframe>'+
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




/* 스페셜 탭  */
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
    $content.find('div').hide();
    $content.find("div:nth-child(" + settings.starting_tab + ")").show();
    $list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

    $("." + settings.list_class + ' li a').hover(function(e){
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
	$.fn.basicTabs3 = function(options){ /* basicTabs3 변경 */
		var settings = $.extend({
		  active_class: "current",
		  list_class: "tabs3", /* tabe3 변경*/
		  content_class: "tab_content3", /* tab_content3 */
		  starting_tab: 1
		}, options );
		var $content = $('.' + settings.content_class);
		var $list = $('.' + settings.list_class);
		$content.find('div').hide();
		$content.find("div:nth-child(" + settings.starting_tab + ")").show();
		$list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

		$("." + settings.list_class + ' li a').hover(function(e){
			$list.find("li").removeClass(settings.active_class);
			$("." + settings.content_class + " > div").hide();
			$(this).parent().addClass(settings.active_class);
			var currentTab = $(this).attr('href');
			$(currentTab).show();
			e.preventDefault();
		});
	  };
}( jQuery ));
