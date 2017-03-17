<%--
  Created by IntelliJ IDEA.
  User: iMac
  Date: 16/03/17
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="register" var="loc"/>
<fmt:message bundle="${loc}" key="profileTitle" var="profileTitle"/>

<t:page title="${profileTitle}">
        <jsp:attribute name="header">
        <t:header/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <t:footerx/>
    </jsp:attribute>

    <jsp:body>
        <div>
            <h2>${profileTitle}</h2>
            <t:profile actionLink="EditUser" cancelLink="/index.jsp" />
        </div>
    </jsp:body>
</t:page>