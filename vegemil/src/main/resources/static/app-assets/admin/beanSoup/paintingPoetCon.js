/**
 * DataTables Basic
 */
 
 'use strict';

// Advanced Search Functions Starts
// --------------------------------------------------------------------

// Filter column wise function
function filterColumn(val) {

    var startDate = $('.start_date').val(),
      endDate = $('.end_date').val();
      
      if (startDate !== '' && endDate !== '') {
        filterByDate(5, startDate, endDate); // We call our filter function
      }

    $('.datatables-basic').dataTable().fnDraw();

}

// Datepicker for advanced filter
var separator = ' - ',
  rangePickr = $('.flatpickr-range'),
  dateFormat = 'yyyy-MM-dd';
var options = {
  autoUpdateInput: false,
  autoApply: true,
  locale: {
    format: dateFormat,
    separator: separator
  },
  opens: $('html').attr('data-textdirection') === 'rtl' ? 'left' : 'right'
};

//
if (rangePickr.length) {
  rangePickr.flatpickr({
    mode: 'range',
    dateFormat: 'Y-m-d',
    onClose: function (selectedDates, dateStr, instance) {
      var startDate = '',
        endDate = new Date();
        
      var month_s,date_s,month_e,date_e;
      
      if((selectedDates[0].getMonth() + 1) < 10) month_s = '0'+(selectedDates[0].getMonth() + 1);
      else month_s = (selectedDates[0].getMonth() + 1);
      if(selectedDates[0].getDate() < 10) date_s = '0'+selectedDates[0].getDate();
      else date_s = selectedDates[0].getDate();
        
      if (selectedDates[0] != undefined) {
        startDate =
          selectedDates[0].getFullYear() + '-' + month_s + '-' + date_s;
        $('.start_date').val(startDate);
      }

      if((selectedDates[1].getMonth() + 1) < 10) month_e = '0'+(selectedDates[1].getMonth() + 1);
      else month_e = (selectedDates[1].getMonth() + 1);
      if(selectedDates[1].getDate() < 10) date_e = '0'+selectedDates[1].getDate();
      else date_e = selectedDates[1].getDate();
      
      if (selectedDates[1] != undefined) {
        endDate =
          selectedDates[1].getFullYear() + '-' + month_e + '-' + date_e;
        $('.end_date').val(endDate);
      }
      $(rangePickr).trigger('change').trigger('keyup');
    }
  });
}

// Advance filter function
// We pass the column location, the start date, and the end date
var filterByDate = function (column, startDate, endDate) {
  // Custom filter syntax requires pushing the new filter to the global filter array
  $.fn.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {
    var rowDate = normalizeDate(aData[column]),
      start = normalizeDate(startDate),
      end = normalizeDate(endDate);
	
    // If our date from the row is between the start and end
    if (start <= rowDate && rowDate <= end) {
      return true;
    } else if (rowDate >= start && end === '' && start !== '') {
      return true;
    } else if (rowDate <= end && start === '' && end !== '') {
      return true;
    } else {
      return false;
    }
  });
};

// converts date strings to a Date object, then normalized into a YYYYMMMDD format (ex: 20131220). Makes comparing dates easier. ex: 20131220 > 20121220
var normalizeDate = function (dateString) {
  //var date = new Date(dateString);
  var normalized = dateString.slice(0,4) + dateString.slice(5,7) + dateString.slice(8,10);
    //date.getFullYear() + '' + ('0' + (date.getMonth() + 1)).slice(-2) + '' + ('0' + date.getDate()).slice(-2);
  return normalized;
};
// Advanced Search Functions Ends
/*<![CDATA[*/
function drawGraph(){

	$.ajax({
		url:"/admin/beansoup/submitCount",
		type:"get",
		dataType:"json",
		success:function(data){

			new Chart(document.getElementById("bar-chart1"), {
				type: 'bar',
				data: {
				  labels: ["미취학", "초등 저학년", "초등 고학년",""],
				  datasets: [
					{
					  label: "Population (millions)",
					  backgroundColor: ["#3e95cd","#9FC93C", "#8e5ea2","#3cba9f"],
					  data: [data.data[0], data.data[1], data.data[2], 0]
					}
				  ]
				},
				options: {
				  legend: { display: false },
				  title: {
					display: true,
					text: '그림 동시 대회 연령별 참가자'
				  }
				}
			});
		}
	});
	
}
/*]]>*/

