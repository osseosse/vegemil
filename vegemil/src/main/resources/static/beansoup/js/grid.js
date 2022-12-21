/*====================================================== 
	BROOK > main.js
=======================================================*/
    (function ($) {
        'use strict';

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


})(jQuery);
/*====================================================== 
	// BROOK > main.js
=======================================================*/







