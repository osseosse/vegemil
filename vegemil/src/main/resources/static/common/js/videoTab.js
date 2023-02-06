var tag = document.createElement('script');
		tag.src = "https://www.youtube.com/player_api";
		var firstScriptTag = document.getElementsByTagName('script')[0];
		firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
var player;
var player2;
var player3;
function onYouTubePlayerAPIReady() {
  // TVCF
  player = new YT.Player('player', {
   // width: 640,
   // height: 400,
    videoId: 'M9RY0KW61SA',
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
  
    videoId: 'Xf3nXHwMeLY',
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
    videoId: 'sskZWJ-pSnw',
    "playerVars": {
      //autoplay: 1,
      showinfo:0,
      controls:1,
      rel: 0
    }
  });



}
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
    player3.cueVideoById(
      {
        videoId: url
      }
    );
    player3.playVideo();
});


