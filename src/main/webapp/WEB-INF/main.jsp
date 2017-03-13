<%--
  Created by IntelliJ IDEA.
  User: iMac
  Date: 06/03/17
  Time: 01:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="main" var="loc"/>
    <fmt:message bundle="${loc}" key="profile" var="profile"/>
    <fmt:message bundle="${loc}" key="logout" var="logout"/>
    <fmt:message bundle="${loc}" key="english" var="english"/>
    <fmt:message bundle="${loc}" key="russian" var="russian"/>
    <fmt:message bundle="${loc}" key="instruments" var="instruments"/>
    <fmt:message bundle="${loc}" key="like" var="like"/>
    <fmt:message bundle="${loc}" key="newTweetPlaceholder" var="newTweetPlaceholder"/>
    <fmt:message bundle="${loc}" key="myTweets" var="myTweets"/>
    <fmt:message bundle="${loc}" key="country" var="country"/>
    <fmt:message bundle="${loc}" key="post" var="post"/>
    <fmt:message bundle="${loc}" key="cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="subscribe" var="subscribe"/>
    <fmt:message bundle="${loc}" key="subscriptions" var="subscriptions"/>

    <script src="<c:url value="/js/jquery-3.1.1.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/main.js" />" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value = "/css/main.css" />"/>
    <meta charset="utf-8" />
    <title>${sessionScope.User.login} - MusicTwitter</title>
</head>

<body>
<div id="container">
    <div id="header">
        <div id="logo">
            MusicTwitter
        </div>
        <div id="menu">
            <ul class="menu">
                <li class="menu"><a class="menu" onclick="getSubscriptionTweets()">${subscriptions}</a></li>
                <li class="menu"><a class="menu" onclick="getInstrumentTweets()">${instruments}</a></li>
                <li class="menu"><a class="menu" onclick="getCountryTweets()">${country}</a></li>
                <li class="menu"><a class="menu" onclick="getMyTweets()">${myTweets}</a></li>
            </ul>
        </div>
    </div>

    <div id="sidebar">

        <div id="user">
            <div id="usericon">
                <img src="<c:url value="/img/user_icon.png"/>" alt="user icon" width="48" height="48"/>
            </div>

            <div id="userinfo">
                <span id="login">${sessionScope.User.login}</span><br>
                <span id="name">${sessionScope.User.firstName} ${sessionScope.User.lastName}</span><br>
            </div>

            <div id="instruments">
                <ul>
                    <c:forEach var="item" items="${sessionScope.Instruments}">
                        <li><img src="<c:url value="/img/note_icon.png"/>" width="20" height="20"/>
                                ${item.instrumentName}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <div id="newTweet">
            <form method="post">
                <div style="text-align: center;">
                    <textarea name="tweet" id="tweet" placeholder="${newTweetPlaceholder}" maxlength="250"></textarea>
                </div>
                <input type="button" class="button" value="${post}" onclick="newTweet()">
                <input type="reset" class="button" value="${cancel}">
            </form>
        </div>

    </div>

    <div id="content">

    </div>

    <div class="clear">
    </div>

    <div id="footer">
        <a href="<c:url value = "/logout"/>">${logout}</a>
        &nbsp;&nbsp;&nbsp;
        <a href="<c:url value="/index.jsp?locale=en"/>">English</a> / <a href="<c:url value="/index.jsp?locale=ru"/>">Русский</a>
    </div>
</div>
</div>
</body>
</html>
