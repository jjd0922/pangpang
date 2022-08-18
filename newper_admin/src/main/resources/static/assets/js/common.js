//ajax 로딩 화면 및 alert
$(document).ajaxStart(function(event, xhr, settings){
    //로딩창
    $(document.body).css('cursor','wait');
}).ajaxSend(function(event, jqXHR, ajaxOptions ){

    //응답전에 다시 조회하는 경우 기존 요청 취소
    var reqUrl=ajaxOptions.url;
    try{
        if(reqUrl.indexOf('.dataTable')!=-1){
            $('table').each(function(){
                var table_id=$(this).attr('id');
                if(table_id!==undefined && table_id!=null){
                    if ( $.fn.DataTable.isDataTable( '#'+table_id ) ) {
                        var table=$('#'+table_id).DataTable();
                        if(table.settings()[0].jqXHR!=null && table.settings()[0].jqXHR.readyState!=4 && reqUrl==table.settings()[0].ajax.url){
                            table.settings()[0].jqXHR.abort();
                        }
                    }
                }
            });

            //datatable 열 넓이 자동 정렬
            try{
                $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
            }catch(e){
                console.log('data table adjust error start');
                console.log(e);
                console.log('data table adjust error end');
            }
        }
    }catch(e){}
}).ajaxError(function(event, xhr, settings,data){
    try{
        if(xhr.responseJSON != null && xhr.responseJSON.message!=null){
            alert(xhr.responseJSON.message);
        }else{
            if(xhr.statusText != 'abort'){
                alert('에러 발생');
            }
        }
    }catch(e){
        console.log(e);
    }
}).ajaxSuccess(function(event, xhr, settings, thrownError){
    if(xhr.responseJSON != null){
        if(xhr.responseJSON.message!=null){
            alert(xhr.responseJSON.message);
        }

        if(xhr.responseJSON.location!=null){
            location.href = xhr.responseJSON.location;
        }
    }
}).ajaxStop(function(event, xhr, settings, thrownError){
    //로딩 끝
    $(document.body).css('cursor','');
});

//dataTable destroy
function destroyDatatable(id){
    try{
        if ($.fn.DataTable.isDataTable('#'+id)) {
            $('#'+id).DataTable().destroy();
            $('#'+id).html('');
        }
    }catch(e){}
}

//datatable 기본 설정
if($.fn.dataTable !== undefined){
    $.extend($.fn.dataTable.defaults, {
        /*scrollY:        400,*/
        /*scrollCollapse: true,*/
        /*fixedHeader:    false,*/
        /*fixedColumns: {
            leftColumns: 4,
        },*/
        scrollX:        true,
        deferRender:    true,
        scroller:       true,
        autoWidth: false,
        lengthMenu: [
            [ 10, 25, 50],
            [ '10행', '25행', '50행']
        ],
        dom: '<"datatable-header"Bip><"datatable-scroll"t><"datatable-footer">',
        language: {
            search: '_INPUT_',
            searchPlaceholder: '결과내 재검색',
            paginate: { 'first': 'First', 'last': 'Last', 'next': '&rarr;', 'previous': '&larr;' },
            emptyTable: '데이터가 없습니다.',
            info: '_TOTAL_개의 검색결과 [ _START_ ~ _END_  ]',
            infoEmpty: '검색결과가 없습니다. ',
            infoFiltered: '(총 _MAX_개의 자료에서 재검색)',
            lengthMenu: '_MENU_',
            zeroRecords: '검색 결과가 없습니다.',
        },
        buttons: {
            buttons: [
                {
                    extend: 'selectAll',
                    className: 'btn btn-sm btn-primary opacity-80',
                    text: '<i class="icon-checkbox-checked2 mr-1"></i>전체선택',
                    exportOptions: {
                        columns: ':visible'
                    }
                },
                {
                    extend: 'selectNone',
                    className: 'btn btn-sm badge-indigo opacity-80',
                    text: '<i class="icon-cross2 mr-1"></i>선택해제',
                    exportOptions: {
                        columns: ':visible'
                    }
                },
                {
                    extend: 'excelHtml5',
                    className: 'btn btn-sm btn-dark opacity-60',
                    action: function (e, dt, node, config) {
                        downloadAllExcel(dt.settings()[0].sTableId);
                    },
                    text: '<i class="icon-file-excel mr-1"></i>엑셀',
                    footer: true
                },
                {
                    extend: 'pageLength',
                    className: 'btn btn-sm btn-dark opacity-50'
                }
                ,
                {
                    text: '새로고침',
                    className: 'btn btn-sm btn-secondary',
                    action: function (e, dt, node, config) {
                        dt.ajax.reload();
                    }
                }
            ]
        }
        ,'footerCallback':function(tfoot, data, start, end, display){
            //init하기 전에 tfoot 태그 있어야함
            if(tfoot!=null){
                var api = this.api();

                var recordsTotal=api.table().ajax.json().recordsTotal;
                if(recordsTotal == 0){
                    $(tfoot).parent().hide();
                }else{
                    $(tfoot).parent().show();
                }

                var countMap=api.table().ajax.json().map.countMap;
                var col_length=api.table().columns()[0].length;
                $(tfoot).html('');
                $(tfoot).append('<td><strong>합계</strong></td>');
                for(var i=1;i<col_length;i++){
                    //필드명
                    var col_data=api.context[0].aoColumns[i].data;
                    var col_value=countMap[col_data];
                    if(col_value==null){
                        col_value='';
                    }else{
                        if(typeof col_value == 'number'){
                            col_value=comma(col_value);
                            //col_value=comma(col_value)+'<span class="small">원</span>';
                        }else{
                            col_value=col_value;
                        }
                    }
                    $(tfoot).append('<td class="text-right"><strong class="text-secondary">'+col_value+'</strong></td>');
                }
            }
        }
    });
}

