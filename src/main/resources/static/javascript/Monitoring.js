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
                    "<button class='btn btn-primary' id='" + item.id + "_subscribe' type='button' onclick='subscribeTopic(this.id);'>구독</button>" +
                    "<button class='btn btn-primary' id='" + item.id + "_stop' type='button' onclick='stopTopic(this.id);'>중지</button>" +
                    "<button class='btn btn-primary' id='" + item.id + "_search' type='button' onclick='searchTopic(this.id);'>조회</button>" +
                    "</div>" +
                    "</div>" +
                    "<table class='table' id='MonitoringListTable'>" +
                    "<thead class='thead-light'>" +
                    "<tr class='text-center'>" +
                    "<th scope='col'>No.</th>" +
                    "<th scope='col'>Col</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody id='" + item.id + "_listArea'>" +
                    "</tbody>" +
                    "</table>" +
                    "</div>";
            })
            $("#monitoringTable").append(html);
            $('[id*=status]').each(function (index, item) {
                console.log(item);
                if ($("#" + item.id).text() == 'RUNNING') {
                    $("#" + item.id).attr('style', 'color: green');
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
            for(var i = 0; i < result.length; i++) {
                html += "<tr> <td> # </td><td>" + result[i] + "</td></tr>";
            }
            $("#"+sendId+"_listArea").append(html);
        }
    })
}