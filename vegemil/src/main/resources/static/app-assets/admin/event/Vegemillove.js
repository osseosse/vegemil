
function getLoveData(year){
	
	
	$.ajax({
        url: '/event/loveBoard/'+year,  // 호출할 URL
        type: 'GET',          			// 요청 방식 (GET, POST 등)    	        
        success: function(response) {
            console.log('Success:', response); // 성공 시 처리

            var yearRes = response.data.year;
            var temperature = response.data.temperature;
            
            $("#loveBoard").empty();
            $("#loveBoard").append('<p class="py-5" style="font-size: 30px; color: antiquewhite; "><span>' + yearRes + '</span>년  온도 현황 </p>')
            $("#loveBoard").append('<p class="pb-6" style="font-size: 100px; color: aliceblue;"><span>' + temperature + '</span></p>')	
          	                	                	            
        },
        error: function(error) {
            console.error('Error:', error);    // 에러 시 처리
        }
    });	
}
