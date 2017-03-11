var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/room/query";
var resourceUrl = "/room";

//添加影厅模态层关闭事件
$("#addRoom").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#add_roomName").val("");
    $("#add_tmplId option").remove();
    $("#add_cinemaId option").remove();
});

//添加影厅模态层显示事件
$("#addRoom").on('show.bs.modal', function () {
    // 获取影院信息
    $.ajax({
        url: "/cinema/query",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "perPage": 1000,
            "page": 1
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var cinemaInfo = result.content.cinemaInfo;
                var cinemaData = cinemaInfo.pageData;
                $("#add_cinemaId option").remove();
                $("#add_cinemaId").append("<option value='-1'>请选择</option>");
                for (var i = 0; i < cinemaData.length; i++) {
                    var cinemaId = cinemaData[i].cinemaId;
                    var cinemaName = cinemaData[i].cinemaName;
                    $("#add_cinemaId").append("<option value=" + cinemaId +
                        ">" + cinemaName + "</option>");
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });

    // 获取影厅模板
    $.ajax({
        url: "/tmpl/query",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "perPage": 1000,
            "page": 1
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var tmplInfo = result.content.tmplInfo;
                var tmplData = tmplInfo.pageData;

                $("#add_tmplId option").remove();
                $("#add_tmplId").append("<option value='-1'>请选择</option>");


                for (var i = 0; i < tmplData.length; i++) {
                    var tmplId = tmplData[i].tmplId;
                    var tmplName = tmplData[i].tmplName;

                    $("#add_tmplId").append("<option value=" + tmplId +
                        ">" + tmplName + "</option>");
                }


            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });
});

//编辑影厅模态层关闭事件
$("#updateRoom").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#update_roomName").val("");
    $("#update_roomId").val("");
    $("#update_tmplId option").remove();
    $("#update_cinemaId option").remove();

});

