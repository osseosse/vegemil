/**
 * DataTables Basic
 */

$(function () {
  'use strict';
  createTable();
  
});

var createTable = function() {
	console.log('createTable')
	 var dt_basic_table = $('.datatables-basic'),
    dt_date_table = $('.dt-date'),
    table = $('.datatables-basic').DataTable();
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
        url : '/admin/manage/customer/faqScoreList',
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
        { data: 'idx' },
        { data: 'faqIdx' },
        { data: 'fQuestion' }, // used for sorting so will hide this column
        { data: 'fId' },
        { data: 'fName' },
        { data: 'fScore' },
        { data: 'fEtcBox'},
        { data: 'fInsertdate' }
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
            if(full['faqIdx']==null)	return '';
      			else	return full['faqIdx'];
          }
        },
        {
          targets: 2,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fQuestion']==null)	return '';
      			else	return full['fQuestion'];
          }
        },
        {
          targets: 3,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fId']==null)	return '';
      			else	return full['fId'];
          }
          
        },
        {
          targets: 4,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fName']==null)	return '';
      			else	return full['fName'];
          }
          
        },
        {
          targets: 5,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fScore']==null)	return '';
      			else	return full['fScore'];
          }
          
        },
        {
          targets: 6,
          orderable: false,
          className: 'my_modal',
          render: function (data, type, full, meta) {
//            if(full['fEtcBox']==null)	return '';
//      			else	return full['fEtcBox'];
			return (
					'<a data-bs-toggle="modal" data-bs-target="#modal'+full['idx']+'">'+full['fEtcBox']+'</a>'+
				   '<section id="modal-sizes">'+
					'<div class="modal fade text-start" id="modal'+full['idx']+'" tabindex="-1" aria-labelledby="myModalLabel17" aria-hidden="true">'+
						'<div class="modal-dialog modal-dialog-centered modal-lg">'+
							'<div class="modal-content">'+
								'<div class="modal-header">'+
									'<h4 class="modal-title" id="myModalLabel17">FAQ 의견 상세보기</h4>'+
									'<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>'+
								'</div>'+
								'<table class="table table-bordered  f13 paddingType">'+
									'<colgroup>'+
										'<col width="20%">'+
										'<col width="20%">'+
										'<col width="40%">'+
									'</colgroup>'+
									'<thead>'+
									'<tr>'+
										'<th>ID <span class="text-warning">'+full['fId']+'</span></th>'+
										'<th>작성자명 <span class="text-warning">'+full['fName']+'</span></th>'+
										'<th>등록일시 <span class="text-warning">'+full['fInsertdate']+'</span></th>'+
									'</tr>'+
									'<tr>'+
										'<th>상담분류 <span class="text-warning">{분류명}</span></th>'+
										'<th colspan="2">FAQ 제목 <span class="text-warning">'+full['fQuestion']+'</span></th>'+
									'</tr>'+
									'<tr>'+
										'<th colspan="3">점수 <span class="text-warning">'+full['fScore']+'</span></th>'+
									'</tr>'+
									'</thead>'+
								'</table>'+
								'<div class="modal-body">'+
									'<h6>원문 게시글</h6>'+
									'<div class="text-type">'+
										'<p>'+full['fQuestion']+'</p>'+
									'</div>'+
									'<h6 class="mt-2 ">고객 추가 등록한 의견</h6>'+
									'<div class="text-type text-success mb-2">'+
									full['fEtcBox']+
									'</div>'+
								'</div>'+				   
							'</div>'+
						'</div>'+
					'</div>'+
				'</section>'
				)
          }
          
        },
        {
          targets: 7,
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['fInsertdate']==null)	return '';
      			else	return full['fInsertdate'];
          }
          
        }
      ],
      displayLength: 10,
      lengthMenu: [10, 25, 50, 75, 100],
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
  }

  // Flat Date picker
  if (dt_date_table.length) {
    dt_date_table.flatpickr({
      monthSelectorType: 'static',
      dateFormat: 'm/d/Y'
    });
  }

  dt_basic.on( 'order.dt', function () {
	dt_basic.column(0, {search:'applied'}).nodes().each( function (cell, i) {
		cell.innerHTML = i+1;
	} );
  } ).draw();
  
  /*
  dt_basic.on( 'init.dt', function () {
	dt_basic.column(0, {search:'applied'}).nodes().each( function (cell, i) {
		console.log(i)
		var num = i
		$('.my_modal').eq(num)
		  .attr('data-bs-toggle', 'modal')
          .attr('data-bs-target', '#modal'+num);
	} );
  } ).draw();
  */
	
  $('div.head-label').html('<h4 class="card-title">의견글 목록</h4>');

}
