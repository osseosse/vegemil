/*================================
 * 	 ºê·£µå > swiper
 *================================*/
 $(document).ready(function(){
	var swiper = new Swiper(".swiper-container", {
	  slidesPerView: "auto",
	  freeMode: true,
	  slideToClickedSlide: true,
	  spaceBetween: 0,
	  scrollbar: {
		el: ".swiper-scrollbar",
		draggable: true,
		dragSize: 100
	  },
	  mousewheel: true
	});
});
