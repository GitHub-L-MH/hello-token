<%--
  Created by IntelliJ IDEA.
  User: L-PC
  Date: 2017/10/27
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>
    <center>
        <table>
            <tr>
                <td>用户名：</td>
                <td><input name="userCode" type="text"></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input name="userPassword" type="text"></td>
            </tr>
            <tr>
                <td colspan="2"><button id="loginBtn">登录</button></td>
            </tr>
        </table>
        <div id="resultData"></div>
    </center>
<script type="text/javascript" src="/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.cookie.js"></script>
    <script type="text/javascript">
    $(function () {
        $("#loginBtn").click(function () {
            var userCode = $("input[name=userCode]").val();
            var userPassword = $("input[name=userPassword]").val();
            var url = "/user/login.action";
            var data = "userCode="+userCode+"&userPassword="+userPassword;
            $.post(url,data,callBack,"JSON");

        });
        function callBack(data) {
            $.cookie("userToken",data.data.token,{expires:1});
            window.location.href = "/user/main.html";
        }
    })
</script>
</body>
</html>
