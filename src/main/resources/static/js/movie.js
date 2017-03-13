var pageSize = 5;//当前每页显示条数
var currentPage = window.parent.windowPage;//当前页数
var totalPage;//总页数
var totalCount;//总条数

var queryUrl = "/movie/query";
var resourceUrl = "/movie";

//添加影片模态层关闭事件
$("#addMovie").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#add_movieName").val("");
    $("#add_moviePic").val("");
    // $("#add_movieInfo").text("");
    addEditor.$txt.html('');
});

//编辑影片模态层关闭事件
$("#updateMovie").on('hide.bs.modal', function () {
    // 执行一些动作...
    $("#update_movieName").val("");
    $("#update_moviePic").val("");
    $("#update_movieId").val("");
    updateEditor.$txt.html('');
});

// 查询
function queryMovieList() {
    var searchMovieName = $("#search_movie_name").val();
    $.ajax({
        url: queryUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "movieName": searchMovieName,
            "perPage": pageSize,
            "page": currentPage
        }),
        success: function (result) {
            if (result.repCode == 1) {
                var movieInfo = result.content.movieInfo;
                var movieData = movieInfo.pageData;

                //当前页
                currentPage = movieInfo.currentPage;
                window.parent.windowPage = currentPage;

                //总页数
                totalPage = Math.ceil(movieInfo.totalCount / pageSize);

                $("#page_info").text(currentPage + "/" + totalPage);

                totalCount = movieInfo.totalCount;

                $("#start_row").html((movieInfo.currentPage - 1) * pageSize + 1);
                var end_row = ((movieInfo.currentPage * pageSize) <= totalCount) ? movieInfo.currentPage * pageSize : totalCount;
                $("#end_row").html(end_row);

                $("#total_row_acc").html(totalCount);

                $("#movie_info tbody tr").remove();

                for (var i = 0; i < movieData.length; i++) {
                    var movieId = movieData[i].movieId;
                    var movieName = movieData[i].movieName;
                    var moviePic = movieData[i].pic;
                    var movieInfo = movieData[i].info;
                    var id = "\"" + movieId + "\"";

                    movieInfo = movieInfo.substring(0, 40) + "...";

                    //序号
                    var j = (currentPage - 1) * pageSize + i + 1;

                    $("#movie_info").append(
                        "<tr>" +
                        "<td>" + j + "</td>" +
                        "<td>" + movieName + "</td>" +
                        "<td>" + moviePic + "</td>" +
                        "<td>" + movieInfo + "</td>" +
                        "<td>" +
                        "<div class='btn-group' id='toggle_switch'>" +

                        "<button type='button' class='btn btn-primary' style='margin-left:5px' " +
                        "onclick=infoMovie(" + id + ")>" +
                        "<i class='fa fa-edit'>编辑</i></button>" +

                        "<button type='button' class='btn  btn-danger' style='margin-left:5px' " +
                        "onclick=delMovie(" + id + ")>" +
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
                $("#movie_info tbody tr").remove();
                $("#movie_info").append(
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
function infoMovie(id) {
    $('#updateMovie').modal('show');
    var params = "/"+id;
    $.ajax({
        url : resourceUrl + params,
        type : "GET",
        dataType : 'json',
        success : function (result) {
            if(result.repCode == 1){
                var data = result.content.movieInfo;
                var movieId = data.movieId;
                var movieName = data.movieName;
                var moviePic = data.pic;
                var movieInfo = data.info;

                $("#update_movieId").val(movieId);
                $("#update_movieName").val(movieName);
                $("#update_moviePic").val(moviePic);
                updateEditor.$txt.html(movieInfo);

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
function delMovie(id) {
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
                    queryMovieList();
                },
                error : function () {
                    swal("网络异常，请稍后重试！", "", "error");
                }
            });
        });
}

// 添加
function addMovie() {
    var movieName = $("#add_movieName").val();
    var moviePic = $("#add_moviePic").val();
    var movieInfo = addEditor.$txt.html();

    if(movieName == null || movieName == "") {
        swal("请输入影片名称！", "", "info");
        return;
    }
    $.ajax({
        url: resourceUrl,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "movieName" : movieName,
            "pic" : moviePic,
            "info" : movieInfo
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影片添加成功",//影片添加成功
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryMovieList();
                        $('#addMovie').modal('hide');
                    });
            } else {
                swal("影片添加失败", "", "error");//添加失败
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

// 更新
function updateMovie() {
    var movieId =  $("#update_movieId").val();
    var movieName = $("#update_movieName").val();
    var moviePic = $("#update_moviePic").val();
    var movieInfo = updateEditor.$txt.html();

    if(movieId == null || movieId == "") {
        swal("出现错误,ID为空，请重新刷新！", "", "error");
        return;
    }

    if(movieName == null || movieName == "") {
        swal("请输入影片名称！", "", "error");
        return;
    }
    var params = "/"+movieId;
    $.ajax({
        url: resourceUrl + params,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            "movieId" : movieId,
            "movieName" : movieName,
            "pic" : moviePic,
            "info" : movieInfo
        }),
        success: function (result) {
            if (result.repCode == "1") {
                swal({
                        title: "影片更新成功",
                        type: "success",
                        confirmButtonText: "确定"
                    },
                    function () {
                        queryMovieList();
                        $('#updateMovie').modal('hide');
                    });
            } else {
                swal("影片更新失败", "", "error");
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("网络异常，请稍后重试！", "", "error");
        }
    });


}

//清空内容
function clean() {
    $("#search_movie_name").val("");
}


//分页
$("#home_page").click(function firstPage() {    // 首页
    currentPage = 1;
    $("#movie_info tbody tr").remove();
    queryMovieList();
});
$("#previous_page").click(function frontPage() {    // 上一页
    if (currentPage <= 1) {
        currentPage = 1;
    } else {
        currentPage--;
    }
    $("#movie_info tbody tr").remove();
    queryMovieList();
});
$("#next_page").click(function nextPage() {    // 下一页
    if (currentPage >= totalPage) {
        currentPage = totalPage;
    } else {
        currentPage++;
    }
    $("#movie_info tbody tr").remove();
    queryMovieList();
});
$("#trailer_page").click(function lastPage() {    // 尾页
    currentPage = totalPage;
    $("#movie_info tbody tr").remove();
    queryMovieList();
});

$("#page_size").change(function lastPage() {    // 每页显示记录数改变
    pageSize = $("#page_size").val();
    $("#movie_info tbody tr").remove();
    queryMovieList();
});