/*<![CDATA[*/

function btnAvtive() {
	
	const sName = $('#sName').val();
	const sEmail = $('#sEmail').val();
	
	if($.trim(sName).length > 1
	&& $("input:radio[name='gStip']").is(":checked") == true
	&& $("input:radio[name='sActive']").is(":checked") == true
	&& $.trim(sEmail).length > 1) 
	{
		$("#submitBtn").attr("disabled", false); //해제
		$("#submitBtn").addClass('active');
	} else {
		$("#submitBtn").attr("disabled", true); //설정
		$("#submitBtn").removeClass('active');		
	}
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
		
		$("#sEmail").val($("#txtEmail").val()+$("#txtEmail2").val());
		
   });
});

$('#txtEmail2').keyup(function () {

	$("#sEmail").val($("#txtEmail").val()+$("#txtEmail2").val());
	return;

});


/*]]>*/