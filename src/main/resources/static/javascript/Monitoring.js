$(document).ready(function () {
    $.ajax({
        url: "getAllTopic",
        success: function (result) {
            console.log(result);
            $("#monitoringTable").empty();
            var html = "";
            result.forEach(function (item) {
                var setStatus = "STOPPED";
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
            $('[id*=status]').each(function (index, item) {
                console.log(item);
                if ($("#" + item.id).text() == 'RUNNING') {
                    $("#" + item.id).attr('style', 'color: green');
                } else {
                    var id = item.id.split("_")[0];
                    $("#" + id).css('background-color', '#e8e8e8');
                }
            });
        }
    })
});

function subscribeTopic(id) {
    var sendId = parseInt(id.split('_')[0])
    $.ajax({
        url: "subscribe1/" + sendId,
        success: function (result) {
            $("#" + sendId + "_status").css("color", "green");
            $("#" + sendId + "_status").text("RUNNING");
            $("#" + sendId).css('background-color', '#ffffff');
            var tableHtml = "<table class='table MonitoringListTable'>" +
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
        }
    })
}

function stopTopic(id) {
    var sendId = parseInt(id.split('_')[0])
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
}

function searchTopic(id) {
    var sendId = parseInt(id.split('_')[0])
    $.ajax({
        url: "getData1/" + sendId,
        success: function (result) {
            console.log(result);
            var html = "";
            if (result.length > 0) {
                $("#" + sendId + "_listArea").empty();
                var len = 0;
                if (result.length >= 6) {
                    len = result.length - 6;
                }
                for (var i = result.length - 1; i >= len; i--) {
                    html += "<tr> <td> # </td><td>" + result[i] + "111111111111111111111111111111111111111111111111111111111111111111111111111111111</td></tr>";
                }
            }
            $("#" + sendId + "_listArea").append(html);
        }
    })
}