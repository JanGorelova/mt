<%@tag description="Footer tag" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<a href="<c:url value="${pageContext.request.requestURI}?locale=en"/>">English</a> / <a href="<c:url value="${pageContext.request.requestURI}?locale=ru"/>">Русский</a>