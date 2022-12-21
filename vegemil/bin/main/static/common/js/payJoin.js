/*<![CDATA[*/

function btnAvtive() {
	
	const sId = $('#mId').val();
	const bizDuplicate = $('#bizDuplicate').val();
	const sEmail = $('#mEmail').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	
	if($.trim(sId).length > 1
	&& $.trim(bizDuplicate) == '1'
	&& $.trim(sPw1).length > 1
	&& $.trim(sPw2).length > 1
	&& $.trim(sEmail).length > 1) 
	{
		$("#joinBtn").attr("disabled", false); //해제
		$("#joinBtn").addClass('active');
	} else {
		$("#joinBtn").attr("disabled", true); //설정
		$("#joinBtn").removeClass('active');		
	}
}

//id 중복확인
$("#overlappedBiz").click(function(){
	//$("#joinBtn").prop("type", "button");
	const vId = $("#mId").val();
	
	if( vId.length != 10 ) {
		alert("10자리 숫자로 입력해주세요.");
		return false;
	}
	
	$.ajax({
		url: "/member/idCheck",
		data: {mId: vId},
		dataType : 'json',
		success: function (data) {
			if(data == 1) {
				$("#bizCheackOk").css('display', 'none');
	            $("#bizCheackNo").css('display', 'inline-block');
	            $("#bizDuplicate").val('0');
	            btnAvtive();
			} else {
				$("#bizCheackNo").css('display', 'none');
	            $("#bizCheackOk").css('display', 'inline-block');
				$("#joinBtn").prop("type", "submit");
				$("#bizDuplicate").val('1');
			}
		}
	})
	return;
});

$('#mId').keyup(function () {

    var retVal = false;
    const vId = $("#mId").val();

	if (vId.length != 10) {
	    $("#bizCheack").text("사업자번호는 10자 여야합니다.");
	    $("#bizCheack").css('display', 'inline-block');
	} else {
		$("#bizCheack").css('display', 'none');
	    retVal = true;
	    btnAvtive();
	}
	
	return retVal;

});


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
		
		$("#mEmail").val($("#txtEmail").val()+$("#txtEmail2").val());
		btnAvtive();
   });
});

$('#txtEmail2').keyup(function () {

	$("#mEmail").val($("#txtEmail").val()+$("#txtEmail2").val());
	btnAvtive();
	return;

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
		btnAvtive();
		return true;
	}
	
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
            btnAvtive();
        } else {
            $("#pwCheackOk").css('display', 'none');
            $("#pwCheackNo").css('display', 'inline-block');
            $("#pw1").addClass('error');
            $("#pw2").addClass('error');
        }
    }
    
});

//핸드폰 번호 입력
$('input[name=hp]').keyup(function () {

	$('#mHp').val($('#hp1').val()+$('#hp2').val()+$('#hp3').val());

});


/*]]>*/