$(function () {

	drawGraph();
	createTable();
	
});


var createTable = function() {
	console.log('carateTable')
	var dt_basic_table = $('.datatables-basic'),
    dt_date_table = $('.dt-date');

  // DataTable with buttons
  // --------------------------------------------------------------------

  if (dt_basic_table.length) {	
	
    var dt_basic = dt_basic_table.DataTable({
	  destroy: true,
	  lengthChange: false,
      bPaginate: true,
	  pageLength: 10,
	  serverSide: true,
	  processing: true,
      ajax: {
        url : '/admin/manage/beanSoup/paintingPoetConList',
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data:function(params){   
			var json = $("#frm").serializeObject();
			$.each(json,function(e){
				params[e] = json[e];
			});
		},
		error : function(xhr, ajaxSettings, thrownError) { 
			console.log('error');
		}
	  },
      columns: [
      	{ data: 'id' },
      	{ data: 'section' },
      	{ data: 'material' },
      	{ data: 'guardianName' },
      	{ data: 'contestantName' },
      	{ data: 'guardianPh' },
        { data: 'paintingDesc'},
        { data: 'contestRoot'},
        { data: 'beansoupAwareness' },
        { data: 'prize' },
        { data: 'id' },
        
      ],
      columnDefs: [
      	{
      		targets: 0,
      		orderable: false
      	},
		  {
			targets: 1,
			orderable: false,
			render: function (data, type, full, meta) {
				if(data==null)	return '-';
				else	return data;
			}
		},
      	{
      		targets: 2,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(data==null)	return '-';
      			else	return data;
      		}
      	},
      	{
      		targets: 3,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(data==null)	return '-';
      			else	return data;
      		}
      	},
      	{
      		targets: 4,
      		orderable: false,
      		render: function (data, type, full, meta) {
				if(data==null)	return '-';
      			else	return data;													
      		}
      	},
      	{
      		targets: 5,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(data==null)	return '-';
      			else	return data;
      		}
      	},
      	{
      		targets: 6,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['zipCode']==null)	return '-';
      			else return '(' + full['zipCode'] + ') ' + full['addr1'] + ' ' +full['addr2'];
      		}
      	},   
      	{
      		targets: 7,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(data==null)	return '-';
      			else return data;
      		}
      	},   
      	{
      		targets: 8,
      		orderable: false,
      		render: function (data, type, full, meta) {
				let checked;
				return (
				'<div class="form-check form-check-inline">'+
				'<input class="form-check-input" type="radio" name="beansoupAwareness'+full['id']+'" id="inlineRadio" value="0" '+getCheck(1, full['beansoupAwareness'])+' disabled >'+													
				'<label class="form-check-label" for="inlineRadio1">Y</label>'+
				'</div>'+
				'<div class="form-check form-check-inline">'+
				'<input class="form-check-input" type="radio" name="beansoupAwareness'+full['id']+'" id="inlineRadio1" value="1" '+getCheck(0, full['beansoupAwareness'])+' disabled >'+													
				'<label class="form-check-label" for="inlineRadio1">N</label>'+
				'</div>'
				)
      			
      		}
      	},
      	{
      		targets: 9,
      		orderable: false,
      		render: function (data, type, full, meta) {
				let checked;
				return (
				'<div class="form-check form-check-inline">'+
				'<input class="form-check-input" type="radio" name="prize'+full['id']+'" id="inlineRadio" value="0" '+getCheck(0, full['prize'])+' onclick="btnSave('+full['id']+',\'U\')" />'+													
				'<label class="form-check-label" for="inlineRadio1">낙선</label>'+
				'</div>'+
				'<div class="form-check form-check-inline">'+
				'<input class="form-check-input" type="radio" name="prize'+full['id']+'" id="inlineRadio1" value="1" '+getCheck(1, full['prize'])+' onclick="btnSave('+full['id']+',\'U\')" />'+													
				'<label class="form-check-label" for="inlineRadio1">입선</label>'+
				'</div>'
				)
      			
      		}
      	},
      	{
      		targets: 10,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			return( '<a data-bs-toggle="modal" data-bs-target="#large'+full['id']+'"><button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" \'">작품보기</button></a>'
				  +getModal(full)
				)
				
      		}
      	}
      ],
      order: [[0, 'desc']],
      dom:
        '<"card-header border-bottom p-1"<"head-label"><"dt-action-buttons text-right"B>><"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"d-flex justify-content-between mx-0 row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
      displayLength: 10,
      lengthMenu: [10, 25, 50, 75, 100],
      buttons: [
        {
          extend: 'collection',
          className: 'btn btn-outline-secondary dropdown-toggle mr-2',
          text: feather.icons['share'].toSvg({ class: 'font-small-4 mr-50' }) + 'Export',
          buttons: [
            {
              extend: 'print',
              text: feather.icons['printer'].toSvg({ class: 'font-small-4 mr-50' }) + 'Print',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'copy',
              text: feather.icons['copy'].toSvg({ class: 'font-small-4 mr-50' }) + 'Copy',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8] }
            }
          ],
          init: function (api, node, config) {
            $(node).removeClass('btn-secondary');
            $(node).parent().removeClass('btn-group');
            setTimeout(function () {
              $(node).closest('.dt-buttons').removeClass('btn-group').addClass('d-inline-flex');
            }, 50);
          }
		}
      ],
      language: {
		search : '검색',
	    emptyTable:     "표에서 사용할 수있는 데이터가 없습니다.",
	    zeroRecords: "해당 조건에 대한 검색 결과가 없습니다.",
	    lengthMenu: "&nbsp;&nbsp;페이지당 _MENU_ 개씩 보기",
        paginate: {
          // remove previous & next text from pagination
          previous: '&nbsp;',
          next: '&nbsp;'
        }
      },
      info: false,
      searching: false
    });
    dt_basic.on( 'order.dt', function () {
		dt_basic.column(0, {search:'applied'}).nodes().each( function (cell, i) {
			cell.innerHTML = i+1;
		} );
	} ).draw();
    
	// 그래프 그리기
	/* new Chart(document.getElementById("bar-chart2"), {
	    type: 'bar',
	    data: {
	      labels: ["고사리", "미나리", "콩나물",""],
	      datasets: [
	        {
	          label: "Population (millions)",
	          backgroundColor: ["#3e95cd","#9FC93C", "#8e5ea2","#3cba9f"],
	          data: [20,10,30,0]
	        }
	      ]
	    },
	    options: {
	      legend: { display: false },
	      title: {
	        display: true,
	        text: '가장 많이 선택한 재철재료'
	      }
	    }
	}); */
    // 그래프 그리기
	/* new Chart(document.getElementById("bar-chart1"), {
	    type: 'bar',
	    data: {
	      labels: ["미취학", "초등 저학년", "초등 고학년",""],
	      datasets: [
	        {
	          label: "Population (millions)",
	          backgroundColor: ["#3e95cd","#9FC93C", "#8e5ea2","#3cba9f"],
	          data: [20,10,30,0]
	        }
	      ]
	    },
	    options: {
	      legend: { display: false },
	      title: {
	        display: true,
	        text: '그림 동시 대회 연령별 참가자'
	      }
	    }
	}); */
    
    $('div.head-label').html('<h4 class="card-title">접수목록</h4>');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
}


