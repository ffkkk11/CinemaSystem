var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/cinema/query";
var resourceUrl = "/cinema";

//添加影院模态层关闭事件
$("#addCinema").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#add_cinemaName").val("");
    $("#add_cinemaAddress").val("");
    $("#add_cinemaTel").val("");
    // $("#add_cinemaInfo").text("");
    addEditor.$txt.html('');
});

//编辑影院模态层关闭事件
$("#updateCinema").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#update_cinemaName").val("");
    $("#update_cinemaId").val("");
    $("#update_cinemaAddress").val("");
    $("#update_cinemaTel").val("");
    // $("#add_cinemaInfo").text("");
    updateEditor.$txt.html('');
});

// 查询
function queryCinemaList() {
    var searchCinemaName = $("#search_cinema_name").val();
    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "cinemaName": searchCinemaName,
            "perPage": pageSize,
            "page": currentPage
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var cinemaInfo = result.content.cinemaInfo;
                var cinemaData = cinemaInfo.pageData;

                //当前页
                currentPage = cinemaInfo.currentPage;
                window.parent.windowPage = currentPage;

                //总页数
                totalPage = Math.ceil(cinemaInfo.totalCount / pageSize);

                $("#page_info").text(currentPage + "/" + totalPage);

                totalCount = cinemaInfo.totalCount;

                $("#start_row").html((cinemaInfo.currentPage - 1) * pageSize + 1);
                var end_row = ((cinemaInfo.currentPage * pageSize) <= totalCount) ? cinemaInfo.currentPage * pageSize : totalCount;
                $("#end_row").html(end_row);

                $("#total_row_acc").html(totalCount);

                $("#cinema_info tbody tr").remove();

                for (var i = 0; i < cinemaData.length; i++) {
                    var cinemaId = cinemaData[i].cinemaId;
                    var cinemaName = cinemaData[i].cinemaName;
                    var address = cinemaData[i].address;
                    var tel = cinemaData[i].tel;
                    var info = cinemaData[i].info;
                    var id = "\"" + cinemaId + "\"";

                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#cinema_info").append(
                        "<tr>" +
                        "<td>" + j + "</td>" +
                        "<td>" + cinemaName + "</td>" +
                        "<td>" + address + "</td>" +
                        "<td>" + tel + "</td>" +
                        // "<td>" + info +"</td>" +
                        "<td>" +
                        "<div class='btn-group' id='toggle_switch'>" +

                        "<button type='button' class='btn btn-primary' style='margin-left:5px' " +
                        "onclick=infoCinema(" + id + ")>" +
                        "<i class='fa fa-edit'>编辑</i></button>" +

                        "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                        "onclick=delCinema(" + id + ")>" +
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
                $("#cinema_info tbody tr").remove();
                $("#cinema_info").append(
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
function infoCinema(id) {
    $('#updateCinema').modal('show');
    var params = "/"+id;
    $.ajax({
        url : resourceUrl + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                var data = result.content.cinemaInfo;
                var cinemaId = data.cinemaId;
                var cinemaName = data.cinemaName;
                var address = data.address;
                var tel = data.tel;
                var info = data.info;

                $("#update_cinemaId").val(cinemaId);
                $("#update_cinemaName").val(cinemaName);
                $("#update_cinemaAddress").val(address);
                $("#update_cinemaTel").val(tel);
                updateEditor.$txt.html(info);

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
function delCinema(id) {
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
                    queryCinemaList();
                },
                error : function () {
                    swal("网络异常，请稍后重试！", "", "error");
                }
            });
        });
}

// 添加
function addCinema() {
    var cinemaName = $("#add_cinemaName").val();
    var cinemaAddress = $("#add_cinemaAddress").val();
    var cinemaTel = $("#add_cinemaTel").val();
    var cinemaInfo = addEditor.$txt.html();

    if(cinemaName == null || cinemaName == "") {
        swal("请输入影院名称！", "", "info");
        return;
    }
    $.ajax({
        url: resourceUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "cinemaName" : cinemaName,
            "address" : cinemaAddress,
            "tel" : cinemaTel,
            "info" : cinemaInfo
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影院添加成功",//影院添加成功
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryCinemaList();
                        $('#addCinema').modal('hide');
                    });
            } else {
                swal("影院添加失败", "", "error");//添加失败
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

// 更新
function updateCinema() {
    var cinemaId =  $("#update_cinemaId").val();
    var cinemaName = $("#update_cinemaName").val();
    var cinemaAddress = $("#update_cinemaAddress").val();
    var cinemaTel = $("#update_cinemaTel").val();
    var cinemaInfo = updateEditor.$txt.html();

    if(cinemaId == null || cinemaId == "") {
        swal("出现错误,ID为空，请重新刷新！", "", "error");
        return;
    }

    if(cinemaName == null || cinemaName == "") {
        swal("请输入影院名称！", "", "error");
        return;
    }
    var params = "/"+cinemaId;
    $.ajax({
        url: resourceUrl + params,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "cinemaId" : cinemaId,
            "cinemaName" : cinemaName,
            "address" : cinemaAddress,
            "tel" : cinemaTel,
            "info" : cinemaInfo
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影院更新成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryCinemaList();
                        $('#updateCinema').modal('hide');
                    });
            } else {
                swal("影院更新失败", "", "error");
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

//清空内容
function clean() {
    $("#search_cinema_name").val("");
}


//分页
$("#home_page").click(function firstPage() {    // 首页
    currentPage = 1;
    $("#cinema_info tbody tr").remove();
    queryCinemaList();
});
$("#previous_page").click(function frontPage() {    // 上一页
    if (currentPage <= 1) {
        currentPage = 1;
    } else {
        currentPage--;
    }
    $("#cinema_info tbody tr").remove();
    queryCinemaList();
});
$("#next_page").click(function nextPage() {    // 下一页
    if (currentPage >= totalPage) {
        currentPage = totalPage;
    } else {
        currentPage++;
    }
    $("#cinema_info tbody tr").remove();
    queryCinemaList();
});
$("#trailer_page").click(function lastPage() {    // 尾页
    currentPage = totalPage;
    $("#cinema_info tbody tr").remove();
    queryCinemaList();
});

$("#page_size").change(function lastPage() {    // 每页显示记录数改变
    pageSize = $("#page_size").val();
    $("#cinema_info tbody tr").remove();
    queryCinemaList();
});