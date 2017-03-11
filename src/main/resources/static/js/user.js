var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/user/query";
var resourceUrl = "/user";

//添加用户模态层关闭事件
$("#addUser").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#add_username").val("");
    $("#add_userPassword").val("");
    $("#add_userMobile").val("");
});

//编辑用户模态层关闭事件
$("#updateUser").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#update_username").val("");
    $("#update_userId").val("");
    $("#update_userPassword").val("");
    $("#update_userMobile").val("");
});

// 查询
function queryUserList() {
    var searchUserName = $("#search_user_name").val();
    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "username": searchUserName,
            "perPage": pageSize,
            "page": currentPage
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var userInfo = result.content.userInfo;
                var userData = userInfo.pageData;

                //当前页
                currentPage = userInfo.currentPage;
                window.parent.windowPage = currentPage;

                //总页数
                totalPage = Math.ceil(userInfo.totalCount / pageSize);

                $("#page_info").text(currentPage + "/" + totalPage);

                totalCount = userInfo.totalCount;

                $("#start_row").html((userInfo.currentPage - 1) * pageSize + 1);
                var end_row = ((userInfo.currentPage * pageSize) <= totalCount) ? userInfo.currentPage * pageSize : totalCount;
                $("#end_row").html(end_row);

                $("#total_row_acc").html(totalCount);

                $("#user_info tbody tr").remove();

                for (var i = 0; i < userData.length; i++) {
                    var userId = userData[i].userId;
                    var username = userData[i].username;
                    var userMobile = userData[i].mobile;
                    var userRole = userData[i].role;
                    var id = "\"" + userId + "\"";

                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#user_info").append(
                        "<tr>" +
                        "<td>" + j + "</td>" +
                        "<td>" + username + "</td>" +
                        "<td>" + userMobile + "</td>" +
                        "<td>" + userRole + "</td>" +
                        "<td>" +
                        "<div class='btn-group' id='toggle_switch'>" +

                        "<button type='button' class='btn btn-primary' style='margin-left:5px' " +
                        "onclick=infoUser(" + id + ")>" +
                        "<i class='fa fa-edit'>编辑</i></button>" +

                        "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                        "onclick=delUser(" + id + ")>" +
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
                $("#user_info tbody tr").remove();
                $("#user_info").append(
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
function infoUser(id) {
    $('#updateUser').modal('show');
    var params = "/"+id;
    $.ajax({
        url : resourceUrl + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                var data = result.content.userInfo;
                var userId = data.userId;
                var username = data.username;
                var userPassword = data.password;
                var userMobile = data.mobile;
                var userRole = data.role;

                $("#update_userId").val(userId);
                $("#update_username").val(username);
                $("#update_userPassword").val(userPassword);
                $("#update_userMobile").val(userMobile);
                $("#update_userRole").val(userRole);

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
function delUser(id) {
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
                    queryUserList();
                },
                error : function () {
                    swal("网络异常，请稍后重试！", "", "error");
                }
            });
        });
}

// 添加
function addUser() {
    var username = $("#add_username").val();
    var userPassword = $("#add_userPassword").val();
    var userMobile = $("#add_userMobile").val();
    var userRole = $("#add_userRole").val();

    if(username == null || username == "") {
        swal("请输入用户名称！", "", "info");
        return;
    }
    if(userPassword == null || userPassword == "") {
        swal("请输入用户密码！", "", "info");
        return;
    }

    $.ajax({
        url: resourceUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "username" : username,
            "password" : userPassword,
            "mobile" : userMobile,
            "role" : userRole
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "用户添加成功",//用户添加成功
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryUserList();
                        $('#addUser').modal('hide');
                    });
            } else {
                swal("用户添加失败", result.repMsg, "error");//添加失败
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

// 更新
function updateUser() {
    var userId =  $("#update_userId").val();

    var username = $("#update_username").val();
    var userPassword = $("#update_userPassword").val();
    var userMobile = $("#update_userMobile").val();
    var userRole = $("#update_userRole").val();

    if(userId == null || userId == "") {
        swal("出现错误,ID为空，请重新刷新！", "", "error");
        return;
    }

    if(userName == null || userName == "") {
        swal("请输入用户名称！", "", "error");
        return;
    }
    var params = "/"+userId;
    $.ajax({
        url: resourceUrl + params,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "userId" : userId,
            "username" : username,
            "password" : userPassword,
            "mobile" : userMobile,
            "role" : userRole
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "用户更新成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryUserList();
                        $('#updateUser').modal('hide');
                    });
            } else {
                swal("用户更新失败", "", "error");
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

//清空内容
function clean() {
    $("#search_user_name").val("");
}


//分页
$("#home_page").click(function firstPage() {    // 首页
    currentPage = 1;
    $("#user_info tbody tr").remove();
    queryUserList();
});
$("#previous_page").click(function frontPage() {    // 上一页
    if (currentPage <= 1) {
        currentPage = 1;
    } else {
        currentPage--;
    }
    $("#user_info tbody tr").remove();
    queryUserList();
});
$("#next_page").click(function nextPage() {    // 下一页
    if (currentPage >= totalPage) {
        currentPage = totalPage;
    } else {
        currentPage++;
    }
    $("#user_info tbody tr").remove();
    queryUserList();
});
$("#trailer_page").click(function lastPage() {    // 尾页
    currentPage = totalPage;
    $("#user_info tbody tr").remove();
    queryUserList();
});

$("#page_size").change(function lastPage() {    // 每页显示记录数改变
    pageSize = $("#page_size").val();
    $("#user_info tbody tr").remove();
    queryUserList();
});