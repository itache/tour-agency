<%@include file="../jspf/page.jspf"%>
<%@ taglib uri="/WEB-INF/pagination.tld" prefix="paginator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:message key="user-list.title" var="title" />

<t:wrapper title="${title}">
    <form action="${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}"
          id="form" class="col s10 offset-s1">
        <div class="row" style="margin-bottom: 0px;">
            <div class="input-field col s3 m3">
                <select class="browser-default" name="sort" onchange="this.form.submit()">
                    <option value="login"
                            <c:if test="${empty param.sort or param.sort == 'login'}">
                                selected
                            </c:if>>
                        <fmt:message key="user-list.sort.by_login"/>
                    </option>
                    <option value="email"
                            <c:if test="${ param.sort == 'email'}">
                                selected
                            </c:if>>
                        <fmt:message key="user-list.sort.by_email"/>
                    </option>
                </select>
            </div>
            <div class="input-field col s2 m2">
                <button class="waves-effect waves-teal btn-flat" name="order" onclick="this.form.submit()"
                        <c:choose>
                            <c:when test="${empty param.order or param.order == 'asc'}">
                                value="desc"
                            </c:when>
                            <c:otherwise>
                                value="asc"
                            </c:otherwise>
                        </c:choose>>
                    <c:choose>
                        <c:when test="${empty param.order or param.order == 'asc'}">
                            <i class="material-icons">arrow_upward</i>
                        </c:when>
                        <c:otherwise>
                            <i class="material-icons">arrow_downward</i>
                        </c:otherwise>
                    </c:choose>
                </button>
            </div>
        </div>
    </form>
    <div class="row">
        <div class="col s12">
            <table class="centered bordered">
                <thead>
                <tr>
                    <th data-field="login"><fmt:message key="enter.login.label" /></th>
                    <th data-field="email"><fmt:message key="enter.email.label"/> </th>
                    <th data-field="role"><fmt:message key="user.role.label"/> </th>
                    <th data-field="blocked"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <%@include file="../jspf/user_item.jspf" %>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <c:if test="${pagination.pageCount > 1}">
        <div class="center-align">
            <paginator:display pagination="${pagination}"/>
        </div>
    </c:if>

</t:wrapper>
