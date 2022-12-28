/**
 * DataTables Basic
 */

$(function () {
  'use strict';
  createTable();
  
  $('#btnDel').click(function(e){
	console.log('e', e)
    var form = document.form;
      
    // Output form data to a console
    console.log("Form submission", decodeURIComponent($(form).serialize())); 
      
    if(confirm('삭제하시겠습니까?')){
		$.ajax({
			url : '/admin/customer/faq/delete',
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
	 var dt_basic_table = $('.datatables-basic'),
    dt_date_table = $('.dt-date'),
    assetPath = '../../../app-assets/';
    const table = $('.datatables-basic').DataTable();
    table.destroy();

  if ($('body').attr('data-framework') === 'laravel') {
    assetPath = $('body').attr('data-asset-path');
  }

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
        url : '/admin/customer/faq/table',
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
        { data: 'fIdx' },
        { data: 'fIdx' },
        { data: 'fIdx' }, // used for sorting so will hide this column
        { data: 'fCate' },
        { data: 'fQuestion' },
        { data: 'fHit' },
        { data: 'fEditdate' },
        { data: 'fView' }
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
          className: "align-center",
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
              '<button type="button" class="btn btn-primary btn-sm btn-sm waves-effect waves-float waves-light" onclick="location.href = \'faqAdd?fIdx='+full['fIdx']+ '\'">수정</button>'
            );
          }
          
        },
        {
          targets: 3,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fCate']==null)	return '';
      			else	return full['fCate'];
          }
          
        },
        {
          targets: 4,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fQuestion']==null)	return '';
      			else	return full['fQuestion'];
          }
          
        },
        {
          targets: 5,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fHit']==null)	return '';
      			else	return full['fHit'];
          }
          
        },
        {
          targets: 6,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fEditdate']==null)	return '';
      			else	return full['fEditdate'];
          }
          
        },
        {
          targets: 7,
          orderable: false,
          render: function (data, type, full, meta) {
			let checked = '';
			console.log('fVew', full['fView']);
			if(full['fView'] == 1) {
				checked = 'checked';
			}
            return (
              '<div class="form-check form-switch center-ck">'+
                '<input type="checkbox" class="form-check-input" '+checked+' id="faqActive'+full['fIdx']+ '" name="listOn"'+ 'value="'+full['fIdx']+ '" onclick="javascript:btnDisplay('+full['fIdx']+')" >'+
				'<label class="form-check-label" for="listOn"></label>'+
		      '</div>'
            );
          }
          
        }
      ],
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
  }

  // Flat Date picker
  if (dt_date_table.length) {
    dt_date_table.flatpickr({
      monthSelectorType: 'static',
      dateFormat: 'm/d/Y'
    });
  }

  // Add New record
  // ? Remove/Update this code as per your requirements ?
//  var count = 101;
//  $('.data-submit').on('click', function () {
//    var $new_name = $('.add-new-record .dt-full-name').val(),
//      $new_post = $('.add-new-record .dt-post').val(),
//      $new_email = $('.add-new-record .dt-email').val(),
//      $new_date = $('.add-new-record .dt-date').val(),
//      $new_salary = $('.add-new-record .dt-salary').val();
//
//    if ($new_name != '') {
//      dt_basic.row
//        .add({
//          fIdx: null,
//          fIdx: count,
//          fQuestion: $new_name,
//          fGory: $new_post,
//          fCate: $new_email,
//          fEditdate: $new_date,
//          fCate: '$' + $new_salary,
//          fView: 5
//        })
//        .draw();
//      count++;
//      $('.modal').modal('hide');
//    }
//  });
  
  dt_basic.on( 'order.dt', function () {
	dt_basic.column(0, {search:'applied'}).nodes().each( function (cell, i) {
		cell.innerHTML = i+1;
	} );
  } ).draw();
	
  $('div.head-label').html('<h4 class="card-title">FAQ 글 목록 <button type="button" id="btnDel" class="btn btn-outline-danger btn-sm me-1">선택삭제</button>'+
                           '<button type="button" onclick="location.href=\'/admin/customer/faqAdd\'" class="btn btn-outline-info btn-sm me-1">새글등록</button></h4>');

  // Delete Record
  $('.datatables-basic tbody').on('click', '.delete-record', function () {
    dt_basic.row($(this).parents('tr')).remove().draw();
  });
}

function btnDisplay(idx) {
	console.log('btnDisplay', idx)
	let fView;
	if($('#faqActive'+idx).is(":checked")){
		console.log('checked')
		fView = 1;
	}else{
		console.log('unchecked')
		fView = 0;
	}
	if(confirm('진열을 수정하시겠습니까?')){
		$.ajax({
			url : '/admin/customer/faq/updateDisplay?fIdx='+idx+'&fView='+fView,
			type : "post",
			data : $(form).serialize(),
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