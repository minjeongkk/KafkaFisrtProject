// 표에서 선택한 토픽의 고유 id
var num = "";

// 선택한 항목 id 저장, 클릭 효과
function tableClick(no) {
    $("table tbody tr").css("background-color", "white");
    console.log("선택:" + no);
    num = no;
    $("#" + no).css("background-color", "lightgray");
}

$(document).ready(function () {
    // 전체 토픽 목록 조회
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

    // 토픽 생성 팝업창 띄움
    $("#new").click(function () {
        $("#popup_layer_new").css("display", "block");

    });

    // 항목을 누르고 수정 버튼을 누르면 수정 팝업창이 뜸
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

                        // 기존값 불러옴
                        $("#editTopic").val(result.topicName);
                        $("#editMonitoring").val(result.monitoringName);
                        $("#editIP").val(result.ip);
                        $("#editPort").val(result.port);
                    }
                }
            })
        }
    });

    // 항목을 누르고 삭제 버튼 누르면 지워짐
    $("#delete").click(function () {
        if (num == "") {
            alert("항목을 선택해주세요.");
        } else {
            if (confirm("삭제하시겠습니까?")) {
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
        }
    });

    // 토픽 저장
    $("#saveBtn").click(function () {
        if (confirm("저장하시겠습니까?")) {
            // 빈칸이 있을 때 예외처리
            if ($("#inputTopic").val().trim() == "" || $("#inputMonitoring").val().trim() == ""
                || $("#inputIP").val().trim() == "" || $("#inputPort").val().trim() == "") {
                alert("값을 입력해주세요.");
            } else {
                $.ajax({
                    type: "POST",
                    url: "saveTopic",
                    data: {
                        topicName: $("#inputTopic").val(),
                        monitoringName: $("#inputMonitoring").val(),
                        ip: $("#inputIP").val(),
                        port: $("#inputPort").val()
                    },
                    success: function (result) {
                        console.log(result);
                        $("#popup_layer_new").css("display", "none");
                        $("#page").load("TopicSetting");
                    },
                    error: function (error) {
                        console.log("실패:" + error.status);
                        // 토픽명 중복 시 예외처리
                        if (error.status == 500) {
                            alert("이미 존재하는 토픽명입니다.");
                        }
                    }
                })
            }
        }
    });

    // 토픽 수정
    $("#editBtn").click(function () {
        if (confirm("수정하시겠습니까?")) {
            // 빈칸이 있을 때 예외처리
            if ($("#editTopic").val().trim() == "" || $("#editMonitoring").val().trim() == ""
                || $("#editIP").val().trim() == "" || $("#editPort").val().trim() == "") {
                alert("값을 입력해주세요.");
            } else {
                $.ajax({
                    type: "POST",
                    url: "edit/"+num,
                    data: {
                        topicName: $("#editTopic").val(),
                        monitoringName: $("#editMonitoring").val(),
                        ip: $("#editIP").val(),
                        port: $("#editPort").val()
                    },
                    success: function (result) {
                        console.log(result);
                        $("#popup_layer_edit").css("display", "none");
                        $("#page").load("TopicSetting");
                    },
                    error: function (error) {
                        console.log("실패:" + error.status);
                        // 토픽명 중복 시 예외처리
                        if (error.status == 500) {
                            alert("이미 존재하는 토픽명입니다.");
                        }
                    }
                })
            }
        }
    });

    // 토픽 생성 팝업창에 취소 버튼 누르면 팝업창 닫힘
    $("#saveCancelBtn").click(function () {
        $("#popup_layer_new").css("display", "none");
    });

    // 토픽 수정 팝업창에 취소 버튼 누르면 팝업창 닫힘
    $("#editCancelBtn").click(function () {
        $("#popup_layer_edit").css("display", "none");
    });
});
