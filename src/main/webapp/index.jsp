<%--
  Created by IntelliJ IDEA.
  User: Air
  Date: 07/02/2017
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="login" var="loc"/>
    <fmt:message bundle="${loc}" key="welcome" var="welcome_title"/>
    <fmt:message bundle="${loc}" key="loginPlaceholder" var="login_placeholder_msg"/>
    <fmt:message bundle="${loc}" key="passwordPlaceholder" var="password_placeholder_msg"/>
    <fmt:message bundle="${loc}" key="login" var="login_msg"/>
    <fmt:message bundle="${loc}" key="register" var="register_msg"/>
    <fmt:message bundle="${loc}" key="welcomeFull" var="welcome_msg"/>
    <title>${welcome_title}</title>
</head>
<body>
<div>
  <form method="post" action="login">
      <h3>${welcome_msg}</h3>
      <input type="text" name="login" placeholder="${login_placeholder_msg}">
      <input type="password" name="password" placeholder="${password_placeholder_msg}">
      <br>
        <span>
            <input type="submit" value="${login_msg}">
            <a href="/register.jsp">${register_msg}</a>
        </span>
  </form>
</div>
<div>
    <c:if test="${!empty requestScope.loginError}">
        <p>${requestScope.loginError}</p>
    </c:if>
</div>
<div>
    <a href="<c:url value="/index.jsp?locale=en"/>">English</a> / <a href="<c:url value="/index.jsp?locale=ru"/>">Русский</a>
</div>
</body>
</html>
