<%
/**************************************************************************************************************************
* Program Name  : ����Ȯ�� ��� ���� Sample JSP 
* File Name     : pcc_result_seed2.jsp
* Comment       : 
* History       :  
*
**************************************************************************************************************************/
%>

<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page import = "java.util.*" %> 
<%
    // ���� --------------------------------------------------------------------------------
    String retInfo		= "";																// �������

	String id			= "";                                                               //ȸ���� ������̷����̵�
	String name			= "";                                                               //����
	String sex			= "";																//����
	String birYMD		= "";																//�������
	String fgnGbn		= "";																//���ܱ��� ���а�
	String scCode		= "";																//����ĺ���ȣ
    String di			= "";																//DI
    String ci1			= "";																//CI
    String ci2			= "";																//CI
    String civersion    = "";                                                               //CI Version
    
    String reqNum		= "";                                                               // ����Ȯ�� ��û��ȣ
    String result		= "";                                                               // ����Ȯ�ΰ�� (Y/N)
    String certDate		= "";                                                               // �����ð�
    String certGb		= "";                                                               // ��������
	String cellNo		= "";																// �ڵ��� ��ȣ
	String cellCorp		= "";																// �̵���Ż�
	String addVar		= "";

	//���� �ʵ�
	String ext1			= "";
	String ext2			= "";
	String ext3			= "";
	String ext4			= "";
	String ext5			= "";

	//��ȭȭ�� ����
	String encPara		= "";
	String encMsg		= "";
	String msgChk       = "N";  
	
    //-----------------------------------------------------------------------------------------------------------------
    
	//sample �������� reqNum�� �������� ������ ��������� ��ȣȭ �� ����
	//��Ű�� ���� ����
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

        // Parameter ���� --------------------------------------------------------------------
        retInfo  = request.getParameter("retInfo").trim(); //�ݵ�� get�� post ��� �� �� �������ְ� ����س�����.

%>
<%
        // 1. ��ȣȭ ��� (jar) Loading
        com.sci.v2.pccv2.secu.SciSecuManager sciSecuMg = new com.sci.v2.pccv2.secu.SciSecuManager();
		sciSecuMg.setInfoPublic("SJSP001","309089EAF0519818D08884A89D66756F");  //bizsiren.com > ȸ�������� �α����� Ȯ��. 

        // 3. 1�� �Ľ�---------------------------------------------------------------

		retInfo  = sciSecuMg.getDec(retInfo, cookiereqNum);

		// 4. ��û��� ��ȣȭ
        String[] aRetInfo1 = retInfo.split("\\^");

		encPara  = aRetInfo1[0];         //��ȣȭ�� ���� �Ķ����
        encMsg   = aRetInfo1[1];    //��ȣȭ�� ���� �Ķ������ Hash��
		
		String encMsg2   = sciSecuMg.getMsg(encPara);
		
		// 5. ��/���� ���� ---------------------------------------------------------------

        if(encMsg2.equals(encMsg)){
            msgChk="Y";
        }

		if(msgChk.equals("N")){
%>
		    <script language=javascript>
            alert("���������� �����Դϴ�.!!<%=msgChk%>");
		    </script>
<%
			return;
		}

        // ��ȣȭ �� ��/���� ���� ---------------------------------------------------------------
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

		//���� �ʵ�
		ext1		= aRetInfo[15];
		ext2		= aRetInfo[16];
		ext3		= aRetInfo[17];
		ext4		= aRetInfo[18];
		ext5		= aRetInfo[19];

		//----------------------------------------------------------------------
		// [SECURITY] Save the verified result into the server session.
		// The hidden form below is submitted through the browser and can be
		// forged, so server must read these values from the session
		// only, never from the request parameters.
		//----------------------------------------------------------------------
		if ("Y".equals(result)) {
			session.setAttribute("PCC_VERIFIED_DI",     di);
			session.setAttribute("PCC_VERIFIED_NAME",   name);
			session.setAttribute("PCC_VERIFIED_SEX",    sex);
			session.setAttribute("PCC_VERIFIED_BIRYMD", birYMD);
			session.setAttribute("PCC_VERIFIED_CELLNO", cellNo);
			session.setAttribute("PCC_VERIFIED_TIME",   Long.valueOf(System.currentTimeMillis()));
		}
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