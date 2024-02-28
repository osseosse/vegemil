/*================================
 * 	 �귣�� > swiper
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


/*================================
 * 	 ��ǰ����Ʈ > ����� > swiper
 *================================*/
 $(document).ready(function(){
	 /*
	var swiper = new Swiper(".listSwiper01", {
		slidesPerView: 1,
		spaceBetween: 30,
		loop: true,
		pagination: {
		el: ".swiper-pagination",
		clickable: true,
		},
		navigation: {
		nextEl: ".listSwiper01-botton .swiper-button-next",
		prevEl: ".listSwiper01-botton .swiper-button-prev",
		},
	});

	var swiper = new Swiper(".listSwiper02", {
		slidesPerView: 1,
		spaceBetween: 30,
		loop: true,
		pagination: {
		el: ".swiper-pagination",
		clickable: true,
		},
		navigation: {
		nextEl: ".listSwiper02-botton .swiper-button-next",
		prevEl: ".listSwiper02-botton .swiper-button-prev",
		},
	});
	*/
	
	var classNames = ".listSwiper0";
	for(var i=0;i<42;i++) {
		
		if(i == 10) {
			classNames = ".listSwiper";
		}						
		var swiper = new Swiper(classNames + i, {
			slidesPerView: 1,
			spaceBetween: 30,
			loop: true,
			pagination: {
			el: ".swiper-pagination",
			clickable: true,
		},
			navigation: {
			nextEl: classNames + i + "-botton .swiper-button-next",
			prevEl: classNames + i + "-botton .swiper-button-prev",
		},
		});
	}

});