function getCheck(val1, val2) {
	if(val1 == val2) return 'checked';
}

function btnSave(idx, action) {
	const form = $('#prizePost');
	let msg;
	let preVal = $('input[name=prize'+idx+']:checked').val();
	
	if(action == "I") {
		msg = "등록하시겠습니까?";
	}else{
		if(action == "U") {
			msg = "수정하시겠습니까?";
			if($('input[name=prize'+idx+']:checked').val() == 1) {
				msg = "입선작선택";				
			}else{
				msg = "낙선작";
			}
			$('#prize').val($('input[name=prize'+idx+']:checked').val());
		}else {
			msg = "삭제하시겠습니까?";	
		}
	}
	$('#id').val(idx);
	$('#action').val(action);
	
	if(confirm(msg)) {
		$.ajax({
	       url: '/admin/manage/beanSoup/savePaintingPoetConData',
		   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
		   contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
		   data: form.serialize(),
		   dataType : 'json',
		}).done(function(data){
		   console.log('done', data)
		   if(data.result) {
		   	   alert('수정되었습니다.');
		   	   $('.datatables-basic').DataTable().ajax.reload();
		   }else{
		  	   alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
		   }
		 }).fail(function() {
		   	   console.log('fail')
		 })
	}else {
		//이전 라디오버튼 체크
		$("input[name=prize"+idx+"]:radio[value='" + preVal + "']").prop('checked', true);
		return;
	}
}

