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

function deleteData(){
	$('#btnDel').click(function(e){

	    var form = document.form;
	      
	    // Output form data to a console

	      
	    if(confirm('삭제하시겠습니까?')){
			$.ajax({
				url : '/admin/manage/sabo/deleteSaboSubscribe',
				type : "post",
				data : $(form).serialize(),
				dataType : "json",
				success : function(data) {
		
					if(data){
						alert("삭제되었습니다.");
						//window.location.reload();
						$('.datatables-basic').DataTable().ajax.reload();
						return;
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

// 날짜 범위를 선택했다면
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
/*var filterByDate = function (column, startDate, endDate) {
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
};*/

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

	deleteData();
	
	
});


var createTable = function() {

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
      bPaginate: true,
      pageLength: 10,
	  serverSide: true,
	  processing: true,
      ajax: {
        url : '/admin/manage/sabo/subscribeList',
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data:function(params){   
			var json = $("#frm").serializeObject();
			console.log('json>>', json);			
			$.each(json,function(e){
				params[e] = json[e];
				console.log(e);
			});
			console.log('json', json);
			console.log('params', params);
		},
		error : function(xhr, ajaxSettings, thrownError) { 
			console.log('error');
		}

	  },
      columns: [
      	{ data: 'sIdx' },
      	{ data: 'sIdx' },
        { data: 'sWritedate' },
        { data: 'sName' },
    	{ data: 'sHp' },
        { data: 'sEmail' },
        { data: 'sMemo' }             
      ],
      columnDefs: [
	    {
          // For Responsive
          orderable: false,
          targets: 0
        },
		{
      		targets: 1,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			console.log('data',data);
      			console.log('full',full);
	            return (
	              '<div class="form-check">' +
		              '<input type="checkbox" class="form-check-input" id="customCheck2" name="checkList" value="'+full['sIdx']+'" />' +
		              '<label class="form-check-label" for="customCheck2"></label>' +
	              '</div>'
	            );
	        }
      	}
		,
      	{
      		targets: 2,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sWritedate']==null)	return '';
      			else	return full['sWritedate'];
      			
      		}
      	},    	
      	{
      		targets: 3,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sName']==null)	return '';
      			else	return full['sName'];
      			
      		}
      	},
      	{
      		targets: 4,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sHp']==null)	return '';
      			else	return full['sHp'];
      			
      		}
      	},
      	
      	{
      		targets: 5,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sEmail']==null)	return '';
      			else	return full['sEmail'];
      			
      		}
      	},      	  	
      	{
      		targets: 6,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['sMemo']==null)	return '';
      			else	return full['sMemo'];
      			
      		}
      	},
      	
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
              exportOptions: { columns: [0, 2, 3, 4, 5, 6] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6] }
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6] }
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6] }
            },
            {
              extend: 'copy',
              text: feather.icons['copy'].toSvg({ class: 'font-small-4 mr-50' }) + 'Copy',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 2, 3, 4, 5, 6] }
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
    
    $('div.head-label').html('<h4 class="mb-0">사보 신청자 목록 <button type="button" id="btnDel" class="btn btn-outline-danger btn-sm me-1">선택삭제</button></h4>');
    deleteData();
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
    });
  }
}

function btnSave(idx) {
	console.log('data', idx)
	
	const form = $('#modalForm'+idx).serializeArray();
	//const formData = new FormData(form);
	let cRank = false;
	for(var i=0; i<form.length; i++) {
		console.log('name----------',form[i].name)
		if(form[i].name == "cRank") {
			cRank = true;
		}
	}
	
	if(!cRank) {
		alert("선정등수를 선택해주세요.")
		return false;
	}
	    	   
	    	   
    $.ajax({
       url: '/admin/manage/baby/updateCalendarModel',
	   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
	   contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
	   data: $('#modalForm'+idx).serialize()
	}).done(function(data){
	   console.log('done', data)
	   if(data.result) {
	   	   alert('저장되었습니다.');
	   }else{
	  	   alert('저장에 실패하였습니다.\n잠시 후 다시 시도해주세요.');
	   }
	 }).fail(function() {
	   	   console.log('fail')
	 })
}

function getRankYn(val) {
	if(val != null) {
		return '<span class="badge rounded-pill bg-success">Y</span>';
	}else{
		return '<span class="badge rounded-pill bg-danger">N</span>';
	}
}

function getRank(val, rank) {
	if(val == rank) {
		return 'checked';
	}else{
		return '';
	}
}


