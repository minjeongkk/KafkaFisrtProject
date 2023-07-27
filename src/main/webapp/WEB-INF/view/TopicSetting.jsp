<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib  prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"/>
    <title>카프카 토픽 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/Main.css">
    <link rel="stylesheet" href="/css/TopicSetting.css">
    <script type="text/javascript" src="/javascript/Main.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<script>
</script>
<div class="container">
    <div class="row">
        <div class="col-auto" id="buttons">
            <button class="btn btn-primary" id="new" type="button">New</button>
            <button class="btn btn-primary" id="edit" type="button">Edit</button>
            <button class="btn btn-primary" id="delete"  type="button">Delete</button>
        </div>
    </div>
    <table class="table" id = "boardListTable">
        <thead class="thead-light">
        <tr class="text-center">
            <th scope="col">Topic Name</th>
            <th scope="col">Monitoring Name</th>
            <th scope="col">IP</th>
            <th scope="col">Port</th>
            <th scope="col">status</th>
        </tr>
        </thead>
        <tbody id="listArea">
        </tbody>
    </table>
</div>
</body>
</html>

