<%@tag description="Input with errors" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="messages"/>

<%@ attribute name="value" required="true" %>
<%@ attribute name="type" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="err" required="true" type="java.util.List" %>

<c:choose>
    <c:when test="${type == 'textarea'}">
        <c:set var="invalid" value="${not empty err ? 'invalid' : ''}"/>
        <textarea id="${name}" name="${name}" class="materialize-textarea ${invalid}">${value}</textarea>
    </c:when>
    <c:otherwise>
        <c:set var="invalid" value="${not empty err ? 'invalid' : ''}"/>
        <c:set var="datepicker" value="${type == 'date' ? ' datepicker' : ''}"/>
        <input id="${name}" type="${type}" name="${name}" required
        class="${invalid}${datepicker}"
        <c:if test="${not empty value}"> value="<c:out value="${value}"/>"
        </c:if>>
    </c:otherwise>
</c:choose>
<label style="left:0" for="${name}">${label}</label>
<c:if test="${not empty err}">
    <c:forEach var="error" items="${err}">
        <p class="red-text text-darken-1" style="margin-top: -5px">
            <fmt:message key="error.${error.code}">
                <fmt:param value="${error.value}"/>
            </fmt:message>
        </p>
    </c:forEach>
</c:if>