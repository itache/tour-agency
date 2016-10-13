<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="css" required="false" %>
<%@ attribute name="js" required="false" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<head>
    <title>${title}</title>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="/css/materialize.min.css">
    <link rel="stylesheet" href="/css/material_icons.css">
    <link rel="stylesheet" href="/css/circle_caps.css">
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/user-details.css">
    <!--custom css-->
    <c:forEach var="stylesheet" items="${css}">
        <link rel="stylesheet" href="${stylesheet}"/>
    </c:forEach>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/materialize.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('select').material_select();
            $("select[required]").css({display: "inline", height: 0, padding: 0, width: 0});
        });
    </script>
</head>
<body>
<div class="row">
    <nav class="header">
        <div class="nav-wrapper lime lighten-2">
            <ul class="left">
                <c:if test="${not empty user and user.role == 'ADMIN'}">
                    <li>
                        <a href="/user/list">
                            <i class="material-icons left">people</i><fmt:message key="menu.user-list"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty user and (user.role == 'ADMIN' or user.role == 'MANAGER')}">
                    <li>
                        <a href="/order/list">
                            <i class="material-icons left">shopping_cart</i><fmt:message key="menu.order-list"/>
                        </a>
                    </li>
                </c:if>
                <li>
                    <a href="/tour/list">
                        <i class="material-icons left">photo_library</i><fmt:message key="menu.tour-list"/>
                    </a>
                </li>
                <c:if test="${not empty user and (user.role == 'CUSTOMER')}">
                    <li>
                        <a href="/customer/orders">
                            <i class="material-icons left">shopping_cart</i><fmt:message key="menu.customer-order-list"/>
                        </a>
                    </li>
                </c:if>
            </ul>
            <a href="/" class="brand-logo center"><img src="/images/logo.png" height="113px"/></a>
            <ul id="nav-mobile" class="right">

            </ul>
        </div>
    </nav>
    <c:if test="${empty user}">
        <a href="/enter" class="btn-flat btn-large" style="position: absolute; right: 0px; top:0px">
            <i class="large material-icons" style="vertical-align: bottom">input</i>
            <fmt:message key="enter.button.label"/>
        </a>
    </c:if>
    <%@tag import="me.itache.helpers.LocaleManager" %>
    <span style="position: absolute; right: 50px; top: 74px">
    <c:forEach items="${LocaleManager.instance().getAvailableLocales()}" var="loc">
            <a href="/set_lang?lang=${loc.value}"
               class="btn-flat ${loc.value == locale ? 'disabled' : ''}"
               style="margin: 0px; width:50px; padding-left: 15px">
                    ${loc.value}
            </a>
    </c:forEach>
        </span>
</div>
<!-- User details-->
<c:if test="${not empty user}">
<div class="lime lighten-1 user-details">
    <i class="circle-caps letter">
        <c:out value="${fn:substring(user.login, 0, 1)}"/>
    </i>
    <div>${user.login}</div>
    <div><fmt:message key="user.role.${fn:toLowerCase(user.role)}"/></div>
    <a href="/logout" class="btn-floating lime logout">
        <i class="material-icons">exit_to_app</i>
    </a>
</div>
</c:if>

<jsp:doBody/>

<script src="/js/jquery.noty.packaged.min.js"></script>

<c:forEach var="script" items="${js}">
    <script src="${script}"></script>
</c:forEach>

<!-- Notification -->
<c:if test="${not empty notification}">
    <script>
        $(document).ready(function () {
            noty({
                text: '<fmt:message key="notification.${notification.code}"><fmt:param value="${notification.value}"/></fmt:message>',
                layout: '${notification.type == "ERROR" ? "center" : "bottom"}',
                type: '${fn:toLowerCase(notification.type)}',
                theme: 'relax'
            });
        });
    </script>
    <c:remove var="notification" scope="session"/>
</c:if>
</body>
</html>