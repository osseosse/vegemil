<%
/**************************************************************************************************************************
* Program Name  : ����Ȯ�� �߰���Ȱ ��������� 
* File Name     : pcc_V3_popup_seed
* Comment       :  �ش� �������� �߰���Ȱ �������̱�� ��û���������� �����������  pcc_V3_result_seed �� �����ؼ� retInfo �� �޾Ƶ� �������.
* History       : 
**************************************************************************************************************************/
%>

<%@ page  contentType = "text/html;charset=ksc5601"%>
<%
	response.setHeader("Cache-Control","no-cache");     
	response.setHeader("Pragma","no-cache");
%>

<%
	String param = "";
	//�ݵ�� get�� post ��� �� �� �������ְ� ����س�����.
	String enc_retInfo   = request.getParameter("retInfo").trim(); //  �ȹ޾����� ȸ���簡 ������ ���ɼ��������� ��Ʈ��ũ in,out�� ����ʿ�. ���߹�������
	
	param= "?retInfo="+enc_retInfo;
%>
<html>
<head>
<script language="JavaScript">
	function end() {
		window.opener.location.href = './result_seed.jsp<%=param%>';
		self.close();
	}
</script>
</head>
<body onload="javascript:end()">
</body>
</html>