//编辑影厅模态层显示事件
$("#updateRoom").on('show.bs.modal', function () {
    // 获取影院信息
    $.ajax({
        url: "/cinema/query",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "perPage": 1000,
            "page": 1
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var cinemaInfo = result.content.cinemaInfo;
                var cinemaData = cinemaInfo.pageData;
                $("#update_cinemaId option").remove();
                $("#update_cinemaId").append("<option value='-1'>请选择</option>");
                for (var i = 0; i < cinemaData.length; i++) {
                    var cinemaId = cinemaData[i].cinemaId;
                    var cinemaName = cinemaData[i].cinemaName;
                    $("#update_cinemaId").append("<option value=" + cinemaId +
                        ">" + cinemaName + "</option>");
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });

    // 获取影厅模板
    $.ajax({
        url: "/tmpl/query",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "perPage": 1000,
            "page": 1
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var tmplInfo = result.content.tmplInfo;
                var tmplData = tmplInfo.pageData;

                $("#update_tmplId option").remove();
                $("#update_tmplId").append("<option value='-1'>请选择</option>");


                for (var i = 0; i < tmplData.length; i++) {
                    var tmplId = tmplData[i].tmplId;
                    var tmplName = tmplData[i].tmplName;

                    $("#update_tmplId").append("<option value=" + tmplId +
                        ">" + tmplName + "</option>");
                }


            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });
});

// 查询
function queryRoomList() {
    var searchRoomName = $("#search_room_name").val();
    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "roomName": searchRoomName,
            "perPage": pageSize,
            "page": currentPage
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var roomInfo = result.content.roomInfo;
                var roomData = roomInfo.pageData;

                //当前页
                currentPage = roomInfo.currentPage;
                window.parent.windowPage = currentPage;

                //总页数
                totalPage = Math.ceil(roomInfo.totalCount / pageSize);

                $("#page_info").text(currentPage + "/" + totalPage);

                totalCount = roomInfo.totalCount;

                $("#start_row").html((roomInfo.currentPage - 1) * pageSize + 1);
                var end_row = ((roomInfo.currentPage * pageSize) <= totalCount) ? roomInfo.currentPage * pageSize : totalCount;
                $("#end_row").html(end_row);

                $("#total_row_acc").html(totalCount);

                $("#room_info tbody tr").remove();

                for (var i = 0; i < roomData.length; i++) {
                    var roomId = roomData[i].roomId;
                    var roomName = roomData[i].roomName;
                    var cinemaId = roomData[i].cinemaId;
                    var cinemaName = roomData[i].cinemaName;
                    var tmplName = roomData[i].tmplName;
                    var totalSeat = roomData[i].totalSeat;
                    var tmplId = roomData[i].tmplId;

                    var id = "\"" + roomId + "\""
                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#room_info").append(
                        "<tr>" +
                        "<td>" + j + "</td>" +
                        "<td>" + cinemaName + "</td>" +
                        "<td>" + roomName + "</td>" +
                        "<td>" + totalSeat + "</td>" +
                        "<td>" +
                        "<div class='btn-group' id='toggle_switch'>" +

                        "<button type='button' class='btn btn-primary' style='margin-left:5px' " +
                        "onclick=infoRoom(" + id + ")>" +
                        "<i class='fa fa-edit'>编辑</i></button>" +

                        "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                        "onclick=delRoom(" + id + ")>" +
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
                $("#room_info tbody tr").remove();
                $("#room_info").append(
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
function infoRoom(id) {
    $('#updateRoom').modal('show');
    var params = "/"+id;
    $.ajax({
        url : resourceUrl + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                var data = result.content.roomInfo;
                var roomId = data.roomId;
                var roomName = data.roomName;
                var cinemaId = data.cinemaId;
                var tmplId = data.tmplId;


                $("#update_roomId").val(roomId);
                $("#update_roomName").val(roomName);
                $("#update_cinemaId").val(cinemaId);
                $("#update_tmplId").val(tmplId);


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
function delRoom(id) {
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
                    queryRoomList();
                },
                error : function () {
                    swal("网络异常，请稍后重试！", "", "error");
                }
            });
        });
}

// 添加
function addRoom() {
    var roomName = $("#add_roomName").val();
    var cinemaId = $("#add_cinemaId").val();
    var tmplId = $("#add_tmplId").val();

    if(roomName == null || roomName == "") {
        swal("请输入影厅名称！", "", "error");
        return;
    }
    if( cinemaId == null ||cinemaId == "-1" || cinemaId == -1 ) {
        swal("请选择影院！", "", "error");
        return;
    }

    if( tmplId == null ||tmplId == "-1" || tmplId == -1 ) {
        swal("请选择影厅模板！", "", "error");
        return;
    }

    $.ajax({
        url: resourceUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "roomName" : roomName,
            "cinemaId" : cinemaId,
            "tmplId" : tmplId
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影厅添加成功",//影厅添加成功
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryRoomList();
                        $('#addRoom').modal('hide');
                    });
            } else {
                swal("影厅添加失败", result.repMsg, "error");//添加失败
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

// 更新
function updateRoom() {
    var roomId =  $("#update_roomId").val();
    var roomName = $("#update_roomName").val();
    var tmplId = $("#update_tmplId").val();
    var cinemaId = $("#update_cinemaId").val();


    if(roomId == null || roomId == "") {
        swal("出现错误,ID为空，请重新刷新！", "", "error");
        return;
    }

    if(roomName == null || roomName == "") {
        swal("请输入影厅名称！", "", "error");
        return;
    }
    if( cinemaId == null ||cinemaId == "-1" || cinemaId == -1 ) {
        swal("请选择影院！", "", "error");
        return;
    }

    if( tmplId == null ||tmplId == "-1" || tmplId == -1 ) {
        swal("请选择影厅模板！", "", "error");
        return;
    }
    var params = "/"+roomId;
    $.ajax({
        url: resourceUrl + params,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "roomId" : roomId,
            "roomName" : roomName,
            "cinemaId" : cinemaId,
            "tmplId" : tmplId
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影厅更新成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryRoomList();
                        $('#updateRoom').modal('hide');
                    });
            } else {
                swal("影厅更新失败", result.repMsg, "error");
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", result.repMsg, "error");
        }
    });


}

//清空内容
function clean() {
    $("#search_room_name").val("");
}


//分页
$("#home_page").click(function firstPage() {    // 首页
    currentPage = 1;
    $("#room_info tbody tr").remove();
    queryRoomList();
});
$("#previous_page").click(function frontPage() {    // 上一页
    if (currentPage <= 1) {
        currentPage = 1;
    } else {
        currentPage--;
    }
    $("#room_info tbody tr").remove();
    queryRoomList();
});
$("#next_page").click(function nextPage() {    // 下一页
    if (currentPage >= totalPage) {
        currentPage = totalPage;
    } else {
        currentPage++;
    }
    $("#room_info tbody tr").remove();
    queryRoomList();
});
$("#trailer_page").click(function lastPage() {    // 尾页
    currentPage = totalPage;
    $("#room_info tbody tr").remove();
    queryRoomList();
});

$("#page_size").change(function lastPage() {    // 每页显示记录数改变
    pageSize = $("#page_size").val();
    $("#room_info tbody tr").remove();
    queryRoomList();
});