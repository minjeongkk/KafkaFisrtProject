$(document).ready(function () {
    // db에 저장된 토픽 수만큼 모니터링 화면 생성
    $.ajax({
        url: "getAllTopic",
        success: function (result) {
            console.log(result);
            $("#monitoringTable").empty();
            let html = "";
            result.forEach(function (item) {
                let setStatus = "STOPPED";
                if (item.status == 'Running') {
                    setStatus = "RUNNING";
                }
                html += "<div class='topic' id='" + item.id + "'>" +
                    "<span class = 'monitoringName' id='" + item.id + "_monitoringName'>" + item.monitoringName + "</span>" +
                    "<span class ='status' id='" + item.id + "_status'>" + setStatus + "</span>" +
                    "<div class='row' id='buttons'>" +
                    " <div class='col-auto'>" +
                    "<button class='button' id='" + item.id + "_subscribe' type='button' onclick='subscribeTopic(this.id);'>구독</button>" +
                    "<button class='button' id='" + item.id + "_stop' type='button' onclick='stopTopic(this.id);'>중지</button>" +
                    "<button class='button' id='" + item.id + "_search' type='button' onclick='searchTopic(this.id);'>조회</button>" +
                    "</div>" +
                    "</div> " +
                    "<div class='tableWrap' id='" + item.id + "_table'></div>" +
                    "</div>";
            })
            $("#monitoringTable").append(html);

            // 상태에 따라 모니터링 화면 설정
            $('[id*=status]').each(function (index, item) {
                console.log(item);
                if ($("#" + item.id).text() == 'RUNNING') {
                    $("#" + item.id).attr('style', 'color: green');
                } else {
                    let id = item.id.split("_")[0];
                    $("#" + id).css('background-color', '#e8e8e8');
                }
            });
        }
    })
});

// 토픽 구독
function subscribeTopic(id) {
    let sendId = parseInt(id.split('_')[0])
    $("#" + sendId + "_status").css("color", "green");
    $("#" + sendId + "_status").text("RUNNING");
    $("#" + sendId).css('background-color', '#ffffff');

    // 서버 확인
    $.ajax({
        url: "checkServer/" + sendId,
        success: function (result) {
            console.log(result);
            // 토픽 구독
            $.ajax({
                type: "POST",
                url: "subscribe/" + sendId,
                dataType: "json",
                data: {
                    id: sendId
                },
                success: function (result) {
                    console.log(result);
                }
            })
        },
        error: function (error) {
            console.log(error);
            if (error.status == 404) {
                alert("구독할 수 없습니다.");
                $("#" + sendId + "_status").css("color", "red");
                $("#" + sendId + "_status").text("STOPPED");
                $("#" + sendId).css('background-color', '#e8e8e8');
            }
        }
    })
}

// 구독이 된 상태에서 누르면 구독 중지
function stopTopic(id) {
    let sendId = parseInt(id.split('_')[0])
    if ($("#" + sendId + "_status").text() == "RUNNING") {
        $.ajax({
            type: "POST",
            url: "stop/" + sendId,
            data: {
                id: sendId
            },
            success: function (result) {
                console.log(result);
                $("#" + sendId + "_status").css("color", "red");
                $("#" + sendId + "_status").text("STOPPED");
                $("#" + sendId).css('background-color', '#e8e8e8');
                $("#" + sendId + "_table").empty();
            },
            error: function (error) {
                console.log(error);
            }
        })
    } else {
        alert("구독하지 않은 토픽입니다.");
    }
}

// 구독이 된 상태에서 누르면 조회
function searchTopic(id) {
    let sendId = parseInt(id.split('_')[0])
    if ($("#" + sendId + "_status").text() == "RUNNING") {
        // 데이터를 받아와 표로 출력
        $.ajax({
            url: "getData/" + sendId,
            success: function (result) {
                console.log(result);
                let html = "";
                let tableHtml = "<table class='table MonitoringListTable'>" +
                    "<thead class='thead-light'>" +
                    "<tr class='text-center'>" +
                    "<th scope='col'>No.</th>" +
                    "<th scope='col'>Col</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody id='" + sendId + "_listArea'>" +
                    "</tbody>" +
                    "</table>";
                $("#" + sendId + "_table").append(tableHtml);
                if (result.length > 0) {
                    $("#" + sendId + "_listArea").empty();
                    let len = 0;
                    if (result.length >= 6) {
                        len = result.length - 6;
                    }
                    for (let i = result.length - 1; i >= len; i--) {
                        html += "<tr> <td> # </td><td>" + result[i] + "</td></tr>";
                    }
                }
                $("#" + sendId + "_listArea").append(html);
            }
        })
    } else {
        alert("구독하지 않은 토픽입니다.");
    }
}