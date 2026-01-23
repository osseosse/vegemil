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
        url : '/admin/manage/scm/bizProposals',
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
      	{ data: 'companyName' },
        { data: 'personInCharge' },
        { data: 'contactNumber' },
        { data: 'email' },
        { data: 'item' },
        { data: 'title' },
        { data: 'createdAt' },
        { data: 'ipAddr' },
        { data: 'device' }
      ],
      columnDefs: [
        {
          // For Responsive
          orderable: false,
          targets: 0,
          render: function (data, type, full, meta) {
         	 return full['id']
		  }
        },
        {
          // 업체명
          targets: 1,
          orderable: false,
          render: function (data, type, full, meta) {
              if(full['companyName']==null)	return ''; else return full['companyName'];
            }
        },
        {
          // 담당자
          targets: 2,
          orderable: false,
          render: function (data, type, full, meta) {
              if(full['personInCharge']==null)	return ''; else return full['personInCharge'];
            }
          
        },
        {
          // 연락처
          targets: 3, 
          orderable: false,
          render: function (data, type, full, meta) {
            if(full['contactNumber']==null)	return ''; else return full['contactNumber'];
          }
        
        },
        {
          // 이메일
          targets: 4, 
	      orderable: false,
	      render: function (data, type, full, meta) {
	            if(full['email']==null)	return ''; else return full['email'];
          }
        },
        {
          // 품목
          targets: 5, 
  	      orderable: false,
  	      render: function (data, type, full, meta) {
	            if(full['item']==null) return ''; else return full['item'];
	      }
        },
        {
         targets: 6,
         orderable: false,
         render: function (data, type, full, meta) {
	            if(full['title']==null)	return ''; 
	            else return `
	            		  <div class="d-flex align-items-center">
	            <span class="text-truncate">${full.title}</span>

	            <span
	              class="btn btn-sm btn-info rounded-pill ms-auto"
	              style="font-size:11px;"
	              onclick="showupContentModal(${full.id})">
	            				내용
	            </span>
	          </div>
	        `;
	      }        
        },
        {
        	targets: 7,
        	orderable: false,
        	render: function (data, type, full, meta) {
        		if(full['createdAt']==null)	return ''; else return full['createdAt'];
        	}        
        },
        {
        	targets: 8,
        	orderable: false,
        	render: function (data, type, full, meta) {
        		if(full['ipAddr']==null)	return ''; else return full['ipAddr'];
        	}        
        },
        {
      	 targets: 9,
           orderable: false,
           render: function (data, type, full, meta) {
             let checked = '';
 			if(full['isCheck'] == 1) {
 				checked = 'checked';
 			}
 			return `
 					 <div class="form-check form-switch center-ck">
    <input
      type="checkbox"
      class="form-check-input"
      id="isCheck-${full.id}"
      ${checked}
      onclick="updateStatus(${full.id}, this)"
    >
    <label class="form-check-label" for="isCheck-${full.id}"></label>
  </div>
 					`;
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
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'csv',
              text: feather.icons['file-text'].toSvg({ class: 'font-small-4 mr-50' }) + 'Csv',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'excel',
              text: feather.icons['file'].toSvg({ class: 'font-small-4 mr-50' }) + 'Excel',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'pdf',
              text: feather.icons['clipboard'].toSvg({ class: 'font-small-4 mr-50' }) + 'Pdf',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6, 7, 8] }
            },
            {
              extend: 'copy',
              text: feather.icons['copy'].toSvg({ class: 'font-small-4 mr-50' }) + 'Copy',
              className: 'dropdown-item',
              exportOptions: { columns: [0, 1, 2, 3, 4, 5, 6, 7, 8] }
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
	
    $('div.head-label').html('<h4 class="card-title">문의 목록 </h4>');							 
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }	
}


function showupContentModal(id) {

	  $.ajax({
	    url: '/admin/manage/scm/bizProposeContent/' + id,
	    type: 'get',
	    dataType: 'json',
	    success: function (data) {
	    	  if (data) {
	    	    $('#modal-title').text(data.title);
	    	    $('#modal-body').text(data.content);
	    	    var fileText = ""
	    	    if(data.filePath1 != null && data.filePath1 !== "" ) {
	    	    	fileText += `[파일첨부1] <a href="/download${data.filePath1}" download>${data.fileOriginName1}</a><br/>`;
	    	    }
	    	    
	    	    if(data.filePath2 != null && data.filePath2 !== "" ) {
	    	    	fileText += `[파일첨부2] <a href="/download${data.filePath2}" download>${data.fileOriginName2}</a><br/>`; 
	    	    }
	    	    
	    	    if(data.filePath3 != null && data.filePath3 !== "" ) {
	    	    	fileText += `[파일첨부3] <a href="/download${data.filePath3}" download>${data.fileOriginName3}</a><br/>`;
	    	    }
	    	    
	    	    if(fileText === "") {
	    	    	fileText += "* 첨부된 파일이 없습니다 * "
	    	    }
	    	    document.getElementById("modal_file").innerHTML = fileText
	    	    
	    	    $('#contentModal').modal('show'); // ← 이거
	    	  }
    	  },
	    error: function () {
	      alert('조회 중 오류가 발생했습니다.');
	    }
	  });

}

function updateStatus(id, el) {
	  // 체크된 상태가 true면 1, 아니면 0
	  const status = el.checked ? "1" : "0";

	  $.ajax({
	    url: `/admin/manage/scm/bizProposeCheck/${id}/${status}`,
	    type: 'get',
	    dataType: 'json',
	    success: function () {
	      alert("변경되었습니다.");
	    },
	    error: function () {
	      alert('처리 중 오류가 발생했습니다.');
	      // 실패 시 원래 상태로 되돌리기
	      el.checked = !el.checked;
	    }
	  });
	}

