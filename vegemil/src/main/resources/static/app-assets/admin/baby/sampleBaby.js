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

$(function () {
  createTable();
  
  // 전체 체크 하는 부분
  $("[type=checkbox][name=allCheck]").on("change", function(){ //0
  	var check = $(this).prop("checked"); //1
	//전체 체크
	if($(this).hasClass("form-check-input")){ //2
		$("[type=checkbox][name=checkList]").prop("checked", check);
	}
  });
  
  $('#btnDel').click(function(e){
	console.log('e', e)
    var form = document.form;
      
    // Output form data to a console
    console.log("Form submission", decodeURIComponent($(form).serialize())); 
      
    if(confirm('삭제하시겠습니까?')){
		$.ajax({
			url : '/admin/manage/baby/deleteSampleBaby',
			type : "post",
			data : $(form).serialize(),
			dataType : "json",
			success : function(data) {
				console.log('data============',data);
				if(data){
					alert("삭제되었습니다.");
					//window.location.reload();
					$('.datatables-basic').DataTable().ajax.reload();
				}
				else{
					alert("실패했습니다.");
				}
				
			},
			error : function(){
			}
		});
	}
      
       
      // Prevent actual form submission
      e.preventDefault();
 });

});

var createTable = function() {
	console.log('carateTable')
	var dt_basic_table = $('.datatables-basic'),
    dt_date_table = $('.dt-date');
    const table = $('.datatables-basic').DataTable();
    table.destroy();

  // DataTable with buttons
  // --------------------------------------------------------------------

  if (dt_basic_table.length) {
	const range = $('#fp-range').val().split(' to ');
	const startdate = range[0];
	const enddate = range[1];
	$('#sStartdate').attr("value",startdate);
	$('#sEnddate').attr("value",enddate);
    var dt_basic = dt_basic_table.DataTable({
	  lengthChange: false,
      ajax: {
        url : '/admin/manage/baby/sampleBabyList',
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data:function(params){   
			var json = $("#frm").serializeObject();
			console.log('json', json);
			$.each(json,function(e){
				params[e] = json[e];
			});
		},
		error : function(xhr, ajaxSettings, thrownError) { 
			console.log('error');
		}
	  },
      columns: [
      	{ data: 'sIdx' },
        { data: 'sItem' },
        { data: 'sMname' },
        { data: 'sId' },
        { data: 'sHp' },
        { data: 'sAddr1' },
        { data: 'sAddr2' },
        { data: 'sEmail' },
        { data: 'sWritedate' },
        { data: 'sBefore' },
        { data: 'sDeliverdate' },
        { data: 'sDeliver' }
      ],
      columnDefs: [
      	{
      		targets: 1,
      		orderable: false,
      		render: function (data, type, full, meta) {
	            return (
	              '<div class="form-check">' +
		              '<input type="checkbox" class="form-check-input" id="customCheck2" name="checkList" value="'+full['sIdx']+'" />' +
		              '<label class="form-check-label" for="customCheck2"></label>' +
	              '</div>'
	            );
	        }
      	},
      	{
      		targets: 2,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sItem']==null)	return '미선택';
      			else if(full['sItem']=='INF')	return '인펀트';
      			else if(full['sItem']=='TO2')	return '토들러2';
      			else if(full['sItem']=='TO3')	return '토들러3';
      			else if(full['sItem']=='NINF')	return '(신)인펀트';
      			else if(full['sItem']=='NTO2')	return '(신)토들러2';
      			else if(full['sItem']=='NTO3')	return '(신)토들러3';
      			else	return '미선택';
      			
      		}
      	},
      	{
      		targets: 3,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sId']==null)	return '';
      			else	return full['sId'];
      			
      		}
      	},
      	{
      		targets: 4,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sMname']==null)	return '';
      			else	return full['sMname'];
      		}	
      		
      	},
      	{
      		targets: 5,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sHp']==null)	return '';
      			else	return full['sHp'];
      			
      		}
      	},
      	{
      		targets: 6,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sAddr1']==null && full['sAddr2']==null)	return '';
      			else	return full['sAddr1']+full['sAddr2'];
      			
      		}
      	},
      	{
      		targets: 7,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sMaterdate']==null)	return '';
      			else	return full['sMaterdate'];
      			
      		}
      	},
      	{
      		targets: 8,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sMaterdate2']==null)	return '';
      			else	return full['sMaterdate2'];
      			
      		}
      		
      	},
      	{
      		targets: 9,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sMaterdate3']==null)	return '';
      			else	return full['sMaterdate3'];
      			
      		}
      	},
      	{
      		targets: 10,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sWritedate']==null)	return '';
      			else 	return full['sWritedate'];
      		}
      	},
      	{
          targets: 11,
          orderable: false,
          render: function (data, type, full, meta) {
      			if(full['sBefore']==null)	return '미선택';
      			else if(full['sBefore']=='1')	return '매터니티스쿨 행사참여';
      			else if(full['sBefore']=='2')	return '맘스스토리 행사참여';
      			else if(full['sBefore']=='3')	return '인터넷';
      			else if(full['sBefore']=='4')	return '지인추천';
      			else if(full['sBefore']=='5')	return '산부인과';
      			else if(full['sBefore']=='6')	return '카카오스토리';
      			else if(full['sBefore']=='7')	return '롯데마트 문화센터';
      			else if(full['sBefore']=='9')	return 'K클래스 행사참여';
      			else if(full['sBefore']=='10')	return '영유아식 인스타그램';
      			else	return '기타';
      		}
        }
//        {
//      		targets: 12,
//      		render: function (data, type, full, meta) {
//      			return(
//					'<div class="d-inline-flex">' +
//					'<a class="pr-1 dropdown-toggle hide-arrow text-primary" data-toggle="dropdown">' +
//					feather.icons['more-vertical'].toSvg({ class: 'font-small-4' }) +
//					'</a>' +
//					'<div class="dropdown-menu dropdown-menu-right">' +
//					'<form action="/admin/event/sampleBaby/update?sIdx=' + full['sIdx'] + '&sDeliver=1" method="post" enctype="multipart/form-data">' + 
//					'<input type="submit" class="btn btn-outline-primary" value="       배송         "></input>' +
//					'</form>' + 
//					'<form action="/admin/event/sampleBaby/update?sIdx=' + full['sIdx'] + '&sDeliver=0" method="post" enctype="multipart/form-data">' + 
//					'<input type="submit" class="btn btn-outline-primary" value="       준비        "></input>' +
//					'</form>' + 
//					'</div>' 
//					
//      			);
//      		}
//      	},
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
              exportOptions: { columns: [0, 2, 3, 4, 5, 6, 7, 8, 10, 11] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6, 7, 8, 10, 11] }
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6, 7, 8, 10, 11] }
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6, 7, 8, 10, 11] }
            },
            {
              extend: 'copy',
              text: feather.icons['copy'].toSvg({ class: 'font-small-4 mr-50' }) + 'Copy',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6, 7, 8, 10, 11] }
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
	
    $('div.head-label').html('<h4 class="card-title">신청목록 <button type="button" id="btnDel" class="btn btn-outline-danger btn-sm me-1">선택삭제</button></h4>');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
	
}

