<%--
  Created by IntelliJ IDEA.
  User: kahsolt
  Date: 17-6-1
  Time: 上午5:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>

<html>
<head>
    <title>WindChest - Error Page</title>
</head>
<body>
    <h2>风箱不灵了!</h2>
    <hr/>
    <h5>程序运行时错误</h5>
    <h5>请联系管理员kahsolt@qq.com</h5>
    <hr />
    <%=exception.toString()%><br />
    <%=exception.getCause()%><br />
    <%=exception.getMessage()%><br />
    <%=exception.getLocalizedMessage()%><br />
    <%=exception.getStackTrace()%><br />
    <%=exception.getSuppressed()%><br />
    <%
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        out.println(sw);
    %>
</body>
</html>
