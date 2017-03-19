<%--Simple page template--%>
<%@tag description="Page template" pageEncoding="UTF-8" %>
<%@attribute name="title" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <script src="<c:url value="/js/jquery-3.1.1.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/main.js" />" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value = "/css/main.css" />"/>
    <title>${title}</title>
</head>

<body>
<div id="container">

    <div id="header">
        <jsp:invoke fragment="header"/>
    </div>

    <div id="body">
        <jsp:doBody/>
    </div>

    <div id="footer">
        <jsp:invoke fragment="footer"/>
    </div>

</div>
</body>
</html>
