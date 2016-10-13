<%@include file="../jspf/page.jspf" %>
<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>

<t:wrapper css="${css}" title="${code}">
    <div class="row">
        <img class="center-block" src="/images/404_end.png" style="
        position: absolute;
    margin-left: auto;
    margin-right: auto;
    left: 0;
    right: 0;"/>
        <div>
            <div style="    position: absolute;
    left: 41%;
    right: 41%;
    top: 220px;
    margin-left: auto;
    margin-right: auto;
    width: auto;">
                <h5 class="white-text center-align"><fmt:message key="error.${code}"/></h5>
                <p class="lime-text center-align" style="    font-size: 70px;
    margin-top: 0px;">${code}</p>
            </div>
        </div>
    </div>
    <div class="container" style="margin-top: 400px">
        <c:if test="${user.role == 'ADMIN'}">
            <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
            <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
            <p><strong>Message:</strong> ${message}</p>
            <p><strong>Exception:</strong> ${exception}</p>
        </c:if>
    </div>
</t:wrapper>