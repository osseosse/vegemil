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

//    $('.datatables-basic').dataTable().fnDraw();

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
  
});

var createTable = function() {
	
	var dt_basic_table = $('.datatables-basic'),
    dt_date_table = $('.dt-date');
    const table = $('.datatables-basic').DataTable();
    table.destroy();
	
  // DataTable with buttons
  // --------------------------------------------------------------------

  if (dt_basic_table.length) {
    var dt_basic = dt_basic_table.DataTable({
    
	  lengthChange: false,
      ajax: {
        url : '/admin/manage/baby/sampleBabycntInfo',
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data:function(params){   
			var json = $("#frm").serializeObject();
			
			$.each(json,function(e){
				params[e] = json[e];
			});
			
		},
		dataSrc: function(res) {
			
			return res.data
		},
		error : function(xhr, ajaxSettings, thrownError) { 
			console.log('error');
		}
	  },
      columns: [
      	{ data: 'mId' },
      	{ data: 'mYear' },
      	{ data: 'mNinf' },
      	{ data: 'mNtod' },
      	{ data: 'mNkin' },
      	{ data: 'mUpdateDate' }
      ],
      columnDefs: [
 		
        {
          // For Checkboxes
          targets: 0,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mId']==null)	return '';
      			else return '<p type="text" class="form-control" id="mId'+full['mId']+'" value="'+full['mId']+'"/>';
          }
        },
        {
          targets: 1,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mYear']==null)	return '';
      			else  return full['mYear']+'년  '+full['mMon']+'월';
          }
          
        },
        {
          targets: 2,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mNinf']==null)	return '';
      			else  return '<input type="text" class="form-control" id="mNinf'+full['mId']+'" value="'+full['mNinf']+'">' 
      				+ '<p class="small m-1 text-info">*현재신청수량 : <span>'+ full['ninfCurrent']+'</span></p>'
      				+ '<p class="small m-1 text-warning">*현재잔여수량 : <span>'+ (parseInt(full['mNinf'], 10) - parseInt(full['ninfCurrent'], 10)) +'</span></p>'
          }
          
        },
        {
          targets: 3,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mNtod']==null)	return '';
      			else  return '<input type="text" class="form-control" id="mNtod'+full['mId']+'" value="'+full['mNtod']+'">'
      			    + '<p class="small mt-1 text-info">*현재신청수량 : <span>'+ full['ntodCurrent']+'</span></p>'
      				+ '<p class="small m-1 text-warning">*현재잔여수량 : <span>'+ (parseInt(full['mNtod'], 10) - parseInt(full['ntodCurrent'], 10)) +'</span></p>'
          }
          
        },
        {
          targets: 4,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mNkin']==null)	return '';
      			else  return '<input type="text" class="form-control" id="mNkin'+full['mId']+'" value="'+full['mNkin']+'">'
      				+ '<p class="small m-1 text-info">*현재신청수량 : <span>'+ full['nkinCurrent']+'</span></p>'
      				+ '<p class="small m-1 text-warning">*현재잔여수량 : <span>'+ (parseInt(full['mNkin'], 10) - parseInt(full['nkinCurrent'], 10)) +'</span></p>'
          }
          
        },
     	{
          targets: 5,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mUpdateDate']==null)	return '';
      			else  return full['mUpdateDate'];
          }
          
        },
     	{
          targets: 6,
          orderable: false,
          render: function (data, type, full, meta) {
            return '<button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" onclick="btnSave('+ full['mId'] + ',\'U\')">수정</button>'
          }
          
        }
      ],
      order: [[0, 'desc']],
      dom:
        '<"card-header border-bottom p-1"<"head-label"><"dt-action-buttons text-right"B>><"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"d-flex justify-content-between mx-0 row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
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
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6]}
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6]}
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6]}
            },
            {
              extend: 'copy',
              text: feather.icons['copy'].toSvg({ class: 'font-small-4 mr-50' }) + 'Copy',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6]}
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
	
    $('div.head-label').html('<h4 class="card-title">샘플수량설정 </h4> ');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
	
}


function btnSave(idx, action) {
	
	var msg = "";
	const form = $('#form');	
	
	if(action == "I") {
		msg = "등록하시겠습니까?";
		$('#mId').val(idx);
		$('#mNinf').val($('#mNinf'+idx).val());
		$('#mNtod').val($('#mNtod'+idx).val());
		$('#mNkin').val($('#mNkin'+idx).val());

	}else{
		if(action == "U") {
			msg = "수정하시겠습니까?";	
		}
		$('#mId').val(idx);	
		$('#mNinf').val($('#mNinf'+idx).val());
		$('#mNtod').val($('#mNtod'+idx).val());
		$('#mNkin').val($('#mNkin'+idx).val());

	}
	
	if(confirm(msg)) {
		$.ajax({
	       url: '/admin/manage/baby/sampleBabycntUpdate',
		   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
		   contentType: 'application/x-www-form-urlencoded',  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
		   data: form.serialize(),
		   type: 'POST' 
		}).done(function(data){
	
		   if(data) {
		   	   console.log(data.result);
		   	   if(data.result){
		   	   	alert('저장되었습니다.');
		   	   }else{
		   	    alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
		   	   }
		   	   $('.datatables-basic').DataTable().ajax.reload();
		   }else{
		  	   alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
		   }
		 }).fail(function() {
		   	   console.log('fail')
		 })
	}
}

