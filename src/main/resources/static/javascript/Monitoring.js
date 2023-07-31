$.ajax({
    url: "getAllTopic",
    success: function (result) {
        console.log(result);
        $("#monitoringTable").empty();
        var html = "";
        var i = 0;
        result.forEach(function (item) {
            if (i % 2 == 0) {
                html += "<div class='topic'>" + item.id + "</div>";
            } else {
                html += "<div class='topic'>" + item.id + "</div>";
            }
            i += 1;
            // html += "<div class='topic'> item.id </div>";
            // html += "<tr onclick='tableClick(" + item.id + ")' id='" + item.id + "'> <td>" + item.topicName + "</td><td>"
            //     + item.monitoringName + "</td><td>" + item.ip + "</td><td>" + item.port + "</td><td>" + item.status + "</td></tr>";
        })
        $("#monitoringTable").append(html);
    }
})