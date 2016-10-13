<%@include file="../jspf/page.jspf" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="title" value="${tour_bean.getDescription(locale).title}"/>
<c:set var="tour" value="${tour_bean.tour}"/>
<t:wrapper title="${title}">
    <style>
        .parallax-container {
            height: 400px;
        }
    </style>
    <div class="parallax-container">
        <div class="parallax"><img src="/images/tour_${tour.id}.jpg"></div>
    </div>
    <div class="section white">
        <div class="row container">
            <h3>${tour_bean.getDescription(locale).title}</h3>
            <p class="grey-text text-darken-3 lighten-3">${tour_bean.getDescription(locale).description}</p>
            <div class="col s5">
                <p><fmt:message key="tour.price.label"/> : ${tour.price} $</p>
                <p><fmt:message key="tour.type.label"/>: <fmt:message
                        key="tour.type.${fn:toLowerCase(tour.type)}"/></p>
                <p><fmt:message key="tour.hotel_level.label"/> :
                    <fmt:message key="tour-item.hotel_level.${fn:toLowerCase(tour.hotelLevel)}"/>
                </p>
                <p>
                    <i class="material-icons" style="vertical-align: bottom !important;">people</i> :
                        ${tour.persons}
                </p>
            </div>
            <div class="col s5">
                <p>
                    <i class="material-icons" style="vertical-align: bottom !important;">place</i> :
                        ${tour_bean.country}
                </p>
                <p>
                    <i class="material-icons" style="vertical-align: bottom !important;">date_range</i> :
                    <fmt:formatDate value="${tour.start}" type="date"/> -
                    <fmt:formatDate value="${tour_bean.end}" type="date"/>
                </p>
                <p>
                    <i class="material-icons" style="vertical-align: bottom !important;">schedule</i> :
                        ${tour.duration} <fmt:message key="tour.duration.days"/>
                </p>
            </div>
            <c:if test="${user.role == 'CUSTOMER'}">
                <div class="row">
                    <div class="col s12 center-align">
                        <a href="/order/new?tour_id=${tour.id}" class="btn waves-effect lime"><fmt:message
                                key="tour-item.button.order"/></a>
                    </div>
                </div>
            </c:if>
            <c:if test="${user.role == 'ADMIN'}">
                <div class="row">
                    <div class="col s12 center-align">
                        <a href="/tour/edit?id=${tour.id}" class="btn waves-effect deep-orange"><fmt:message key="tour-item.button.edit"/></a>
                        <a href="/tour/update_image?id=${tour.id}" class="btn waves-effect lime"><fmt:message
                                key="tour-item.button.update_image"/></a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    </div>
    <div class="parallax-container">
        <div class="parallax"><img src="/images/tour_${tour.id}.jpg"></div>
    </div>
    <script>
        $(document).ready(function () {
            $('.parallax').parallax();
        });
    </script>
</t:wrapper>
