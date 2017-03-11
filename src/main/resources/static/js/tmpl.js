var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/tmpl/query";
var resourceUrl = "/tmpl";

//添加影厅模板模态层关闭事件
$("#addTmpl").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#add_tmplName").val("");
    $("#add_tmplTotalSeat").val("");
    $("#add_tmplSeatMap").val("");

});

//编辑影厅模板模态层关闭事件
$("#updateTmpl").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#update_tmplId").val("");
    $("#update_tmplName").val("");
    $("#update_tmplSeatMap").val("");
    $("#update_tmplTotalSeat").val("");

});

//座位图模态关闭
$("#seat_map").on('hide.bs.modal', function () {
    $("#seat #seat-map").remove();
});

// 查询
function queryTmplList() {
    var searchTmplName = $("#search_tmpl_name").val();
    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "tmplName": searchTmplName,
            "perPage": pageSize,
            "page": currentPage
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var tmplInfo = result.content.tmplInfo;
                var tmplData = tmplInfo.pageData;

                //当前页
                currentPage = tmplInfo.currentPage;
                window.parent.windowPage = currentPage;

                //总页数
                totalPage = Math.ceil(tmplInfo.totalCount / pageSize);

                $("#page_info").text(currentPage + "/" + totalPage);

                totalCount = tmplInfo.totalCount;

                $("#start_row").html((tmplInfo.currentPage - 1) * pageSize + 1);
                var end_row = ((tmplInfo.currentPage * pageSize) <= totalCount) ? tmplInfo.currentPage * pageSize : totalCount;
                $("#end_row").html(end_row);

                $("#total_row_acc").html(totalCount);

                $("#tmpl_info tbody tr").remove();

                for (var i = 0; i < tmplData.length; i++) {
                    var tmplId = tmplData[i].tmplId;
                    var tmplName = tmplData[i].tmplName;
                    var tmplTotalSeat = tmplData[i].totalSeat;
                    var id = "\"" + tmplId + "\"";

                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#tmpl_info").append(
                        "<tr>" +
                        "<td>" + j + "</td>" +
                        "<td>" + tmplName + "</td>" +
                        "<td>" + tmplTotalSeat + "</td>" +
                        "<td>" +
                        "<div class='btn-group' id='toggle_switch'>" +

                        "<button type='button' class='btn btn-primary' style='margin-left:5px' " +
                        "onclick=infoTmpl(" + id + ")>" +
                        "<i class='fa fa-edit'>编辑</i></button>" +

                        "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                        "onclick=delTmpl(" + id + ")>" +
                        "<i class='fa fa-trash-o'></i>删除</button>" +

                        "</div>" +
                        "</td>" +
                        "</tr>"
                    );
                }


            } else {
                $("#total_row_acc").html(0);
                $("#end_row").html(0);
                $("#start_row").html(0);
                $("#page_info").text("1/1");
                $("#tmpl_info tbody tr").remove();
                $("#tmpl_info").append(
                    "<tr role='row' class='odd'>" +
                    "  <td colspan=10>" +
                    "    <h3 class=text-center style='color:#777'>没有符合条件的数据，请重新选择条件再试！</h3>" +
                    "  </td>" +
                    "</tr>"
                );
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });
}

// 查看信息
function infoTmpl(id) {
    $('#updateTmpl').modal('show');
    var params = "/"+id;
    $.ajax({
        url : resourceUrl + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                var data = result.content.tmplInfo;
                var tmplId = data.tmplId;
                var tmplName = data.tmplName;
                var tmplTotalSeat = data.totalSeat;
                var tmplSeatMap = data.seatMap;

                $("#update_tmplId").val(tmplId);
                $("#update_tmplName").val(tmplName);
                $("#update_tmplTotalSeat").val(tmplTotalSeat);
                $("#update_tmplSeatMap").val(tmplSeatMap);

            }else{
                swal(result.repMsg, "", "error");
            }
        },
        error : function () {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });
}

// 删除
function delTmpl(id) {
    swal({
            title: "确定删除吗？",
            type: "warning", showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定", closeOnConfirm: false
        },
        function () {
            var params = "/"+id;
            $.ajax({
                url : resourceUrl + params,
                type : "DELETE",
                dataType : 'json',
                success : function (result) {
                    if(result.repCode == 1){
                        swal(result.repMsg,"","success");
                    }else{
                        swal(result.repMsg, "", "error");
                    }
                    queryTmplList();
                },
                error : function () {
                    swal("网络异常，请稍后重试！", "", "error");
                }
            });
        });
}

