<%@include file="../jspf/page.jspf" %>
<%@ taglib uri="/WEB-INF/pagination.tld" prefix="paginator" %>

<fmt:message key="tour-edit.title" var="title"/>

<c:set value="${empty tour_bean ? param : tour_bean.tour}" var="source"/>
<c:if test="${not empty tour_bean}">
    <c:set var="description" value="${tour_bean.getDescription(locale)}"/>
</c:if>
<t:wrapper title="${title}">
    <div class="container">
        <h4 class="center-align">${description.title}</h4>
        <div class="row">
            <img height="300px" src="/images/tour_${source.id}_thumb.jpg" class="center-block">
        </div>
        <form class="row"
              action="${uri}"
              method="post">
            <input type="hidden" name="action"
            <c:if test="${not empty action}">
                   value="<c:out value="${action}"/>"
            </c:if>>
            <input name="name" type="hidden"
                    <c:choose>
                        <c:when test="${not empty description}">
                            value="<c:out value="${description.title}"/>"
                        </c:when>
                        <c:otherwise>
                            value="<c:out value="${param.name}"/>"
                        </c:otherwise>
                    </c:choose>
            />
            <input type="hidden" name="id"
            <c:if test="${not empty source.id}">
                   value="<c:out value="${source.id}"/>"
            </c:if>>
            <div class="row">
                <!--Discount step-->
                <div class="input-field col s2 offset-s1">
                    <fmt:message key="tour.discountStep.label" var="discountStep_label"/>
                    <t:input value="${source.discountStep}" type="number" name="discountStep"
                             label='${discountStep_label}' err="${errors.discountStep}"/>
                </div>
                <!--Max discount-->
                <div class="input-field col s2 offset-s2">
                    <fmt:message key="tour.maxDiscount.label" var="maxDiscount_label"/>
                    <t:input value="${source.maxDiscount}" type="number" name="maxDiscount"
                             label='${maxDiscount_label}' err="${errors.maxDiscount}"/>
                </div>
                <!--Hot-->
                <div class="input-field col s3 offset-s2">
                    <input type="checkbox" id="hot" name="hot" value="true"
                            <c:if test="${source.hot == true}"> checked
                            </c:if>/>
                    <label for="hot"><fmt:message key="tour.hot.label"/></label>
                </div>
            </div>
            <div class="center-align">
                <button type="submit" class="btn waves-green">
                    <fmt:message key="tour-edit.button.save"/>
                </button>
            </div>
        </form>
    </div>
</t:wrapper>
