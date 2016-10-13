<%@include file="../jspf/page.jspf" %>
<%@ taglib uri="/WEB-INF/pagination.tld" prefix="paginator" %>

<fmt:message key="blocked-user.title" var="title"/>

<t:wrapper css="${css}" title="${title}">
    <div class="container">
    <h4><fmt:message key="blocked-user.text"/></h4>
    <form action="/contact">
        <div class="input-field col s12">
            <fmt:message key="contact-form.topic" var="email_label"/>
            <t:input value="${param.topic}" type="text"
                     name="topic" label='${email_label}' err="${errors.topic}"/>
        </div>
        <div class="input-field col s12">
            <fmt:message key="contact-form.text" var="text_label"/>
            <t:input value="${param.text}" type="textarea"
                     name="text" label='${text_label}' err="${errors.text}"/>
        </div>
        <div class="center-align">
            <button type="submit" class="btn waves-effect lime"><fmt:message key="contact-form.submit"/></button>
        </div>
    </form>
    </div>
</t:wrapper>
