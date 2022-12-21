


// loaded fixed-sticky polyfill
// https://github.com/filamentgroup/fixed-sticky



//브랜드 360탭
(function( $ ) {
  $.fn.basicTabs_360 = function(options){ /* basicTabs_360 변경 */
    var settings = $.extend({
      active_class: "current",
      list_class: "tabs360", /* tabe2 변경*/
      content_class: "tab_content2", /* tab_content2 */
      starting_tab: 1
    }, options );
    var $content = $('.' + settings.content_class);
    var $list = $('.' + settings.list_class);
    $content.find('dl').hide(); // 3D노출 문제로 div > dl로 변경
    $content.find("dl:nth-child(" + settings.starting_tab + ")").show();  // 3D노출 문제로 div > dl로 변경
    $list.find("li:nth-child(" + settings.starting_tab + ")").addClass(settings.active_class);

    $("." + settings.list_class + ' li a').click(function(e){
        $list.find("li").removeClass(settings.active_class);
        $("." + settings.content_class + " > dl").hide();  // 3D노출 문제로 div > dl로 변경
        $(this).parent().addClass(settings.active_class);
        var currentTab = $(this).attr('href');
        $(currentTab).show();
        e.preventDefault();
    });
  };
}( jQuery ));