// 添加
function addTmpl() {
    var tmplName = $("#add_tmplName").val();
    var tmplTotalSeat = $("#add_tmplTotalSeat").val();
    var tmplSeatMap = $("#add_tmplSeatMap").val();

    if(tmplName == null || tmplName == "") {
        swal("请输入影厅模板名称！", "", "error");
        return;
    }
    if(tmplTotalSeat == null || tmplTotalSeat == "" || tmplTotalSeat < 10) {
        swal("请正确输入座位数！", "", "error");
        return;
    }
    if(tmplSeatMap == null || tmplSeatMap =="") {
        swal("请正确输入座位图！", "", "error");
        return;
    }
    $.ajax({
        url: resourceUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "tmplName" : tmplName,
            "totalSeat" : tmplTotalSeat,
            "seatMap" : tmplSeatMap
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影厅模板添加成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryTmplList();
                        $('#addTmpl').modal('hide');
                    });
            } else {
                swal("影厅模板添加失败", "", "error");//添加失败
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

// 更新
function updateTmpl() {
    var tmplId =  $("#update_tmplId").val();
    var tmplName = $("#update_tmplName").val();
    var tmplTotalSeat = $("#update_tmplTotalSeat").val();
    var tmplSeatMap = $("#update_tmplSeatMap").val();

    if(tmplId == null || tmplId =="") {
        swal("ID错误请刷新！", "", "error");
        return;
    }

    if(tmplName == null || tmplName == "") {
        swal("请输入影厅模板名称！", "", "error");
        return;
    }
    if(tmplTotalSeat == null || tmplTotalSeat == "" || tmplTotalSeat < 10) {
        swal("请正确输入座位数！", "", "error");
        return;
    }
    if(tmplSeatMap == null || tmplSeatMap =="") {
        swal("请正确输入座位图！", "", "error");
        return;
    }
    var params = "/"+tmplId;
    $.ajax({
        url: resourceUrl + params,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "tmplId" : tmplId,
            "tmplName" : tmplName,
            "totalSeat" : tmplTotalSeat,
            "seatMap" : tmplSeatMap
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影厅模板更新成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryTmplList();
                        $('#updateTmpl').modal('hide');
                    });
            } else {
                swal("影厅模板更新失败", "", "error");
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

//清空内容
function clean() {
    $("#search_tmpl_name").val("");
}


//分页
$("#home_page").click(function firstPage() {    // 首页
    currentPage = 1;
    $("#tmpl_info tbody tr").remove();
    queryTmplList();
});
$("#previous_page").click(function frontPage() {    // 上一页
    if (currentPage <= 1) {
        currentPage = 1;
    } else {
        currentPage--;
    }
    $("#tmpl_info tbody tr").remove();
    queryTmplList();
});
$("#next_page").click(function nextPage() {    // 下一页
    if (currentPage >= totalPage) {
        currentPage = totalPage;
    } else {
        currentPage++;
    }
    $("#tmpl_info tbody tr").remove();
    queryTmplList();
});
$("#trailer_page").click(function lastPage() {    // 尾页
    currentPage = totalPage;
    $("#tmpl_info tbody tr").remove();
    queryTmplList();
});

$("#page_size").change(function lastPage() {    // 每页显示记录数改变
    pageSize = $("#page_size").val();
    $("#tmpl_info tbody tr").remove();
    queryTmplList();
});


function showMap(v) {
    var m;
    if(v == 1) {
        m = $("#add_tmplSeatMap").val().split(',');
    }else if(v == 2) {
        m = $("#update_tmplSeatMap").val().split(',');
    }else {
        return;
    }


    $("#seat").append("<div id='seat-map'><div class='front'>屏幕</div></div>");
    var sc = $('#seat-map').seatCharts({
        map: m,
        legend : { //定义图例
            node : $('#legend'),
            items : [
                [ 'a', 'available',   '可选座' ],
                [ 'a', 'unavailable', '已售出']
            ]
        },
        click: function () { //点击事件
        }
    });
}