function selfClose(){
    window.open('','_self').close();
}

function modalDiv(dtId){
    var divStr =  '<html lang="ko" xmlns:th="http://www.thymeleaf.org">\n' +
    '<div class="modal" tabindex="-1" role="dialog" id="dtExcelReason">\n' +
    '  <div class="modal-dialog" role="document">\n' +
    '    <div class="modal-content">\n' +
    '      <div class="modal-header">\n' +
    '        <h5 class="modal-title">엑셀 다운로드 사유 입력</h5>\n' +
    '        <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
    '          <span aria-hidden="true">&times;</span>\n' +
    '        </button>\n' +
    '      </div>\n' +
    '      <div class="modal-body">\n' +
    '        <form id="dtExcelReasonForm" onkeypress="if(event.keyCode==13){searchModalBtn();return false;}">\n' +
    '          <div class="col-lg-12">\n' +
    '            <div class="card">\n' +
    '              <div class="card-body popup-contents">\n' +
    '                <fieldset class="mb-0">\n' +
    '                  <div class="row">\n' +
    // '                    <div class="col-sm-6">\n' +
    // '                      <div class="form-group">\n' +
    // '                        <div class="input-group">\n' +
    // '                          <span class="input-group-prepend">\n' +
    // '                              <span class="input-group-text">아이디</span>\n' +
    // '                          </span>\n' +
    // '                          <input type="text" class="form-control" th:value="" readonly>\n' +
    // '                        </div>\n' +
    // '                      </div>\n' +
    // '                    </div>\n' +
    // '                    <div class="col-sm-6">\n' +
    // '                      <div class="input-group">\n' +
    // '                          <span class="input-group-prepend">\n' +
    // '                              <span class="input-group-text">다운로드일자</span>\n' +
    // '                          </span>\n' +
    // '                        <input type="text" class="form-control" th:value="" readonly>\n' +
    // '                      </div>\n' +
    // '                    </div>\n' +
    '                    <div class="col-sm-12">\n' +
    '                      <div class="input-group">\n' +
    '                          <span class="input-group-prepend">\n' +
    '                              <span class="input-group-text">\n' +
    '                                다운로드 사유 (<b id="byteLimit">300</b> Byte)\n' +
    '                              </span>\n' +
    '                          </span>\n' +
    '                        <textarea type="text" class="form-control" id="downloadReason" name="reason" onkeyup="byteLimit(this, 300)"></textarea>\n' +
    '                      </div>\n' +
    '                    </div>\n' +
    '                  </div>\n' +
    '                </fieldset>\n' +
    '              </div>\n' +
    '            </div>\n' +
    '          </div>\n' +
    '        </form>\n' +
    '      </div>\n' +
    '      <div class="modal-footer">\n' +
    '        <button type="button" class="btn btn-light" data-dismiss="modal">닫기</button>\n' +
    '        <button type="button" class="btn btn-primary" onclick=downloadAllExcel("'+dtId+'") >다운로드</button>\n' +
    '      </div>\n' +
    '    </div>\n' +
    '  </div>\n' +
    '</div>\n' +
    '\n' +
    '<script>\n' +
    '  //취소사유 입력 byte확인 및 제한 입력시\n' +
    '  function byteLimit(obj, maxByte) {\n' +
    '    var str = obj.value;\n' +
    '    var str_len = str.length;\n' +
    '    var rbyte = 0;\n' +
    '    var rlen = 0;\n' +
    '    var one_char = "";\n' +
    '    var str2 = "";\n' +
    '\n' +
    '\n' +
    '    for(var i=0; i<str_len; i++) {\n' +
    '      one_char = str.charAt(i);\n' +
    '      if(escape(one_char).length > 4) {\n' +
    '        rbyte += 2;                                         //한글2Byte\n' +
    '      }else{\n' +
    '        rbyte++;                                            //영문 등 나머지 1Byte\n' +
    '      }\n' +
    '      if(rbyte <= maxByte){\n' +
    '        rlen = i+1;                                          //return할 문자열 갯수\n' +
    '      }\n' +
    '    }\n' +
    '    if(rbyte > maxByte){\n' +
    '      str2 = str.substr(0,rlen);                                  //문자열 자르기\n' +
    '      obj.value = str2;\n' +
    '      byteLimit(obj, maxByte);\n' +
    '    }else {\n' +
    '      document.getElementById(\'byteLimit\').innerText = maxByte-rbyte;\n' +
    '    }\n' +
    '  }\n' +
    '</script>';
    return divStr;
}

