///// Section-1 CSS-Slider /////    
  // Auto Switching Images for CSS-Slider
  function bannerSwitcher() {
    next = $('.sec-1-input').filter(':checked').next('.sec-1-input');
    if (next.length) next.prop('checked', true);
    else $('.sec-1-input').first().prop('checked', true);
  }

  var bannerTimer = setInterval(bannerSwitcher, 5000);

  $('nav .controls label').click(function() {
    clearInterval(bannerTimer);
    bannerTimer = setInterval(bannerSwitcher, 5000)
  });


///// Anchor Smooth Scroll /////
//   $('.main-menu a, .learn-more-button a').click(function(e){
    
//     e.preventDefault();
        
//     var target = $(this).attr('href');
        
//     $('html, body').animate({scrollTop: $(target).offset().top}, 1000);
//     return false;
//   });





/* --------------------------
	Å×¸¶ Tab 
--------------------------- */
jQuery('.tab-menu-item').click(function(){
	var _this 		= jQuery(this);
	var _menu_index	= _this.index();

	jQuery(this).addClass('w-p-active-tab-item').siblings().removeClass('w-p-active-tab-item');
	jQuery('.tab-tab-item').eq(_menu_index).addClass('w-p-active-tab-item').siblings().removeClass('w-p-active-tab-item');

	jQuery('.tab-tab-item').eq(_menu_index).addClass('w-p-active-tab-item');
	jQuery('.tab-tab-item').not(':eq(' + _menu_index + ')').removeClass('w-p-active-tab-item');

	// Menu item
	jQuery('.tab-menu-item').removeClass('tab-menu-item-active');
	_this.addClass('tab-menu-item-active');

});



/* --------------------------
	scrolling-parallax
--------------------------- */
$('div.parallaxBackground').each(function(){
  var item = $(this);

  item.find('.art').css({
        position:'absolute',
        left: (item .width() - $('.art').width())/2,
        top: (item .height() - $('.art').height())/2
    });
 
  $(window).scroll(function() {

    var yPosition = -($(window).scrollTop() / item.data('scroll-speed')); 

    var backgroundPosition = '50% '+ yPosition + 'px';


    item.css('background-position', backgroundPosition );
    

  }); 
   
   $(window).resize(function() {
 
   item.find('.art').css({
        position:'absolute',
        left: (item .width() - $('.art').width())/2,
        top: (item .height() - $('.art').height())/2
    });
  
});
});