<%@include file="../jspf/page.jspf" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:message key="reset_password.title" var="title"/>

<t:wrapper title="${title}">
    <div class="container">
<c:choose>
    <c:when test="${param.token == 'expired'}">
        <h5><fmt:message key="reset_password.expired.text"/> </h5>
    </c:when>
    <c:otherwise>
        <h5><fmt:message key="reset_password.default.text"/></h5>
    </c:otherwise>
        </c:choose>
        <form action="/user/reset_password" method="post">
            <c:if test="${not empty unknown_mail}">
                <p class="red-text text-darken-1">
                    <fmt:message key="reset_password.unknown_mail"/>
                </p>
            </c:if>
            <c:if test="${not empty not_enabled}">
                <p class="red-text text-darken-1">
                    <fmt:message key="reset_password.not_enabled"/>
                </p>
            </c:if>
            <div class="input-field col s12">
                <fmt:message key="enter.email.label" var="email_label"/>
                <t:input value="${param.email}" type="text"
                         name="email" label='${email_label}' err="${invalid_mail}"/>
            </div>
            <div class="center-align">
                <button type="submit" class="btn waves-effect lime"><fmt:message
                        key="bad_token.submit"/></button>
            </div>
        </form>
    </div>
</t:wrapper>