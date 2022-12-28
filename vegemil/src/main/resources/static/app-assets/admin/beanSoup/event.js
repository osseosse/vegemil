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
  
  // 전체 체크 하는 부분
  $("[type=checkbox][name=allCheck]").on("change", function(){ //0
  	var check = $(this).prop("checked"); //1
	//전체 체크
	if($(this).hasClass("form-check-input")){ //2
		$("[type=checkbox][name=checkList]").prop("checked", check);
	}
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
        url : '/admin/manage/beanSoup/eventList',
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data:function(params){   
			var json = $("#frm").serializeObject();
			console.log('json', json);
			$.each(json,function(e){
				params[e] = json[e];
			});
			console.log('length',params.length)
		},
		dataSrc: function(res) {
			console.log('res', res.data)
			return res.data
		},
		error : function(xhr, ajaxSettings, thrownError) { 
			console.log('error');
		}
	  },
      columns: [
      	{ data: 'mIdx' },
        { data: 'mProduct' },
        { data: 'mStartDate' },
        { data: 'mContent' },
        { data: 'mEdayId' },
        { data: 'mType' },
        { data: 'mThum' }, 
        { data: 'mDisplay' },
        { data: 'mIng' },
        { data: 'mModify' },
        { data: 'mDelete' }
      ],
      columnDefs: [
        {
          // For Responsive
          orderable: false,
          targets: 0
        },
        {
          // For Checkboxes
          targets: 1,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mProduct']==null)	return '';
      			else	return '<input type="text" class="form-control" id="mProduct'+full['mIdx']+'" value="'+full['mProduct']+'">';
          }
        },
        {
          targets: 2,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mStartDate']==null)	return '';
      			else	return full['mStartDate'];
          }
          
        },
        {
          targets: 3,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mContent']==null)	return '-';
      			else	return '<input type="text" class="form-control" id="mContent'+full['mIdx']+'" value="'+full['mContent']+'">';
          }
          
        },
        {
          targets: 4,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mEdayId']==null)	return '-';
      			else	return '<input type="text" class="form-control" id="mEdayId'+full['mIdx']+'" value="'+full['mEdayId']+'">';
          }
          
        },
        {
          targets: 5,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mType']==null)	return '-';
      			else	return '<input type="text" class="form-control" id="mType'+full['mIdx']+'" value="'+full['mType']+'">';
          }
        },
        {
          targets: 6,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mThum']==null)	return '-';
      			else	return '<img src="https://www.vegemil.co.kr/main/BeanSoup/assets/event/'+full['mThum']+' " width="80" />';
          }
          
        },
        {
          targets: 7,
          orderable: false,
          render: function (data, type, full, meta) {
            let checked = '';
			if(full['mDisplay'] == 1) {
				checked = 'checked';
			}
            return (
              '<div class="form-check form-switch center-ck">'+
                '<input type="checkbox" class="form-check-input" '+checked+' id="mDisplay'+full['mIdx']+ '" name="listOn" onclick="javascript:btnSave('+full['mIdx']+',\'U\')" >'+
				'<label class="form-check-label" for="listOn"></label>'+
		      '</div>'
            );
          }
          
        },
        {
          targets: 8,
          orderable: false,
          render: function (data, type, full, meta) {
            let checked = '';
			if(full['mIng'] == 1) {
				checked = 'checked';
			}
            return (
              '<div class="form-check form-switch center-ck">'+
                '<input type="checkbox" class="form-check-input" '+checked+' id="mIng'+full['mIdx']+ '" name="listOn" onclick="javascript:btnSave('+full['mIdx']+',\'U\')" >'+
				'<label class="form-check-label" for="listOn"></label>'+
		      '</div>'
            );
          }
        },
        {
          targets: 9,
          orderable: false,
          render: function (data, type, full, meta) {
            return (
              '<button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" onclick="btnSave('+full['mIdx']+',\'U\')" />수정</button>'
            );
          }
          
        },
        {
          targets: 10,
          orderable: false,
          render: function (data, type, full, meta) {
            return (
              '<button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" onclick="btnSave('+full['mIdx']+',\'D\')" />삭제</button>'
            );
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
	
    $('div.head-label').html('<h4 class="card-title">간단요리사 목록 </h4> ');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
	
}

/*
function btnDisplay(idx) {
	console.log('btnDisplay', idx)
	let mDisplay;
	if($('#mDisplay'+idx).is(":checked")){
		mDisplay = 1;
	}else{
		mDisplay = 0;
	}
	console.log('============mDisplay',mDisplay)
	
	if(confirm('진열을 수정하시겠습니까?')){
		$.ajax({
			url : '/admin/beanSoup/displayBeanSoupVideo?mIdx='+idx+'&mDisplay='+mDisplay,
			type : "get",
			dataType : "json",
			success : function(data) {
				console.log('data============',data);
				if(data){
					alert("수정되었습니다.");
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
}
*/

function btnSave(idx, action) {
	console.log('action', action);
	console.log('mtitle', $('#mTitle'+idx).val());
	
	const form = $('#form');
	let msg;
	let mDisplay;
	let mIng;
	
	if($('#mDisplay'+idx).is(":checked")){
		mDisplay = 1;
	}else{
		mDisplay = 0;
	}
	
	if($('#mIng'+idx).is(":checked")){
		mIng = 1;
	}else{
		mIng = 0;
	}
	console.log('mDisplay', mDisplay);
	
	if(action == "I") {
		msg = "등록하시겠습니까?";
		$('#mIdx').val("");
		$('#mProduct').val($('#mProduct').val());
		$('#mContent').val($('#mContent').val());
		$('#mEdayId').val($('#mEdayId').val());
		$('#mStartDate').val($('#mStartDate').val());
		$('#mType').val();
		$('#mDisplay').val();
		$('#mIng').val($('#ing').val()); //=="on"?"1":"0"
		$('#action').val(action);
	}else{
		if(action == "U") {
			msg = "수정하시겠습니까?";	
		}else {
			msg = "삭제하시겠습니까?";	
		}
		$('#mIdx').val(idx);
		$('#mProduct').val($('#mProduct'+idx).val());
		$('#mContent').val($('#mContent'+idx).val());
		$('#mEdayId').val($('#mEdayId'+idx).val());
		$('#mType').val($('#mType'+idx).val());
		$('#mDisplay').val(mDisplay);
		$('#mIng').val(mIng);
		$('#action').val(action);
	}
	
	console.log(form.serialize());
	if(confirm(msg)) {
		$.ajax({
	       url: '/admin/manage/beanSoup/saveBeanSoupEvent',
		   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
		   contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
		   data: form.serialize(),
		   dataType : 'json',
		}).done(function(data){
		   console.log('done', data)
		   if(data.result) {
		   	   alert('저장되었습니다.');
		   	   $('.datatables-basic').DataTable().ajax.reload();
		   }else{
		  	   alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
		   }
		 }).fail(function() {
		   	   console.log('fail')
		 })
	}
}

