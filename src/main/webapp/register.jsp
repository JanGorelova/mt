<%--
Registration page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

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

<t:page title="${register_title}">
        <jsp:attribute name="header">
        <t:header/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <t:footer/>
    </jsp:attribute>

    <jsp:body>
        <div>
            <h2>${register_title}</h2>

            <form method="post" action="registerUser">

                <p>
                    <label for="login">
                        <span>
                                ${input_login}
                        </span>
                    </label>
                    <input type="text" id="login" name="login" value="${param.login}"/>
                    <span class="error">
                    <c:if test="${!empty requestScope.invalidLogin}">
                        <br>${requestScope.invalidLogin}
                    </c:if>
                    <c:if test="${!empty requestScope.loginExists}">
                        <br>${requestScope.loginExists}
                    </c:if>
                    </span>
                </p>


                <p>
                    <label for="firstName">
                        <span>
                                ${input_firstname}
                        </span>
                    </label>
                    <input type="text" id="firstName" name="firstName" value="${param.firstName}"/>
                    <span class="error">
                    <c:if test="${!empty requestScope.invalidFirstname}">
                        <br>${requestScope.invalidFirstname}
                    </c:if>
                    </span>
                </p>


                <p>
                    <label for="lastName">
                        <span>
                                ${input_lastname}
                        </span>
                    </label>
                    <input type="text" id="lastName" name="lastName" value="${param.lastName}"/>
                    <span class="error">
                    <c:if test="${!empty requestScope.invalidLastname}">
                        <br>${requestScope.invalidLastname}
                    </c:if>
                    </span>
                </p>

                <p>
                    <label for="country">
                        <span>
                                ${input_country}
                        </span>
                    </label>
                    <select id="country" name="country">
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
                    </select>
                </p>

                <p>
                    <label for="instrument">
                        <span>
                                ${input_instrument}
                        </span>
                    </label>
                    <input type="text" id="instrument" name="instrument" placeholder="${placeholder_instrument}"/>
                    <span class="error">
                    <c:if test="${!empty requestScope.invalidInstrument}">
                        <br>${requestScope.invalidInstrument}
                    </c:if>
                    </span>
                </p>

                <p>
                    <label for="password">
                        <span>
                                ${input_password}
                        </span>
                    </label>
                    <input type="password" id="password" name="password">
                    <span class="error">
                    <c:if test="${!empty requestScope.invalidPassword}">
                        <br>${requestScope.invalidPassword}
                    </c:if>
                    </span>
                </p>

                <p>
                    <label for="passwordRepeat">
                        <span>
                                ${repeat_password}
                        </span>
                    </label>
                    <input type="password" id="passwordRepeat" name="passwordRepeat">
                    <span class="error">
                    <c:if test="${!empty requestScope.invalidPasswordRepeat}">
                        <br>${requestScope.invalidPasswordRepeat}
                    </c:if>
                    </span>
                </p>

                <p>
                    <button type="submit">${submit}</button>
                    <a href="<c:url value="index.jsp" />">
                        <button type="button">${cancel}</button>
                    </a>
                </p>
            </form>

        </div>
    </jsp:body>
</t:page>