<%@include file="../jspf/page.jspf" %>
<%@ taglib uri="/WEB-INF/pagination.tld" prefix="paginator" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:message key="tour-list.title" var="title"/>
<c:set var="css" value="${'/css/float-button.css,/css/delete-button.css, /css/hot.css'}"/>
<c:if test="${locale != 'en'}">
    <c:set var="js" value="/js/datepicker_${locale}.js"/>
</c:if>
<t:wrapper css="${css}" js="${js}" title="${title}">
    <div class="row" style="margin: 0px">
        <%@include file="../jspf/tour_search_form.jspf" %>
    </div>
    <div class="row">
        <ul class="col s12">
            <c:forEach var="tour_bean" items="${tours}">
                <%@include file="../jspf/tour_item.jspf" %>
            </c:forEach>
        </ul>
        <c:if test="${empty tours}">
            <h5 class="center">
                <fmt:message key="tour-list.not_found"/>
            </h5>
        </c:if>
    </div>
    <!--float button-->
    <c:if test="${user.role == 'ADMIN'}">
        <a href="/tour/new" class="float btn-large btn-floating waves-circle waves-effect lime tooltipped"
           data-position="left" data-delay="50" data-tooltip="Create new">
            <i class="material-icons">add</i>
        </a>
    </c:if>
    <c:if test="${pagination.pageCount > 1}">
        <div class="center-align">
            <paginator:display pagination="${pagination}"/>
        </div>
    </c:if>

    <script>
        $('.btn-delete').on('click', function() {
            var data_id =$(this).attr('data-id');
            noty({
                text: '<fmt:message key="tour-list.delete.confirmation"/> ',
                layout: 'center',
                type: 'warning',
                theme: 'relax',
                buttons: [
                    {addClass: 'btn-flat',
                        text: '<a class="deep-orange-text" href="/tour/delete?id='+ data_id + '"><fmt:message key="tour-list.delete.button"/> </a>', onClick: function($noty) {
                        $noty.close();
                    }
                    },
                    {addClass: 'btn-flat',
                        text: '<fmt:message key="tour-list.cancel.button"/>', onClick: function($noty) {
                        $noty.close();
                    }
                    }
                ]
            });
        });
    </script>
</t:wrapper>
