var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/order/query";
var resourceUrl = "/order";

//添加订单模态层关闭事件
$("#addOrder").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#add_seats").val("");
});

//添加订单模态层显示事件
$("#addOrder").on('show.bs.modal', function () {
    //获取排片信息
    $.ajax({
        url: "/schedule/new",
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        success: function (result) {
            if (result.repCode == 1) {
                var scheduleInfo = result.content.scheduleInfo;

                $("#add_scheduleId option").remove();
                $("#add_scheduleId").append("<option value='-1'>请选择</option>");


                for (var i = 0; i < scheduleInfo.length; i++) {
                    var scheduleId = scheduleInfo[i].scheduleId;
                    var beginTime = scheduleInfo[i].beginTime;
                    var bTime = scheduleInfo[i].bTime;
                    var roomId = scheduleInfo[i].roomId;
                    var roomName = scheduleInfo[i].roomName;
                    var cinemaId = scheduleInfo[i].cinemaId;
                    var cinemaName = scheduleInfo[i].cinemaName;
                    var movieId = scheduleInfo[i].movieId;
                    var movieName = scheduleInfo[i].movieName;
                    var price = scheduleInfo[i].price;
                    var status =  scheduleInfo[i].status ;
                    $("#add_scheduleId").append("<option value='" + scheduleId +"'>" + cinemaName + "," + roomName + ","+ movieName + ","+ bTime +"</option>");


                }
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });

    //获取用户信息
    $.ajax({
        url:  "/user/query",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "perPage": 1000,
            "page": 1
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var userInfo = result.content.userInfo;
                var userData = userInfo.pageData;

                $("#add_userId option").remove();
                $("#add_userId").append("<option value='-1'>选择用户</option>");

                for (var i = 0; i < userData.length; i++) {
                    var userId = userData[i].userId;
                    var username = userData[i].username;
                    $("#add_userId").append("<option value='" + userId+"'>" + username+"</option>");
                }

            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });
});


//编辑订单模态层关闭事件
$("#updateOrder").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#update_seats").val("");
});

// 查询
function queryOrderList() {
    // var searchOrderName = $("#search_order_name").val();
    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            // "orderName": searchOrderName,
            "perPage": pageSize,
            "page": currentPage
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var orderInfo = result.content.orderInfo;
                var orderData = orderInfo.pageData;

                //当前页
                currentPage = orderInfo.currentPage;
                window.parent.windowPage = currentPage;

                //总页数
                totalPage = Math.ceil(orderInfo.totalCount / pageSize);

                $("#page_info").text(currentPage + "/" + totalPage);

                totalCount = orderInfo.totalCount;

                $("#start_row").html((orderInfo.currentPage - 1) * pageSize + 1);
                var end_row = ((orderInfo.currentPage * pageSize) <= totalCount) ? orderInfo.currentPage * pageSize : totalCount;
                $("#end_row").html(end_row);

                $("#total_row_acc").html(totalCount);

                $("#order_info tbody tr").remove();

                for (var i = 0; i < orderData.length; i++) {
                    var orderId = orderData[i].orderId;
                    var beginTime = orderData[i].beginTime;
                    var roomId = orderData[i].roomId;
                    var roomName = orderData[i].roomName;
                    var cinemaId = orderData[i].cinemaId;
                    var cinemaName = orderData[i].cinemaName;
                    var movieId = orderData[i].movieId;
                    var movieName = orderData[i].movieName;
                    var price = orderData[i].price;
                    var status =  orderData[i].status ;
                    var id = "\"" + orderId + "\"";

                    status = status == true ? "上映" : "下映";

                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#order_info").append(
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
                        "onclick=infoOrder(" + id + ")>" +
                        "<i class='fa fa-edit'>编辑</i></button>" +

                        "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                        "onclick=delOrder(" + id + ")>" +
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
                $("#order_info tbody tr").remove();
                $("#order_info").append(
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
function infoOrder(id) {
    $('#updateOrder').modal('show');
    var params = "/"+id;
    $.ajax({
        url : resourceUrl + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                var data = result.content.orderInfo;
                var orderId = data.orderId;
                var scheduleId = data.scheduleId;
                var userId = data.userId;
                var userName = data.userName;
                var movieName = data.movieName;
                var orderStatus = data.orderStatus;
                var seats = data.seats;


                //TODO lalaa
                $("#update_roomId option").remove();
                $("#update_roomId").append("<option value=" + roomId +
                    ">" + roomName + "</option>");

                $("#update_cinemaId option").remove();
                $("#update_cinemaId").append("<option value=" + cinemaId +
                    ">" + cinemaName + "</option>");

                $("#update_movieId option").remove();
                $("#update_movieId").append("<option value=" + movieId +
                    ">" + movieName + "</option>");

                $("#update_orderId").val(orderId);
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
function delOrder(id) {
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
                    queryOrderList();
                },
                error : function () {
                    swal("网络异常，请稍后重试！", "", "error");
                }
            });
        });
}

// 添加
function addOrder() {

    var scheduleId = $("#add_scheduleId").val();
    var userId =  $("#add_userId").val();
    var seats = $("#add_seats").val();
    var orderStatus = $("#add_orderStatus").val();


    if(scheduleId == null ||  scheduleId == "" || scheduleId == "-1") {
        swal("请选择排片计划！", "", "error");
        return;
    }
    if(userId == null || userId == "" ) {
        swal("请选择用户！", "", "error");
        return;
    }
    if(seats == null || seats == ""){
        swal("请输入选座！", "", "error");
        return;
    }

    $.ajax({
        url: resourceUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "scheduleId" : scheduleId,
            "userId" : userId,
            "seats" : seats,
            "orderStatus" : orderStatus
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "订单添加成功",//订单添加成功
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryOrderList();
                        $('#addOrder').modal('hide');
                    });
            } else {
                swal("订单添加失败", "", "error");//添加失败
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

// 更新
function updateOrder() {
    var orderId =  $("#update_orderId").val();
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
    var params = "/"+orderId;
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
                        title: "订单更新成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryOrderList();
                        $('#updateOrder').modal('hide');
                    });
            } else {
                swal("订单更新失败", "", "error");
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

//清空内容
function clean() {
    $("#search_order_name").val("");
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
    $("#order_info tbody tr").remove();
    queryOrderList();
});
$("#previous_page").click(function frontPage() {    // 上一页
    if (currentPage <= 1) {
        currentPage = 1;
    } else {
        currentPage--;
    }
    $("#order_info tbody tr").remove();
    queryOrderList();
});
$("#next_page").click(function nextPage() {    // 下一页
    if (currentPage >= totalPage) {
        currentPage = totalPage;
    } else {
        currentPage++;
    }
    $("#order_info tbody tr").remove();
    queryOrderList();
});
$("#trailer_page").click(function lastPage() {    // 尾页
    currentPage = totalPage;
    $("#order_info tbody tr").remove();
    queryOrderList();
});

$("#page_size").change(function lastPage() {    // 每页显示记录数改变
    pageSize = $("#page_size").val();
    $("#order_info tbody tr").remove();
    queryOrderList();
});