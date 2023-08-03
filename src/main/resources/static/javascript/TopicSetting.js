$.ajax({
    url: "getAllTopic",
    success: function (result) {
        console.log(result);
        $("#listArea").empty();
        var html = "";
        result.forEach(function (item) {
            html += "<tr onclick='tableClick(" + item.id + ")' id='" + item.id + "'> <td>" + item.topicName + "</td><td>"
                + item.monitoringName + "</td><td>" + item.ip + "</td><td>" + item.port + "</td><td>" + item.status + "</td></tr>";
        })
        $("#listArea").append(html);
    }
})

var num = "";

function tableClick(no) {
    $("table tbody tr").css("background-color", "white");
    console.log(no);
    num = no;
    $("#" + no).css("background-color", "lightgray");
}

$(document).ready(function () {
    $("#new").click(function () {
        $("#popup_layer_new").css("display", "block");
        $("form").submit(function (e) {
            e.preventDefault(); // avoid to execute the actual submit of the form.

            if ($("#inputTopic").val().trim() == "" || $("#inputMonitoring").val().trim() == ""
                || $("#inputIP").val().trim() == "" || $("#inputPort").val().trim() == "") {
                alert("값을 입력해주세요.");
                e.preventDefault();
            } else {
                var form = $(this);
                $.ajax({
                    type: "POST",
                    url: "saveTopic",
                    data: form.serialize(), // serializes the form's elements.
                    dataType: 'json',
                    success: function (json) {
                        console.log("저장 성공");
                        $("#popup_layer_new").css("display", "none");
                        $("#page").load("TopicSetting");
                    },
                    error : function (error){
                        console.log(error);
                        if(error.status == 400){
                            alert("이미 존재하는 토픽명입니다.");
                        }
                    }
                });
            }
        });

    });
    $("#delete").click(function () {
        $.ajax({
            url: "delete/" + num,
            type: "GET",
            success: function (result) {
                alert("삭제되었습니다.");
                $("#page").load("TopicSetting");
            }
        })
    });
    $("#edit").click(function () {
        if (num == "") {
            alert("항목을 선택해주세요.");
        } else {
            $.ajax({
                url: "edit/" + num,
                type: "GET",
                success: function (result) {
                    console.log(result);
                    if(result.status=="Running"){
                        alert("구독 중에는 편집할 수 없습니다.");
                    }
                    else{
                        $("#popup_layer_edit").css("display", "block");
                        $.ajax({
                            url: "edit/" + num,
                            type: "GET",
                            success: function (result) {
                                console.log(result);
                                $("#editTopic").val(result.topicName);
                                $("#editMonitoring").val(result.monitoringName);
                                $("#editIP").val(result.ip);
                                $("#editPort").val(result.port);

                            }
                        })
                        $("form").submit(function (e) {
                            e.preventDefault(); // avoid to execute the actual submit of the form.

                            if ($("#editTopic").val().trim() == "" || $("#editMonitoring").val().trim() == ""
                                || $("#editIP").val().trim() == "" || $("#editPort").val().trim() == "") {
                                alert("값을 입력해주세요.");
                                e.preventDefault();
                            } else {
                                var form = $(this);

                                $.ajax({
                                    type: "POST",
                                    url: "edit/" + num,
                                    data: form.serialize(), // serializes the form's elements.
                                    dataType: 'json',
                                    success: function (json) {
                                        console.log("수정 성공");
                                        $("#popup_layer_edit").css("display", "none");
                                        $("#page").load("TopicSetting");
                                    }
                                    // ,
                                    // error: function(request,status,error){
                                    //     alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                                    // }
                                });
                            }
                        });
                    }
                }
            })
        }
    });
    $("#saveCancelBtn").click(function () {
        $("#popup_layer_new").css("display", "none");
    });
    $("#editCancelBtn").click(function () {
        $("#popup_layer_edit").css("display", "none");
    });
});
