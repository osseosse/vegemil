/*****************************************************************************
*	�� ����Ʈ ��ü���� ���Ǵ� �ν��Ͻ� ����
*
*	SESON
*	�ۼ����� : 2013-07-16
//****************************************************************************/
//=============================================================================
// ���� ���� ���� (ȯ�漳��)
//=============================================================================
// GNB
var def = 0;

// �� �޴�
var stmnLEFT = 13; // ���� ���� 
var stmnGAP1 = 0; // ���� ���� 
var stmnGAP2 = 70; // ��ũ�ѽ� ������ ���ʰ� �������� �Ÿ� 
var stmnBASE = 70; // ��ũ�� ������ġ 
var stmnActivateSpeed = 15;
var stmnScrollSpeed = 10;
var stmnTimer;

// ����������
var oEditors = [];

// V�������Ǿ� ���� ����/���� ����
var CyberResearchOpenValue = "0";	// 1:����,  0:����

//=============================================================================
// ������ �ʱ�ȭ �۾�
//=============================================================================
$(document).ready(function(){
	// iframe, popup ���������� �ʱ�ȭ ���� �ʵ��� ó��
	// �ش� �������� <div id="pagePopup"> �߰� �ʿ�
	if($("#pagePopup").length < 1){
	
		// GNB �ʱ�ȭ
		gnbInit();
		
		// QuickMenu �ʱ�ȭ
		//quickMenuInit();
	}
	
	PopupView();
});


