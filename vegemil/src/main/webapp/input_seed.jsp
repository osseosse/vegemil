<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page import ="java.util.*,java.text.SimpleDateFormat"%>
<%
        //��¥ ����
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String day = sdf.format(today.getTime());

        String reqURL    =  request.getParameter("reqURL");
    	String firstURL  =  request.getParameter("firstURL");
    	String secondURL =  request.getParameter("secondURL");

        java.util.Random ran = new Random();
        //���� ���� ����
        int numLength = 6;
        String randomStr = "";

        for (int i = 0; i < numLength; i++) {
            //0 ~ 9 ���� ���� ����
            randomStr += ran.nextInt(10);
        }

		//reqNum�� �ִ� 40byte ���� ��� ����
        String reqNum = day + randomStr;
		String certDate=day;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    </head>
    <body onload="document.reqCBAForm.id.focus();" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 marginheight=0 marginwidth=0>
          <form name="reqCBAForm" method="post" action="sample_seed.jsp">
              <!--  <td align=center>ȸ������̵�</td>-->
              <input type="hidden" name="id" size='41' maxlength ='8' value = "SJSP001">
              <!--  <td align=center>���񽺹�ȣ</td>-->
              <!-- localhost:8080 -->
              <!-- <input type="hidden" name="srvNo" size='41' maxlength ='6' value="010002"> -->
              <!-- vegemil.co.kr -->
              <!-- <input type="hidden" name="srvNo" size='41' maxlength ='6' value="012001"> -->
              <!-- vegemil.kr -->
              <input type="hidden" name="srvNo" size='41' maxlength ='6' value="013001">
              <!--  <td align=center>��û��ȣ</td>-->
              <input type="hidden" name="reqNum" size='41' maxlength ='40' value='<%=reqNum%>'>
              <!--  <td align=center>��������</td>-->
              <input type="hidden" name="certGb" size='41' maxlength ='40' value="H">
              <!--  <td align=center>��û�ð�</td>-->
              <input type="hidden" name="certDate" size='41' maxlength ='40' value='<%=certDate%>'>
              <!--  <td align=center>�߰��Ķ����</td>-->
              <input type="hidden" name="addVar"  size="41" maxlength ='320' value='<%=reqURL%>'>
              <!--  <td align=center>�������URL</td>-->
              <input type="hidden" name="retUrl" size="41" value="32<%=firstURL%>//<%=secondURL%>/popup_seed.jsp">
          </form>
          <script type="text/javascript">
          	reqCBAForm.submit();
		</script>
    </body>
</html>