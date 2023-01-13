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
    
    //선택삭제
    $('#btnDel').click(function(e){
		console.log('e', e)
	    var form = document.form;
	    var table = $('#bestReview').DataTable();
	    console.log('table', table)
	
	      
	    // Output form data to a console
	    console.log("Form submission", decodeURIComponent($(form).serialize())); 
	      
	    if(confirm('삭제하시겠습니까?')){
			$.ajax({
				url : '/admin/manage/baby/deleteBestReview',
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
    const table = $('#bestReview').DataTable();
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
      bPaginate: true,
	  pageLength: 10,
	  serverSide: true,
	  processing: true,
      ajax: {
        url : '/admin/manage/baby/bestReviewList',
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data:function(params){   
//			params.sTh = $('#sIdx').val();
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
      	{ data: 'sIdx' },
      	{ data: 'sIdx' },
      	{ data: 'sUid' },
        { data: 'sEdayId' },
        { data: 'mName' },
        { data: 'sTitle'  },
        { data: 'sUrl' },
        { data: 'sImage' },
        { data: 'sWritedate' },
        { data: 'sRank' },
        { data: 'sDesctime' }
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
				return (
					'<a data-bs-toggle="modal" data-bs-target="#large'+full['sIdx']+'"><button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" \'">상세보기</button></a>'
					+ getModal(full)
      			)
      		}
      	},
      	{
      		targets: 3,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sUid']==null)	return '';
      			else	return full['sUid'];
      			
      		}
      	},
      	{
      		targets: 4,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sEdayId']==null)	return '';
      			else	return full['sEdayId'];
      			
      		}
      	},
      	{
      		targets: 5,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['mName']==null)	return '';
      			else	return full['mName'];
      		}
      	},
      	{
      		targets: 6,
      		orderable: false,
      		render: function (data, type, full, meta) {
            return (
				'<div class="todo-task-list-wrapper"><div class="todo-item textline"><a href="#large" data-bs-toggle="modal" data-bs-target="#large">'+full['sTitle']+'</a></div></div>'
            );
          }
      	},
      	{
      		targets: 7,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sUrl']==null)	return '';
      			else	return '<a href="'+full['sUrl']+'" target="_blank">'+full['sUrl']+'</a>';
      			
      		}
      	},
      	
      	{
			targets: 8,
	        orderable: false,
	        render: function (data, type, full, meta) {
				if(full['sImage']==null)	return '';
	  			else	return '<img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/review_upload/'+full['sImage']+'" height="40" width="40" class="rounded">';
	  		}
        },
        {
      		targets: 9,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sWritedate']==null)	return '';
      			else	return full['sWritedate'] +' '+ full['sWritetime'];
      			
      		}
      	},
      	{
      		targets: 10,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sRank']==null || full['sRank'] == 0)	return '<span class="badge rounded-pill bg-danger">N</span>';
      			else return '<span class="badge rounded-pill bg-success">Y</span>';
      			
      		}
      	},
      	{
      		targets: 11,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sDesctime']==null)	return '-';
      			else	return full['sDesctime'].substr(0, 7);
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
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 10, 11] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 10, 11] }
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 10, 11] }
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 3, 4, 5, 6, 7, 8, 10, 11] }
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
    
    $('div.head-label').html('<h4 class="card-title">등록 후기 목록 <button id="btnDel" class="btn btn-outline-danger text-nowrap px-1 btn-sm" data-repeater-delete type="button"><span>선택삭제</span></button></h4>');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
}

