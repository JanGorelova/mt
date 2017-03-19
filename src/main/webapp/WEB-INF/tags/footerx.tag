<%--Extended footer with logout tag--%>
<%@tag description="Extended footer with logout tag" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="main" var="loc"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>

<a href="<c:url value = "/logout"/>">${logout}</a>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="ShowProfile?locale=en"/>">English</a> / <a href="<c:url value="ShowProfile?locale=ru"/>">Русский</a>