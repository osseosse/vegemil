/*****************************************************************************
*	�� ��������(������ ID ����) �ε� �Ϸ� �� �ʱ�ȭ
*
*	SESON
*	�ۼ����� : 2013-07-16
//****************************************************************************/
//=============================================================================
// �������� �ʱ�ȭ - ������ �ε��� �Ϸ� �Ǿ�����(�÷���, �̹��� ����) ����
// ������� : $(document).ready ==> $(window).ready ==> $(window).load
//=============================================================================
$(window).ready(function(){
	//-------------------------------------------------------------------------
	// ����
	//-------------------------------------------------------------------------
	if($("#pageIncludePopup").length > 0){					// �˾�
	}
	if($("#pageIncludeZipcode").length > 0){				// �����ȣ ã��
		$("#txtDong").focus();
	}
	if($("#pageAgencyPopup").length > 0){					// �븮�� �˻�
		$('#txtFind').focus();
	}
	//-------------------------------------------------------------------------
	// ����
	//-------------------------------------------------------------------------
	if($("#pageDefault").length > 0){						// ���� ������
		// �˾� üũ
		//if(CookieRead("PopupMulti") != "1"){
		//	$("#PopupFrame").attr("src","/main/include/popupMulti.aspx");
		//	$("#PopupDiv").show("slow");
		//}
		// ����ǰ �����̵� ����
		$('#mEventBnr').slides({preload:true, play:4000, pause:2500, slideSpeed:1000, hoverPause:true});
		
		// ����ǰ �����̵� ����
		$('#mEventBnr02').slides({preload:true, play:4000, pause:2500, slideSpeed:1000, hoverPause:true});

		// ���� ������ �ε�/���
		/* $(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			var flvSwf = document.getElementById("MainFlvPlayer");
			if(flvSwf == null || typeof(flvSwf.SetVariable) == "undefined") {
				flvSwf = document.getElementById("MainFlvPlayerEmbed");
			}
			flvSwf.SetVariable("/mc/flv:contentPath", "http://www.vegemil.co.kr/main/images/main/mainVisual_2800_30.flv");
			flvSwf.SetVariable("/mc/flv:autoPlay", "-1");
			flvSwf.SetVariable("/mc/flv:autoRewind", "-1");
			flvSwf.SetVariable("/mc/flv:isLive", "-1");
			flvSwf.SetVariable("/mc/flv:skin", "0");
		}); */
	}

	/* if($("#pageDefault").length > 0){						// ���� ������
		
		// ����ǰ �����̵� ����
		$('#mEventBnr02').slides({preload:true, play:4000, pause:2500, slideSpeed:1000, hoverPause:true});

		
	} */
	 
	if($("#pagePublicAdEtcList").length > 0){					// ��Ÿ�����ڷ�
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			//LeftMenuSelected("5", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvContent = $("#firstFlvContent").val();
			var firstFlvSubject = $("#firstFlvSubject").val();
			var firstTAB = $("#firstFlvTAB").val();
			AdFlvPlay(firstFlvFile, firstFlvContent,firstFlvSubject,firstTAB);
		});
	}
	//-------------------------------------------------------------------------
	// Ŀ�´�Ƽ
	//-------------------------------------------------------------------------
	/*
	if($("#pageCommunityRecipeList").length > 0){			// ������ > ����Ʈ
		LeftMenuSelected("1", "");
	}
	if($("#pageCommunityRecipeDetail").length > 0){			// ������ > ��
		LeftMenuSelected("1", "");
	}
	if($("#pageCommunityVegemilList").length > 0){			// �������ڼ����˱� > ����Ʈ
		LeftMenuSelected("2", "");
	}
	if($("#pageCommunityVegemilDetail").length > 0){		// �������ڼ����˱� > ��
		LeftMenuSelected("2", "");
	}
	if($("#pageCommunitySoyList").length > 0){				// ���̾߱� > ����Ʈ
		LeftMenuSelected("3", "");
	}
	if($("#pageCommunitySoyDetail").length > 0){			// ���̾߱� > ��
		LeftMenuSelected("3", "");
	}
	//-------------------------------------------------------------------------
	// ����Ұ�
	//-------------------------------------------------------------------------
	if($("#pageCompanyIntroduceDR").length > 0){			// �λ縻 > ��ȸ��
		LeftMenuSelected("1", "1");
	}
	if($("#pageCompanyIntroduceCEO").length > 0){			// �λ縻 > ��ǥ�̻�
		LeftMenuSelected("1", "2");
	}
	if($("#pageCompanyProfile").length > 0){				// ������� > ������
		LeftMenuSelected("2", "1");
	}
	if($("#pageCompanyHistory").length > 0){				// ������� > ����
		LeftMenuSelected("2", "2");
	}
	if($("#pageCompanyFamily").length > 0){					// ������� > ���û�
		LeftMenuSelected("2", "3");
	}
	if($("#pageCompanyCiMean").length > 0){					// CI�Ұ� > �ǹ�
		LeftMenuSelected("3", "1");
	}
	if($("#pageCompanyCiApply").length > 0){				// CI�Ұ� > ������
		LeftMenuSelected("3", "2");
	}
	if($("#pageCompanyCiUtil").length > 0){					// CI�Ұ� > Ȱ�뿹��
		LeftMenuSelected("3", "3");
	}
	if($("#pageCompanyCerHaccp").length > 0){				// ��ǰ����������Ȳ > HACCP
		LeftMenuSelected("4", "1");
	}
	if($("#pageCompanyCerIso").length > 0){					// ��ǰ����������Ȳ > ISO22000
		LeftMenuSelected("4", "2");
	}
	if($("#pageCompanyDevProfile").length > 0){				// �����ҼҰ� > ������
		LeftMenuSelected("5", "1");
	}
	if($("#pageCompanyDevTeam").length > 0){				// �����ҼҰ� > �������Ұ�
		LeftMenuSelected("5", "2");
	}
	if($("#pageCompanyDevEquip").length > 0){				// �����ҼҰ� > �ü��������Ȳ
		LeftMenuSelected("5", "3");
	}
	if($("#pageCompanyDevKolas").length > 0){				// �����ҼҰ� > KOLAS
		LeftMenuSelected("5", "4");
	}
	if($("#pageCompanyDevPaper").length > 0){				// �����ҼҰ� > ����.Ư����Ȳ
		LeftMenuSelected("5", "5");
	}
	if($("#pageCompanyFactProfile").length > 0){			// ����Ұ��װ��� > ����Ұ�
		LeftMenuSelected("6", "1");
	}
	if($("#pageCompanyFactCalendar").length > 0){			// ����Ұ��װ��� > ��������ǥ
		LeftMenuSelected("6", "2");
	}
	if($("#pageCompanyFactWriteForm").length > 0){			// ����Ұ��װ��� > ���н�û
		LeftMenuSelected("6", "3");
		//alert("����Ǯ�� Ȯ������ ����\r\n\r\n9������ ��������� ��� �ߴ��մϴ�.");
		//history.back();
		//document.all.btnCancel.focus();	// ��Ŀ�� �Ʒ��� �̵�
	}
	if($("#pageCompanyAgencyList").length > 0){				// �����븮��
		LeftMenuSelected("7", "");
	}
	if($("#pageCompanyContact").length > 0){				// Contact us > ��ġ�׿���ó
		LeftMenuSelected("8", "1");
	}
	if($("#pageCompanyAgencyRec").length > 0){				// Contact us > �븮����.�ǰ����̵����
		LeftMenuSelected("8", "2");
	}
	//-------------------------------------------------------------------------
	// ��ǰ�Ұ�
	//-------------------------------------------------------------------------
	if($("#pageProductListNew").length > 0){				// ����ǰ
		LeftMenuSelected("1", "");
	}
	if($("#pageProductDetail").length > 0){					// �󼼺���
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			var params = GetQueryStrings();	// ��ũ��Ʈ�� �� �ޱ�
			var pCode = params["c"];		// ex:V01001
			if(pCode != null && typeof(pCode) != "undefined"){
				var menuM = "";
				switch(pCode.substring(0,1)) {
					case "V": menuM = "2"; break;	// ������
					case "B": menuM = "3"; break;	// �����ƽ�
					case "S": menuM = "4"; break;	// ����
					case "G": menuM = "5"; break;	// �׸����
					case "U": menuM = "6"; break;	// �츮��
					case "C": menuM = "7"; break;	// ī��Ҿ�
					default: menuM = ""; break;
				}
				var menuS = pCode.substring(2,3);
				LeftMenuSelected(menuM, menuS);
			}
		});
	}
	//-------------------------------------------------------------------------
	// ȫ������
	//-------------------------------------------------------------------------
	if($("#pagePublicVegemilStroy").length > 0){				// �����н��丮
		LeftMenuSelected("1", "");
	}
	if($("#pagePublicVegemilStroyFrame").length > 0){			// �����н��丮 ������
		LeftMenuSelected("1", "");
	}
	if($("#pagePublicAdvertList").length > 0){					// ���ȫ������
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			LeftMenuSelected("2", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvContent = $("#firstFlvContent").val();
			AdFlvPlay(firstFlvFile, firstFlvContent);
		});
	}
	if($("#pagePublicAdvertPlayerFrame").length > 0){			// ���ȫ������ ������
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			var params = GetQueryStrings();	// ��ũ��Ʈ�� �� �ޱ�
			var flvFile = params["f"];		// flv ���ϸ�
			if(typeof(flvFile) != "undefined"){
				var movie = document.getElementById("AdFlvPlayer");
				if(typeof(movie.SetVariable) == "undefined") {
					movie = document.getElementById("AdFlvPlayerEmbed");
				}	// ȭ�� �ε��� ����
				movie.SetVariable("/mc/flv:contentPath", "http://www.vegemil.co.kr/upload/PUBLIC/"+flvFile);
			}
		});
	}
	if($("#pagePublicCfList").length > 0){						// TV-CF
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			LeftMenuSelected("3", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvSubject = $("#firstFlvSubject").val();
			var firstFlvDate = $("#firstFlvDate").val();
			CfFlvPlay(firstFlvFile, firstFlvSubject, firstFlvDate);
		});
	}
	if($("#pagePublicCfPlayerFrame").length > 0){				// TV-CF ������
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			var params = GetQueryStrings();	// ��ũ��Ʈ�� �� �ޱ�
			var flvFile = params["f"];		// flv ���ϸ�
			if(typeof(flvFile) != "undefined"){
				var movie = document.getElementById("CfFlvPlayer");
				if(typeof(movie.SetVariable) == "undefined") {
					movie = document.getElementById("CfFlvPlayerEmbed");
				}	// ȭ�� �ε��� ����
				movie.SetVariable("/mc/flv:contentPath", "http://www.vegemil.co.kr/upload/PUBLIC/"+flvFile);
			}
		});
	}
	if($("#pagePublicCmList").length > 0){						// ���� CM
		LeftMenuSelected("4", "");
	}
	if($("#pagePublicAdEtcList").length > 0){					// ��Ÿ�����ڷ�
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			//LeftMenuSelected("5", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvContent = $("#firstFlvContent").val();
			var firstFlvSubject = $("#firstFlvSubject").val();
			var firstTAB = $("#firstFlvTAB").val();
			AdFlvPlay(firstFlvFile, firstFlvContent,firstFlvSubject,firstTAB);
		});
	}
	if($("#pagePublicMediaNewsList").length > 0){				// �����ڷ� > ����Ʈ
		LeftMenuSelected("6", "");
	}
	if($("#pagePublicMediaNewsDetail").length > 0){				// �����ڷ� > �󼼺���
		LeftMenuSelected("6", "");
	}
	if($("#pagePublicMediaPrintList").length > 0){				// �μⱤ�� > ����Ʈ
		var params = GetQueryStrings();	// ��ũ��Ʈ�� �� �ޱ�
		var flag = params["f"];		// �� �޴� flag
		if(typeof(flag) != "undefined" && flag == "old"){
			LeftMenuSelected("7", "2");
		} else {
			LeftMenuSelected("7", "1");
		}
	}
	if($("#pagePublicMediaPrintFrame").length > 0){				// �μⱤ�� > ������
		var divWidth = $("#divWidth").val();
		var divHeight = $("#divHeight").val();
		window.parent.$("#OrgSizeDiv").css("width",eval(divWidth) + 16);
		window.parent.$("#OrgSizeDiv").css("height",eval(divHeight) + 43);
		window.parent.$("#FrameImg").css("width",eval(divWidth) + 16);
		window.parent.$("#FrameImg").css("height",eval(divHeight) + 43);
	}
	if($("#pagePublicWebzine").length > 0){						// ����
		LeftMenuSelected("8", "");
	}
	if($("#pagePublicWebzinePop").length > 0){					// ���� > �˾�
	}
	if($("#pagePublicWebzineLoading").length > 0){				// ���� > �ε�
	}
	//-------------------------------------------------------------------------
	// ������
	//-------------------------------------------------------------------------
	if($("#pageCustomerCcmsInfo").length > 0){					// CCMS��?
		LeftMenuSelected("1", "");
	}
	if($("#pageCustomerSupportList").length > 0){				// ����� > ����Ʈ
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportWrite").length > 0){				// ����� > �ۼ�
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportDetail").length > 0){				// ����� > �󼼺���
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportEdit").length > 0){				// ����� > ����
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportDelete").length > 0){				// ����� > ����
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportFlow").length > 0){				// ���ǼҸ�����
		LeftMenuSelected("3", "");
	}
	if($("#pageCustomerFaqList").length > 0){					// FAQ > ����Ʈ
		LeftMenuSelected("4", "");
	}
	if($("#pageCustomerFaqDetail").length > 0){					// FAQ > �󼼺���
		LeftMenuSelected("4", "");
	}
	if($("#pageCustomerOfflineNews").length > 0){				// �纸��û�׺���
		LeftMenuSelected("5", "");
	}
	if($("#pageCustomerSitemap").length > 0){					// ����Ʈ��
	}
	//-------------------------------------------------------------------------
	// ȸ�� ����
	//-------------------------------------------------------------------------
	if($("#pageMemberPrivacy").length > 0){						// ����������޹�ħ
		LeftMenuSelected("6", "1");
	}
	if($("#pageMemberJoinForm").length > 0){					// ȸ������
		LeftMenuSelected("6", "1");
	}
	if($("#pageMemberIdCheck").length > 0){						// ���̵� üũ
		$("#txtIdNew").focus();
		if($("#txtOkFlag").val() == "1")
			$("#btnSelect").show();
		else
			$("#btnSelect").hide();
	}
	if($("#pageMemberLogin").length > 0){						// �α���
		LeftMenuSelected("6", "");
		$("#txtId").focus();
	}
	if($("#pageMemberLoginCheck").length > 0){					// �α��� üũ ����������
	}
	if($("#pageMemberLogout").length > 0){						// �α׾ƿ� ����������
	}
	if($("#pageMemberMyPage").length > 0){						// ����������
		LeftMenuSelected("6", "1");
	}
	if($("#pageMemberEditForm").length > 0){					// ȸ����������
		LeftMenuSelected("6", "2");
	}
	if($("#pageMemberIdPwSearch").length > 0){					// ���̵�, ��й�ȣ ã��
		LeftMenuSelected("6", "2");
	}
	if($("#pageMemberLeave").length > 0){						// ȸ��Ż��
		LeftMenuSelected("6", "3");
		$("#txtLeave").focus();
	}
	//-------------------------------------------------------------------------
	// �����ŷ�
	//-------------------------------------------------------------------------
	if($("#pageCpDeclar").length > 0){							// CP > ���Լ���
		LeftMenuSelected("1", "");
	}
	if($("#pageCpDeclarMsg").length > 0){						// CP > �����ؼ� �޼���
		LeftMenuSelected("1", "");
		DeclarMsgOpen($("#msgNo").val());
	}
	if($("#pageCpProgram").length > 0){							// CP > �����ؼ� ���α׷��Ұ�
		LeftMenuSelected("2", "");
	}
	if($("#pageCpManage").length > 0){							// CP > �����ؼ� ���Ȳ
		LeftMenuSelected("3", "");
	}
	if($("#pageCpManual").length > 0){							// CP > �����ؼ� ���
		LeftMenuSelected("4", "");
	}
	if($("#pageCpLogin").length > 0){							// CP > ��� �α���
		LeftMenuSelected("4", "");
		$("#txtPwd").focus();
	}
	if($("#pageCpLogin").length > 0){							// CP > ��� �α���
		document.domain = "www.vegemil.co.kr";
		document.title = "E-Book ���� : �������";
		$("#txtPwd").focus();
	}
	if($("#pageCpPdsFrame").length > 0){						// CP > �����ڷ�� > ������
	}
	if($("#pageCpPds").length > 0){								// CP > �ڷ��
		LeftMenuSelected("5", "");
	}
	if($("#pageCpClaim").length > 0){							// CP > �Ұ����ŷ��Ű�
		LeftMenuSelected("6", "");
	}
	if($("#pageCpNews").length > 0){							// CP > �����긮�� > ����Ʈ
		LeftMenuSelected("7", "");
	}
	if($("#pageCpNewsView").length > 0){						// CP > �����긮�� > �󼼺���
		LeftMenuSelected("7", "");
	}
	//-------------------------------------------------------------------------
	// ��ȸ����
	//-------------------------------------------------------------------------
	if($("#pageCsrScholaship").length > 0){						// ��������ȸ
		LeftMenuSelected("1", "");
	}
	if($("#pageCsrGoodneighbors").length > 0){					// �³��̹���
		LeftMenuSelected("2", "");
	}
	if($("#pageCsrHeart").length > 0){							// �ѱ��������
		LeftMenuSelected("3", "");
	}
	if($("#pageCsrBloodcancer").length > 0){					// ���׾���ȸ
		LeftMenuSelected("4", "");
	}
	if($("#pageCsrService").length > 0){						// ����Ȱ��
		LeftMenuSelected("5", "");
	}
	//-------------------------------------------------------------------------
	// �̺�Ʈ
	//-------------------------------------------------------------------------
	if($("#pageEventList").length > 0){							// �̺�Ʈ > ����Ʈ
	}
	if($("#pageEventE20130415").length > 0){					// ���� �������� �̺�Ʈ ������
		EventPageSet();
	}
	//-------------------------------------------------------------------------
	// V�������Ǿ�
	//-------------------------------------------------------------------------
	/*
	if($("#pageCyberDefault").length > 0){						// ����
	}
	if($("#pageCyberBoardList").length > 0){					// ���հԽ��� > ����Ʈ
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			CyberBoardLeftMenu();	// ���հԽ��� ���� �޴� ����
		});
	}
	if($("#pageCyberBoardView").length > 0){					// ���հԽ��� > �󼼺���
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			CyberBoardLeftMenu();	// ���հԽ��� ���� �޴� ����
		});
	}
	if($("#pageCyberBoardEdit").length > 0){					// ���հԽ��� > ����
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			CyberBoardLeftMenu();	// ���հԽ��� ���� �޴� ����
			CyberTextEditSkin();	// ������ ��Ų ����
		});
	}
	if($("#pageCyberBoardWrite").length > 0){					// ���հԽ��� > �ۼ�
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			CyberBoardLeftMenu();	// ���հԽ��� ���� �޴� ����
			CyberTextEditSkin();	// ������ ��Ų ����
		});
	}
	if($("#pageCyberBestPostList").length > 0){					// ����ı� > ����Ʈ
		LeftMenuSelected("3", "2");
	}
	if($("#pageCyberBestPostView").length > 0){					// ����ı� > �󼼺���
		LeftMenuSelected("3", "2");
	}
	if($("#pageCyberMissionList").length > 0){					// �̼� > ����Ʈ
		LeftMenuSelected("3", "1");
	}
	if($("#pageCyberMissionView").length > 0){					// �̼� > �󼼺���
		LeftMenuSelected("3", "1");
		$(window).load(function(){	// �ε� �Ϸ� �� ����(OnLoad)
			CyberTextEditSkin();	// ������ ��Ų ����
		});
	}
	if($("#pageCyberInfo").length > 0){							// �Ұ� > V�������Ǿ�
		LeftMenuSelected("5", "1");
	}
	if($("#pageCyberInfoPower").length > 0){					// �Ұ� > �Ŀ� V�������Ǿ�
		LeftMenuSelected("5", "2");
	}
	if($("#pageCyberPointList").length > 0){					// ����Ʈ����
		LeftMenuSelected("6", "");
		if($("#txtEday").val() == ""){
			alert("�̵��̸� ���̵� ����Ͽ� �ֽʽÿ�.\n\n���� ����Ʈ�� �̵��̸� ���̵�� �����˴ϴ�.");
			$("#txtEday").focus();
		}
	}
	if($("#pageCyberResearchView").length > 0){					// ��������
		LeftMenuSelected("3", "2");
		if(CyberResearchOpenValue == "1"){			// ���� (common.js ����)
			$("#ResearchDiv").show();
			$("#ResearchEmptyDiv").hide();
		} else if(CyberResearchOpenValue == "0"){	// ���� (common.js ����)
			$("#ResearchDiv").hide();
			$("#ResearchEmptyDiv").show();
		}
	}
	if($("#pageCyberMemberJoin").length > 0){					// V�������Ǿ� �������ۼ�
	}
	if($("#pageCyberMemberJoinInfo").length > 0){				// V�������Ǿ� �����ȳ�
	}
	if($("#pageCyberMemberOk").length > 0){						// V�������Ǿ� �հ��� ��ǥ
	}
	*/
	//-------------------------------------------------------------------------
	// ���θ�
	//-------------------------------------------------------------------------
	if($("#pageShop").length > 0){								// ���θ�
	}
	//-------------------------------------------------------------------------
	// �¶���ä��
	//-------------------------------------------------------------------------
	// ������ID �������� ����
});

