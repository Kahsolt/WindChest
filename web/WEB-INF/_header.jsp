<%--
  Created by IntelliJ IDEA.
  User: kahsolt
  Date: 17-6-1
  Time: 上午5:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page errorPage="_error.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="tk.kahsolt.windchest.entity.UserEntity" %>

<%!boolean loginerror = false;%>
<%!boolean signinerror = false;%>
<%!boolean guest = true;%>
<%!UserEntity user = null;%>
<%!String username = "";%>
<%
    guest = session.getAttribute("user")==null;
    loginerror = request.getAttribute("loginerror")!=null && (boolean)request.getAttribute("loginerror");
    signinerror = request.getAttribute("signinerror")!=null && (boolean)request.getAttribute("signinerror");

    if(guest) { //设置为访客
        user=new UserEntity();
        user.setNickname("New Windchaser?");
    } else {
        user=(UserEntity)session.getAttribute("user");
    }
    if(user.getNickname()!=null) {
        username = user.getNickname();
    } else {
        username = user.getUsername();
    }
%>

<%!String chest = " ";%>
<%
    if(session.getAttribute("chest") != null) {
        chest = (String)session.getAttribute("chest");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="../css/w3.css">
    <title>WindChest</title>
</head>

<body class="w3-responsive">

<nav class="w3-card-4 w3-blue w3-large">
    <ul class="w3-navbar w3-padding-tiny">
        <li><a class="w3-text-white w3-large" onclick="confirmLeave();">WindChest</a><li>
<% if(!guest) {%>
        <li class="w3-right">
            <div class="w3-dropdown-hover">
                <p class="w3-hoverable">收藏</p>
                <div class="w3-dropdown-content w3-border">
                    <a href="#">Chest1</a>
                    <a href="#">Chest2</a>
                </div>
            </div>
            <div class="w3-dropdown-hover">
                <div style="vertical-align: middle;">
                    <a class="w3-show-inline-block"><%=username%></a>
                    <img src="<%=user.getAvatar()%>" alt="avatar" height="48">
                </div>
                <div class="w3-dropdown-content w3-border">
                    <a onclick="modelManager('modelSettings','open')">设置</a>
                    <a class="w3-light-grey w3-hover-red" onclick="confirmLogout();">注销</a>
                </div>
            </div>
        </li>
<%} else {%>
        <li class="w3-right">
            <div class="w3-dropdown-hover">
                <a class="w3-blue"><%=username%></a>
                <div class="w3-dropdown-content w3-border">
                    <a class="w3-text-white w3-blue w3-hover-opacity" onclick="modelManager('modelLogin','open')">登录</a>
                    <a class="w3-text-white w3-indigo w3-hover-opacity" onclick="modelManager('modelSignin','open')">注册</a>
                </div>
            </div>
        </li>
<%}%>
    </ul>

    <!-- Modal： Login -->
<%if(user!=null) {%>
    <div id="modelLogin" class="w3-modal w3-animate-opacity">
        <div class="w3-card-4 w3-modal-content" style="display: block; max-width: 35%; min-width: 350px;">
            <div class="w3-container w3-blue">
                <span class="w3-closebtn" onclick="modelManager('modelLogin','close')">×</span>
                <h2 class="w3-center w3-text-white">登录</h2>
            </div>
            <div class="w3-container w3-white">
                <form class="w3-form" method="post" action="Login.action">
                    <div class="w3-input-group">
                        <label class="w3-label w3-validate">用户名：</label>
                        <input name="user.username" class="w3-input" type="text" autofocus="autofocus" required="required" />
                    </div>
                    <div class="w3-input-group">
                        <label class="w3-label w3-validate">密码：</label>
                        <input name="user.password" class="w3-input" type="password" required="required" />
                    </div>
                    <div class="w3-input-group">
                        <%
                            if(loginerror) {
                               out.write("<p class=\"w3-text-red\">登录失败！</p>");
                            }
                        %>
                        <input class="w3-input w3-blue w3-hover-opacity" type="submit" value="登录">
                    </div>
                </form>
            </div>
        </div>
    </div>
<%}%>

    <!-- Modal： Register -->
<%if(user!=null||signinerror) {%>
    <div id="modelSignin" class="w3-modal w3-animate-opacity">
        <div class="w3-card-4 w3-modal-content" style="display: block; max-width: 42%; min-width: 350px;">
            <div class="w3-container w3-indigo">
                <span class="w3-closebtn" onclick="modelManager('modelSignin','close')">×</span>
                <h2 class="w3-center w3-text-white">注册</h2>
            </div>
            <div class="w3-container w3-white">
                <form class="w3-form" method="post" action="Signin.action">
                    <div class="w3-input-group">
                        <label class="w3-label w3-validate">用户名：</label>
                        <input name="user.username" class="w3-input" type="text" autofocus="autofocus" required="required" />
                    </div>
                    <div class="w3-input-group">
                        <label class="w3-label w3-validate">密码：</label>
                        <input name="user.password" class="w3-input" type="password" required="required" />
                    </div>
                    <div class="w3-input-group">
                        <label class="w3-label w3-validate">邮箱：(可选)</label>
                        <input name="user.email" class="w3-input" type="email" />
                    </div>
                    <div class="w3-input-group">
                        <%
                            if(signinerror) {
                                out.write("<p class=\"w3-text-red\">注册失败！</p>");
                            }
                        %>
                        <input class="w3-input w3-indigo w3-hover-opacity" type="submit" value="注册">
                    </div>
                </form>
            </div>
        </div>
    </div>
<%}%>

</nav>

<style>
    a {
        cursor: pointer;
    }
</style>

<script>
    function modelManager(modal,operation) {
        if(operation=='open') {
            document.getElementById(modal).style.display = "block"; //inline-block?
        } else {
            document.getElementById(modal).style.display = "none";
        }
    }
    function confirmLeave() {
        if(confirm("确定离开这个Chest？")) {
            window.location.href="Leave.action";
        }
    }
    function confirmLogout() {
        if(confirm("确定注销？")) {
            window.location.href="Logout.action";
        }
    }
</script>

<%
    if(loginerror) {
        out.write("<script>modelManager('modelLogin','open');</script>");
    }
    if(signinerror) {
        out.write("<script>modelManager('modelSignin','open');</script>");
    }
%>