function getModal(obj) {
	console.log('obj---------------',obj)
	let modal = "";
	modal += '<form name="modalForm'+obj.sIdx+'" id="modalForm'+obj.sIdx+'" method="post">'
	modal +=  '<section id="modal-sizes">'
	modal +=     '<div class="modal fade text-start" id="large'+obj.sIdx+'" tabindex="-1" aria-labelledby="myModalLabel17" aria-hidden="true">'
	modal +=		'<div class="modal-dialog modal-dialog-centered modal-lg">'
	modal +=			'<div class="modal-content">'
	modal +=				'<div class="modal-header">'
	modal +=					'<h4 class="modal-title" id="myModalLabel17">등록 후기 상세보기</h4>'
	modal +=					'<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>'
	modal +=				'</div>'
	modal +=				'<table class="table table-bordered  f13 paddingType">'
	modal +=					'<colgroup>'
	modal +=						'<col width="25%">'
	modal +=						'<col width="25%">'
	modal +=						'<col width="25%">'
	modal +=						'<col width="25%">'
	modal +=					'</colgroup>'
	modal +=					'<thead>'
	modal +=						'<tr>'
	modal +=							'<th>ID <span class="text-warning">'+obj.sUid+'</span></th>'
	modal +=							'<th>이데이몰ID <span class="text-warning">'+obj.sEdayId+'</span></th>'
	modal +=							'<th colspan="2">작성자명<span class="text-warning"> '+obj.mName+'</span></th>'
	modal +=						'</tr>'
	modal +=						'<tr>'
	modal +=							'<th>등록일시<span class="text-warning"> '+obj.sWritedate + obj.sWritetime+'</span></th>'
									if(obj.sRank != null) {
	modal +=							'<th>선정여부 <span class="badge rounded-pill bg-success">Y</span></th>'
									}else{
	modal +=							'<th>선정여부 <span class="badge rounded-pill bg-danger">N</span></th>'									
									}
	modal +=							'<th>선정연월 <span class="text-warning">'+obj.sDesctime+'</span></th>'
	modal +=							'<th>선정처리일시 <span class="text-warning">'+obj.sDesctime+'</span></th>'
	modal +=						'</tr>'
	modal +=					'</thead>'
	modal +=				'</table>'
	modal +=				'<div class="modal-body">'
	modal +=					'<p>URL <a href="#"><span class="text-warning underline"> '+obj.sUrl+'</span></a></p>'
	modal +=					'<p class="mt-1">제목 <span class="text-warning">'+obj.sTitle+'</span></p>'
	modal +=					'<div class="row">'
	modal +=						'<div class="col-12">'
	modal +=							'<dl class="photoUL">'
										if(obj.sImage != null) {
	modal +=								'<dd>'
	modal +=									'<div class="form-check">'
	modal +=										'<input type="radio" id="#" name="photoSelection" class="form-check-input" checked />'
	modal +=										'<label class="form-check-label" for="#">대표이미지로 선택</label>'
	modal +=										'<div class="mt-1 photoBox1">'
	modal +=											'<button type="button"  class="rotateL btn btn-outline-primary btn-sm" >좌</button>'
	modal +=											'<button type="button"  class="rotateR btn btn-outline-primary btn-sm" >우</button>'
	modal +=											'<p class="mt-1"><img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/review_upload/'+obj.sImage+'" class="rounded"></p>'
	modal +=										'</div>'
	modal +=									'</div>'
	modal +=								'</dd>'
										}
	modal +=							'</dl>'
	modal +=						'</div>'
	modal +=					'</div>'
	modal +=					'<h4 class="mt-2">베스트 후기 선정</h4>'
	modal +=					'<table class="table table-bordered  f13 paddingType mt-1">'
	modal +=						'<thead>'
	modal +=							'<tr>'
	modal +=								'<th>'
	modal +=									'<div class="row">'
	modal +=										'<div class="col-1"><p>선정등수</p></div>'
	modal +=										'<div class="col-4">'
	modal +=											'<dl class="in_dl">'
	modal +=												'<dd>'
	modal +=													'<div class="form-check">'
	modal +=                                                        '<input type="hidden" name="sIdx" value="'+obj.sIdx+'">'
	modal +=														'<input type="radio" id="#" name="sRank" class="form-check-input" value="1" '
																if(obj.sRank == 1) {
    modal +=														'checked />'
																}else {
	modal +=														' />'
																}
	modal +=														'<label class="form-check-label" for="#">1등</label>'
	modal +=													'</div>'
	modal +=												'</dd>'
	modal +=												'<dd>'
	modal +=													'<div class="form-check">'
	modal +=														'<input type="radio" id="#" name="sRank" class="form-check-input" value="2" '
																if(obj.sRank == 2) {
    modal +=														'checked />'
																}else {
	modal +=														' />'
																}
	modal +=														'<label class="form-check-label" for="#">2등</label>'
	modal +=													'</div>'
	modal +=												'</dd>'
	modal +=											'</dl>'
	modal +=										'</div>'
	modal +=									'</div>'
	modal +=								'</th>'
	modal +=							'</tr>'
	modal +=						'</thead>'
	modal +=					'</table>'
	modal +=				'</div>'
	modal +=				'<div class="modal-footer">'
	modal +=					'<button type="button" onclick="btnSave('+obj.sIdx+');" class="btn btn-primary" ">저장</button>'
	modal +=				'</div>'
	modal +=			'</div>'
	modal +=		'</div>'
	modal +=	'</div>'
	modal += '</section>'
	modal +='</form>'
	
	return modal;
}

function btnSave(idx) {
	console.log('data', idx)
	
	const form = $('#modalForm'+idx).serializeArray();
	//const formData = new FormData(form);
	let sRank = false;
	for(var i=0; i<form.length; i++) {
		console.log('name----------',form[i].name)
		if(form[i].name == "sRank") {
			sRank = true;
		}
	}
	
	if(!sRank) {
		alert("선정등수를 선택해주세요.")
		return false;
	}
	    	   
	    	   
    $.ajax({
       url: '/admin/manage/baby/updateBestReview',
	   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
	   contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
	   data: $('#modalForm'+idx).serialize()
	}).done(function(data){
	   console.log('done', data)
	   if(data.result) {
	   	   alert('저장되었습니다.');
	   	   //$('.datatables-basic').DataTable().ajax.reload();
	   }else{
	  	   alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
	   }
	 }).fail(function() {
	   	   console.log('fail')
	 })
}
