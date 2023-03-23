var tag = document.createElement('script');
		tag.src = "https://www.youtube.com/player_api";
		var firstScriptTag = document.getElementsByTagName('script')[0];
		firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
var player;
var player2;
var player3;
var player4;

/* 유튜부 api 플레이어 생성 */

function onYouTubePlayerAPIReady() {
  // TVCF
  player = new YT.Player('player', {
   // width: 640,
   // height: 400,
    videoId: cfLoadId,
    "playerVars": {
      //autoplay: 1,
      showinfo:0,
      controls:1,
      rel: 0
    }
  });
  
  // 동영상 공모전
  player4 = new YT.Player('player4', {
   // width: 640,
   // height: 400,
    videoId: contestLoadId,
    "playerVars": {
      //autoplay: 1,
      showinfo:0,
      controls:1,
      rel: 0
    }
  });
  
  // Radio
  player2 = new YT.Player('player2', {
   // width: 640,
   // height: 400,
    videoId: cmLoadId,
    "playerVars": {
      //autoplay: 1,
      showinfo:0,
      controls:1,
      rel: 0
    }
  });

   // Etc
   player3 = new YT.Player('player3', {
   // width: 640,
   // height: 400,
    videoId: etcLoadId,
    "playerVars": {
      //autoplay: 1,
      showinfo:0,
      controls:1,
      rel: 0
    }
  });
}

/*클릭 이벤트 리스너 등록*/
 
// TVCF
$('.imge').on('click', function () {
    var url = $(this).attr('data-id');
    player.cueVideoById(
      {
        videoId: url
      }
    );
    player.playVideo();
});

// 동영상 공모전
$('.imge4').on('click', function () {
    var url = $(this).attr('data-id');
    player4.cueVideoById(
      {
        videoId: url
      }
    );
    player.playVideo();
});

// Radio
$('.imge2').on('click', function () {
    var url = $(this).attr('data-id');
    player2.cueVideoById(
      {
        videoId: url
      }
    );
    player2.playVideo();
});

// Etc
$('.imge3').on('click', function () {
    var url = $(this).attr('data-id');
	$("#player3").css("display","block");
 	$("#playerMp4").css("display","none");
    player3.cueVideoById(
      {
        videoId: url
      }
    );
    player3.playVideo();
});

$(".imgeMp").on("click", function() {
	 var url = $(this).attr('data-id');
	 
	 $("#player3").css("display","none");
	 $("#playerMp4").css("display","block");
	 $("#videoMp4").attr("src",url);
});

