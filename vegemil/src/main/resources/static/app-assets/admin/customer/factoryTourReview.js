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
	
	$(".flatpickr").flatpickr({});
	
	//선택삭제
    $('#btnDel').click(function(e){
		console.log('e', e)
	    var form = document.form;
	      
	    // Output form data to a console
	    console.log("Form submission", decodeURIComponent($(form).serialize())); 
	      
	    if(confirm('삭제하시겠습니까?')){
			$.ajax({
				url : '/admin/manage/customer/deleteVisit',
				type : "post",
				data : $(form).serialize(),
				dataType : "json",
				success : function(data) {
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

  // DataTable with buttons
  // --------------------------------------------------------------------

  if (dt_basic_table.length) {
	const range = $('#fp-range').val().split(' to ');
	const startdate = range[0];
	const enddate = range[1];
	$('#sStartdate').attr("value",startdate);
	$('#sEnddate').attr("value",enddate);
    var dt_basic = dt_basic_table.DataTable({
	  destroy: true,
	  lengthChange: false,
      bPaginate: true,
	  pageLength: 10,
	  serverSide: true,
	  processing: true,
      ajax: {
        url : '/admin/manage/customer/factoryTourReviewList',
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
      	{ data: 'sIdx' },
      	{ data: 'sWritedate' },
      	{ data: 'sBest' },
      	{ data: 'sSubject' },
      	{ data: 'sName' },
        { data: 'sFile' },
        { data: 'sId' },
        { data: 'sHp' },
        { data: 'sHit' },
        { data: 'sBest'  },
        { data: 'sIdx' }
        
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
      			else	return data.substr(0, 10);;
      		}
      	},
      	{
      		targets: 2,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(data==null)	return '-';
      			else if(data == 0) return '일반후기';
      			else	return '베스트후기';
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
				if($.trim(data).length==0)	return '-';
      			else	return '<img src="/web/upload/'+data+'height="40" width="40" class="rounded">';
      		}
      	},
      	{
      		targets: 6,
      		orderable: false,
      		render: function (data, type, full, meta) {
				if(data==null)	return '-';
      			else	return data;
      		}
      	},
      	{
      		targets: 7,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(data==null)	return '-';
      			else	return data;
      		}
      	},
      	{
      		targets: 8,
      		orderable: false,
      		render: function (data, type, full, meta) {
	            if(data==null)	return '-';
      			else	return data;
          }
      	},
      	{
      		targets: 9,
      		orderable: false,
      		render: function (data, type, full, meta) {
				let checked;
				return (
				'<div class="form-check form-check-inline">'+
				'<input class="form-check-input" type="radio" name="sBest'+full['sIdx']+'" id="inlineRadio1" value="0" '+getCheck(0, full['sBest'])+'/>'+													
				'<label class="form-check-label" for="inlineRadio1">일반</label>'+
				'</div>'+
				'<div class="form-check form-check-inline">'+
				'<input class="form-check-input" type="radio" name="sBest'+full['sIdx']+'" id="inlineRadio1" value="1" '+getCheck(1, full['sBest'])+'/>'+													
				'<label class="form-check-label" for="inlineRadio1">베스트</label>'+
				'</div>'
				)
      			
      		}
      	},
      	{
      		targets: 10,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			return '<button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" onclick="btnSave('+full['mIdx']+',\'D\')">삭제</button>'
      			
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
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13] }
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13] }
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13] }
            },
            {
              extend: 'copy',
              text: feather.icons['copy'].toSvg({ class: 'font-small-4 mr-50' }) + 'Copy',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13] }
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
    
    $('div.head-label').html('<h4 class="card-title">후기목록</h4>');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
}


function btnSave(idx) {
	const form = $('#modalForm'+idx).serializeArray();
	let check = false;
	for(var i=0; i<form.length; i++) {
		if(form[i].value.length < 1) {
			alert('처리내용을 입력해주세요');
			return false;
		}
	}
	
    $.ajax({
       url: '/admin/manage/customer/saveVisit',
	   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
	   contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
	   data: $('#modalForm'+idx).serialize()
	}).done(function(data){
	   if(data.result) {
	   	   alert('저장되었습니다.');
	   	   $('.datatables-basic').DataTable().ajax.reload();
	   }else{
	  	   alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
	   }
	 }).fail(function() {
			alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
	 })
}

function getCheck(val1, val2) {
	console.log('val1', val1)
	console.log('val2', val2)
	if(val1 == val2) return 'checked';
}

function btnSave(idx, action) {
	let msg;
	
	if(action == "I") {
		msg = "등록하시겠습니까?";
	}else{
		if(action == "U") {
			msg = "수정하시겠습니까?";	
		}else {
			msg = "삭제하시겠습니까?";	
		}
	}
	
	if(confirm("삭제하시겠습니까?")) {
		$.ajax({
	       url: '/admin/manage/customer/deleteFactoryTourReview?sIdx='+idx,
		   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
		   contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
		   dataType : 'json',
		}).done(function(data){
		   console.log('done', data)
		   if(data.result) {
		   	   alert('삭제되었습니다.');
		   	   $('.datatables-basic').DataTable().ajax.reload();
		   }else{
		  	   alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
		   }
		 }).fail(function() {
		   	   console.log('fail')
		 })
	}
}