/*<![CDATA[*/

function btnAvtive() {
	
	const sName = $('#sName').val();
	const sEmail = $('#sEmail').val();
	
	if($.trim(sName).length > 1
	&& $("input:radio[name='gStip']:checked'").val() == '1'
	&& $("input:radio[name='sActive']").is(":checked") == true
	&& $.trim(sEmail).length > 1) 
	{
		$("#submitBtn").attr("disabled", false); //해제
		$("#submitBtn").addClass('active');
	} else {
		$("#submitBtn").attr("disabled", true); //설정
		$("#submitBtn").removeClass('active');		
	}
	return;
}

$(function(){

	$("input:radio[name=gStip]").click(function() {
		btnAvtive();
	});
	
	$("input:radio[name=sActive]").click(function() {
		btnAvtive();
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
			
			$("#sEmail").val($("#txtEmail").val()+'@'+$("#txtEmail2").val());
			btnAvtive();
	   });
	});

	$('#txtEmail2').keyup(function () {
	
		$("#sEmail").val($("#txtEmail").val()+"@"+$("#txtEmail2").val());
		btnAvtive();
	
	});

	//휴대폰번호 체크
	$("#sHp").keyup(function() {
		const sHp = $('#sHp').val();sHp
		
		if( this.value.length == 0){
			$("#hpCheck").css('display', 'inline-block');
			$("#sHp").addClass('error');
	    } else {
	    	$("#hpCheck").css('display', 'none');
	    	$("#sHp").removeClass('error');
	    }
	    if( this.value.length > 13){
	         this.value = this.value.substr(0, 13);
	     }
	     var val         = this.value.replace(/\D/g, '');
	     var original    = this.value.replace(/\D/g, '').length;
	     var conversion  = '';
	     for(i=0;i<2;i++){
	         if (val.length > 3 && i===0) {
	             conversion += val.substr(0, 3) + '-';
	             val         = val.substr(3);
	         }
	         else if(original>7 && val.length > 4 && i===1){
	             conversion += val.substr(0, 4) + '-';
	             val         = val.substr(4);
	         }
	     }
	     conversion += val;
	     this.value = conversion;
	     
	     btnAvtive();
	     
	});

});
/*]]>*/