// ��Ű üũ �� �˾� ���
function PopupView(){
	// �˾�(��Ƽ)
	
	if(CookieRead("PopupView") != "1"){
		
		$("#PopupDiv2").show();
		//$("#PopupFrame2").attr("src","/Main/popup/PopupDetail.aspx");	                        
	} 
	
	else {
	
		$("#PopupDiv2").hide();
		$("#PopupFrame2").attr("src","about:blank");	                        
	}
	
	
	if(CookieRead("PopupView2") != "1"){
	
	
		$("#PopupDiv").show();
		$("#PopupFrame").attr("src","/Main/popup/PopupDetail3.aspx");	                        
	} 
	
	else {
	
		$("#PopupDiv").hide();
		$("#PopupFrame").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView3") != "1"){
		$("#PopupDiv3").show();              
	} else {
		$("#PopupDiv3").hide();
		$("#PopupFrame3").attr("src","/Main/popup/PopupDetail3.aspx");	                        
	}
	
	if(CookieRead("PopupView4") != "1"){
		$("#PopupDiv4").show();              
	} else {
		$("#PopupDiv4").hide();
		$("#PopupFrame4").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView5") != "1"){
		$("#PopupDiv5").show();              
	} else {
		$("#PopupDiv5").hide();
		$("#PopupFrame5").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView6") != "1"){
		$("#PopupDiv6").show();              
	} else {
		$("#PopupDiv6").hide();
		$("#PopupFrame6").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView7") != "1"){
		$("#PopupDiv7").show();              
	} else {
		$("#PopupDiv7").hide();
		$("#PopupFrame7").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView8") != "1"){
		$("#PopupDiv8").show();              
	} else {
		$("#PopupDiv8").hide();
		$("#PopupFrame8").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView9") != "1"){
		$("#PopupDiv9").show();              
	} else {
		$("#PopupDiv9").hide();
		$("#PopupFrame9").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView10") != "1"){
		$("#PopupDiv10").show();              
	} else {
		$("#PopupDiv10").hide();
		$("#PopupFrame10").attr("src","about:blank");	                        
	}
}
//-----------------------------------------------------------------------------
// GNB ����
//-----------------------------------------------------------------------------
function gnbInit(){
	var showin = function() {
		$(this).siblings("div").slideDown(320);
		$(this).parent("li").siblings("li").find("div").slideUp(1);
		var s = this;
		// �̹��� �������� ����
		$(this).parent('li').siblings().each(function() {
			if($(this).children('a').children('img').prop('src').indexOf('_on') > 0) {
				$(this).children('a').children('img').prop('src', $(this).find('img').prop('src').substr(0, $(this).find('img').prop('src').lastIndexOf('_'))+'.png');
			}
		});
		if($(this).find('img').prop('src').indexOf('_on') < 0) {
			$(this).find('img').prop('src', $(this).find('img').prop('src').substr(0, $(this).find('img').prop('src').lastIndexOf('.'))+'_on.png');
		}
	}
	var showout = function() {
		//alert("showout");
		$(this).siblings().each(function() {
			//alert($("#gnb>li>div>ul>li>a>img").size());
			if($("#gnb>li>div>ul>li>a>img").prop('src').indexOf('_on') > 0) {
				//$($("#gnb>li>div>ul>li>a>img").prop('src', $($("#gnb>li>div>ul>li>a>img").prop('src').substr(0, $($("#gnb>li>div>ul>li>a>img").prop('src').lastIndexOf('_'))+'.png');
			} else {
			}
		});
	}
	var showoutSub = function() {
	}
	$("#gnb>li>a").mouseover(showin).focusin(showin).mouseleave(showout);
	$('#gnb').mouseleave(function(){
		$("#gnb>li>a").siblings('div').css({'display':'none'});
		$("#gnb>li>a").each(function(){
			// ���� �޴��� Ȱ��ȭ �Ǿ� ������, ��ü �ʱ�ȭ
			if($(this).index() != (def-1) && $(this).children('img').prop('src').indexOf('_on') > 0) {
				$(this).children('img').prop('src', $(this).children('img').prop('src').substr(0, $(this).children('img').prop('src').lastIndexOf('_'))+'.gif');
			}
		});
		// ���� �޴� �� ���õ� �׸� _on ���� ǥ��
		if($("#gnb>li>a>img").eq(def-1).prop('src').indexOf('_on') < 0 && def > 0) {
			$("#gnb>li>a>img").eq(def-1).prop('src', $("#gnb>li>a>img").eq(def-1).prop('src').substr(0, $("#gnb>li>a>img").eq(def-1).prop('src').lastIndexOf('.'))+'_on.gif');
		}

	});
	$('#gnb a:last').bind('focusout',function(){
		$('.gnb08').hide();
		$('#gnb li div').hide();
	});
	/* 2013-04-29 �߰� */
	$("#gnb>li>div>ul>li>a").mouseover(showin).focusin(showin).mouseleave(showoutSub);
	/* 2013-07-30 �߰� */
	$('#gnb>li>div>ul>li>a').mouseleave(function(){
		$("#gnb>li>div>ul>li>a").siblings('div').css({'display':'none'});
		$("#gnb>li>div>ul>li>a").each(function(){
			// ���� �޴��� Ȱ��ȭ �Ǿ� ������, ��ü �ʱ�ȭ
			if($(this).index() != (def-1) && $(this).children('img').prop('src').indexOf('_on') > 0) {
				$(this).children('img').prop('src', $(this).children('img').prop('src').substr(0, $(this).children('img').prop('src').lastIndexOf('_'))+'.png');
			}
		});
	});
	/* // 2013-07-30 �߰� */
	/*
	$('#gnb').mouseleave(function(){
			if($("#gnb>li>a>img").eq(def-1).prop('src').indexOf('_on')<0&&def>0){
			$("#gnb>li>div>ul>li>a").eq(def-1).prop('src',$("#gnb>li>div>ul>li>a").eq(def-1).prop('src').substr(0,$("#gnb>li>div>ul>li>a").eq(def-1).prop('src').lastIndexOf('.'))+'_on.png');} 
	})
		$('#gnb a:last').bind('focusout',function(){
		$('.gnb08').hide();
	});
	*/
}
//-----------------------------------------------------------------------------
// �� �޴�
//-----------------------------------------------------------------------------
function quickMenuRefresh() { 
	var stmnStartPoint, stmnEndPoint; 
	stmnStartPoint = parseInt(document.getElementById('quick').style.top, 10); 
	stmnEndPoint = Math.max(document.documentElement.scrollTop, document.body.scrollTop) + stmnGAP2; 
	if (stmnEndPoint < stmnGAP1) stmnEndPoint = stmnGAP1; 
	if (stmnStartPoint != stmnEndPoint) { 
		stmnScrollAmount = Math.ceil( Math.abs( stmnEndPoint - stmnStartPoint ) / 15 ); 
		document.getElementById('quick').style.top = parseInt(document.getElementById('quick').style.top, 10) + ( ( stmnEndPoint<stmnStartPoint ) ? -stmnScrollAmount : stmnScrollAmount ) + 'px'; 
		stmnRefreshTimer = stmnScrollSpeed; 
	}
	stmnTimer = setTimeout("quickMenuRefresh();", stmnActivateSpeed); 
} 
function quickMenuInit() {
	document.getElementById('quick').style.right = stmnLEFT + 'px'; 
	document.getElementById('quick').style.top = document.body.scrollTop + stmnBASE + 'px'; 
	quickMenuRefresh();
}
//--------------------------------------------------------------------------------
// ���� ����޴� ���� ����
//--------------------------------------------------------------------------------
function LeftMenuSelected(leftMenuM, leftMenuS){
	//var leftMenuSwf = window.document.getElementById("leftMenu");
	//leftMenuSwf.SetVariable("m", eval(leftMenuM));
	//leftMenuSwf.SetVariable("s", eval(leftMenuS));
}
//-----------------------------------------------------------------------------
// �����ȣ �˾�
//-----------------------------------------------------------------------------
// ����
function PopZipcode(){
	$("#ZipcodeFrame").attr("src","/main/include/zipcodeCheck.aspx");
	$("#ZipcodeDiv").show();
}
// �ݱ�
function PopZipcodeClose(){
	$("#ZipcodeFrame").attr("src","about:blank");
	$("#ZipcodeDiv").hide();
}
// �θ�â���� �ݱ�
function PopParentZipcodeClose(){
	window.parent.$("#ZipcodeFrame").attr("src","about:blank");
	window.parent.$("#ZipcodeDiv").hide();
}
//-----------------------------------------------------------------------------
// ���� ������ ����
//-----------------------------------------------------------------------------
// ���� - �븮�� ���� üũ
function agencyEnterClick(){
	if(event.keyCode == 13){
		self.focus();		// �� ������ ���� ��� ����� �������� �ʴ´�.(��, ����Ʈ���� �Ͼ��.)
		agencyResultList();	// �˾�
		return false;
	}
}
// ���� - �븮�� �˻� �˾�
function agencyResultList(){
	//var str = document.Form1.txtFind.value;
	//window.open("/main/Company/AgencyFindSmallResult.aspx?n="+str,"agencyResultList","left=0,top=0,width=620,height=200," +
	//	"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	window.open("/main/company/agencyFindSmallResult.aspx","agencyResultList","left=0,top=0,width=620,height=400," +
		"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
}
// ���� - �븮�� �˻� �˾� �˻� ��ư
function agencyResultPopup(){
	self.focus();
	var str = document.getElementById("txtFind").value;
	if(str.length > 0) {
		document.location.href("/main/company/agencyFindSmallResult.aspx?n=" + str);
	} else {
		document.location.href("/main/company/agencyFindSmallResult.aspx");
	}
}
// ���� - �˾� ����
function pageDefaultClearAllPopup(){
	$("#PopupDiv").hide("slow");
	$("#PopupFrame").attr("src","about:blank");
}
// ���� - �θ�â �˾� ����
function pageDefaultParentPopupClose(){
	window.parent.$("#PopupDiv").hide("slow");
	window.parent.$("#PopupFrame").attr("src","about:blank");
}
// ���� - ��Ű ���� �� ����
function pageDefaultParentOneDayClose(){
	CookieSave("PopupMulti", "1" , 1);
	pageDefaultParentPopupClose();
}
//-----------------------------------------------------------------------------
// ���� �� �� ����
//-----------------------------------------------------------------------------
function historyTabView(num){
	var tabMenu1;
	var tabMenu2;
	// ��� �ݱ�
	for(i=1;i<6;i++){	// ���� ����
		tabMenu1 = eval("document.all.m"+i+".style");
		if(tabMenu1.display == "")
			tabMenu1.display = "none";
	}
	// 1���� ��ħ
	tabMenu2 = eval("document.all.m"+num+".style");
	tabMenu2.display = "";
}
//-----------------------------------------------------------------------------
// ���� �� �� ����
//-----------------------------------------------------------------------------
function familyTabView(num){
	var tabMenu1;
	var tabMenu2;
	// ��� �ݱ�
	for(i=1;i<5;i++){	// ���� ����
		tabMenu1 = eval("document.all.m"+i+".style");
		if(tabMenu1.display == "")
			tabMenu1.display = "none";
	}
	// 1���� ��ħ
	tabMenu2 = eval("document.all.m"+num+".style");
	tabMenu2.display = "";
}
//-----------------------------------------------------------------------------
// ����.Ư����Ȳ ��
//-----------------------------------------------------------------------------
function devPaperTabView(num)
{
	var tabMenu1;
	var tabMenu2;
	// ��� �ݱ�
	for(i=1;i<4;i++)	// ���� ����
	{
		tabMenu1 = eval("document.all.m"+i+".style");
		if(tabMenu1.display == "")
				tabMenu1.display = "none";
	}
	// 1���� ��ħ
	tabMenu2 = eval("document.all.m"+num+".style");
	tabMenu2.display = "";
}
//-----------------------------------------------------------------------------
// ��ǰ�Ұ�
//-----------------------------------------------------------------------------
// �� ���� �μ�
function ProductDetailDPrint(rCode, imgXD){
	var objWin = window.open('','ProductDetailDPrint','top=0,left=0,width='+imgXD+',height=1000'+
		',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizeble=no');
	objWin.focus();
	objWin.document.open();
	objWin.document.write('<'+'html'+'><'+'body bottommargin=0 leftmargin=0 rightmargin=0 topmargin=0'+'>');
	objWin.document.write('<'+'img'+' src=/upload/PRODUCTS/'+rCode+'_D.jpg'+' height=1050 />');	// ���������� ��� �μ�
	objWin.document.write('<'+'/body'+'><'+'/html'+'>');
	objWin.document.close();
	objWin.print();
	objWin.close();
}
// ���缺��ǥ ����
function ProductDetailSView(){
	if($("#DetailSDiv").is(":hidden")){
		$("#DetailSDiv").css("top",eval(document.body.scrollTop) + 20);
		$("#DetailSDiv").css("left",250);
		$("#DetailSDiv").show();
	}else{
		$("#DetailSDiv").hide();
	}
}
//-----------------------------------------------------------------------------
// ȫ������
//-----------------------------------------------------------------------------
// ������ ���丮 ����
function PopVegemilStory(){
	window.open("/main/publicCenter/vegemilStoryFrame.aspx","VegemilStory","fullscreen=yes,width=800,height=600,top=0,left=0"+
	",marginwidth=0,marginheight=0,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no");
}
function VSClose(){
	self.close();
}
// AD �÷���

