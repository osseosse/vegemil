<%
/**************************************************************************************************************************
* Program Name  : ���νǸ�Ȯ�� ��û Sample JSP (Real)  
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
	  
    String id       = request.getParameter("id");                               // ���νǸ�Ȯ�� ȸ���� ���̵�
    String srvNo    = request.getParameter("srvNo");                            // ���νǸ�Ȯ�� ���񽺹�ȣ
    String reqNum   = request.getParameter("reqNum");                           // ���νǸ�Ȯ�� ��û��ȣ
	String exVar    = "0000000000000000";                                       // ��ȣȭ�� �ӽ��ʵ�
    String retUrl   = request.getParameter("retUrl");                           // ���νǸ�Ȯ�� ������� URL
	String certDate	= request.getParameter("certDate");                         // ���νǸ�Ȯ�� ��û�ð�
	String certGb	= request.getParameter("certGb");                           // ���νǸ�Ȯ�� ����Ȯ�� ��������
	String addVar	= request.getParameter("addVar");                           // ���νǸ�Ȯ�� �߰� �Ķ����
	
	Cookie c = new Cookie("reqNum", reqNum);
	//c.setMaxAge(1800);  // <== �ʿ�� ����(�ʴ����� �����˴ϴ�)
	response.addCookie(c);

	//01. ��ȣȭ ��� ����
	com.sci.v2.pccv2.secu.SciSecuManager seed  = new com.sci.v2.pccv2.secu.SciSecuManager();
	
	//02. 1�� ��ȣȭ
	String encStr = "";
	String reqInfo      = id+"^"+srvNo+"^"+reqNum+"^"+certDate+"^"+certGb+"^"+addVar+"^"+exVar;  // ������ ��ȣȭ
	
	seed.setInfoPublic(id,"309089EAF0519818D08884A89D66756F"); // ȸ���� ID �� PWD ���� �н������ 16�ڸ� �ʼ� ���� ����
	
	encStr              = seed.getEncPublic(reqInfo);

	//03. ������ ���� �� ����
	com.sci.v2.pccv2.secu.hmac.SciHmac hmac = new com.sci.v2.pccv2.secu.hmac.SciHmac();
	//String hmacMsg = hmac.HMacEncriptPublic(encStr);
	String hmacMsg  = seed.getEncReq(encStr,"HMAC");
	
	//03. 2�� ��ȣȭ
	reqInfo  = seed.getEncPublic(encStr + "^" + hmacMsg + "^" + "0000000000000000");  //2����ȣȭ
	
	//04. ȸ���� ID ó���� ���� ��ȣȭ
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