var num = "";

function tableClick(no) {
    $("table tbody tr").css("background-color", "white");
    console.log("선택:" + no);
    num = no;
    $("#" + no).css("background-color", "lightgray");
}

$(document).ready(function () {
    $.ajax({
        url: "getAllTopic",
        success: function (result) {
            console.log(result);
            $("#listArea").empty();
            let html = "";
            result.forEach(function (item) {
                html += "<tr onclick='tableClick(" + item.id + ")' id='" + item.id + "'> <td>" + item.topicName + "</td><td>"
                    + item.monitoringName + "</td><td>" + item.ip + "</td><td>" + item.port + "</td><td>" + item.status + "</td></tr>";
            })
            $("#listArea").append(html);
        }
    })

    $("#new").click(function () {
        $("#popup_layer_new").css("display", "block");
        $("form").submit(function (e) {
            e.preventDefault(); // avoid to execute the actual submit of the form.

            if ($("#inputTopic").val().trim() == "" || $("#inputMonitoring").val().trim() == ""
                || $("#inputIP").val().trim() == "" || $("#inputPort").val().trim() == "") {
                alert("값을 입력해주세요.");
            } else {
                let form = $(this);
                $.ajax({
                    type: "POST",
                    url: "saveTopic",
                    data: form.serialize(), // serializes the form's elements.
                    success: function (result) {
                        console.log(result);
                        $("#popup_layer_new").css("display", "none");
                        $("#page").load("TopicSetting");
                    },
                    error: function (error) {
                        console.log("실패:" + error.status);
                        if (error.status == 500) {
                            alert("이미 존재하는 토픽명입니다.");
                        }
                    }
                });
            }
        });

    });
    $("#delete").click(function () {
        if (num == "") {
            alert("항목을 선택해주세요.");
        } else {
            $.ajax({
                url: "getTopic/" + num,
                type: "GET",
                success: function (result) {
                    console.log(result);
                    if (result.status == "Running") {
                        alert("구독 중에는 삭제할 수 없습니다.");
                    } else {
                        $.ajax({
                            url: "delete/" + num,
                            type: "DELETE",
                            success: function (result) {
                                console.log(result);
                                alert("삭제되었습니다.");
                                $("#page").load("TopicSetting");
                            }
                        })
                    }
                }
            })
        }
    });
    $("#edit").click(function () {
        if (num == "") {
            alert("항목을 선택해주세요.");
        } else {
            $.ajax({
                url: "getTopic/" + num,
                type: "GET",
                success: function (result) {
                    console.log(result);
                    if (result.status == "Running") {
                        alert("구독 중에는 편집할 수 없습니다.");
                    } else {
                        $("#popup_layer_edit").css("display", "block");
                        $.ajax({
                            url: "getTopic/" + num,
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
                            } else {
                                let form = $(this);

                                $.ajax({
                                    type: "POST",
                                    url: "edit/" + num,
                                    data: form.serialize(), // serializes the form's elements.
                                    success: function (result) {
                                        console.log(result);
                                        $("#popup_layer_edit").css("display", "none");
                                        $("#page").load("TopicSetting");
                                    },
                                    error: function (error) {
                                        console.log("실패:" + error.status);
                                        if (error.status == 500) {
                                            alert("이미 존재하는 토픽명입니다.");
                                        }
                                    }
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
