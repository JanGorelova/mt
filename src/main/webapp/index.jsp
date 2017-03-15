<%--
Login page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="login" var="loc"/>
<fmt:message bundle="${loc}" key="welcome" var="welcome_title"/>
<fmt:message bundle="${loc}" key="loginPlaceholder" var="login_placeholder_msg"/>
<fmt:message bundle="${loc}" key="passwordPlaceholder" var="password_placeholder_msg"/>
<fmt:message bundle="${loc}" key="login" var="login_msg"/>
<fmt:message bundle="${loc}" key="register" var="register_msg"/>
<fmt:message bundle="${loc}" key="welcomeFull" var="welcome_msg"/>
<fmt:message bundle="${loc}" key="inputLogin" var="inputLogin"/>
<fmt:message bundle="${loc}" key="inputPassword" var="inputPassword"/>

<t:page title="${welcome_title}">

    <jsp:attribute name="header">
        <t:header/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <t:footer/>
    </jsp:attribute>

    <jsp:body>
        <div>
            <h2>${welcome_msg}</h2>
            <form method="post" action="login">
                <p>
                    <label for="login">
                        <span>
                            ${inputLogin}:
                        </span>
                    </label>
                    <input type="text" id="login" name="login" placeholder="${login_placeholder_msg}">
                </p>
                <p>
                    <label for="password">
                        <span>
                            ${inputPassword}:
                        </span>
                    </label>
                    <input type="password" id="password" name="password" placeholder="${password_placeholder_msg}">
                </p>
                <p>
                    <button type="submit">${login_msg}</button>
                    <a href="<c:url value="register.jsp" />"><button type="button">${register_msg}</button></a>
                </p>
            </form>
        </div>
        <div>
            <c:if test="${!empty requestScope.loginError}">
                <p><span class="error">${requestScope.loginError}</span></p>
            </c:if>
        </div>
    </jsp:body>

</t:page>