function downloadAllExcel(dtId){
    var excelForm=document.createElement('form');
    var excel_cols=document.createElement('input');
    excel_cols.setAttribute('name', 'cols');
    excel_cols.setAttribute('type', 'hidden');
    var dt = $("#"+dtId).DataTable();
    var t=dt.ajax.params().columns;
    var excel_cols_value='';
    var phoneCheck = true;
    // var phoneCheck = false;
    for(var i=0;i<t.length;i++){
        if(dt.columns(i).visible()[0]){
            excel_cols_value+=t[i].data+';';
            var tiData = t[i].data+'';
            if(tiData.indexOf('_PHONE') != -1){
                phoneCheck = true;
            }
        }
    }
    if(phoneCheck){
        if( !$("#dtExcelReason").hasClass('show') ){
            $('body').after(modalDiv(dtId));
            $("#dtExcelReason").modal('show');
            return false;
        }
        var downloadReason = $("#downloadReason");
        if(downloadReason.val() == ''){
            alert('엑셀 다운로드 사유를 입력해주세요.');
            downloadReason.focus();
            return false;
        }

        var reason = document.createElement('input');
        reason.setAttribute('name', 'reason');
        reason.setAttribute('type', 'hidden');
        reason.value=downloadReason.val();
        excelForm.appendChild(reason);
    }

    excel_cols.value=excel_cols_value;
    excelForm.appendChild(excel_cols);

    //order
    try{
        var order_column=document.createElement('input');
        order_column.setAttribute('name', 'order[0][column]');
        order_column.setAttribute('type', 'hidden');
        order_column.value=dt.ajax.params().order[0].column;
        excelForm.appendChild(order_column);

        var order_column2=document.createElement('input');
        order_column2.setAttribute('name', "columns["+dt.ajax.params().order[0].column+"][data]");
        order_column2.setAttribute('type', 'hidden');
        order_column2.value=dt.ajax.params().columns[dt.ajax.params().order[0].column].data;
        excelForm.appendChild(order_column2);

        var order_column3=document.createElement('input');
        order_column3.setAttribute('name', 'order[0][dir]');
        order_column3.setAttribute('type', 'hidden');
        order_column3.value=dt.ajax.params().order[0].dir;
        excelForm.appendChild(order_column3);
    }catch (e) {}

    //thead
    var thead=document.createElement('input');
    thead.setAttribute('name', 'thead');
    thead.setAttribute('type', 'hidden');

    //크롬은 \t 익스는 \r\n\r\n
    var colHead='';
    for(var i=0; i<dt.columns().header().length; i++){
        colHead += dt.columns().header()[i].innerText+';';
    }
    thead.value=colHead;

    excelForm.appendChild(thead);

    excelForm.method='post';

    var pathNameStr = window.location.pathname;
    // if(opener != null){
    //     pathNameStr = opener.location.pathname;
    // }

    if(dt.ajax.url().indexOf('?')==-1){
        excelForm.action=dt.ajax.url()+'?download=true&length=-1&pathUrl='+pathNameStr;
    }else{
        excelForm.action=dt.ajax.url()+'&download=true&length=-1&pathUrl='+pathNameStr;
    }
    document.body.appendChild(excelForm);
    excelForm.submit();
}
function dateFormat(yyyymmdd){
    if(yyyymmdd.length!=8){
        return yyyymmdd;
    }
    return yyyymmdd.substring(0,4)+'-'+yyyymmdd.substring(4,6)+'-'+yyyymmdd.substring(6);
}
//datatable 선택된 값 가져오기
function dataTableGetSelectedData(id,name){
    var getData_table=$('#'+id).DataTable();
    var getData_arr=getData_table.rows({selected:true})[0];
    var param='';
    for(idx in getData_arr){
        param+=getData_table.data()[getData_arr[idx]][name]+',';
    }
    return param;
}
//datatable 선택된 값 가져오기
function dataTableGetSelectedDataArray(id,name){
    var getData_table=$('#'+id).DataTable();
    var getData_arr=getData_table.rows({selected:true})[0];
    var param=new Array();
    for(idx in getData_arr){
        param.push(getData_table.data()[getData_arr[idx]][name]);
    }
    return param;
}

