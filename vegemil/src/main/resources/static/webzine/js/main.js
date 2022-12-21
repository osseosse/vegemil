



(function ($) {
	"use strict";


    jQuery(document).ready(function($){
        
        // Responsive Embed
        $(".embed-responsive iframe").addClass("embed-responsive-item");
        
        // Auto carousel class
        $(".carousel-inner .item:first-child").addClass("active");

        // Bootstrap tooltips
        $('[data-toggle="tooltip"]').tooltip();    

        // jQuery Instafeed
        // var userFeed = new Instafeed({
        //    limit: 12,
        //    get: 'tagged',
        //    tagName: 'nature',
        //    accessToken: '1189321718.467ede5.59564e795f3d4cedbd2d5c5a33b03ca1',
		//    template: '<a target="_blank" href="{{link}}"><img src="{{image}}" /></a>'
		//  });
        // userFeed.run(); 

        // Parallax
        // $('.slide-bg, .breadcroumb-bg').scrolly({bgParallax: true});
        
        
        // Scroll to up
        $('.scroll-to-top a').click(function(){
            $('html, body').animate({scrollTop : 0},800);
            return false;
        });          

    });


    jQuery(window).load(function(){
        // Masonry
        jQuery('.masonry-blog').masonry({ singleMode: true });
        
        // Preloader
        jQuery(".preloader").fadeOut(500);
    });

	
    
    
}(jQuery));	    



