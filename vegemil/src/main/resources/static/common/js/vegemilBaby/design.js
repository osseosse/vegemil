



/* Tab ����ǰ����Ʈ */
$(document).ready(function() {
	var imgWrapper = $('.newPT th img');
	imgWrapper.hide().filter(':first').show();
	$('table.newPT td a').click(function () {
		if (this.className.indexOf('current') == -1){
			imgWrapper.hide();
			imgWrapper.filter(this.hash).fadeIn(500);
			$('table.newPT td a').removeClass('current');

		}
		return false;
	});
});
/* // Tab ����ǰ����Ʈ */


/* Tab ����  */
jQuery(function($){
	var tab = $('.tab_line');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});
/* // Tab ����  */


/* Tab ���û�  */
jQuery(function($){
	var tab = $('.tab_line2');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line2:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line2').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});
/* // Tab ���û�  */


/* Tab �븮��  */
jQuery(function($){
	var tab = $('.tab_line3');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line3:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line3').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});
/* // Tab �븮��  */

/* Tab �̿���  */
jQuery(function($){
	var tab = $('.tab_line7');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line7:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line7').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});


/* Tab ���ȫ������  */
jQuery(function($){
	var tab = $('.tab_line4');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line4:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line4').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});


/* Tab �����ŷ� > ���������α׷����Ȳ  */
jQuery(function($){
	var tab = $('.tab_line5');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line5:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line5').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});


/* -----------------------------------------------------------
	Tab ��ǰ ����Ʈ �ۺ��̿� ��ũ��Ʈ
------------------------------------------------------------ 
$(document).ready(function(){
	$('#tabs div').hide(); 
	$('#tabs div:first').show(); 
	$('#tabs ul li:first').addClass('active');
	$('#tabs > ul > li > a').click(function(){ 
		$('#tabs ul li').removeClass('active');
		$(this).parent().addClass('active'); 
			var currentTab = $(this).attr('href'); 
			$('#tabs > div').hide(); 
			$(currentTab).show();
		return false;
		});
});
/* -----------------------------------------------------------
	// Tab ��ǰ ����Ʈ �ۺ��̿� ��ũ��Ʈ
------------------------------------------------------------ */


/* -----------------------------------------------------------
	Tab ��ǰ ����Ʈ ���߿� ��ũ��Ʈ
------------------------------------------------------------ */
function Right(str, n){

  if (n <= 0)
     return "";
  else if (n > String(str).length)
     return str;
  else {
     var iLen = String(str).length;
     return String(str).substring(iLen, iLen - n);
   }
}

function curSubPageActivate(){
			 
	var frmUrl;
	var curLiIndex;
	var gubun;
		
	frmUrl = location.href;
	// ����ǥ ������ ������ ������
	gubun = frmUrl.indexOf("?");
	frmUrl = frmUrl.substring(gubun+1);
	// &�� �����ڷ� �ɰ�
	split_array = frmUrl.split("&");
	// 0������ Contents 1��°�� Tot 2��°�� Cur
	curLiIndex = "Cur_" + Right(split_array[1],1);
	$('.cookTab li[id='+curLiIndex+']').addClass('active');										
}
		


$(document).ready(function(){
	
	var pathname = document.URL;
	var c_code = Right(pathname,3) ; 
	
	var realvalue = c_code.substring(0,1);
	
	/*�����Ȳ �븮��*/
	if(c_code == "ARE"){/* �븮�� ���� */	
		$('#LNB ul li[id="agencyRecruit"]').addClass('active');
	}
	else if(c_code == "OST"){
		$('#LNB ul li[id="officeStatus"]').addClass('active');
	}
	else if(c_code == "VEA"){/* ��ǰ ����Ʈ */	
		$('#LNB ul li[id="VegemilAdult"]').addClass('active');
	}
	else if(c_code == "NUT"){
		$('#LNB ul li[id="Nuts"]').addClass('active');
	}
	else if(c_code == "SUN"){
		$('#LNB ul li[id="Sunmond"]').addClass('active');
	}
	else if(c_code == "VEB"){
		$('#LNB ul li[id="VegemilBaby"]').addClass('active');
	}
	else if(c_code == "VEP"){
		$('#LNB ul li[id="VegemilPregnant"]').addClass('active');
	}
	else if(c_code == "WOO"){
		$('#LNB ul li[id="Woorian"]').addClass('active');
	}
	else if(c_code == "GRE"){
		$('#LNB ul li[id="Greenbia"]').addClass('active');
	}
	else if(c_code == "REP"){
		$('#LNB ul li[id="Reproduction"]').addClass('active');
	}
	else if(c_code == "ETC"){
		$('#LNB ul li[id="Etc"]').addClass('active');
	}
	else if(c_code == "NGS"){
		$('#LNB ul li[id="NewGoods"]').addClass('active');
	}/* Ŀ�´�Ƽ */
	else if(c_code == "CNC"){
		$('#LNB ul li[id="CookNCook"]').addClass('active');
	}
	else if(c_code == "AVG"){
		$('#LNB ul li[id="AboutVegemil"]').addClass('active');
	}
	else if(c_code == "SOY"){
		$('#LNB ul li[id="SoyStory"]').addClass('active');
	}/* ȫ������- ������ ���丮*/
	else if(c_code == "VES"){
		$('#LNB ul li[id="vegemilStory"]').addClass('active');
	}
	else if(c_code == "ADV"){
		$('#LNB ul li[id="advertise"]').addClass('active');
	}
	else if(c_code == "CFL"){
		$('#LNB ul li[id="cfList"]').addClass('active');
	}
	else if(c_code == "CML"){
		$('#LNB ul li[id="cmList"]').addClass('active');
	}
	else if(c_code == "ADE"){
		$('#LNB ul li[id="adEtcList"]').addClass('active');
		$('.LNB ul li[id="adEtcList"]').addClass('active');
	}
	else if(c_code == "MNL"){
		$('#LNB ul li[id="mediaNewsList"]').addClass('active');
		$('.LNB ul li[id="mediaNewsList"]').addClass('active');
	}
	else if(c_code == "MPL"){
		
		//$('#LNB ul li[id="mediaPrintList"]').addClass('active');
		//�ٷ� id��  �����ϵ��� ����
		$('#mediaPrintList').addClass('active');
	}
	else if(c_code == "VCT"){
		
		//�ٷ� id��  �����ϵ��� ����
		$('#videoContest').addClass('active');
	}
	else if(c_code == "WEB"){
		$('#LNB ul li[id="webzine"]').addClass('active');
	} /*�� ����*/
	else if(c_code == "CCM"){
		$('#LNB ul li[id="ccmsInfo"]').addClass('active');
	}
	else if(c_code == "SPL"){
		$('#supportList').addClass('active');
	}
	else if(c_code == "SPF"){
		$('#LNB ul li[id="supportFlow"]').addClass('active');
	}
	else if(c_code == "FAQ"){
		$('#faqList').addClass('active');
	}
	else if(c_code == "OLN"){
		$('#LNB ul li[id="offlineNews"]').addClass('active');
	}
	else if(c_code == "MYP"){
		$('#LNB ul li[id="myPage"]').addClass('active');
	} /*CP*/
	else if(c_code == "DCM"){
		$('#LNB ul li[id="cpDeclarMsg"]').addClass('active');
	}
	else if(c_code == "PGM"){
		$('#LNB ul li[id="cpProgram"]').addClass('active');
	}
	else if(c_code == "MNG"){
		$('#LNB ul li[id="cpManage"]').addClass('active');
	}
	else if(c_code == "MNA"){
		$('#LNB ul li[id="cpManual"]').addClass('active');
	}
	else if(c_code == "PDS"){
		$('#LNB ul li[id="cpPds"]').addClass('active');
	}
	else if(c_code == "CLA"){
		$('#LNB ul li[id="cpClaim"]').addClass('active');
	}
	else if(c_code == "PNE"){
		$('#LNB ul li[id="cPNews"]').addClass('active');
	}/*��ȸ ����*/
	else if(c_code == "SLS"){
		$('#LNB ul li[id="scholarship"]').addClass('active');
	}
	else if(c_code == "GNS"){
		$('#LNB ul li[id="goodneighbors"]').addClass('active');
	}
	else if(c_code == "HRT"){
		$('#LNB ul li[id="heart"]').addClass('active');
	}
	else if(c_code == "BLC"){
		$('#LNB ul li[id="bloodcancer"]').addClass('active');
	}
	else if(c_code == "SRV"){
		$('#LNB ul li[id="service"]').addClass('active');
	}/*�̺�Ʈ*/
	else if(c_code == "EVT"){
		$('#eventList').addClass('active');
	}
	else if(c_code == "LVT"){
		$('#lastEventList').addClass('active');
	}
	else if(c_code == "IDC"){
		$('#ideaContest').addClass('active');
	}

	
	
	//$('#categoryid li[id='+c_code+']').addClass('active');
	
	return false;
	
});

/* -----------------------------------------------------------
	// Tab ��ǰ ����Ʈ ���߿� ��ũ��Ʈ
------------------------------------------------------------ */


/* ��ǰ����Ʈ ���� */
$(document).ready(function(){
		$('a.pro_border').borderEffect({borderColor : '#bd3823', speed : 100, borderWidth: 9}); /* ��ǰ����Ʈ ���� */
		$('ul.listProduct > table tr:first-child td:first-child').borderEffect({borderColor : '#bd3823', speed : 100, borderWidth: 0}); /* ��ǰ����Ʈ�� ����κ� ���� */
});



/* quick */
(function($){
	$(document).ready(function() {
		//quick
	//	if (!$('#contents').length){return false};
		var limit =  $(window).height() - ($('#quick').height()+200);
		//alert(limit);
		if (limit > 0){
			$('#quick').addFloating({  
				targetRight: 10,  
				targetTop: 10,
				snap: true  
			});  
		} 
	});
})(jQuery);









/* ------------------------------------------------------
		***** slider������ *****
--------------------------------------------------------*/
$(window).load(function(){
	
	/* �귣��� > ���� */
	$('#slider4 .slider').prrpleSlider({
		autoPlay:true,				//play slider automatically?  //false ���� ����
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
	
});
