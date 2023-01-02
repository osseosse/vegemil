/*****************************************************************************
*	각 페이지별(페이지 ID 기준) 로딩 완료 후 초기화
*
*	SESON
*	작성일자 : 2013-07-16
//****************************************************************************/
//=============================================================================
// 페이지별 초기화 - 페이지 로딩이 완료 되었을때(플래시, 이미지 포함) 실행
// 실행순서 : $(document).ready ==> $(window).ready ==> $(window).load
//=============================================================================
$(window).ready(function(){
	//-------------------------------------------------------------------------
	// 공통
	//-------------------------------------------------------------------------
	if($("#pageIncludePopup").length > 0){					// 팝업
	}
	if($("#pageIncludeZipcode").length > 0){				// 우편번호 찾기
		$("#txtDong").focus();
	}
	if($("#pageAgencyPopup").length > 0){					// 대리점 검색
		$('#txtFind').focus();
	}
	//-------------------------------------------------------------------------
	// 메인
	//-------------------------------------------------------------------------
	if($("#pageDefault").length > 0){						// 메인 페이지
		// 팝업 체크
		//if(CookieRead("PopupMulti") != "1"){
		//	$("#PopupFrame").attr("src","/main/include/popupMulti.aspx");
		//	$("#PopupDiv").show("slow");
		//}
		// 신제품 슬라이드 시작
		$('#mEventBnr').slides({preload:true, play:4000, pause:2500, slideSpeed:1000, hoverPause:true});
		
		// 신제품 슬라이드 시작
		$('#mEventBnr02').slides({preload:true, play:4000, pause:2500, slideSpeed:1000, hoverPause:true});

		// 메인 동영상 로딩/재생
		/* $(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
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

	/* if($("#pageDefault").length > 0){						// 메인 페이지
		
		// 신제품 슬라이드 시작
		$('#mEventBnr02').slides({preload:true, play:4000, pause:2500, slideSpeed:1000, hoverPause:true});

		
	} */
	 
	if($("#pagePublicAdEtcList").length > 0){					// 기타영상자료
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			//LeftMenuSelected("5", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvContent = $("#firstFlvContent").val();
			var firstFlvSubject = $("#firstFlvSubject").val();
			var firstTAB = $("#firstFlvTAB").val();
			AdFlvPlay(firstFlvFile, firstFlvContent,firstFlvSubject,firstTAB);
		});
	}
	//-------------------------------------------------------------------------
	// 커뮤니티
	//-------------------------------------------------------------------------
	/*
	if($("#pageCommunityRecipeList").length > 0){			// 레시피 > 리스트
		LeftMenuSelected("1", "");
	}
	if($("#pageCommunityRecipeDetail").length > 0){			// 레시피 > 상세
		LeftMenuSelected("1", "");
	}
	if($("#pageCommunityVegemilList").length > 0){			// 베지밀자세히알기 > 리스트
		LeftMenuSelected("2", "");
	}
	if($("#pageCommunityVegemilDetail").length > 0){		// 베지밀자세히알기 > 상세
		LeftMenuSelected("2", "");
	}
	if($("#pageCommunitySoyList").length > 0){				// 콩이야기 > 리스트
		LeftMenuSelected("3", "");
	}
	if($("#pageCommunitySoyDetail").length > 0){			// 콩이야기 > 상세
		LeftMenuSelected("3", "");
	}
	//-------------------------------------------------------------------------
	// 기업소개
	//-------------------------------------------------------------------------
	if($("#pageCompanyIntroduceDR").length > 0){			// 인사말 > 명예회장
		LeftMenuSelected("1", "1");
	}
	if($("#pageCompanyIntroduceCEO").length > 0){			// 인사말 > 대표이사
		LeftMenuSelected("1", "2");
	}
	if($("#pageCompanyProfile").length > 0){				// 기업정보 > 프로필
		LeftMenuSelected("2", "1");
	}
	if($("#pageCompanyHistory").length > 0){				// 기업정보 > 연혁
		LeftMenuSelected("2", "2");
	}
	if($("#pageCompanyFamily").length > 0){					// 기업정보 > 관련사
		LeftMenuSelected("2", "3");
	}
	if($("#pageCompanyCiMean").length > 0){					// CI소개 > 의미
		LeftMenuSelected("3", "1");
	}
	if($("#pageCompanyCiApply").length > 0){				// CI소개 > 적용방법
		LeftMenuSelected("3", "2");
	}
	if($("#pageCompanyCiUtil").length > 0){					// CI소개 > 활용예시
		LeftMenuSelected("3", "3");
	}
	if($("#pageCompanyCerHaccp").length > 0){				// 식품안전인증현황 > HACCP
		LeftMenuSelected("4", "1");
	}
	if($("#pageCompanyCerIso").length > 0){					// 식품안전인증현황 > ISO22000
		LeftMenuSelected("4", "2");
	}
	if($("#pageCompanyDevProfile").length > 0){				// 연구소소개 > 프로필
		LeftMenuSelected("5", "1");
	}
	if($("#pageCompanyDevTeam").length > 0){				// 연구소소개 > 연구팀소개
		LeftMenuSelected("5", "2");
	}
	if($("#pageCompanyDevEquip").length > 0){				// 연구소소개 > 시설및장비현황
		LeftMenuSelected("5", "3");
	}
	if($("#pageCompanyDevKolas").length > 0){				// 연구소소개 > KOLAS
		LeftMenuSelected("5", "4");
	}
	if($("#pageCompanyDevPaper").length > 0){				// 연구소소개 > 연구.특허현황
		LeftMenuSelected("5", "5");
	}
	if($("#pageCompanyFactProfile").length > 0){			// 공장소개및견학 > 공장소개
		LeftMenuSelected("6", "1");
	}
	if($("#pageCompanyFactCalendar").length > 0){			// 공장소개및견학 > 견학일정표
		LeftMenuSelected("6", "2");
	}
	if($("#pageCompanyFactWriteForm").length > 0){			// 공장소개및견학 > 견학신청
		LeftMenuSelected("6", "3");
		//alert("신종풀루 확산으로 인해\r\n\r\n9월부터 공장견학을 잠시 중단합니다.");
		//history.back();
		//document.all.btnCancel.focus();	// 포커스 아래로 이동
	}
	if($("#pageCompanyAgencyList").length > 0){				// 전국대리점
		LeftMenuSelected("7", "");
	}
	if($("#pageCompanyContact").length > 0){				// Contact us > 위치및연락처
		LeftMenuSelected("8", "1");
	}
	if($("#pageCompanyAgencyRec").length > 0){				// Contact us > 대리점장.건강레이디모집
		LeftMenuSelected("8", "2");
	}
	//-------------------------------------------------------------------------
	// 제품소개
	//-------------------------------------------------------------------------
	if($("#pageProductListNew").length > 0){				// 신제품
		LeftMenuSelected("1", "");
	}
	if($("#pageProductDetail").length > 0){					// 상세보기
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			var params = GetQueryStrings();	// 스크립트로 값 받기
			var pCode = params["c"];		// ex:V01001
			if(pCode != null && typeof(pCode) != "undefined"){
				var menuM = "";
				switch(pCode.substring(0,1)) {
					case "V": menuM = "2"; break;	// 베지밀
					case "B": menuM = "3"; break;	// 콩유아식
					case "S": menuM = "4"; break;	// 썬몬드
					case "G": menuM = "5"; break;	// 그린비아
					case "U": menuM = "6"; break;	// 우리안
					case "C": menuM = "7"; break;	// 카페소야
					default: menuM = ""; break;
				}
				var menuS = pCode.substring(2,3);
				LeftMenuSelected(menuM, menuS);
			}
		});
	}
	//-------------------------------------------------------------------------
	// 홍보센터
	//-------------------------------------------------------------------------
	if($("#pagePublicVegemilStroy").length > 0){				// 베지밀스토리
		LeftMenuSelected("1", "");
	}
	if($("#pagePublicVegemilStroyFrame").length > 0){			// 베지밀스토리 프레임
		LeftMenuSelected("1", "");
	}
	if($("#pagePublicAdvertList").length > 0){					// 기업홍보영상
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			LeftMenuSelected("2", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvContent = $("#firstFlvContent").val();
			AdFlvPlay(firstFlvFile, firstFlvContent);
		});
	}
	if($("#pagePublicAdvertPlayerFrame").length > 0){			// 기업홍보영상 프레임
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			var params = GetQueryStrings();	// 스크립트로 값 받기
			var flvFile = params["f"];		// flv 파일명
			if(typeof(flvFile) != "undefined"){
				var movie = document.getElementById("AdFlvPlayer");
				if(typeof(movie.SetVariable) == "undefined") {
					movie = document.getElementById("AdFlvPlayerEmbed");
				}	// 화면 로딩시 실행
				movie.SetVariable("/mc/flv:contentPath", "http://www.vegemil.co.kr/upload/PUBLIC/"+flvFile);
			}
		});
	}
	if($("#pagePublicCfList").length > 0){						// TV-CF
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			LeftMenuSelected("3", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvSubject = $("#firstFlvSubject").val();
			var firstFlvDate = $("#firstFlvDate").val();
			CfFlvPlay(firstFlvFile, firstFlvSubject, firstFlvDate);
		});
	}
	if($("#pagePublicCfPlayerFrame").length > 0){				// TV-CF 프레임
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			var params = GetQueryStrings();	// 스크립트로 값 받기
			var flvFile = params["f"];		// flv 파일명
			if(typeof(flvFile) != "undefined"){
				var movie = document.getElementById("CfFlvPlayer");
				if(typeof(movie.SetVariable) == "undefined") {
					movie = document.getElementById("CfFlvPlayerEmbed");
				}	// 화면 로딩시 실행
				movie.SetVariable("/mc/flv:contentPath", "http://www.vegemil.co.kr/upload/PUBLIC/"+flvFile);
			}
		});
	}
	if($("#pagePublicCmList").length > 0){						// 라디오 CM
		LeftMenuSelected("4", "");
	}
	if($("#pagePublicAdEtcList").length > 0){					// 기타영상자료
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			//LeftMenuSelected("5", "");
			var firstFlvFile = $("#firstFlvFile").val();
			var firstFlvContent = $("#firstFlvContent").val();
			var firstFlvSubject = $("#firstFlvSubject").val();
			var firstTAB = $("#firstFlvTAB").val();
			AdFlvPlay(firstFlvFile, firstFlvContent,firstFlvSubject,firstTAB);
		});
	}
	if($("#pagePublicMediaNewsList").length > 0){				// 보도자료 > 리스트
		LeftMenuSelected("6", "");
	}
	if($("#pagePublicMediaNewsDetail").length > 0){				// 보도자료 > 상세보기
		LeftMenuSelected("6", "");
	}
	if($("#pagePublicMediaPrintList").length > 0){				// 인쇄광고 > 리스트
		var params = GetQueryStrings();	// 스크립트로 값 받기
		var flag = params["f"];		// 소 메뉴 flag
		if(typeof(flag) != "undefined" && flag == "old"){
			LeftMenuSelected("7", "2");
		} else {
			LeftMenuSelected("7", "1");
		}
	}
	if($("#pagePublicMediaPrintFrame").length > 0){				// 인쇄광고 > 프레임
		var divWidth = $("#divWidth").val();
		var divHeight = $("#divHeight").val();
		window.parent.$("#OrgSizeDiv").css("width",eval(divWidth) + 16);
		window.parent.$("#OrgSizeDiv").css("height",eval(divHeight) + 43);
		window.parent.$("#FrameImg").css("width",eval(divWidth) + 16);
		window.parent.$("#FrameImg").css("height",eval(divHeight) + 43);
	}
	if($("#pagePublicWebzine").length > 0){						// 웹진
		LeftMenuSelected("8", "");
	}
	if($("#pagePublicWebzinePop").length > 0){					// 웹진 > 팝업
	}
	if($("#pagePublicWebzineLoading").length > 0){				// 웹진 > 로딩
	}
	//-------------------------------------------------------------------------
	// 고객지원
	//-------------------------------------------------------------------------
	if($("#pageCustomerCcmsInfo").length > 0){					// CCMS란?
		LeftMenuSelected("1", "");
	}
	if($("#pageCustomerSupportList").length > 0){				// 고객상담 > 리스트
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportWrite").length > 0){				// 고객상담 > 작성
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportDetail").length > 0){				// 고객상담 > 상세보기
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportEdit").length > 0){				// 고객상담 > 수정
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportDelete").length > 0){				// 고객상담 > 삭제
		LeftMenuSelected("2", "");
	}
	if($("#pageCustomerSupportFlow").length > 0){				// 고객의소리절차
		LeftMenuSelected("3", "");
	}
	if($("#pageCustomerFaqList").length > 0){					// FAQ > 리스트
		LeftMenuSelected("4", "");
	}
	if($("#pageCustomerFaqDetail").length > 0){					// FAQ > 상세보기
		LeftMenuSelected("4", "");
	}
	if($("#pageCustomerOfflineNews").length > 0){				// 사보신청및변경
		LeftMenuSelected("5", "");
	}
	if($("#pageCustomerSitemap").length > 0){					// 사이트맵
	}
	//-------------------------------------------------------------------------
	// 회원 관련
	//-------------------------------------------------------------------------
	if($("#pageMemberPrivacy").length > 0){						// 개인정보취급방침
		LeftMenuSelected("6", "1");
	}
	if($("#pageMemberJoinForm").length > 0){					// 회원가입
		LeftMenuSelected("6", "1");
	}
	if($("#pageMemberIdCheck").length > 0){						// 아이디 체크
		$("#txtIdNew").focus();
		if($("#txtOkFlag").val() == "1")
			$("#btnSelect").show();
		else
			$("#btnSelect").hide();
	}
	if($("#pageMemberLogin").length > 0){						// 로그인
		LeftMenuSelected("6", "");
		$("#txtId").focus();
	}
	if($("#pageMemberLoginCheck").length > 0){					// 로그인 체크 전용페이지
	}
	if($("#pageMemberLogout").length > 0){						// 로그아웃 전용페이지
	}
	if($("#pageMemberMyPage").length > 0){						// 마이페이지
		LeftMenuSelected("6", "1");
	}
	if($("#pageMemberEditForm").length > 0){					// 회원정보변경
		LeftMenuSelected("6", "2");
	}
	if($("#pageMemberIdPwSearch").length > 0){					// 아이디, 비밀번호 찾기
		LeftMenuSelected("6", "2");
	}
	if($("#pageMemberLeave").length > 0){						// 회원탈퇴
		LeftMenuSelected("6", "3");
		$("#txtLeave").focus();
	}
	//-------------------------------------------------------------------------
	// 공정거래
	//-------------------------------------------------------------------------
	if($("#pageCpDeclar").length > 0){							// CP > 도입선언문
		LeftMenuSelected("1", "");
	}
	if($("#pageCpDeclarMsg").length > 0){						// CP > 자율준수 메세지
		LeftMenuSelected("1", "");
		DeclarMsgOpen($("#msgNo").val());
	}
	if($("#pageCpProgram").length > 0){							// CP > 자율준수 프로그램소개
		LeftMenuSelected("2", "");
	}
	if($("#pageCpManage").length > 0){							// CP > 자율준수 운영현황
		LeftMenuSelected("3", "");
	}
	if($("#pageCpManual").length > 0){							// CP > 자율준수 편람
		LeftMenuSelected("4", "");
	}
	if($("#pageCpLogin").length > 0){							// CP > 사번 로그인
		LeftMenuSelected("4", "");
		$("#txtPwd").focus();
	}
	if($("#pageCpLogin").length > 0){							// CP > 사번 로그인
		document.domain = "www.vegemil.co.kr";
		document.title = "E-Book 열람 : 사번인증";
		$("#txtPwd").focus();
	}
	if($("#pageCpPdsFrame").length > 0){						// CP > 교육자료실 > 프레임
	}
	if($("#pageCpPds").length > 0){								// CP > 자료실
		LeftMenuSelected("5", "");
	}
	if($("#pageCpClaim").length > 0){							// CP > 불공정거래신고
		LeftMenuSelected("6", "");
	}
	if($("#pageCpNews").length > 0){							// CP > 뉴스브리핑 > 리스트
		LeftMenuSelected("7", "");
	}
	if($("#pageCpNewsView").length > 0){						// CP > 뉴스브리핑 > 상세보기
		LeftMenuSelected("7", "");
	}
	//-------------------------------------------------------------------------
	// 사회공헌
	//-------------------------------------------------------------------------
	if($("#pageCsrScholaship").length > 0){						// 혜춘장학회
		LeftMenuSelected("1", "");
	}
	if($("#pageCsrGoodneighbors").length > 0){					// 굿네이버스
		LeftMenuSelected("2", "");
	}
	if($("#pageCsrHeart").length > 0){							// 한국심장재단
		LeftMenuSelected("3", "");
	}
	if($("#pageCsrBloodcancer").length > 0){					// 혈액암협회
		LeftMenuSelected("4", "");
	}
	if($("#pageCsrService").length > 0){						// 봉사활동
		LeftMenuSelected("5", "");
	}
	//-------------------------------------------------------------------------
	// 이벤트
	//-------------------------------------------------------------------------
	if($("#pageEventList").length > 0){							// 이벤트 > 리스트
	}
	if($("#pageEventE20130415").length > 0){					// 현재 진행중인 이벤트 페이지
		EventPageSet();
	}
	//-------------------------------------------------------------------------
	// V컨슈머피아
	//-------------------------------------------------------------------------
	/*
	if($("#pageCyberDefault").length > 0){						// 메인
	}
	if($("#pageCyberBoardList").length > 0){					// 통합게시판 > 리스트
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			CyberBoardLeftMenu();	// 통합게시판 왼쪽 메뉴 선택
		});
	}
	if($("#pageCyberBoardView").length > 0){					// 통합게시판 > 상세보기
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			CyberBoardLeftMenu();	// 통합게시판 왼쪽 메뉴 선택
		});
	}
	if($("#pageCyberBoardEdit").length > 0){					// 통합게시판 > 수정
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			CyberBoardLeftMenu();	// 통합게시판 왼쪽 메뉴 선택
			CyberTextEditSkin();	// 에디터 스킨 적용
		});
	}
	if($("#pageCyberBoardWrite").length > 0){					// 통합게시판 > 작성
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			CyberBoardLeftMenu();	// 통합게시판 왼쪽 메뉴 선택
			CyberTextEditSkin();	// 에디터 스킨 적용
		});
	}
	if($("#pageCyberBestPostList").length > 0){					// 우수후기 > 리스트
		LeftMenuSelected("3", "2");
	}
	if($("#pageCyberBestPostView").length > 0){					// 우수후기 > 상세보기
		LeftMenuSelected("3", "2");
	}
	if($("#pageCyberMissionList").length > 0){					// 미션 > 리스트
		LeftMenuSelected("3", "1");
	}
	if($("#pageCyberMissionView").length > 0){					// 미션 > 상세보기
		LeftMenuSelected("3", "1");
		$(window).load(function(){	// 로딩 완료 후 실행(OnLoad)
			CyberTextEditSkin();	// 에디터 스킨 적용
		});
	}
	if($("#pageCyberInfo").length > 0){							// 소개 > V컨슈머피아
		LeftMenuSelected("5", "1");
	}
	if($("#pageCyberInfoPower").length > 0){					// 소개 > 파워 V컨슈머피아
		LeftMenuSelected("5", "2");
	}
	if($("#pageCyberPointList").length > 0){					// 포인트내역
		LeftMenuSelected("6", "");
		if($("#txtEday").val() == ""){
			alert("이데이몰 아이디를 등록하여 주십시오.\n\n설문 포인트는 이데이몰 아이디로 적립됩니다.");
			$("#txtEday").focus();
		}
	}
	if($("#pageCyberResearchView").length > 0){					// 설문조사
		LeftMenuSelected("3", "2");
		if(CyberResearchOpenValue == "1"){			// 오픈 (common.js 설정)
			$("#ResearchDiv").show();
			$("#ResearchEmptyDiv").hide();
		} else if(CyberResearchOpenValue == "0"){	// 마감 (common.js 설정)
			$("#ResearchDiv").hide();
			$("#ResearchEmptyDiv").show();
		}
	}
	if($("#pageCyberMemberJoin").length > 0){					// V컨슈머피아 지원서작성
	}
	if($("#pageCyberMemberJoinInfo").length > 0){				// V컨슈머피아 지원안내
	}
	if($("#pageCyberMemberOk").length > 0){						// V컨슈머피아 합격자 발표
	}
	*/
	//-------------------------------------------------------------------------
	// 쇼핑몰
	//-------------------------------------------------------------------------
	if($("#pageShop").length > 0){								// 쇼핑몰
	}
	//-------------------------------------------------------------------------
	// 온라인채용
	//-------------------------------------------------------------------------
	// 페이지ID 설정되지 않음
});

