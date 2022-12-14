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
	
	// ?????? ?????? ?????? ??????
	$("[type=checkbox][name=allCheck]").on("change", function(){ //0
        var check = $(this).prop("checked"); //1
        //?????? ??????
        if($(this).hasClass("form-check-input")){ //2
            $("[type=checkbox][name=checkList]").prop("checked", check);
     	}
    });

	$('#btnDel').click(function(e){
		console.log('e', e)
	    var form = document.form;
	      
	    // Output form data to a console
	    console.log("Form submission", decodeURIComponent($(form).serialize())); 
	      
	    if(confirm('?????????????????????????')){
			$.ajax({
				url : '/admin/manage/baby/deleteCalendarModel',
				type : "post",
				data : $(form).serialize(),
				dataType : "json",
				success : function(data) {
					console.log('data============',data);
					if(data){
						alert("?????????????????????.");
						//window.location.reload();
						$('.datatables-basic').DataTable().ajax.reload();
					}
					else{
						alert("??????????????????.");
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
      bPaginate: true,
	  pageLength: 10,
	  serverSide: true,
	  processing: true,
      ajax: {
        url : '/admin/manage/baby/calendarModelList',
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
      	{ data: 'cIdx' },
      	{ data: 'cIdx' },
      	{ data: 'cName' },
        { data: 'cBabyName' },
        { data: 'cAlived' },
        { data: 'cHp' },
        { data: 'cAddr' },
        { data: 'cEmail' },
        { data: 'cRoute' },
        { data: 'cImage' },
        { data: 'cWriteDate' },
        { data: 'cRank' },
        { data: 'cUpdatetime' }
      ],
      columnDefs: [
		{
      		targets: 1,
      		orderable: false,
      		render: function (data, type, full, meta) {
	            return (
	              '<div class="form-check">' +
		              '<input type="checkbox" class="form-check-input" id="customCheck2" name="checkList" value="'+full['cIdx']+'" />' +
		              '<label class="form-check-label" for="customCheck2"></label>' +
	              '</div>'
	            );
	        }
      	},
      	{
      		targets: 2,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cName']==null)	return '';
      			else	return full['cName'];
      			
      		}
      	},
      	{
      		targets: 3,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			return (
              	 '<a data-bs-toggle="modal" data-bs-target="#large'+full['cIdx']+'"><button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" \'">????????????</button></a>'+
              	 getModal(full)
          	    );
      			
      		}
      	},
      	{
      		targets: 4,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cBabyName']==null)	return '';
      			else	return full['cBabyName'];
      			
      		}
      	},
      	{
      		targets: 5,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cAlived']==null)	return '';
      			else	return full['cAlived'];
      			
      		}
      	},
      	{
      		targets: 6,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cHp']==null)	return '';
      			else	return full['cHp'];
      			
      		}
      	},
      	{
      		targets: 7,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cAddr']==null)	return '';
      			else	return full['cAddr'];
      			
      		}
      	},
      	{
      		targets: 8,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cEmail']==null)	return '';
      			else	return full['cEmail'];
      			
      		}
      	},
      	{
      		targets: 9,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cRoute']==null)	return '';
      			else	return full['cRoute'];
      			
      		}
      	},
      	{
          targets: 10,
          orderable: false,
          className: 'my_modal',
          render: function (data, type, full, meta) {
			  if(full['cImage']==null)	return '';
      		  else	return '<img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/model_upload/'+full['cImage']+'" height="40" width="40" class="rounded">';
          }
        },
        {
      		targets: 11,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			var rank;
      			
      			if(full['cWriteDate']==null)	return '';
      		    else	return full['cWriteDate'];
      		}
      	},
      	{
      		targets: 12,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cRank']==null || full['cRank'] == 0)	return '<span class="badge rounded-pill bg-danger">N</span>';
      			else return '<span class="badge rounded-pill bg-success">Y</span>';
      			
      		}
      	},
      	{
      		targets: 13,
      		orderable: false,
      		render: function (data, type, full, meta) {
      			if(full['cUpdatetime']==null)	return '';
      			else	return full['cUpdatetime'].substring(2,4)+'.'+full['cUpdatetime'].substring(5,7);
      			
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
              exportOptions: { columns: [1, 2, 3, 4, 5, 6, 7, 8, 10] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [1, 2, 3, 4, 5, 6, 7, 8, 10] }
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [1, 2, 3, 4, 5, 6, 7, 8, 10] }
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [1, 2, 3, 4, 5, 6, 7, 8, 10] }
            },
            {
              extend: 'copy',
              text: feather.icons['copy'].toSvg({ class: 'font-small-4 mr-50' }) + 'Copy',
              className: 'dropdown-item',
              exportOptions: { columns: [1, 2, 3, 4, 5, 6, 7, 8, 10] }
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
		search : '??????',
	    emptyTable:     "????????? ????????? ????????? ???????????? ????????????.",
	    zeroRecords:??"?????? ????????? ?????? ?????? ????????? ????????????.",
	    lengthMenu: "&nbsp;&nbsp;???????????? _MENU_ ?????? ??????",
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
    
    $('div.head-label').html('<h6 class="mb-0">?????? ?????? ?????? ?????? <button type="button" id="btnDel" class="btn btn-outline-danger btn-sm me-1">????????????</button></h6>');
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
		alert("??????????????? ??????????????????.")
		return false;
	}
	    	   
	    	   
    $.ajax({
       url: '/admin/manage/baby/updateCalendarModel',
	   processData: false,  // ????????? ????????? ???????????? ???????????? ?????? ?????????. true??? ????????????...
	   contentType: false,  // ?????? ????????? true??? ?????? ?????? text??? ???????????? ??????.
	   data: $('#modalForm'+idx).serialize()
	}).done(function(data){
	   console.log('done', data)
	   if(data.result) {
	   	   alert('?????????????????????.');
	   }else{
	  	   alert('????????? ?????????????????????.\n?????? ??? ?????? ??????????????????.');
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
	modal +=							'<h4 class="modal-title" id="myModalLabel17">?????? ?????? ????????????</h4>'
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
	modal +=									'<th>????????????ID <span class="text-warning"> {????????????ID}</span></th>'
	modal +=									'<th colspan="2">????????? <span class="text-warning">'+obj.cAlived+'</span></th>'
	modal +=								'</tr>'
	modal +=								'<tr>'
	modal +=									'<th>???????????? <span class="text-warning">'+obj.cHp+'</span></th>'
	modal +=									'<th colspan="3">?????? <span class="text-warning">'+obj.cAddr+'</span></th>'
	modal +=								'</tr>'
	modal +=								'<tr>'
	modal +=									'<th>E-mail <span class="text-warning">'+obj.cEmail+'</span></th>'
	modal +=									'<th colspan="3">???????????? <span class="text-warning">'+obj.cRoute+'</span></th>'
	modal +=								'</tr>'
	modal +=								'<tr>'
	modal +=									'<th>???????????? <span class="text-warning">'+obj.cWriteDate+'</span></th>'
											if(obj.cRank != null) {
	modal +=									'<th>???????????? <span class="badge rounded-pill bg-success">Y</span></th>'
											}else{
	modal +=									'<th>???????????? <span class="badge rounded-pill bg-danger">N</span></th>'											
											}
	modal +=									'<th>???????????? <span class="text-warning">{??????}</span></th>'
	modal +=									'<th>?????????????????? <span class="text-warning">'+obj.cUpdatetime+'</span></th>'
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
	modal +=												'<label class="form-check-label" for="#">?????????????????? ??????</label>'
	modal +=												'<div class="mt-1 photoBox1">'
	modal +=													'<button type="button"  class="rotateL btn btn-outline-primary btn-sm" >???</button>'
	modal +=													'<button type="button"  class="rotateR btn btn-outline-primary btn-sm" >???</button>'
	modal +=													'<p class="mt-1"><img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/model_upload/'+obj.cImage+'" /></p>'
	modal +=												'</div>'
	modal +=											'</div>'
	modal +=										'</dd>'
												}
												if(obj.cImage2 != null) {
	modal +=										'<dd>'
	modal +=											' <div class="form-check">'
	modal +=												'<input type="radio" id="photoSelection" name="photoSelection" class="form-check-input" />'
	modal +=												'<label class="form-check-label" for="#">?????????????????? ??????</label>'
	modal +=												'<div class="mt-1 photoBox2">'
	modal +=													'<button type="button"  class="rotateL btn btn-outline-primary btn-sm" >???</button>'
	modal +=													'<button type="button"  class="rotateR btn btn-outline-primary btn-sm" >???</button>'
	modal +=													'<p class="mt-1"><img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/model_upload/'+obj.cImage2+'" /></p>'
	modal +=												'</div>'
	modal +=											'</div>'
	modal +=										'</dd>'
												}					
	modal +=									'</dl>'
	modal +=								'</div>'
	modal +=							'</div>'
	modal +=							'<h4 class="mt-2">???????????? ??????</h4>'
	modal +=							'<table class="table table-bordered  f13 paddingType mt-1">'
	modal +=								'<thead>'
	modal +=									'<tr>'
	modal +=										'<th>'
	modal +=											'<div class="row">'
	modal +=												'<div class="col-1"><p>????????????</p></div>'
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
	modal +=																	'<label class="form-check-label" name="cRank" for="#">?????????</label>'
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
	modal +=							'<button type="button" onclick="btnSave('+obj.cIdx+');" class="btn btn-primary" >??????</button>'
	modal +=						'</div>'
	modal +=					'</div>'
	modal +=				'</div>'
	modal +=			'</div>'
    modal += 		'</section>'
    modal +=	'</form>'
    
    return modal;
}