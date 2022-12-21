<%
/**************************************************************************************************************************
* Program Name  : 본인확인 결과 수신 Sample JSP 
* File Name     : pcc_result_seed2.jsp
* Comment       : 
* History       :  
*
**************************************************************************************************************************/
%>

<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page import = "java.util.*" %> 
<%
    // 변수 --------------------------------------------------------------------------------
    String retInfo		= "";																// 결과정보

	String id			= "";                                                               //회원사 비즈사이렌아이디
	String name			= "";                                                               //성명
	String sex			= "";																//성별
	String birYMD		= "";																//생년월일
	String fgnGbn		= "";																//내외국인 구분값
	String scCode		= "";																//가상식별번호
    String di			= "";																//DI
    String ci1			= "";																//CI
    String ci2			= "";																//CI
    String civersion    = "";                                                               //CI Version
    
    String reqNum		= "";                                                               // 본인확인 요청번호
    String result		= "";                                                               // 본인확인결과 (Y/N)
    String certDate		= "";                                                               // 검증시간
    String certGb		= "";                                                               // 인증수단
	String cellNo		= "";																// 핸드폰 번호
	String cellCorp		= "";																// 이동통신사
	String addVar		= "";

	//예약 필드
	String ext1			= "";
	String ext2			= "";
	String ext3			= "";
	String ext4			= "";
	String ext5			= "";

	//복화화용 변수
	String encPara		= "";
	String encMsg		= "";
	String msgChk       = "N";  
	
    //-----------------------------------------------------------------------------------------------------------------
    
	//sample 페이지의 reqNum과 동일하지 않으면 결과페이지 복호화 시 에러
	//쿠키값 가져 오기
    Cookie[] cookies = request.getCookies();
    String cookiename = "";
    String cookiereqNum = "";
	if(cookies!=null){
		for (int i = 0; i < cookies.length; i++){
			Cookie c = cookies[i];
			cookiename = c.getName();
			cookiereqNum = c.getValue();
			if(cookiename.compareTo("reqNum")==0) break;
			
			cookiereqNum = null;
		}
	}

    try{

        // Parameter 수신 --------------------------------------------------------------------
        retInfo  = request.getParameter("retInfo").trim(); //반드시 get과 post 방식 둘 다 받을수있게 허용해놔야함.

%>
<%
        // 1. 암호화 모듈 (jar) Loading
        com.sci.v2.pccv2.secu.SciSecuManager sciSecuMg = new com.sci.v2.pccv2.secu.SciSecuManager();
		sciSecuMg.setInfoPublic("SJSP001","309089EAF0519818D08884A89D66756F");  //bizsiren.com > 회원사전용 로그인후 확인. 

        // 3. 1차 파싱---------------------------------------------------------------

		retInfo  = sciSecuMg.getDec(retInfo, cookiereqNum);

		// 4. 요청결과 복호화
        String[] aRetInfo1 = retInfo.split("\\^");

		encPara  = aRetInfo1[0];         //암호화된 통합 파라미터
        encMsg   = aRetInfo1[1];    //암호화된 통합 파라미터의 Hash값
		
		String encMsg2   = sciSecuMg.getMsg(encPara);
		
		// 5. 위/변조 검증 ---------------------------------------------------------------

        if(encMsg2.equals(encMsg)){
            msgChk="Y";
        }

		if(msgChk.equals("N")){
%>
		    <script language=javascript>
            alert("비정상적인 접근입니다.!!<%=msgChk%>");
		    </script>
<%
			return;
		}

        // 복호화 및 위/변조 검증 ---------------------------------------------------------------
		retInfo  = sciSecuMg.getDec(encPara, cookiereqNum);

        String[] aRetInfo = retInfo.split("\\^");
		
        name		= aRetInfo[0];
		birYMD		= aRetInfo[1];
        sex			= aRetInfo[2];        
        fgnGbn		= aRetInfo[3];
        di			= aRetInfo[4];
        ci1			= aRetInfo[5];
        ci2			= aRetInfo[6];
        civersion	= aRetInfo[7];
        reqNum		= aRetInfo[8];
        result		= aRetInfo[9];
        certGb		= aRetInfo[10];
		cellNo		= aRetInfo[11];
		cellCorp	= aRetInfo[12];
        certDate	= aRetInfo[13];
		addVar		= aRetInfo[14];

		//예약 필드
		ext1		= aRetInfo[15];
		ext2		= aRetInfo[16];
		ext3		= aRetInfo[17];
		ext4		= aRetInfo[18];
		ext5		= aRetInfo[19];
%>
<html>
	<body>
		<div>
		    <form name="reqPccInfo" accept-charset="utf-8" method="post" action="<%= addVar %>">
      		    <input type="hidden" 	name="PCC_NAME"   	    value="<%= name %>">
          		<input type="hidden" 	name="PCC_SEX"     		value="<%= sex %>">
          		<input type="hidden" 	name="PCC_BIRYMD"       value="<%= birYMD %>">
          		<input type="hidden" 	name="PCC_FGNGBN"       value="<%= fgnGbn %>">
          		<input type="hidden" 	name="PCC_DI"         	value="<%= di %>">
          		<input type="hidden" 	name="PCC_CI1"          value="<%= ci1 %>">
          		<input type="hidden" 	name="PCC_CI2"          value="<%= ci2 %>">
          		<input type="hidden" 	name="PCC_CIVERSION"    value="<%= civersion %>">
          		<input type="hidden" 	name="PCC_REQNUM"       value="<%= reqNum %>">
          		<input type="hidden" 	name="PCC_RESULT" 		value="<%= result %>">
          		<input type="hidden" 	name="PCC_CERTGB"    	value="<%= certGb %>">
          		<input type="hidden" 	name="PCC_CELLNO"    	value="<%= cellNo %>">
          		<input type="hidden" 	name="PCC_CELLCORP"		value="<%= cellCorp %>"> 
          		<input type="hidden" 	name="PCC_CERTDATE"		value="<%= certDate %>">
          	</form>
          	<script type="text/javascript">
          		reqPccInfo.submit();
			</script>
		</div>
	</body>
</html>
<%
        // ----------------------------------------------------------------------------------

    }catch(Exception ex){
          System.out.println("[pcc] Receive Error -"+ex.getMessage());
    }
%>