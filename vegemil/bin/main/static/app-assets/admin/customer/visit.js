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
        url : '/admin/manage/customer/visitList',
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
      	{ data: 'vIdx' },
      	{ data: 'vIdx' },
      	{ data: 'vIdx' },
      	{ data: 'vWritedate' },
      	{ data: 'vName' },
        { data: 'vPcount' },
        { data: 'vOrg' },
        { data: 'vHp' },
        { data: 'vMemo' },
        { data: 'vAdminmemo'  },
        { data: 'vApptime' },
        { data: 'vAppdate' },
        { data: 'vConfdate' },
        { data: 'vConfstat' }
        
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
	              '<div class="form-check center-ck"> <input class="form-check-input dt-checkboxes" type="checkbox" name="checkList" value="'+data+'" id="checkbox' +
	              data +
	              '" /><label class="form-check-label" for="checkbox' +
	              data +
	              '"></label></div>'
	            );
	        },
	        checkboxes: {
	          selectAllRender:
	            '<div class="form-check"> <input class="form-check-input" type="checkbox" value="" id="checkboxSelectAll" /><label class="form-check-label" for="checkboxSelectAll"></label></div>'
	        }
      	},
      	{
      		targets: 2,
      		orderable: false,
      		render: function (data, type, full, meta) {
				return (
					'<a data-bs-toggle="modal" data-bs-target="#large'+full['vIdx']+'"><button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" \'">상세</button></a>'
					+getModal(full)
      			)
      		}
      	},
      	{
      		targets: 3,
      		orderable: false,
      		render: function (data, type, full, meta) {
				if(data==null)	return '-';
      			else	return data.substr(0, 10);
							
							
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
      			if(data==null)	return '';
      			else	return data;
      		}
      	},
      	{
      		targets: 9,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(data==null)	return '';
      			else	return data;
      			
      		}
      	},
      	{
      		targets: 10,
      		orderable: false,
      		render: function (data, type, full, meta) {
				if(data==null)	return '-';
				else if(data==1) return '오전';
				else if(data==2) return '오후';
      			
      		}
      	},
      	{
      		targets: 11,
      		orderable: false,
      		render: function (data, type, full, meta) {
				if(data==null)	return '-';
      			else	return data.substr(5, 5);
      			
      		}
      	},
      	{
      		targets: 12,
      		orderable: false,
      		render: function (data, type, full, meta) {
				if(data==null || data == "0000-00-00 00:00:00")	return '-';
      			else	return data.substr(5, 5);
      			
      		}
      	},
      	{
      		targets: 13,
      		orderable: false,
      		render: function (data, type, full, meta) {
				if(data==null)	return '-';
				else if(data==0) return '취소';
				else if(data==1) return '신청'
				else if(data==2) return '확정'
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
    
    $('div.head-label').html('<h4 class="card-title">신청목록 <button id="btnDel" class="btn btn-outline-danger text-nowrap px-1 btn-sm" data-repeater-delete type="button"><span>선택삭제</span></button></h4>');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
}

function getModal(obj) {
	let vConfdate = "";
	if(obj.vConfdate != null & obj.vConfdate != "0000-00-00 00:00:00") {
		vConfdate = obj.vConfdate.substr(0, 10); 
	}
	let modal = "";
	modal += '<form name="modalForm'+obj.vIdx+'" id="modalForm'+obj.vIdx+'" method="post">'
	modal +=  '<section id="modal-sizes">'
	modal +=     '<div class="modal fade text-start" id="large'+obj.vIdx+'" tabindex="-1" aria-labelledby="myModalLabel17" aria-hidden="true">'
	modal +=		'<div class="modal-dialog modal-dialog-centered modal-lg">'
	modal +=			'<div class="modal-content">'
	modal +=				'<div class="modal-header">'
	modal +=					'<h4 class="modal-title" id="myModalLabel17">견학신청</h4>'
	modal +=					'<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>'
	modal +=				'</div>'
	modal +=				'<table class="table table-bordered  f13 paddingType">'
	modal +=					'<colgroup>'
	modal +=						'<col width="22%">'
	modal +=						'<col width="22%">'
	modal +=						'<col width="22%">'
	modal +=						'<col width="34%">'
	modal +=					'</colgroup>'
	modal +=					'<thead>'
	modal +=						'<tr>'
	modal +=							'<th>신청자명 <span class="text-warning"> '+obj.vName+'</span></th>'
	modal +=							'<th>신청인원 <span class="text-warning"> '+obj.vPcount+'</span></th>'
	modal +=							'<th colspan="2">단체명<span class="text-warning"> '+obj.vOrg+'</span></th>'
	modal +=						'</tr>'
	modal +=						'<tr>'
	modal +=							'<th>H.P<span class="text-warning"> '+obj.vHp+'</span></th>'
	modal +=							'<th>TEL<span class="text-warning"> '+obj.vTel+'</span></th>'
	modal +=							'<th>E-mail<span class="text-warning"> '+obj.vEmail+'</span></th>'									
	modal +=							'<th>주소<span class="text-warning"> '+obj.vAddr+'</span></th>'
	modal +=						'</tr>'
	modal +=						'<tr>'
	modal +=							'<th>등록일자<span class="text-warning"> '+obj.vWritedate+'</span></th>'
	modal +=							'<th>지역<span class="text-warning"> '+obj.vArea+'</span></th>'
	modal +=							'<th colspan="2">'
	modal +=								'<div class="form-check form-check-inline">'									
	modal +=									'<input class="form-check-input" type="radio" name="vApptime" id="#" value="1" '
																if(obj.vApptime == 1) {
    modal +=														'checked />'
																}else {
	modal +=														' />'
																}
	modal +=									'<label class="form-check-label" for="#">오전</label>'
	modal +=								'</div>'
	modal +=								'<div class="form-check form-check-inline">'
	modal +=									'<input class="form-check-input" type="radio" name="vApptime" id="#" value="2" '
																if(obj.vApptime == 2) {
    modal +=														'checked />'
																}else {
	modal +=														' />'
																}
	modal +=									'<label class="form-check-label" for="#">오후</label>'							
	modal +=								'</div>'
	modal +=							'</th>'																
	modal +=						'</tr>'
	modal +=					'</thead>'
	modal +=				'</table>'
	modal +=				'<div class="modal-body">'
	modal +=					'<h4>고객메모</h4>'
	modal +=					'<div class="row">'
	modal +=						'<div class="col-12">'
	modal +=							'<p class="txt-base">'+obj.vMemo+'</p>'
	modal +=						'</div>'
	modal +=					'</div>'
	modal +=					'<h4 class="mt-50">처리내용</h4>'
	modal +=					'<div class="row mb-1 mt-1">'
	modal +=						'<div class="col-1"><p>신청처리</p></div>'
	modal +=						'<div class="col-5">'
	modal +=							'<select class="form-select" name="vConfstat" id="vConfstat'+obj.vIdx+'">'
	modal +=								'<option value="1"'
												if(obj.vConfstat == 1) {  
    modal += 												'selected '
														}
	modal +=                                            '>신청</option>'
	modal +=								'<option value="2"'
														if(obj.vConfstat == 2) {
    modal += 												'selected '
														}
	modal +=                                            '>확정</option>'
	modal +=								'<option value="0"'
														if(obj.vConfstat == 0) {
	modal +=                                                'selected '	
														}														
	modal +=                                            '>취소</option>'
	modal +=							'</select>'
	modal +=						'</div>'
	modal +=					'</div>'
	modal +=					'<div class="row">'
	modal +=						'<div class="col-1"><p>확정일자</p></div>'
	modal +=						'<div class="col-3">'
	modal +=                            '<input type="hidden" name="vIdx" value="'+obj.vIdx+'">'
	modal +=                            '<input type="date" id="vConfdate'+obj.vIdx+'" class="form-control flatpickr" name="vConfdate" placeholder="YYYY-MM-DD" maxlength="10" '  
														if(vConfdate != "") {
    modal +=                                             	'value="'+obj.vConfdate.split(" ",1)+'"/>'
														}else{
	modal +=												' />'													
														}
	modal +=						'</div>'
	modal +=						'<div class="col-2">'
	modal +=							'<div class="form-check form-check-inline">'
	modal +=								'<input class="form-check-input" type="radio" name="vConftime" id="inlineRadio1" value="1"'
												if(obj.vConftime == 1) {
    modal +=                                        ' checked />'													
												}else{
	modal +=                                          '/>'
												}
	modal +=								'<label class="form-check-label" for="inlineRadio1">오전</label>'
	modal +=							'</div>'
	modal +=							'<div class="form-check form-check-inline">'
	modal +=								'<input class="form-check-input" type="radio" name="vConftime" id="inlineRadio2" value="2"' 
												if(obj.vConftime == 2) {
    modal +=                                        ' checked />'													
												}else{
	modal +=                                          '/>'
												}
	modal +=								'<label class="form-check-label" for="inlineRadio2">오후</label>'
	modal +=							'</div>'
	modal +=						'</div>'
	modal +=						'<div class="col-1"><p>신청일자</p></div>'
	modal +=						'<div class="col-3">'
	modal +=                            '<input type="hidden" name="vIdx" value="'+obj.vIdx+'">'
	modal +=                            '<input type="date" id="vAppdate'+obj.vIdx+'" class="form-control flatpickr" name="vAppdate" placeholder="YYYY-MM-DD" maxlength="10" '  
														if(obj.vAppdate != "" || obj.vAppdate != null) {
    modal +=                                             	'value="'+obj.vAppdate.split(" ",1)+'"/>'
														}else{
	modal +=												' />'													
														}
	modal +=						'</div>'
	modal +=						'<div class="col-2">'
	modal +=							'<div class="form-check form-check-inline">'
	modal +=								'<input class="form-check-input" type="radio" name="vApptime" id="inlineRadio1" value="1"'
												if(obj.vApptime == 1) {
    modal +=                                        ' checked />'													
												}else{
	modal +=                                          '/>'
												}
	modal +=								'<label class="form-check-label" for="inlineRadio1">오전</label>'
	modal +=							'</div>'
	modal +=							'<div class="form-check form-check-inline">'
	modal +=								'<input class="form-check-input" type="radio" name="vApptime" id="inlineRadio2" value="2"' 
												if(obj.vApptime == 2) {
    modal +=                                        ' checked />'													
												}else{
	modal +=                                          '/>'
												}
	modal +=								'<label class="form-check-label" for="inlineRadio2">오후</label>'
	modal +=							'</div>'
	modal +=						'</div>'
	modal +=					'</div>'
	modal +=					'<hr>'
	modal +=					'<h4 class="mt-2">답변등록</h4>'
	modal +=					'<textarea name="vAdminmemo" id="vAdminmemo'+obj.vIdx+'" class="form-control" >'
							     if(typeof obj.vAdminmemo != "undefined") 
    modal +=  					     obj.vAdminmemo
	modal +=                     '</textarea>'
	modal +=				'</div>'
	modal +=				'<div class="modal-footer">'
	modal +=					'<button type="button" onclick="btnSave('+obj.vIdx+');" class="btn btn-primary" data-bs-dismiss="modal">저장</button>'
	modal +=				'</div>'
	modal +=			'</div>'
	modal +=		'</div>'
	modal +=	'</div>'
	modal +=  '</section>'
	modal += '</form>'
	
	return modal;
}

function btnSave(idx) {
	const form = $('#modalForm'+idx).serializeArray();
	let check = false;
	for(var i=0; i<form.length; i++) {
		if(form[i].value.length < 1) {
			if(form[i].name == "vConfdate" || form[i].name == "vAdminmemo"){
				continue;
			}
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

function btnDisplay(idx) {
	let vDisplay;
	if($('#vDisplay'+idx).is(":checked")){
		vDisplay = 1;
	}else{
		vDisplay = 0;
	}
	
	$.ajax({
		url : '/admin/manage/customer/displayVisit?vIdx='+idx+'&vDisplay='+vDisplay,
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

function btnSetupSave() {
	const form = $('#frm').serialize()
	console.log('form', decodeURI(form))
	let sMonth1Arr = [];
	let sMonth2Arr = [];
	
	$('input[name=sMonth1]:checked').each(function(){
		sMonth1Arr.push($(this).val())
	});
	$('input[name=sMonth2]:checked').each(function(){
		sMonth2Arr.push($(this).val())
	});
	
    $.ajax({
       url: '/admin/manage/customer/saveVisitSetup',
	   processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
	   contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
	   data: form
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