//datatable rowIndex로 data 가져오기
function getDatatablemap(id, rowIndex){
    var table=$('#'+id).DataTable();
    return table.data()[rowIndex];
}

//trim 지원안하는 브라우저
if (!String.prototype.trim) {
    String.prototype.trim = function () {
        return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
    };
}
//datatable refresh
function refreshTable(selector){
    try{
        $(selector).DataTable().ajax.reload();
    }catch (e) {
        console.log(e);
    }
}
//3자리마다 컴마 찍힌 값으로 return
function comma(strOri){
    var minus = String(strOri).substring(0,1);

    var str=(strOri+'').replace(/[^0-9.]/gi,'');
    str=Number(str)+'';
    str = str.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    //음수
    if(minus == '-')str = '-'+str;

    return str;
}

// input file 이미지 사이즈 체크(가로 세로 같을 경우 + 이미지 파일일 경우만 가능)
// input file 태그에 onchange로 사용
function imageSizeCheck(obj){
    if(obj.value != ''){
        var fileLength = $($(obj)).val().length;
        var dot = $($(obj)).val().lastIndexOf('.');
        var type = $($(obj)).val().substring(dot+1, fileLength).toLowerCase();
        if(type == 'jpeg' || type == 'png' || type == 'jpg'){
        }else{
            alert('jpeg, png, jpg 확장자의 파일만 가능합니다.');
            obj.value = '';
            return false;
        }

        var img = new Image();
        var file = $($(obj))[0].files[0];
        var _URL = window.URL || window.webkitURL;

        img.src = _URL.createObjectURL(file);

        img.onload = function(){
            if(img.width == img.height){
                return true;
            }else{
                alert('이미지의 가로,세로 크기를 맞춰주세요. \n width: ' + img.width + ' / height: ' + img.height);
                obj.value = '';
                return false;
            }
        }
    }
}
//td tag return
function tdTag(html, td_class){
    if(td_class != null){
        return '<td class="'+td_class+'">'+html+'</td>';
    }else{
        return '<td>'+html+'</td>';
    }
}

// 20글자 이상 말줄임표
function ellipsisStr(str){
    str = str+'';
    if(str.length > 20){
        str = str.substr(0, 18) + '...';
    }
    return str;
}

//파일 다운로드
function fileDownload(path, name) {
    location.href = "/download/?obj=" + encodeURIComponent(path) + '&fileName=' + encodeURIComponent(name);
}

//onkeyUp에 주면 숫자+콤마. (한글제거)
function numCommaKeyUp(){
    var tar=$(event.target);
    $(tar).val(comma($(tar).val()));
}