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


$("#new").click(function () {
    $("#popup_layer_new").css("display", "block");
});

$("#edit").click(function () {
    $("#popup_layer_edit").css("display", "block");
});

$("#saveBtn").click(function () {
    $("#popup_layer_new").css("display", "none");
});

$("#editBtn").click(function () {
    $("#popup_layer_edit").css("display", "none");
});