/*<![CDATA[*/

function execPostcode() {
new daum.Postcode({
    oncomplete: function(data) {
        var addr = ''; // 주소 변수
        var extraAddr = ''; // 참고항목 변수

        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
            addr = data.roadAddress;
        } else { // 사용자가 지번 주소를 선택했을 경우(J)
            addr = data.jibunAddress;
        }

        if(data.userSelectedType === 'R'){
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraAddr += data.bname;
            }
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            if(extraAddr !== ''){
                extraAddr = ' (' + extraAddr + ')';
            }
            addr = addr+' '+extraAddr;
        }
        document.getElementById('cZipcode').value = data.zonecode;
        document.getElementById("cAddr1").value = addr;
        document.getElementById("cAddr2").focus();
        
    }
    }).open();
}
/*[- end of function -]*/

function btnAvtive() {
	
	const cName = $('#cName').val();
	const cEmail = $('#cEmail').val();
//	const cHp = $('#cHp').val();
	const cAddr = $('#cAddr').val();
	const cAddr2 = $('#cAddr2').val();
	//const cFileName = $('#fileName1').val();
	
	if($.trim(cName).length > 1
	&& $('input[name=agree_radio]:checked').val() == '1'
	&& $.trim(cAddr).length > 1
	&& $.trim(cEmail).length > 1) 
	{
		$("#joinBtn").attr("disabled", false); //해제
	} else {
		$("#joinBtn").attr("disabled", true); //설정
	}
	return;
}

$(function(){

	$("input[name='agree_radio']:radio").change(function () {
		btnAvtive();
	});
	$("#cAddr2").keyup(function () {
		$("#cAddr").val($("#cAddr1").val()+" "+$("#cAddr2").val());
		btnAvtive();
	});
	
	$('#cHp').keyup(function () {
		btnAvtive();
	});
	
	$('#cEmail').keyup(function () {
		btnAvtive();
	});
	$("input[name='agree_radio2']:radio").change(function () {
		btnAvtive();
	});
	$('#cBabyName').keyup(function () {
		btnAvtive();
	});
	$('#cAlived').keyup(function () {
		btnAvtive();
	});
	$('#cRoute').keyup(function () {
		btnAvtive();
	});

});
/*]]>*/