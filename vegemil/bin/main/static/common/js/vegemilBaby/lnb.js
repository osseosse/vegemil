jQuery(function($){
	// Vertical Navigation
	var LNB = $('div.LNB');
	var LNB_i = LNB.find('>ul>li');
	var LNB_ii = LNB.find('>ul>li>ul>li');
	LNB_i.find('>ul').hide();
	LNB.find('>ul>li>ul>li[class=active]').parents('li').attr('class','active');
	LNB.find('>ul>li[class=active]').find('>ul').show();
	function LNBToggle(event){
		var t = $(this);
		if (t.next('ul').is(':hidden')) {
			LNB_i.find('>ul').slideUp(100);
			t.next('ul').slideDown(100);
		} else if (t.next('ul').is(':visible')){
			t.next('ul').show();
		} else if (!t.next('ul').langth) {
			LNB_i.find('>ul').slideUp(100);
		}
		LNB_i.removeClass('active');
		t.parent('li').addClass('active');
		return false;
	}
	LNB_i.find('>a[href=]').click(LNBToggle).focus(LNBToggle);
	function LNBActive(){
		LNB_ii.removeClass('active');
		$(this).parent(LNB_ii).addClass('active');
		return false;
	}; 
	LNB_ii.find('>a[href=]').click(LNBActive).focus(LNBActive);
	LNB.find('>ul>li>ul').prev('a').append('<span class="i"></span>');
	
	setNavigation();
	
});

jQuery(function($){
	// Vertical Navigation
	var LNB = $('div.LNB2');
	var LNB_i = LNB.find('>ul>table>td>li');
	var LNB_ii = LNB.find('>ul>table>td>li>ul>li');
	LNB_i.find('>ul').hide();
	LNB.find('>ul>li>ul>li[class=active]').parents('li').attr('class','active');
	LNB.find('>ul>li[class=active]').find('>ul').show();
	function LNBToggle(event){
		var t = $(this);
		if (t.next('ul').is(':hidden')) {
			LNB_i.find('>ul').slideUp(100);
			t.next('ul').slideDown(100);
		} else if (t.next('ul').is(':visible')){
			t.next('ul').show();
		} else if (!t.next('ul').langth) {
			LNB_i.find('>ul').slideUp(100);
		}
		LNB_i.removeClass('active');
		t.parent('li').addClass('active');
		return false;
	}
	LNB_i.find('>a[href=]').click(LNBToggle).focus(LNBToggle);
	function LNBActive(){
		LNB_ii.removeClass('active');
		$(this).parent(LNB_ii).addClass('active');
		return false;
	}; 
	LNB_ii.find('>a[href=]').click(LNBActive).focus(LNBActive);
	LNB.find('>ul>li>ul').prev('a').append('<span class="i"></span>');
	
	setNavigation();
	
});

function setNavigation() {
	var splitPath = $(location).attr('href').split('/');
	
	var path = "";
	var href ="";
	var flag ="";
	
	for(var i=3; i< splitPath.length; i++){
		path = path + "/" + splitPath[i];
	}
	
	$("div.LNB>ul>li>ul>li a").each(function(){
		 href = $(this).attr('href');
	
		if(path.substring(0,href.length) == href){
	
			$(this).parents('li').find('>ul').show();
			$(this).parents('li').attr('class','active');
			flag="1";
			return false;
		}
	});
	
	if(flag ==""){
		setNavigation2();
	}
	
}

function setNavigation2() {
	var splitPath = $(location).attr('href').split('/');
	var path = "";
	var href ="";
	
	
	for(var i=3; i< splitPath.length; i++){
		path = path + "/" + splitPath[i];
	}
	
	$("div.LNB>ul>li> a").each(function(){
		 href = $(this).attr('href');
	
		if(path == href){
			$(this).parents('li').attr('class','active');
			return false;
			
		}
	});
	
}



