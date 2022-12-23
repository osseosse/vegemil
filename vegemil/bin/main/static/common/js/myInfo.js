/*<![CDATA[*/

//휴대폰 인증 확인
function joinHPCheck(){
	
	var authForm = $('#authForm');
	var url = document.URL;
	var urlStr = url.split("/");
	var urlStr_1 = urlStr[3];
	var urlStr_2 = urlStr[4];
	var urlStr_3 = urlStr[5];
	var urlStr_4 = urlStr[6];

	var reqURL;
	if(urlStr_3 == null){
		reqURL = "/" + urlStr_1 + "/" + urlStr_2;
	}else if(urlStr_4 == null){
		reqURL = "/" + urlStr_1 + "/" + urlStr_2 + "/" + urlStr_3;
	}else{
		reqURL = "/" + urlStr_1 + "/" + urlStr_2 + "/" + urlStr_3 + "/" + urlStr_4;
	}
	
	var step = $('#step').val();

	$('#reqURL').attr("value", reqURL+"?step="+step);
	$('#firstURL').attr("value", urlStr[0]);
	$('#secondURL').attr("value", urlStr[2]);
	
	var PCC_window = window.open('', 'PCCV3Window', 'width=430, height=560, resizable=1, scrollbars=no, status=0, titlebar=0, toolbar=0, left=300, top=200' );
	authForm.attr("action", "/input_seed.jsp");
	authForm.attr("target", "PCCV3Window");
	authForm.submit();

	if(PCC_window == null){
		alert(" ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.");
   	}

}

function execPostcode() {
new daum.Postcode({
    oncomplete: function(data) {
        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
        var addr = ''; // 주소 변수
        var extraAddr = ''; // 참고항목 변수

        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
            addr = data.roadAddress;
        } else { // 사용자가 지번 주소를 선택했을 경우(J)
            addr = data.jibunAddress;
        }

        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
        if(data.userSelectedType === 'R'){
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraAddr !== ''){
                extraAddr = ' (' + extraAddr + ')';
            }
            // 조합된 참고항목을 해당 필드에 넣는다.
            addr = addr+' '+extraAddr;
        
        }

        // 우편번호와 주소 정보를 해당 필드에 넣는다.
        document.getElementById('mZipcode').value = data.zonecode;
        document.getElementById("mAddr1").value = addr;
        // 커서를 상세주소 필드로 이동한다.
        document.getElementById("mAddr2").focus();
    }
    }).open();
}
/*[- end of function -]*/

function btnAvtive() {
	
	const sEmail = $('#mEmail').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sAddr1 = $('#mAddr1').val();
	const sAddr2 = $('#mAddr2').val();
	
	if($.trim(sPw1).length > 1
	&& $.trim(sPw2).length > 1
	&& $("input:radio[name='mSmssend']").is(":checked") == true
	&& $.trim(sAddr1).length > 1
	&& $.trim(sAddr2).length > 1
	&& $("input:radio[name='mEmailsend']").is(":checked") == true
	&& $.trim(sEmail).length > 1) 
	{
		$("#joinBtn").attr("disabled", false); //해제
		$("#joinBtn").addClass('active');
	} else {
		$("#joinBtn").attr("disabled", true); //설정
		$("#joinBtn").removeClass('active');		
	}
	
	return;
}

//이메일 입력방식 선택
$('#selEmail').change(function(){
   $("#selEmail option:selected").each(function () {
		
		if($(this).val()== '1'){ //직접입력일 경우
			 $("#txtEmail2").val('');                        //값 초기화
			 $("#txtEmail2").attr("disabled",false); //활성화
		}else{ //직접입력이 아닐경우
			 $("#txtEmail2").val($(this).text());      //선택값 입력
			 $("#txtEmail2").attr("disabled",true); //비활성화
		}
		
		$("#mEmail").val($("#txtEmail").val()+"@"+$("#txtEmail2").val());
   });
   btnAvtive();
   
});

$('#mAddr2').keyup(function () {

	btnAvtive();
	return;

});

$('#txtEmail').keyup(function () {
	btnAvtive();
});

$('#txtEmail2').keyup(function () {
	$("#mEmail").val($("#txtEmail").val()+$("#txtEmail2").val());
	btnAvtive();
});

$("input[name='mSmssend']:radio").change(function () {
	btnAvtive();
});

$("input[name='mEmailsend']:radio").change(function () {
	btnAvtive();
});

//비밀번호 정규식 검사
$('#pw1').keyup(function () {

	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	var num = sPw1.search(/[0-9]/g);
	var eng = sPw1.search(/[a-z]/ig);
	var spe = sPw1.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	 
	if( sPw1.length < 8 || sPw1.length > 20 ){
		$("#pwCheack").css('display', 'inline-block');
		return false;
	} else if( sPw1.search(/\s/) != -1 ){
		$("#pwCheack").css('display', 'inline-block');
		return false;
	} else if( num < 0 || eng < 0 || spe < 0 ){
		$("#pwCheack").css('display', 'inline-block');
		return false;
	} else {
		$("#pwCheack").css('display', 'none');
		return true;
	}
	
	btnAvtive();
	 
});

//비밀번호 일치 검사
$('input[name=mPwd]').keyup(function () {
	const sEmail = $('#txtEmail').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();

    if ( sPw1 != '' && sPw2 == '' ) {
        null;
    } else if (sPw1 != "" || sPw2 != "") {
        if (sPw1 == sPw2) {
            $("#pwCheackOk").css('display', 'inline-block');
            $("#pwCheackNo").css('display', 'none');
            $("#pw1").removeClass('error');
            $("#pw2").removeClass('error');
        } else {
            $("#pwCheackOk").css('display', 'none');
            $("#pwCheackNo").css('display', 'inline-block');
            $("#pw1").addClass('error');
            $("#pw2").addClass('error');
        }
    }
    
    btnAvtive();
    
});

/*]]>*/