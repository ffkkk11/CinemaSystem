var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/schedule/query";
var resourceUrl = "/schedule";

//添加排片模态层关闭事件
$("#addSchedule").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#add_beginTime").val("");
    $("#add_price").val("");
});

//添加排片模态层显示事件
$("#addSchedule").on('show.bs.modal', function () {
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
            $("#add_cinemaId option").remove();
            if (result.repCode == 1) {
                var cinemaInfo = result.content.cinemaInfo;
                var cinemaData = cinemaInfo.pageData;
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

    // 获取影片信息
    $.ajax({
        url: "/movie/query",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "perPage": 1000,
            "page": 1
        }),
        success: function (result) {

            $("#add_movieId option").remove();
            $("#add_movieId").append("<option value='-1'>请选择</option>");
            if (result.repCode == 1) {
                var movieInfo = result.content.movieInfo;
                var movieData = movieInfo.pageData;

                for (var i = 0; i < movieData.length; i++) {
                    var movieId = movieData[i].movieId;
                    var movieName = movieData[i].movieName;

                    $("#add_movieId").append("<option value=" + movieId +
                        ">" + movieName + "</option>");
                }
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });
});


//编辑排片模态层关闭事件
$("#updateSchedule").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#update_price").val("");
});

// 查询
function queryScheduleList() {
    // var searchScheduleName = $("#search_schedule_name").val();
    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            // "scheduleName": searchScheduleName,
            "perPage": pageSize,
            "page": currentPage
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var scheduleInfo = result.content.scheduleInfo;
                var scheduleData = scheduleInfo.pageData;

                //当前页
                currentPage = scheduleInfo.currentPage;
                window.parent.windowPage = currentPage;

                //总页数
                totalPage = Math.ceil(scheduleInfo.totalCount / pageSize);

                $("#page_info").text(currentPage + "/" + totalPage);

                totalCount = scheduleInfo.totalCount;

                $("#start_row").html((scheduleInfo.currentPage - 1) * pageSize + 1);
                var end_row = ((scheduleInfo.currentPage * pageSize) <= totalCount) ? scheduleInfo.currentPage * pageSize : totalCount;
                $("#end_row").html(end_row);

                $("#total_row_acc").html(totalCount);

                $("#schedule_info tbody tr").remove();

                for (var i = 0; i < scheduleData.length; i++) {
                    var scheduleId = scheduleData[i].scheduleId;
                    var beginTime = scheduleData[i].beginTime;
                    var roomId = scheduleData[i].roomId;
                    var roomName = scheduleData[i].roomName;
                    var cinemaId = scheduleData[i].cinemaId;
                    var cinemaName = scheduleData[i].cinemaName;
                    var movieId = scheduleData[i].movieId;
                    var movieName = scheduleData[i].movieName;
                    var price = scheduleData[i].price;
                    var status =  scheduleData[i].status ;
                    var id = "\"" + scheduleId + "\"";

                    status = status == true ? "上映" : "下映";

                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#schedule_info").append(
                        "<tr>" +
                        "<td>" + j + "</td>" +
                        "<td>" + cinemaName + "</td>" +
                        "<td>" + roomName + "</td>" +
                        "<td>" + movieName + "</td>" +
                        "<td>" + new Date(beginTime).Format("yyyy-MM-dd hh:mm:ss") + "</td>" +
                        "<td>" + price + "</td>" +
                        "<td>" + status + "</td>" +
                        // "<td>" + info +"</td>" +
                        "<td>" +
                        "<div class='btn-group' id='toggle_switch'>" +

                        "<button type='button' class='btn btn-primary' style='margin-left:5px' " +
                        "onclick=infoSchedule(" + id + ")>" +
                        "<i class='fa fa-edit'>编辑</i></button>" +

                        "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                        "onclick=delSchedule(" + id + ")>" +
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
                $("#schedule_info tbody tr").remove();
                $("#schedule_info").append(
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
function infoSchedule(id) {
    $('#updateSchedule').modal('show');
    var params = "/"+id;
    $.ajax({
        url : resourceUrl + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                var data = result.content.scheduleInfo;
                var scheduleId = data.scheduleId;
                var beginTime = data.beginTime;
                var roomId = data.roomId;
                var roomName = data.roomName;
                var cinemaId = data.cinemaId;
                var cinemaName = data.cinemaName;
                var movieId = data.movieId;
                var movieName = data.movieName;
                var price = data.price;
                var status =  data.status ;

                status = status ? 1 : 0;

                $("#update_roomId option").remove();
                $("#update_roomId").append("<option value=" + roomId +
                    ">" + roomName + "</option>");

                $("#update_cinemaId option").remove();
                $("#update_cinemaId").append("<option value=" + cinemaId +
                    ">" + cinemaName + "</option>");

                $("#update_movieId option").remove();
                $("#update_movieId").append("<option value=" + movieId +
                    ">" + movieName + "</option>");

                $("#update_scheduleId").val(scheduleId);
                $("#update_price").val(price);
                $("#update_status").val(status);
                $("#update_beginTime").val(new Date(beginTime).Format("yyyy-MM-ddThh:mm:ss"));


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
function delSchedule(id) {
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
                    queryScheduleList();
                },
                error : function () {
                    swal("网络异常，请稍后重试！", "", "error");
                }
            });
        });
}

// 添加
function addSchedule() {

    var roomId = $("#add_roomId").val();
    var beginTime = new Date($("#add_beginTime").val().replace('T',' ').replace('-','/')).getTime();
    var movieId = $("#add_movieId").val();

    var price =  $("#add_price").val();
    var status = $("#add_status").val();

    status = status == 1 ? true : false;

    if(roomId == null ||  roomId == "" || roomId == "-1") {
        swal("请选择影厅！", "", "error");
        return;
    }
    if(beginTime == null || beginTime == "" ) {
        swal("请选择播放时间！", "", "error");
        return;
    }
    if(price == null || price == ""){
        swal("请输入价格！", "", "error");
        return;
    }
    if( movieId == null || movieId == "-1") {
        swal("请选择要播放的影片！", "", "error");
        return;
    }

    if(status && beginTime < new Date().getTime()) {
        swal("影片开始时间要在当前时间之后！", "", "error");
        return;
    }

    $.ajax({
        url: resourceUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "roomId" : roomId,
            "beginTime" : beginTime,
            "movieId" : movieId,
            "price" : price,
            "status" : status
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "场次添加成功",//场次添加成功
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryScheduleList();
                        $('#addSchedule').modal('hide');
                    });
            } else {
                swal("场次添加失败", "", "error");//添加失败
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

// 更新
function updateSchedule() {
    var scheduleId =  $("#update_scheduleId").val();
    var roomId = $("#update_roomId").val();
    var beginTime = new Date($("#update_beginTime").val().replace('T',' ').replace('-','/')).getTime();
    var movieId = $("#update_movieId").val();

    var price =  $("#update_price").val();
    var status = $("#update_status").val();

    status = status == 1 ? true : false;

    if(roomId == null ||  roomId == "" || roomId == "-1") {
        swal("请选择影厅！", "", "error");
        return;
    }
    if(beginTime == null || beginTime == "" ) {
        swal("请选择播放时间！", "", "error");
        return;
    }
    if(price == null || price == ""){
        swal("请输入价格！", "", "error");
        return;
    }
    if( movieId == null || movieId == "-1") {
        swal("请选择要播放的影片！", "", "error");
        return;
    }

    if(status && beginTime < new Date().getTime()) {
        swal("影片开始时间要在当前时间之后！", "", "error");
        return;
    }
    var params = "/"+scheduleId;
    $.ajax({
        url: resourceUrl + params,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "roomId" : roomId,
            "beginTime" : beginTime,
            "movieId" : movieId,
            "price" : price,
            "status" : status
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "排片更新成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryScheduleList();
                        $('#updateSchedule').modal('hide');
                    });
            } else {
                swal("排片更新失败", "", "error");
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

//清空内容
function clean() {
    $("#search_schedule_name").val("");
}

//添加时显示影院下面的影厅
$("#add_cinemaId").change(
    function () {
        var cinemaId = $("#add_cinemaId").val();
        if(cinemaId == "-1") {
            return;
        }
        $.ajax({
            url: "/room/query",
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                "cinemaId": cinemaId,
                "perPage": 1000,
                "page": 1
            }),
            success: function (result) {
                $("#add_roomId option").remove();
                $("#add_roomId").append("<option value='-1'>请选择</option>");

                if (result.repCode == 1) {
                    var roomInfo = result.content.roomInfo;

                    var roomData = roomInfo.pageData;
                    for (var i = 0; i < roomData.length; i++) {
                        var roomId = roomData[i].roomId;
                        var roomName = roomData[i].roomName;

                        $("#add_roomId").append("<option value=" + roomId +
                            ">" + roomName + "</option>");
                    }


                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                swal("网络异常，请稍后重试！", "", "error");
            }
        });
    }
);

//分页
$("#home_page").click(function firstPage() {    // 首页
    currentPage = 1;
    $("#schedule_info tbody tr").remove();
    queryScheduleList();
});
$("#previous_page").click(function frontPage() {    // 上一页
    if (currentPage <= 1) {
        currentPage = 1;
    } else {
        currentPage--;
    }
    $("#schedule_info tbody tr").remove();
    queryScheduleList();
});
$("#next_page").click(function nextPage() {    // 下一页
    if (currentPage >= totalPage) {
        currentPage = totalPage;
    } else {
        currentPage++;
    }
    $("#schedule_info tbody tr").remove();
    queryScheduleList();
});
$("#trailer_page").click(function lastPage() {    // 尾页
    currentPage = totalPage;
    $("#schedule_info tbody tr").remove();
    queryScheduleList();
});

$("#page_size").change(function lastPage() {    // 每页显示记录数改变
    pageSize = $("#page_size").val();
    $("#schedule_info tbody tr").remove();
    queryScheduleList();
});