function getModal(obj) {
	console.log('obj----------',obj);
	let modal = "";	
	modal +=	'<form name="modalForm'+obj.cIdx+'" id="modalForm'+obj.cIdx+'" method="post">'
    modal +=		'<section id="modal-sizes">'
    modal +=         	'<div class="modal fade text-start" id="large'+obj.cIdx+'" tabindex="-1" aria-labelledby="myModalLabel17" aria-hidden="true">'
	modal +=				'<div class="modal-dialog modal-dialog-centered modal-lg">'
	modal +=					'<div class="modal-content">'
	modal +=						'<div class="modal-header">'
	modal +=							'<h4 class="modal-title" id="myModalLabel17">신청 내용 상세보기</h4>'
	modal +=							'<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>'
	modal +=						'</div>'
	modal +=						'<table class="table table-bordered  f13 paddingType">'
	modal +=							'<colgroup>'
	modal +=								'<col width="25%">'
	modal +=								'<col width="25%">'
	modal +=								'<col width="25%">'
	modal +=								'<col width="25%">'
	modal +=							'</colgroup>'
	modal +=							'<thead>'
	modal +=								'<tr>'
	modal +=									'<th>ID <span class="text-warning">'+obj.mId+'</span></th>'
	modal +=									'<th>이데이몰ID <span class="text-warning"> {이데이몰ID}</span></th>'
	modal +=									'<th colspan="2">개월수 <span class="text-warning">'+obj.cAlived+'</span></th>'
	modal +=								'</tr>'
	modal +=								'<tr>'
	modal +=									'<th>휴대전화 <span class="text-warning">'+obj.cHp+'</span></th>'
	modal +=									'<th colspan="3">주소 <span class="text-warning">'+obj.cAddr+'</span></th>'
	modal +=								'</tr>'
	modal +=								'<tr>'
	modal +=									'<th>E-mail <span class="text-warning">'+obj.cEmail+'</span></th>'
	modal +=									'<th colspan="3">유입경로 <span class="text-warning">'+obj.cRoute+'</span></th>'
	modal +=								'</tr>'
	modal +=								'<tr>'
	modal +=									'<th>등록일시 <span class="text-warning">'+obj.cWriteDate+'</span></th>'
											if(obj.cRank != null) {
	modal +=									'<th>선정여부 <span class="badge rounded-pill bg-success">Y</span></th>'
											}else{
	modal +=									'<th>선정여부 <span class="badge rounded-pill bg-danger">N</span></th>'											
											}
	modal +=									'<th>선정연월 <span class="text-warning">{연월}</span></th>'
	modal +=									'<th>선정처리일시 <span class="text-warning">'+obj.cUpdatetime+'</span></th>'
	modal +=								'</tr>'
	modal +=							'</thead>'
	modal +=						'</table>'
	modal +=						'<div class="modal-body">'
	modal +=							'<div class="row">'
	modal +=								'<div class="col-12">'
	modal +=									'<dl class="photoUL">'
												if(obj.cImage != null) {
	modal +=										'<dd>'
	modal +=											'<div class="form-check">'
	modal +=												'<input type="radio" id="photoSelection" name="photoSelection" class="form-check-input" checked />'
	modal +=												'<label class="form-check-label" for="#">대표이미지로 선택</label>'
	modal +=												'<div class="mt-1 photoBox1">'
	modal +=													'<button type="button"  class="rotateL btn btn-outline-primary btn-sm" >좌</button>'
	modal +=													'<button type="button"  class="rotateR btn btn-outline-primary btn-sm" >우</button>'
	modal +=													'<p class="mt-1"><img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/model_upload/'+obj.cImage+'" /></p>'
	modal +=												'</div>'
	modal +=											'</div>'
	modal +=										'</dd>'
												}
												if(obj.cImage2 != null) {
	modal +=										'<dd>'
	modal +=											' <div class="form-check">'
	modal +=												'<input type="radio" id="photoSelection" name="photoSelection" class="form-check-input" />'
	modal +=												'<label class="form-check-label" for="#">대표이미지로 선택</label>'
	modal +=												'<div class="mt-1 photoBox2">'
	modal +=													'<button type="button"  class="rotateL btn btn-outline-primary btn-sm" >좌</button>'
	modal +=													'<button type="button"  class="rotateR btn btn-outline-primary btn-sm" >우</button>'
	modal +=													'<p class="mt-1"><img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/model_upload/'+obj.cImage2+'" /></p>'
	modal +=												'</div>'
	modal +=											'</div>'
	modal +=										'</dd>'
												}					
	modal +=									'</dl>'
	modal +=								'</div>'
	modal +=							'</div>'
	modal +=							'<h4 class="mt-2">달력모델 선정</h4>'
	modal +=							'<table class="table table-bordered  f13 paddingType mt-1">'
	modal +=								'<thead>'
	modal +=									'<tr>'
	modal +=										'<th>'
	modal +=											'<div class="row">'
	modal +=												'<div class="col-1"><p>선정등수</p></div>'
	modal +=													'<div class="col-4">'
	modal +=														'<dl class="in_dl">'
	modal +=															'<dd>'
	modal +=																'<div class="form-check">'
	modal +=																	'<input type="radio" name="cRank" class="form-check-input" value="1" '
																			if(obj.cRank == 1) {
	modal +=																	'checked />'
																			}else {
	modal +=																	' />'
																			}
	modal +=																	'<label class="form-check-label" name="cRank" for="#">Best</label>'
	modal +=																'</div>'
	modal +=															'</dd>'
	modal +=															'<dd>'
	modal +=																'<div class="form-check">'
	modal +=																	'<input type="hidden" name="cIdx" value="'+obj.cIdx+'">'
	modal +=																	'<input type="radio" id="#" name="cRank" class="form-check-input" value="2" '
																			if(obj.cRank == 2) {
	modal +=																	'checked />'
																			}else {
	modal +=																	' />'
																			}
	modal +=																	'<label class="form-check-label" name="cRank" for="#">참가상</label>'
	modal +=																'</div>'
	modal +=															'</dd>'
	modal +=														'</dl>'
	modal +=													'</div>'
	modal +=												'</div>'
	modal +=											'</div>'
	modal +=										'</th>'
	modal +=									'</tr>'
	modal +=								'</thead>'
	modal +=							'</table>'
	modal +=						'</div>'
	modal +=						'<div class="modal-footer">'
	modal +=							'<button type="button" onclick="btnSave('+obj.cIdx+');" class="btn btn-primary" >저장</button>'
	modal +=						'</div>'
	modal +=					'</div>'
	modal +=				'</div>'
	modal +=			'</div>'
    modal += 		'</section>'
    modal +=	'</form>'
    
    return modal;
}