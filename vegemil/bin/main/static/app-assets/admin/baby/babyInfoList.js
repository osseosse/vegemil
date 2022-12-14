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
			url : '/admin/manage/baby/deleteBabyInfo',
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
	  pageLength: 5,
	  serverSide: true,
	  processing: true,
      ajax: {
        url : '/admin/manage/baby/babyInfoListData',
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
      	{ data: 'mbsIdx' },
        { data: 'mbsIdx' },
        { data: 'mbsIdx' },
        { data: 'mbsLCategory' },
        { data: 'mbsCategory' }, 
        { data: 'mbsMCategory' },
        { data: 'mbsTitle' },
        { data: 'mbsImage' },
        { data: 'mbsCheck' },
        { data: 'mbsGetdate' },
        { data: 'mbsActive' }
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
            return (
              '<div class="form-check"> <input class="form-check-input dt-checkboxes" type="checkbox" name="checkList" value="'+data+'" id="checkbox' +
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
              '<button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" onclick="location.href = \'babyInfoAdd?mbsIdx='+full['mbsIdx']+ '\'">??????</button>'
            );
          }
          
        },
        {
          targets: 3,
          orderable: false,
          render: function (data, type, full, meta) {
            return '????????????';
          }
          
        },
        {
          targets: 4,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mbsCategory'] == 'pb') {
	        	return '??????/??????';
            }else if(full['mbsCategory'] == 'gh') {
	        	return '??????/??????';
            }else if(full['mbsCategory'] == 'pe') {
	        	return '??????/??????';
            }else if(full['mbsCategory'] == 'lh') {
	        	return '??????/??????/?????????';
            }
          }
        },
        {
          targets: 5,
          orderable: false,
          render: function (data, type, full, meta) {
			let mbsCategory;
      	    if(full['mbsCategory'] == 'pb') {
				if(full['mbsMCategory'] == 'pb01'){
					mbsCategory = '????????????'
				}else if(full['mbsMCategory'] == 'pb02'){
					mbsCategory = '????????????'
				}else if(full['mbsMCategory'] == 'pb03'){
					mbsCategory = '????????????'
				}else if(full['mbsMCategory'] == 'pb04'){
					mbsCategory = '????????? ????????????'
				}else if(full['mbsMCategory'] == 'pb05'){
					mbsCategory = '?????? ??????&??????'
				}else if(full['mbsMCategory'] == 'pb06'){
					mbsCategory = '?????? ??????&??????'
				}else if(full['mbsMCategory'] == 'pb07'){
					mbsCategory = '?????? ??????'
				}
            }else if(full['mbsCategory'] == 'gh') {
	        	if(full['mbsMCategory'] == 'gb01'){
					mbsCategory = '1~3??????'
				}else if(full['mbsMCategory'] == 'gh02'){
					mbsCategory = '4~6??????'
				}else if(full['mbsMCategory'] == 'gh03'){
					mbsCategory = '7~12??????'
				}else if(full['mbsMCategory'] == 'gh04'){
					mbsCategory = '13~18??????'
				}else if(full['mbsMCategory'] == 'gh05'){
					mbsCategory = '19~24??????'
				}else if(full['mbsMCategory'] == 'gh06'){
					mbsCategory = '25~36??????'
				}else if(full['mbsMCategory'] == 'gh07'){
					mbsCategory = '3??? ??????'
				}else if(full['mbsMCategory'] == 'gh08'){
					mbsCategory = '????????? ????????????'
				}
            }else if(full['mbsCategory'] == 'pe') {
	        	if(full['mbsMCategory'] == 'pe01'){
					mbsCategory = 'DIY&?????????'
				}else if(full['mbsMCategory'] == 'pe02'){
					mbsCategory = '??????/?????? ?????????'
				}
            }else if(full['mbsCategory'] == 'lh') {
	        	if(full['mbsMCategory'] == 'lh01'){
					mbsCategory = '??????/??????'
				}else if(full['mbsMCategory'] == 'lh02'){
					mbsCategory = '?????????'
				}
            }
            return mbsCategory;
          }
          
        },
        {
          targets: 6,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mbsTitle']==null)	return '';
      			else	return full['mbsTitle'];
          }
          
        },
        {
          targets: 7,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mbsImage']==null)	return '-';
      			else	return '<img src="https://image.edaymall.com/images/dcf/vegemil/vegemilBaby/summernote/'+full['mbsImage']+  '" height="60" width="100" class="rounded">';
          }
          
        },
        {
          targets: 8,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mbsCheck']==null)	return '';
      			else	return full['mbsCheck'];
          }
          
        },
        {
          targets: 9,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['mbsGetdate']==null)	return '';
      			else	return full['mbsGetdate'];
          }
          
        },
        {
          targets: 10,
          orderable: false,
          render: function (data, type, full, meta) {
            let checked = '';
			if(full['mbsActive'] == 1) {
				checked = 'checked';
			}
            return (
              '<div class="form-check form-switch center-ck">'+
                '<input type="checkbox" class="form-check-input" '+checked+' id="mbsActive'+full['mbsIdx']+ '" name="listOn"'+ 'value="'+full['mbsIdx']+ '" onclick="javascript:btnDisplay('+full['mbsIdx']+')" >'+
				'<label class="form-check-label" for="listOn"></label>'+
		      '</div>'
            );
          }
          
        },
        {
          targets: 11,
          orderable: false,
          render: function (data, type, full, meta) {
            let checked = '';
			if(full['mbsCheck'] == 1) {
				checked = 'checked';
			}
            return (
              '<div class="form-check form-switch center-ck">'+
                '<input type="checkbox" class="form-check-input" '+checked+' id="mbsCheck'+full['mbsIdx']+ '" name="listOn"'+ 'value="'+full['mbsIdx']+ '" onclick="javascript:btnDisplay('+full['mbsIdx']+')" >'+
				'<label class="form-check-label" for="listOn"></label>'+
		      '</div>'
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
	
    $('div.head-label').html('<h4 class="card-title">????????? ?????? <button type="button" id="btnDel" class="btn btn-outline-danger btn-sm me-1">????????????</button>'+
                             '<button type="button" onclick="location.href=\'/admin/manage/baby/babyInfoAdd\'" class="btn btn-outline-info btn-sm me-1">????????????</button></h4>');
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
	
}

function btnDisplay(idx) {
	console.log('btnDisplay', idx)
	let mbsActive;
	let mbsCheck;
	if($('#mbsActive'+idx).is(":checked")){
		mbsActive = 1;
	}else{
		mbsActive = 0;
	}
	
	if($('#mbsCheck'+idx).is(":checked")){
		mbsCheck = 1;
	}else{
		mbsCheck = 0;
	}
	
	if(confirm('????????? ?????????????????????????')){
		$.ajax({
			url : '/admin/manage/baby/displayBabyInfo?mbsIdx='+idx+'&mbsActive='+mbsActive+'&mbsCheck='+mbsCheck,
			type : "get",
			dataType : "json",
			success : function(data) {
				console.log('data============',data);
				if(data){
					alert("?????????????????????.");
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
}

