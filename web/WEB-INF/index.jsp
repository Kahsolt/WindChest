<%--
  Created by IntelliJ IDEA.
  User: kahsolt
  Date: 17-6-1
  Time: 上午5:45
  To change this template use File | Settings | File Templates.
--%>

<%-- Home page --%>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page errorPage="_error.jsp" %>

<!-- Header Navigator -->
<%@ include file="_header.jsp"%>

<!-- Content Body -->
<article class="w3-content">
    <div class="w3-container">
        <div class="w3-container w3-center w3-opacity w3-padding-jumbo  w3-margin-bottom">
            <p id="sitename" class="w3-text-blue w3-jumbo">WindChest</p>
            <p id="siteinfo" class="w3-text-blue w3-xlarge">Simple message deliverer...</p>
        </div>
        <div class="w3-container w3-center w3-opacity w3-padding-tiny">
            <form class="w3-form" action="Enter.action" method="post">
                <input name="chest.name" type="hidden" value="DefaultChest">
                <input class="w3-btn w3-blue w3-xlarge w3-round"type="submit" value="开始">
            </form>
        </div>
    </div>
</article>

<style>
    #sitename, #siteinfo {
        font-family: 'Times New Roman', sans-serif;
    }
</style>

<!-- Footer Copyright-->
<%@ include file="_footer.jsp"%>