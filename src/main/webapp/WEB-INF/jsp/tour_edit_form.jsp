<%@include file="../jspf/page.jspf" %>
<%@ taglib uri="/WEB-INF/pagination.tld" prefix="paginator" %>
<%@page import="me.itache.helpers.CountryManager" %>
<c:choose>
    <c:when test="${action == 'create'}">
        <fmt:message key="tour-create.title" var="title"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="tour-edit.title" var="title"/>
    </c:otherwise>
</c:choose>



<c:set value="${empty tour_bean ? param : tour_bean.tour}" var="source"/>
<c:if test="${not empty tour_bean}">
    <c:set var="descriptions" value="${tour_bean.descriptions}"/>
</c:if>

<t:wrapper title="${title}">
    <div class="container">
        <h4 class="center-align"><fmt:message key="tour-edit.tour_info"/></h4>
        <c:if test="${action != 'create'}">
            <img height="300px" src="/images/tour_${source.id}_thumb.jpg" class="center-block">
        </c:if>
        <form class="row"
              action="${uri}"
              method="post">
            <input type="hidden" name="action"
            <c:if test="${not empty action}">
                   value="<c:out value="${action}"/>"
            </c:if>>
            <input type="hidden" name="id"
            <c:if test="${not empty source.id}">
                   value="<c:out value="${source.id}"/>"
            </c:if>>
            <!--Name-->
            <ul class="tabs">
                <%@page import="me.itache.helpers.LocaleManager" %>
                <c:forEach items="${LocaleManager.instance().getAvailableLocales()}" var="loc">
                    <li class="tab col">
                        <a href="#${loc.key}">
                                ${loc.value}
                        </a>
                    </li>
                </c:forEach>
                <div class="indicator amber darken-4" style="z-index:1"></div>
            </ul>
            <c:forEach items="${LocaleManager.instance().getAvailableLocales()}" var="loc">
                <div id="${loc.key}">
                    <div class="input-field col s12">
                        <c:set var="name" value="name_${loc.value}"/>
                        <c:set var="name_val" value="${not empty descriptions ? descriptions.get(loc.value).title : param[name]}"/>
                        <fmt:message key="tour-edit.${name}" var="label_name"/>
                        <t:input value="${name_val}" type="text" name="${name}" label="${label_name}" err="${errors[name]}"/>
                    </div>
                    <div class="input-field col s12">
                        <c:set var="desc" value="description_${loc.value}"/>
                        <fmt:message key="tour-edit.${desc}" var="label_desc"/>
                        <c:set var="desc_val" value="${not empty descriptions ? descriptions.get(loc.value).description : param[desc]}"/>
                        <t:input value="${desc_val}" type="textarea" name="${desc}" label="${label_desc}" err="${errors[desc]}"/>
                    </div>
                </div>
            </c:forEach>
            <div class="row">
                <!--Price-->
                <div class="input-field col s2">
                    <fmt:message key="tour.price.label" var="price_label"/>
                    <t:input value="${source.price}" type="number" name="price" label="${price_label}" err="${errors.price}"/>
                </div>
                <!--Discount step-->
                <div class="input-field col s2 offset-s1">
                    <fmt:message key="tour.discountStep.label" var="discountStep_label"/>
                    <t:input value="${source.discountStep}" type="number" name="discountStep"
                             label='${discountStep_label}' err="${errors.discountStep}"/>
                </div>
                <!--Max discount-->
                <div class="input-field col s2 offset-s1">
                    <fmt:message key="tour.maxDiscount.label" var="maxDiscount_label"/>
                    <t:input value="${source.maxDiscount}" type="number" name="maxDiscount"
                             label='${maxDiscount_label}' err="${errors.maxDiscount}"/>
                </div>
                <!--Hot-->
                <div class="input-field col s3 offset-s1">
                    <input type="checkbox" id="hot" name="hot" value="true"
                            <c:if test="${source.hot == true}"> checked
                            </c:if>/>
                    <label for="hot"><fmt:message key="tour.hot.label"/></label>
                </div>
                </div>
            <div class="row">
                <!--Country-->
                <div class="input-field col s4">

                    <select name="countryCode" required>
                        <option value=""
                                <c:if test="${empty param.country}">
                                    selected
                                </c:if> >
                            <fmt:message key="tour.country.choose"/></option>
                        </option>
                        <c:forEach items="${CountryManager.getAllCountries(locale)}" var="country">
                            <option value="${country.value}"
                                    <c:if test="${country.value == source.countryCode}">
                                        selected
                                    </c:if>>
                                    ${country.key}
                            </option>
                        </c:forEach>
                    </select>
                    <label><fmt:message key="tour.country.label"/> </label>
                </div>
            <!--Type-->
            <div class="input-field col s3 offset-s1">
                <select name="type" required>
                    <option disabled value=""
                            <c:if test="${empty source.type}">
                                selected
                            </c:if>>
                        <fmt:message key="tour.type.choose"/>
                    </option>
                    <option value="SHOPPING"
                            <c:if test="${source.type == 'SHOPPING'}">
                                selected
                            </c:if>>
                        <fmt:message key="tour.type.shopping"/>
                    </option>
                    <option value="EXCURSION"
                            <c:if test="${source.type == 'EXCURSION'}">
                                selected
                            </c:if>>
                        <fmt:message key="tour.type.excursion"/>
                    </option>
                    <option value="VACATION"
                            <c:if test="${source.type == 'VACATION'}">
                                selected
                            </c:if>>
                        <fmt:message key="tour.type.vacation"/>
                    </option>
                </select>
                <label><fmt:message key="tour.type.label"/></label>
            </div>
            <!--Hotel level-->
            <div class="input-field col s3 offset-s1">

                <select name="hotelLevel" required>
                    <option value=""
                            <c:if test="${empty source.hotelLevel}">
                                selected
                            </c:if>>
                        <fmt:message key="tour.hotel_level.choose"/>
                    </option>
                    <option value="THREE_STARS"
                            <c:if test="${source.hotelLevel == 'THREE_STARS'}">
                                selected
                            </c:if>>
                        3&#x2605;
                    </option>
                    <option value="FOUR_STARS"
                            <c:if test="${source.hotelLevel  == 'FOUR_STARS'}">
                                selected
                            </c:if>>
                        4&#x2605;
                    </option>
                    <option value="FIVE_STARS"
                            <c:if test="${source.hotelLevel  == 'FIVE_STARS'}">
                                selected
                            </c:if>>
                        5&#x2605;
                    </option>
                </select>
                <label><fmt:message key="tour.hotel_level.label"/> </label>
            </div>
            </div>
            <div class="row">
            <!--Persons-->
            <div class="input-field col s2">
                <fmt:message key="tour.persons.label" var="persons_label"/>
                <t:input value="${source.persons}" type="number" name="persons" label="${persons_label}" err="${errors.persons}"/>
            </div>
            <!--Start-->
            <div class="input-field col s3 offset-s3">
                <fmt:message key="tour.start.label" var="start_label"/>
                <t:input value="${source.start}" type="date" name="start" label="${start_label}" err="${errors.start}"/>
            </div>
            <!--Duration-->
            <div class="input-field col s2 offset-s2">
                <fmt:message key="tour.duration.label" var="duration_label"/>
                <t:input value="${source.duration}" type="number" name="duration" label="${duration_label}" err="${errors.duration}"/>
            </div>
            </div>
            <div class="center-align">
            <button type="submit" class="btn waves-green">
                <fmt:message key="${action == 'create' ? 'tour-edit.button.create' : 'tour-edit.button.save'}"/>
            </button>
            </div>
        </form>
    </div>
    <c:if test="${locale != 'en'}">
        <script src="/js/datepicker_${locale}.js"></script>
    </c:if>
    <script>
        $('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15, // Creates a dropdown of 15 years to control year
            format: 'yyyy-mm-dd'
        });

    </script>
</t:wrapper>