function AdFlvPlay(flvFile, flvContent,subject, tabid){


  
  $('#labEtcList').html(subject);
  $('#ETCdownURL').attr("href" ,"http://www.vegemil.co.kr/Upload/PUBLIC/" + flvFile); 
  
  $('#ETCcfTabs ul li').removeClass('active');
  $('#ETCcfTabs ul li[id=' + tabid + ']').addClass('active');

}


/*
function AdFlvPlay(flvFile, flvContent){
	$("#FrameAdFlv").attr("src", "/main/publicCenter/advertPlayerFrame.aspx?f="+flvFile+"&s="+flvContent);
}
*/


// TV-CF �÷���
function CfFlvPlay(flvFile, flvSubject, flvDate){
	$("#FrameCfFlv").attr("src","/main/publicCenter/cfPlayerFrame.aspx?f="+flvFile+"&s="+flvSubject+"&d="+flvDate);
}
// AD ������ �۰��� ���̱�
function AdFlvCopyView(flvFile, flvContent) {
	var cd = document.getElementById('CopyDiv');
	var ca = document.getElementById('CopyArea');
	var leftsize = GetXYLeft("btnCopy");
	cd.style.left = eval(leftsize)-400+"px";
	var topsize = GetXYTop("btnCopy");
	cd.style.top = eval(topsize)-335+"px";
	cd.style.display = "block";
	ca.value = "<iframe id='FrameFlv' name='FrameFlv' width='525' height='488' frameborder='0' scrolling='no' src='/main/publicCenter/advertPlayerFrame.aspx?f="+flvFile+"&s="+flvContent+"'></iframe>";
	ca.focus;
}
// CF ������ �۰��� ���̱�
function CfFlvCopyView(flvFile, flvSubject, flvDate) {
	var cd = document.getElementById('CopyDiv');
	var ca = document.getElementById('CopyArea');
	var leftsize = GetXYLeft("btnCopy");
	cd.style.left = eval(leftsize)-400+"px";
	var topsize = GetXYTop("btnCopy");
	cd.style.top = eval(topsize)-335+"px";
	cd.style.display = "block";
	ca.value = "<iframe id='FrameFlv' name='FrameFlv' width='525' height='488' frameborder='0' scrolling='no' src='/main/publicCenter/cfPlayerFrame.aspx?f="+flvFile+"&s="+flvSubject+"&d="+flvDate+"'></iframe>";
	ca.focus;
}
// AD, CF ������ �۰��� ����
function FlvCopyHide() {
	$("#CopyArea").val("");
	$("#CopyDiv").hide();
}
// �μⱤ�� �̹��� �˾�
function MediaPrintView(idx){
	$("#OrgSizeDiv").css("top",0);
	//$("#OrgSizeDiv").css("left",350);
	$("#OrgSizeDiv").css("left",350);
	$("#OrgSizeDiv").show();
	$("#FrameImg").attr("src","/main/publicCenter/mediaPrintFrame.aspx?i="+idx);
}
// �μⱤ�� �˾� �ݱ�
function MediaPrintClose(){
	$("#FrameImg").attr("src","about:blank");
	$("#OrgSizeDiv").hide();
}
//-----------------------------------------------------------------------------
// ����
//-----------------------------------------------------------------------------
// ���� �˾�
function WebzinePopup(){
	window.open("/main/publicCenter/webzinePop.aspx","Webzine","width=1014,height=708,top=0,left=0"+
	",marginwidth=0,marginheight=0,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no");
}
// ����
function SetFlashVariable_List(){
	var goPage = 2;
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// ���� ������ �� �Ѱ���
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// ó��
function SetFlashVariable_First(){
	var goPage = 1;
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// ���� ������ �� �Ѱ���
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// ����
function SetFlashVariable_Prev(){
	var goPage = eval($("#webzinePage").val());	// ����������
	var wz = document.getElementById("WebZineID");
	if(goPage <= 1)	{
		goPage = 1;
		wz.SetVariable("gof", eval(goPage));		// ���� ������ �� �Ѱ���
		wz.SetVariable("NP", "");
		$("#webzinePage").val(goPage);
	}else{
		wz.SetVariable("gof", eval(goPage));		// ���� ������ �� �Ѱ���
		wz.SetVariable("NP", "prev");
		$("#webzinePage").val(goPage - 1);
	}
}
// ����
function SetFlashVariable_Next(){
	var goPage = eval($("#webzinePage").val());	// ����������
	var wz = document.getElementById("WebZineID");
	if(goPage >= eval($("#nowPage").val()))	{
		goPage = eval($("#nowPage").val());
		wz.SetVariable("gof", eval(goPage));		// ���� ������ �� �Ѱ���
		wz.SetVariable("NP", "");
		$("#webzinePage").val(goPage);
	}else{
		wz.SetVariable("gof", eval(goPage));		// ���� ������ �� �Ѱ���
		wz.SetVariable("NP", "next");
		$("#webzinePage").val(goPage + 1);
	}
}
// ������
function SetFlashVariable_Last(){
	var goPage = eval($("#nowPage").val());
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// ���� ������ �� �Ѱ���
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// �ٷΰ���
function SetFlashVariable_Go(){
	var goPage = eval($("#webzinePage").val());	// ����������
	if(goPage < 1)	goPage = 1;
	if(goPage > eval($("#nowPage").val()))
		goPage = eval($("#nowPage").val());
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// ���� ������ �� �Ѱ���
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// �÷��ÿ��� ������...
// getURL("javascript:getValue('gof��')");

// �÷��ÿ��� �� �ޱ�
function getValue(flashPage){
	$("#webzinePage").val(flashPage);
}
// �ε� ��Ʈ��
function Loading_hidden(){
	$("#LOAD_DIV").hide();
}
//-----------------------------------------------------------------------------
// ȸ�� ����
//-----------------------------------------------------------------------------
// IPIN Ȯ��
function JoinIpinCheck(){
	
	var IpinOpenOk = window.open("/Main/include/ipinRequest.aspx","IPINWindow","top=100,left=300,width=450,height=500");
	if(IpinOpenOk == null){
		alert(" �� ������ XP SP2 �Ǵ� ���ͳ� �ͽ��÷η� 7 ������� ��쿡�� \n    ȭ�� ��ܿ� �ִ� �˾� ���� �˸����� Ŭ���Ͽ� �˾��� ����� �ֽñ� �ٶ��ϴ�. \n\n�� MSN,����,���� �˾� ���� ���ٰ� ��ġ�� ��� �˾������ ���ֽñ� �ٶ��ϴ�.");
	}
}

// �޴��� ���� Ȯ��
function JoinHPCheck(){
	
	
	var HPOpenOk = window.open("/Main/include/hpRequest.aspx", 'PCCWindow', 'width=430, height=660, resizable=1, scrollbars=no, status=0, titlebar=0, toolbar=0, left=300, top=200' );
	if(HPOpenOk == null){
		alert(" �� ������ XP SP2 �Ǵ� ���ͳ� �ͽ��÷η� 7 ������� ��쿡�� \n    ȭ�� ��ܿ� �ִ� �˾� ���� �˸����� Ŭ���Ͽ� �˾��� ����� �ֽñ� �ٶ��ϴ�. \n\n�� MSN,����,���� �˾� ���� ���ٰ� ��ġ�� ��� �˾������ ���ֽñ� �ٶ��ϴ�.");
	}
	
}

// �޴��� ���� Ȯ��

function JoinHP_Check(){
	
	//alert('hhh');
	var HPOpenOk = window.open("/Main/event/hpRequest2.aspx", 'PCCWindow', 'width=430, height=660, resizable=1, scrollbars=no, status=0, titlebar=0, toolbar=0, left=300, top=200' );
	if(HPOpenOk == null){
		alert(" �� ������ XP SP2 �Ǵ� ���ͳ� �ͽ��÷η� 7 ������� ��쿡�� \n    ȭ�� ��ܿ� �ִ� �˾� ���� �˸����� Ŭ���Ͽ� �˾��� ����� �ֽñ� �ٶ��ϴ�. \n\n�� MSN,����,���� �˾� ���� ���ٰ� ��ġ�� ��� �˾������ ���ֽñ� �ٶ��ϴ�.");
	}
	
}


// ���̵� �ߺ�Ȯ��
function JoinIdCheck(oId){
	PopZipcodeClose();
	
	var topsize = GetXYTop(oId);
	var leftsize = GetXYLeft(oId);
	$("#IdCheckDiv").css("top",eval(topsize) + 22);
	$("#IdCheckDiv").css("left",eval(leftsize) - 50);

	$("#txtId").val('');
	$("#IdCheckFrame").attr("src","/main/member/joinIdCheck.aspx");
	$("#IdCheckDiv").show();
}
function JoinIdCheckForEday(oId){
	PopZipcodeClose();
	
	var var_edayYn = "0";
	var_edayYn = document.getElementById('edayYn').value;
	
	var topsize = GetXYTop(oId);
	var leftsize = GetXYLeft(oId);
	$("#IdCheckDiv").css("top",eval(topsize) + 22);
	$("#IdCheckDiv").css("left",eval(leftsize) - 50);
	
	$("#txtId").val('');
	$("#IdCheckFrame").attr("src","/main/member/joinIdCheck.aspx?edayYn=" + var_edayYn + "");
	$("#IdCheckDiv").show();
}
// �����ȣ �˻�
function JoinZipcode(oId){
	$("#IdCheckFrame").attr("src","about:blank");
	$("#IdCheckDiv").hide();
	
	var topsize = GetXYTop(oId);
	var leftsize = GetXYLeft(oId);
	$("#ZipcodeDiv").css("top",eval(topsize) + 22);
	$("#ZipcodeDiv").css("left",eval(leftsize) - 50);
	
	PopZipcode();
}
// ID �ߺ�Ȯ��
function IdCheckClose(f){
	if(f == 1){
		window.parent.$("#txtId").val($("#txtId").val());
	}else{
		window.parent.$("#txtId").val("");
	}
	window.parent.$("#IdCheckDiv").hide();
	window.parent.$("#IdCheckFrame").attr("src","about:blank");
	window.parent.$("#txtPwd1").focus();
}
// �����ȣ �˻�
function EditZipcode(oName)
{
	var topsize = GetXYTop(oName.id);
	var leftsize = GetXYLeft(oName.id);
	$("#ZipcodeDiv").css("top",eval(topsize) + 22);
	$("#ZipcodeDiv").css("left",eval(leftsize) - 50);
	PopZipcode();
}

// ���θ��ּ� API ��� �˻�
function EditZipcodeNew()
{
	var pop = window.open("/Main/include/jusoPopup.asp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}

function EditZipcodeNew2()
{
	var pop = window.open("/Main/include/jusoPopup2.asp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}
//-----------------------------------------------------------------------------
// CP
//-----------------------------------------------------------------------------
// �����ؼ� �޼���
function DeclarMsgOpen(no){
	switch(no){
		case "1": $("#DeclarImg").attr("src","/main/images/cp/detail0106.jpg"); break;
		case "2": $("#DeclarImg").attr("src","/main/images/cp/detail0107.jpg"); break;
		case "3": $("#DeclarImg").attr("src","/main/images/cp/detail0109.jpg"); break;
		case "4": $("#DeclarImg").attr("src","/main/images/cp/detail0110.jpg"); break;
		case "5": $("#DeclarImg").attr("src","/main/images/cp/detail0111.jpg"); break;
		case "6": $("#DeclarImg").attr("src","/main/images/cp/detail0112.jpg"); break;
		case "6": $("#DeclarImg").attr("src","/main/images/cp/detail0113.jpg"); break;
		default : $("#DeclarImg").attr("src","/main/images/cp/detail0113.jpg"); break;	// �ʱ� ȭ��
	}
}
// �����ؼ� ������Ȳ
function ManagerView(no){
	if(no == "m1"){
		$("#m1").show();
		$("#m2").hide();
	}
	if(no == "m2"){
		$("#m2").show();
		$("#m1").hide();
	}
}
// EBook ��ũ��Ʈ
function CpOpenEbook(uri,page){
	window.open("/main/cp/cpEbookView.aspx?uri="+uri+"&page="+page,"NewWindow",
		"left=0,top=0,toolbar=no,location=no,directories=no,status=no,fullscreen=no,menubar=no,scrollbars=no,resizable=yes"+
		",width=1014,height=730");
}
// CP �ڷ��
function CpPdsOpen1(){
	$("#PdsDiv").show();
	$("#labTitle").text("������ CP���� �ڷ�");
	$("#PdsFrame").attr("src", "/main/cp/cpPdsFrame.aspx?c=1");
	$("#PdsFrame").focus();
}
function CpPdsOpen2(){
	$("#PdsDiv").show();
	$("#labTitle").text("CP��� ����");
	$("#PdsFrame").attr("src","/main/cp/cpPdsFrame.aspx?c=2");
	$("#PdsFrame").focus();
}
function CpPdsClose(){	
	$("#PdsFrame").attr("src","about:blank");
	$("#PdsDiv").hide();
}
// �̸��� ����
function ClaimEmailChange(dName)
{
	if(document.getElementById(dName).options[document.getElementById(dName).selectedIndex].value == "0" ||
		document.getElementById(dName).options[document.getElementById(dName).selectedIndex].value == "9")
		$("#txtEmail2").show();
	else
		$("#txtEmail2").hide();
}
//-----------------------------------------------------------------------------
// �̺�Ʈ
//-----------------------------------------------------------------------------
// ���� �������� �̺�Ʈ ������ ����
function EventPageSet(){
	// 1.��������
	//$("#OrderDiv").hide();
	//$("#InputDiv").show();
	//$("#txtCode").focus();
	
	// 2.���𸶰�
	//alert("�����մϴ�.\n�̺�Ʈ ���� �����Ǿ����ϴ�.\n\n* ��÷�� ��ǥ : 2013-06-07");
	//top.location.href = "/main/event/list.aspx";

	// 3.��÷�ڹ�ǥ
	$("#InputDiv").hide();
	$("#OrderDiv").show();
	$("#OrderFrame").attr("src","/main/event/e20130415_Mart_Order.htm");
	alert("��÷�� ����Ʈ�� Ȯ���Ͻñ� �ٶ��ϴ�.");
	$("#OrderFrame").focus();
}
//-----------------------------------------------------------------------------
// V�������Ǿ�
//-----------------------------------------------------------------------------
// ���հԽ��� ���� �޴� ����
/*
function CyberBoardLeftMenu(){
	var params = GetQueryStrings();	// ��ũ��Ʈ�� �� �ޱ�
	var bcate = params["c"];		// flv ���ϸ�
	if(typeof(bcate) != "undefined"){
		var menuM = "";
		var menuS = "";
		switch(bcate) {	// �޴���ȣ
			case "00": menuM="1"; menuS="1"; break;	// ��������
			case "01": menuM="2"; menuS="1"; break;	// �о��о� ����
			case "02": menuM="2"; menuS="2"; break;	// ���ִ� ������
			case "04": menuM="1"; menuS="2"; break;	// ������ �ҽ�
			case "07": menuM="4"; menuS="1"; break;	// �ñ��ؿ�
			case "08": menuM="2"; menuS="3"; break;	// ������ ������
			default  : menuM=""; menuS=""; break;
		}
		LeftMenuSelected(menuM, menuS);
	}
}
*/
// ���������� ��Ų ���� (�������� ����)
function CyberTextEditSkin(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "ir1",
		sSkinURI: "/main/SE/SmartEditor2Skin_CyberBoardWrite.html",
		fCreator: "createSEditor2"
	});
}
// ���������� ������ ����
function CyberTextEditSave(){
	// �������� ������ ������ �����ÿ� ����ߴ� textarea�� �־� �ݴϴ�.
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	// �������� ���뿡 ���� �� ������ �̰����� document.getElementById("ir1").value�� �̿��ؼ� ó���ϸ� �˴ϴ�.
	try{
		// �� ������ ���� ��� ���� ���� ���� �޶����� �ֽ��ϴ�.
		document.Form1.submit();
	}
	catch(e){}
}
//-----------------------------------------------------------------------------f
// Flash �ε�
//-----------------------------------------------------------------------------
// FlashWrite(���̵�, ���ϰ��, ����, ����[, ����][,����][,��������])

function FlashWrite(id,url,w,h,vars,bg,win){


	if(id == null){
		id = url.split("/")[url.split("/").length-1].split(".")[0];	// id�� ������ ���ϸ����� ����
		if(document.forms.length > 0){id = document.forms[0].id;}
	}
	if(vars == null)	{vars='';}
	if(bg == null)		{bg='#FFFFFF';}
	if(win == null)		{win='transparent';}
	var FlashStr= "<OBJECT classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000'";
		FlashStr+="	codebase='https://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0'";
		FlashStr+="	width='"+w+"'";
		FlashStr+="	height='"+h+"'";
		FlashStr+="	id='"+id+"'";
		FlashStr+="	align='middle'>";
		FlashStr+="<PARAM name='allowScriptAccess' value='always' />";
		FlashStr+="<PARAM name='movie' value='"+url+"' />";
		FlashStr+="<PARAM name='FlashVars' value='"+vars+"' />";
		FlashStr+="<PARAM name='wmode' value='transparent' />";
		FlashStr+="<PARAM name='menu' value='-1' />";
		FlashStr+="<PARAM name='quality' value='high' />";
		FlashStr+="<PARAM name='bgcolor' value='"+bg+"' />";
		FlashStr+="<PARAM name='loop' value='-1' />";
		FlashStr+="<EMBED src='"+url+"'";
		FlashStr+="	flashVars='"+vars+"'";
		FlashStr+="	wmode='transparent'";
		FlashStr+="	autostart='-1'";
		FlashStr+="	menu='-1'";
		FlashStr+="	quality='high'";
		FlashStr+="	bgcolor='"+bg+"'";
		FlashStr+="	width='"+w+"'";
		FlashStr+="	height='"+h+"'";
		FlashStr+="	id='"+id+"Embed'";
		FlashStr+="	name='"+id+"Embed'";
		FlashStr+="	align='middle'";
		FlashStr+="	allowScriptAccess='always'";
		FlashStr+="	type='application/x-shockwave-flash'";
		FlashStr+="	pluginspage='http://www.microsoft.com/windows/mediaplay/download/default.asp' alt='flash'/>";
		FlashStr+="</OBJECT>";
	document.write(FlashStr);
	
	
}


function MP4Write(id,url,w,h,vars,bg,win){

	if(id == null){
		id = url.split("/")[url.split("/").length-1].split(".")[0];	// id�� ������ ���ϸ����� ����
		if(document.forms.length > 0){id = document.forms[0].id;}
	}
	if(vars == null)	{vars='';}
	if(bg == null)		{bg='#FFFFFF';}
	if(win == null)		{win='transparent';}
	var FlashStr= "<video  controls>";
		FlashStr+="<source src='/Upload/PUBLIC/AD_20131017_40.mp4' type='video/mp4'>";
		FlashStr+="</video>";
		
		
	document.write(FlashStr);
	

}


//-----------------------------------------------------------------------------
// javascript �� URL querystring �ޱ�
//-----------------------------------------------------------------------------
/*
function GetQueryStrings(){ 
	var assoc = {};
	var decode = function(s){return decodeURIComponent(s.replace(/\+/g, " "));};
	var queryString = location.search.substring(1); 
	var keyValues = queryString.split('&'); 
	for(var i in keyValues){ 
		var key = keyValues[i].split('=');
		if (key.length > 1){
			assoc[decode(key[0])] = decode(key[1]);
		}
	}
	return assoc;
}
*/
//-----------------------------------------------------------------------------
// ��Ű ���
//-----------------------------------------------------------------------------
function CookieRead(cookieName){
	var thisCookie = document.cookie.split("; ");
	for(i=0; i<thisCookie.length; i++){
		if(cookieName == thisCookie[i].split("=")[0]){
			return thisCookie[i].split("=")[1];
		}
	}
	return "x";
}
function CookieSave(name, value, expiredays){ 
	var todayDate = new Date(); 
	todayDate.setDate( todayDate.getDate() + expiredays ); 
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" 
}

//-----------------------------------------------------------------------------
// �Է��� �ؽ�Ʈ ����Ʈ ���
//-----------------------------------------------------------------------------
function realCharCount(tName, nName, mName, maxByte){
	var strCount = 0;
	var tempStr, tempStr2; 
	for(i = 0;i < document.getElementById(tName).innerHTML.length;i++){
		tempStr = document.getElementById(tName).innerHTML.charAt(i);
		if(escape(tempStr).length > 4) {strCount += 2;}
		else {strCount += 1;}
	}
	if (strCount > maxByte){
		alert("�ִ� " + maxByte + "Byte �̹Ƿ� �ʰ��� ���ڴ� �ڵ����� �����˴ϴ�.");  
		strCount = 0;  
		tempStr2 = "";
		for(i = 0; i < document.getElementById(tName).innerHTML.length; i++){
			tempStr = document.getElementById(tName).innerHTML.charAt(i); 
			if(escape(tempStr).length > 4) {strCount += 2;}
			else {strCount += 1;}
			if(strCount > maxByte){
				if(escape(tempStr).length > 4){strCount -= 2;}
				else {strCount -= 1;}
				break;
			}
			else{
				tempStr2 += tempStr;
			}
		}
		document.getElementById(tName).innerHTML = tempStr2;
	}
	document.getElementById(nName).innerHTML = strCount;
	document.getElementById(mName).innerHTML = maxByte;
}

//-----------------------------------------------------------------------------
// ��ü ��ǥ�� ���� �Լ�
// obj : ������ ��ü
//-----------------------------------------------------------------------------
function getBounds(obj)
{
	var name = navigator.appName;
	var ver = navigator.appVersion;
	var ver_int = parseInt(navigator.appVersion);
	var ua = navigator.userAgent, infostr;
	
	var ret = new Object();
    if(name == "Microsoft Internet Explorer"){
		// IE �϶�
		var rect = obj.getBoundingClientRect();
		ret.left = rect.left + (document.documentElement.scrollLeft || document.body.scrollLeft);
		ret.top = rect.top + (document.documentElement.scrollTop || document.body.scrollTop);
		ret.width = rect.right - rect.left;
		ret.height = rect.bottom - rect.top;
	}else{
		// IE�� �ƴҶ�
		//var box = document.getBoxObjectFor(obj);
		var box = obj.getBoundingClientRect();
		
		ret.left = box.left + (document.documentElement.scrollLeft || document.body.scrollLeft);
		ret.top = box.top + (document.documentElement.scrollTop || document.body.scrollTop);
		ret.width = box.right - box.left;
		ret.height = box.bottom - box.top;
		//ret.left = box.left;
		//ret.top = box.top;
		//ret.width = box.width;
		//ret.height = box.height;
		
	}
	return ret;
}
// ��ü LEFT ��ǥ
function GetXYLeft(n){
	var obj = getBounds(document.getElementById(n));
	return obj.left;
}
// ��ü TOP ��ǥ
function GetXYTop(n){
	var obj = getBounds(document.getElementById(n));
	return obj.top;
}
// ��ü WIDTH ��ǥ
function GetXYWidth(n){
	var obj = getBounds(document.getElementById(n));
	return obj.width;
}
// ��ü HEIGHT ��ǥ
function GetXYHeight(n){
	var obj = getBounds(document.getElementById(n));
	return obj.height;
}

function resize(img) {
			// ���� �̹��� ������ ����
			var width = img.width;
			var height = img.height;
    
			// ����, ���� �ִ� ������ ����
			var maxWidth = 550;
			var maxHeight = 540;
			var resizeWidth;
			var resizeHeight;
    
			// �̹��� ���� ���ϱ�
			var basisRatio = maxHeight / maxWidth;
			var imgRatio = height / width;

			if (imgRatio > basisRatio) {
			// height�� ���� �������� ���.
        
				if (height > maxHeight) {
					resizeHeight = maxHeight;
					resizeWidth = Math.round((width * resizeHeight) / height);
				} else {
					resizeWidth = width;
						resizeHeight = height;
				}
			} else if (imgRatio < basisRatio) {
				// width�� ���� �������� ���.
        
				if (width > maxWidth) {
					resizeWidth = maxWidth;
					resizeHeight = Math.round((height * resizeWidth) / width);
				} else {
					resizeWidth = width;
					resizeHeight = height;
				}
        
			} else {
				// ���� ������ ������ ���
				resizeWidth = width;
				resizeHeight = height;
			}
    
			// ���������� ũ��� �̹��� ũ�� �ٽ� ����
			img.width = resizeWidth;
			img.height = resizeHeight;
		}


function  fsOnLoad(frm){
	
	var now = new Date();
	var month = now.getMonth();
	var year = now.getFullYear();
	var yy = frm.searchControl1_year;
	yy.length = 10;
	
	for(var i=0; i<yy.length;i++){
		yy.options[i].value=year+3-i;
		yy.options[i].text=year+3-i;
	
		/*
		if( year == year +1){
			yy.options[i].selected =true;
		}
		*/
		
	}
	
	
	var mm = frm.searchControl1_month;
	mm.length =12;
	
	for(var x=0;x<mm.length;x++){
		mm.options[x].value=x+1;
		mm.options[x].text=x+1;
		
		/*
		if(month+1==x+1){
			mm.options[x].selected=true;
		}
		
		if(month==x+1){
			mm.options[x].selected=true;
		}
		*/
	}
	
	dayChange(frm);
}

function dayChange(frm){
	
	var dd = frm.searchControl1_day;
	var now = new Date();
	var date = now.getDate();
	var dArr = ['0','31','29','31','30','31','30','31','31','30','31','30','31'];
	dd.length = parseInt(dArr[frm.searchControl1_month.value]);
	
	var gYear = parseInt(frm.searchControl1_year.value);
	
	if(dd.length == 29){
		if((gYear%4 ==0 && gYear%100 !=0)||(gYear%400==0)){
			dd.length;
		}else{
			--dd.length;
		}
	}
	
	for(var y=0; y < dd.length; y++){
		dd.options[y].value=y+1;
		dd.options[y].text=y+1;
		
		/*
		if(date == y+1){
			dd.options[y].selected=true;
			//dd.options[y].value = y+1;
			
		}
		*/
	}
	
	
}
 
/*������Ʈ �� �޾ƿ���*/
function Request(valuename) //javascript�� ������ Request 
{ 
	var rtnval = ""; 
	var nowAddress = unescape(location.href); 
	var parameters = (nowAddress.slice(nowAddress.indexOf("?")+1,nowAddress.length)).split("&"); 

	for(var i = 0 ; i < parameters.length ; i++){ 
		var varName = parameters[i].split("=")[0]; 
		if(varName.toUpperCase() == valuename.toUpperCase()) { 
			rtnval = parameters[i].split("=")[1]; 
			break; 
		} 
	} 

	return rtnval; 
}
