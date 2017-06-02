<%--
  Created by IntelliJ IDEA.
  User: kahsolt
  Date: 17-6-1
  Time: 上午5:45
  To change this template use File | Settings | File Templates.
--%>

<%-- Home page --%>

<!-- Header Navigator -->
<jsp:include page="_header.jsp" flush="true"/>

<!-- Content Body -->
<article class="w3-content">
    <section class="w3-container">
        <form action="#" method="post">
            <label class="w3-label">Search</label>
            <input class="w3-input" type="text" name="search" id="search">
            <input class="w3-input" type="submit">
        </form>
        <div style="width: 100px; height: 100px; background-color: #87CEEB;"></div>
    </section>
</article>

<!-- Footer Copyright-->
<jsp:include page="_footer.jsp"/>