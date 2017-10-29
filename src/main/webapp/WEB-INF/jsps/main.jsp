<%--
  Created by IntelliJ IDEA.
  User: L-PC
  Date: 2017/10/27
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>欢迎来到wzry，啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊去死吧</h1>
    <button id="getUserBtn">获取用户信息</button>
    <div id="userInfo"></div>
    <script type="text/javascript" src="/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.cookie.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#getUserBtn").click(function () {
                $.ajax({
                    type:"POST",
                    url:"/user/admin/get/1",
                    headers:{
                        token:$.cookie("userToken")
                    },
                    dataType:"JSON",
                    success:function (d) {
                        console.log(d);
                        $("#userInfo").text(d.data.userName+"\t"+d.data.id+"\t"+d.data.address);
                    }
                })
            });
        })
    </script>
</body>
</html>