function getModal(obj) {
	let modal = "";
	modal +=  '<section id="modal-sizes">'
	modal +=     '<div class="modal fade text-start" id="large'+obj.id+'" tabindex="-1" aria-labelledby="myModalLabel17" aria-hidden="true">'
	modal +=		'<div class="modal-dialog modal-dialog-centered modal-lg">'
	modal +=			'<div class="modal-content">'
	modal +=				'<div class="modal-header">'
	modal +=					'<h4 class="modal-title" id="myModalLabel17">작품</h4>'
	modal +=					'<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="margin:3px;"></button>'
	modal +=				'</div>'
	modal +=				'<table class="table table-bordered p-2 f13 paddingType">'
	modal +=					'<colgroup>'
	modal +=						'<col width="20%">'
	modal +=						'<col width="20%">'
	modal +=						'<col width="40%">'
	modal +=					'</colgroup>'
	modal +=					'<thead>'
	modal +=						'<tr>'
	modal +=							'<th class="px-2">참가자 <span class="text-warning">'+obj.contestantName+' </span></th>'
	modal +=							'<th class="px-2">학년 <span class="text-warning">'+obj.contestantGrade+' </span></th>'
	modal +=							'<th colspan="2" class="px-2">제철재료 <span class="text-warning"> '+obj.material+' </span></th>'
	modal +=						'</tr>'
	modal +=					'</thead>'
	modal +=				'</table>'
	modal +=                '<h4 class="mt-2 ps-2">작품설명</h4>'
	modal +=                '<p class="px-2"><span class="text-warning h6">'+obj.paintingDesc+'</span></p>'
	modal +=				'<div class="row ps-2">'
	modal +=                    '<div class="scroll-h250">'
	modal +=                        '<p class="img100">'
	modal +=                            '<img src="/web/upload/beansoupCon/'+ obj.paintingSavedFilename +'" />'
	modal +=                         '</p>'
	modal +=					'</div>'
	modal +=					'<p><a download class="btn btn-secondary btn-sm2" target="_blank" href="https://www.vegemil.co.kr/web/upload/beansoupCon/'+ obj.paintingSavedFilename +'">그림저장하기</a></p>'
	modal +=				'</div>'
	modal +=			'</div>'
	modal +=		'</div>'
	modal +=	'</div>'
	modal +=  '</section>'
	

	return modal;
}