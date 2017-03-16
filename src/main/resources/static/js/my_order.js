var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/order/query";
var resourceUrl = "/order";

// 查询
function queryOrderList() {
    // var searchOrderName = $("#search_order_name").val();
    var currentUserId = $("#currentUserId").val();
    if (currentUserId == null || currentUserId == "") {
        return;
    }

    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            // "orderName": searchOrderName,
            "userId" : currentUserId,
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
                    var scheduleId = orderData[i].scheduleId;
                    var userId = orderData[i].userId;
                    var username = orderData[i].username;
                    var movieName = orderData[i].movieName;
                    var orderStatus = orderData[i].orderStatus;
                    var seats = orderData[i].seats;
                    var cinemaName = orderData[i].cinemaName;
                    var roomName = orderData[i].roomName;
                    var bTime = orderData[i].bTime;
                    var amount = orderData[i].amount;
                    var beginTime = orderData[i].beginTime;
                    var id = "\"" + orderId + "\"";


                    var option = "<div class='btn-group' id='toggle_switch'>" ;





                    switch (orderStatus) {
                        case 1:
                            orderStatus = "待付款";
                            option += "<button type='button' class='btn btn-primary' style='margin-left:5px' " +
                                "onclick=payShow(" + id + ")>" +
                                "<i class='fa fa-edit'>支付</i></button>" +

                                "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                                "onclick=closeOrder(" + id + ")>" +
                                "<i class='fa fa-trash-o'></i>关闭订单</button>";
                            break;
                        case 2:
                            orderStatus = "已完成";
                            break;
                        case 3:
                            orderStatus = "已关闭";
                            break;
                        case 4:
                            orderStatus = "错误订单";
                            break;
                    }
                    option +="</div>";

                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#order_info").append(
                        "<tr>" +
                        "<td>" + j + "</td>" +
                        "<td>" + cinemaName + "</td>" +
                        "<td>" + roomName + "</td>" +
                        "<td>" + movieName + "</td>" +
                        "<td>" + new Date(beginTime).Format("yyyy-MM-dd hh:mm:ss") + "</td>" +
                        "<td>" + seats + "</td>" +
                        "<td>" + amount + "</td>" +
                        "<td>" + orderStatus + "</td>" +
                        // "<td>" + info +"</td>" +
                        "<td>" + option +  "</td>" +
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
                    "    <h3 class=text-center style='color:#777'>没有订单！</h3>" +
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

// 更新
function updateOrder() {
    var orderId =  $("#update_orderId").val();
    var scheduleId = $("#update_scheduleId").val();
    var userId =  $("#update_userId").val();
    var seats = $("#update_seats").val();
    var orderStatus = $("#update_orderStatus").val();
    var amount = $("#update_amount").val();

    if(scheduleId == null ||  scheduleId == "" || scheduleId == "-1") {
        swal("请选择排片计划！", "", "error");
        return;
    }


    if(orderId == null ||  orderId == "" ) {
        swal("ID错误！", "", "error");
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

    if(amount == null || amount == "") {
        swal("请输入付款金额！", "", "error");
        return;
    }
    var params = "/"+orderId;
    $.ajax({
        url: resourceUrl + params,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "orderId" : orderId,
            "scheduleId" : scheduleId,
            "userId" : userId,
            "seats" : seats,
            "amount" : amount,
            "orderStatus" : orderStatus
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

// 显示支付页面
function payShow(id) {
    $("#payMethod").modal('show');
}

// 关闭订单
function closeOrder(id) {
    var params = "/"+id;
    $.ajax({
        url : resourceUrl +"/close" + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                swal(result.repMsg, "", "info");

            }else{
                swal(result.repMsg, "", "error");
            }
            queryOrderList();
        },
        error : function () {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });
}


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