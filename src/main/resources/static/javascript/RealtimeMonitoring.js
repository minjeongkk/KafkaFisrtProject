function subscribeTopic2(id) {
    let sendId = parseInt(id.split('_')[0]);
    $("#" + sendId + "_status").css("color", "green");
    $("#" + sendId + "_status").text("RUNNING");
    $("#" + sendId).css('background-color', '#ffffff');

    $.ajax({
        url: "checkServer1/" + sendId,
        success: async function (result) {
            console.log(result);
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

            // fetch로 리소스를 비동기 요청
            const response = await fetch("/subscribe1/" + sendId);
            const reader = response.body.getReader();
            while (true) {
                const {value, done} = await reader.read();
                if (done) break;
                let html = "";
                let text = "";
                value.forEach(value1 =>
                    text += String.fromCharCode(value1)
                )
                html += "<tr> <td> # </td><td>" + text + "</td></tr>";
                $("#" + sendId + "_listArea").prepend(html);
                console.log(sendId + ":" + text);

                if ($("#" + sendId + "_listArea tr").length > 6) {
                    $("#" + sendId + "_listArea tr").last().remove();
                }
            }
            console.log('Response fully received');
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

function stopTopic2(id) {
    let sendId = parseInt(id.split('_')[0])
    if ($("#" + sendId + "_status").text() == "RUNNING") {
        $.ajax({
            url: "stop1/" + sendId,
            success: function (result) {
                console.log(result);
                $("#" + sendId + "_status").css("color", "red");
                $("#" + sendId + "_status").text("STOPPED");
                $("#" + sendId).css('background-color', '#e8e8e8');
                $("#" + sendId + "_table").empty();
            }
        })
    } else {
        alert("구독하지 않은 토픽입니다.");
    }
}