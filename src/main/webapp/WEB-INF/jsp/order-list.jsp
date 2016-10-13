<%@include file="../jspf/page.jspf" %>
<%@ taglib uri="/WEB-INF/pagination.tld" prefix="paginator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:message key="order-list.title" var="title"/>

<t:wrapper title="${title}">
    <c:if test="${user.role == 'MANAGER' or user.role == 'ADMIN'}">
    <ul class="tabs">
        <li class="tab col s6">
            <a href="#all" class="amber-text text-darken-4">
                <fmt:message key="order.all"/>
            </a>
        </li>
        <li class="tab col s6">
            <a href="#summary" class="amber-text text-darken-4">
                <fmt:message key="order.summary"/>
            </a>
        </li>
        <div class="indicator amber darken-4" style="z-index:1"></div>
    </ul>
    </c:if>
    <div class="col s12" id="all">
    <form action="${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}"
          id="form" class="col s10 offset-s1">
        <div class="row" style="margin-bottom: 0px;">
            <div class="input-field col s3 m3">
                <select class="browser-default" name="sort" onchange="this.form.submit()">
                    <option value="date"
                            <c:if test="${empty param.sort or param.sort == 'date'}">
                                selected
                            </c:if>>
                        <fmt:message key="order-list.sort.by_date"/>
                    </option>
                    <option value="status"
                            <c:if test="${param.sort == 'status'}">
                                selected
                            </c:if>>
                        <fmt:message key="order-list.sort.by_status"/>
                    </option>
                    <c:if test="${user.role != 'CUSTOMER'}">
                    <option value="login"
                            <c:if test="${param.sort == 'login'}">
                                selected
                            </c:if>>
                        <fmt:message key="order-list.sort.by_login"/>
                    </option>
                    </c:if>
                </select>
            </div>
            <div class="input-field col s2 m2">
                <button class="waves-effect waves-teal btn-flat" name="order" onclick="this.form.submit()"
                        <c:choose>
                            <c:when test="${empty param.order or param.order != 'desc'}">
                                value="desc"
                            </c:when>
                            <c:otherwise>
                                value="asc"
                            </c:otherwise>
                        </c:choose>>
                    <c:choose>
                        <c:when test="${empty param.order or param.order != 'desc'}">
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
            <table class="centered striped">
                <thead>
                <tr>
                    <c:if test="${user.role != 'CUSTOMER'}">
                        <th><fmt:message key="user.name"/></th>
                    </c:if>
                    <th data-field="tour"><fmt:message key="tour.name"/></th>
                    <th data-field="discount"><fmt:message key="order.discount.label"/></th>
                    <th data-field="price"><fmt:message key="order.price.label"/></th>
                    <th data-field="status"><fmt:message key="order.status.label"/></th>
                    <th data-field="date"><fmt:message key="order.date.label"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order_bean" items="${orders}">
                    <%@include file="../jspf/order_item.jspf" %>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty orders}">
                <h5 class="center">
                    <fmt:message key="order-list.not_found"/>
                </h5>
            </c:if>
        </div>
    </div>
    <c:if test="${pagination.pageCount > 1}">
        <div class="center-align">
            <paginator:display pagination="${pagination}"/>
        </div>
    </c:if>
    </div>
    <c:if test="${user.role == 'MANAGER' or user.role == 'ADMIN'}">
        <div class="col s12" id="summary">
        <table class="centered stripped">
            <thead>
            <tr>
                <th><fmt:message key="user.name"/></th>
                <th><fmt:message key="order.sum"/></th>
                <th><fmt:message key="order.reserved_sum"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="sum_item" items="${summary}">
                <tr>
                    <td>${sum_item.login}</td>
                    <td>${sum_item.paid}</td>
                    <td>${sum_item.reserved}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
    </c:if>
</t:wrapper>
