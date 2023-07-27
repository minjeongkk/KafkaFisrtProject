<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"/>
    <title>카프카 메인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/Main.css">
    <script type="text/javascript" src="/javascript/Main.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<div class="nav">
    <img src="../../image/logo.png"/>
    <div class="container">
        <ul>
            <li><a href="#" onclick="changePage('TopicSetting')">Topic 관리</a></li>
            <li><a href="#" onclick="changePage('Monitoring')">Topic 모니터링</a></li>
        </ul>
    </div>
</div>
<div class="container" id="page">
    ${pageContext.request.contextPath}
</div>
</body>
</html>

