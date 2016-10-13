<%@include file="../jspf/page.jspf" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:message key="bad_token.title" var="title"/>

<t:wrapper title="${title}">
    <div class="container">
        <c:choose>
            <c:when test="${param.token == 'expired'}">
                <h5>
                    <fmt:message key="bad_token.expired.text">
                        <fmt:param value="${expired}"/>
                    </fmt:message>
                </h5>
            </c:when>
            <c:when test="${param.token == 'not_exists'}">
                <p>
                    <fmt:message key="bad_token.not_exists.text"/>
                </p>
            </c:when>
            <c:otherwise>
                <p class="flow-text"><fmt:message key="bad_token.default.text"/></p>
            </c:otherwise>
        </c:choose>

        <form action="/user/resend_token" method="post">
            <c:if test="${not empty unknown_mail}">
                <p class="red-text text-darken-1">
                    <fmt:message key="bad_token.error.unknown_mail"/>
                </p>
            </c:if>
            <c:if test="${not empty enabled}">
                <p class="red-text text-darken-1">
                    <fmt:message key="bad_token.error.enabled"/>
                </p>
            </c:if>
            <div class="input-field col s12">
                <fmt:message key="bad_token.email.label" var="email_label"/>
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