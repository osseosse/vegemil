<%
/**************************************************************************************************************************
* Program Name  : 본인실명확인 요청 Sample JSP (Real)  
* File Name     : pccName_sample_seed.jsp
* Comment       : 
* History       : 
*
**************************************************************************************************************************/
%>
<%
    response.setHeader("Pragma", "no-cache" );
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "no-store");
    response.setHeader("Cache-Control", "no-cache" );
%>
<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page  import = "java.util.*"%> 
<%
	  
    String id       = request.getParameter("id");                               // 본인실명확인 회원사 아이디
    String srvNo    = request.getParameter("srvNo");                            // 본인실명확인 서비스번호
    String reqNum   = request.getParameter("reqNum");                           // 본인실명확인 요청번호
	String exVar    = "0000000000000000";                                       // 복호화용 임시필드
    String retUrl   = request.getParameter("retUrl");                           // 본인실명확인 결과수신 URL
	String certDate	= request.getParameter("certDate");                         // 본인실명확인 요청시간
	String certGb	= request.getParameter("certGb");                           // 본인실명확인 본인확인 인증수단
	String addVar	= request.getParameter("addVar");                           // 본인실명확인 추가 파라메터
	
	Cookie c = new Cookie("reqNum", reqNum);
	//c.setMaxAge(1800);  // <== 필요시 설정(초단위로 설정됩니다)
	response.addCookie(c);

	//01. 암호화 모듈 선언
	com.sci.v2.pccv2.secu.SciSecuManager seed  = new com.sci.v2.pccv2.secu.SciSecuManager();
	
	//02. 1차 암호화
	String encStr = "";
	String reqInfo      = id+"^"+srvNo+"^"+reqNum+"^"+certDate+"^"+certGb+"^"+addVar+"^"+exVar;  // 데이터 암호화
	
	seed.setInfoPublic(id,"309089EAF0519818D08884A89D66756F"); // 회원사 ID 및 PWD 셋팅 패스워드는 16자리 필수 영문 무관
	
	encStr              = seed.getEncPublic(reqInfo);

	//03. 위변조 검증 값 생성
	com.sci.v2.pccv2.secu.hmac.SciHmac hmac = new com.sci.v2.pccv2.secu.hmac.SciHmac();
	//String hmacMsg = hmac.HMacEncriptPublic(encStr);
	String hmacMsg  = seed.getEncReq(encStr,"HMAC");
	
	//03. 2차 암호화
	reqInfo  = seed.getEncPublic(encStr + "^" + hmacMsg + "^" + "0000000000000000");  //2차암호화
	
	//04. 회원사 ID 처리를 위한 암호화
	reqInfo = seed.EncPublic(reqInfo + "^" + id + "^"  + "00000000");
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	</head>
	<body>
		<form name="reqPCCForm" method="post" action = "https://pcc.siren24.com/pcc_V3/jsp/pcc_V3_j10_v2.jsp">
		    <input type="hidden" name="reqInfo"     value = "<%=reqInfo%>">
		    <input type="hidden" name="retUrl"      value = "<%=retUrl%>">	
		    <input type="hidden" name="verSion"	value = "2">
		</form>
		<script type="text/javascript">
			reqPCCForm.submit();
		</script>
	</body>
</html>