 $(document).ready(function(){
	/********** DEFAULT **********/
	$('#slider0 .slider').prrpleSlider({
		autoPlay:true,	
		autoPlayInterval:	4000,	
	}); 

		/********** DEFAULT **********/
	$('#slider1 .slider').prrpleSlider({
		autoPlay:true,	
		autoPlayInterval:	4000,	
	}); 
});






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
	$('#gnb>ul>li.drop').mouseover(function(){
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






