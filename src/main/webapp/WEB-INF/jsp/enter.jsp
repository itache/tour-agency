<%@include file="../jspf/page.jspf"%>

<fmt:message key="enter.title" var="title" />

<t:wrapper title="${title}">
    <div class="row">
        <div class="col s4 offset-s4 z-depth-1">
            <div class="col s12">
                <ul class="tabs">
                    <li class="tab col s6">
                        <a href="#sing_in" class="amber-text text-darken-4">
                            <fmt:message key="enter.sing_in"/>
                        </a>
                    </li>
                    <li class="tab col s6">
                        <a href="#sing_up" class="amber-text text-darken-4 ${action == 'sing_up' ? 'active' : ''}">
                            <fmt:message key="enter.sing_up"/>
                        </a>
                    </li>
                    <div class="indicator amber darken-4" style="z-index:1"></div>
                </ul>
            </div>
            <%@include file="../jspf/login_form.jspf"%>
            <%@include file="../jspf/singup_form.jspf"%>
        </div>
    </div>
</t:wrapper>

