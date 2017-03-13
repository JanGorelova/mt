<%--
  Created by IntelliJ IDEA.
  User: iMac
  Date: 06/03/17
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="register" var="loc"/>
    <fmt:message bundle="${loc}" key="registerTitle" var="register_title"/>
    <fmt:message bundle="${loc}" key="inputLogin" var="input_login"/>
    <fmt:message bundle="${loc}" key="inputFirstName" var="input_firstname"/>
    <fmt:message bundle="${loc}" key="inputLastName" var="input_lastname"/>
    <fmt:message bundle="${loc}" key="inputCountry" var="input_country"/>
    <fmt:message bundle="${loc}" key="inputInstrument" var="input_instrument"/>
    <fmt:message bundle="${loc}" key="inputPassword" var="input_password"/>
    <fmt:message bundle="${loc}" key="inputRepeatPassword" var="repeat_password"/>
    <fmt:message bundle="${loc}" key="placeholderInstrument" var="placeholder_instrument"/>
    <fmt:message bundle="${loc}" key="submit" var="submit"/>
    <fmt:message bundle="${loc}" key="cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="invalidFirstname" var="invalid_firstname"/>
    <fmt:message bundle="${loc}" key="invalidLastname" var="invalid_lastname"/>
    <fmt:message bundle="${loc}" key="invalidLogin" var="invalid_login"/>
    <fmt:message bundle="${loc}" key="invalidInstrument" var="invalid_instrument"/>
    <fmt:message bundle="${loc}" key="invalidPassword" var="invalid_password"/>
    <fmt:message bundle="${loc}" key="invalidPasswordRepeat" var="invalid_password_repeat"/>
    <fmt:message bundle="${loc}" key="loginExists" var="login_exists"/>
    <meta charset="utf-8" />
    <title>${register_title}</title>
</head>
<body>
<div>
    <form method="post" action="registerUser">
        <h3>${register_title}</h3>

        <p>${input_login}<br>
            <input type="text" name="login" value="${param.login}"/>
            <c:if test="${!empty requestScope.invalidLogin}">
                ${requestScope.invalidLogin}
            </c:if>
            <c:if test="${!empty requestScope.loginExists}">
                ${requestScope.loginExists}
            </c:if>


        <p>${input_firstname}<br>
            <input type="text" name="firstName" value="${param.firstName}"/>
            <c:if test="${!empty requestScope.invalidFirstname}">
                ${requestScope.invalidFirstname}
            </c:if>

        <p>${input_lastname}<br>
            <input type="text" name="lastName" value="${param.lastName}"/>
            <c:if test="${!empty requestScope.invalidLastname}">
                ${requestScope.invalidLastname}
            </c:if>

        <p>${input_country}<br>
            <select name="country">
                <c:forEach var="item" items="${applicationScope.countries}">
                    <c:choose>
                        <c:when test="${param.country == item}">
                            <option value="${item}" selected>${item}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${item}">${item}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select></p>

        <p>${input_instrument}<br>
            <input type="text" name="instrument" placeholder="${placeholder_instrument}"/>
            <c:if test="${!empty requestScope.invalidInstrument}">
                ${requestScope.invalidInstrument}
            </c:if>

        <p>${input_password}<br>
            <input type="password" name="password">
            <c:if test="${!empty requestScope.invalidPassword}">
                ${requestScope.invalidPassword}
            </c:if>

        <p>${repeat_password}<br>
            <input type="password" name="passwordRepeat">
            <c:if test="${!empty requestScope.invalidPasswordRepeat}">
                ${requestScope.invalidPasswordRepeat}
            </c:if>

        <p><input type="submit" value="${submit}">
            <a href="/"><input type="button" value="${cancel}"></a></p>
    </form>
</div>
</body>
</html>
