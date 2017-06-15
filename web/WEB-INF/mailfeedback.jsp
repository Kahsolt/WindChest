<%--
  Created by IntelliJ IDEA.
  User: kahsolt
  Date: 17-6-15
  Time: 下午3:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!String feedback="";%>
<%
    String res=(String)request.getAttribute("mailOK");
    if(res.equals("OK")) {
        feedback="发送成功，请查收～";
    } else {
        feedback="邮件发送失败，联系管理员……";
    }
%>

<html>
<head>
    <title>WindChest Mail Feedback</title>
</head>
<body onload="closeWindow();">
    <h1>WindChest邮件回执:</h1>
    <p><%=feedback%></p>
    <hr/>
    <div id="show">
        倒计时5秒后自动关闭……
    </div>

    <script>
        var time=5;
        function closeWindow(){
            window.setTimeout('closeWindow()',1000);
            if(time>0){
                document.getElementById("show").innerHTML="倒计时<font color=red>"+time+"</font>秒后关闭当前窗口";
                time--;
            } else{
                window.opener=null;
                window.close();
            }
        }
    </script>
</body>

</html>
