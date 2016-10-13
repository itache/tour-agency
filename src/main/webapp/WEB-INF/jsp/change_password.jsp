<%@include file="../jspf/page.jspf" %>

<fmt:message key="change-password.title" var="title"/>

<t:wrapper title="${title}">
    <div class="container">
        <p class="flow-text">
            <fmt:message key="change-password.text"/>
        </p>
        <form action="/user/change_password" method="post" class="col s12">
            <input type="hidden" value="${param.token}" name="token">
            <div class="row input-field">
                <fmt:message key="enter.password.label" var="password_label"/>
                <t:input type="password" value="" name="password" label="${password_label}" err="${errors.password}"/>
            </div>
            <div class="row input-field">
                <fmt:message key="enter.pass_conf.label" var="pass_conf_label"/>
                <t:input type="password" value="" name="pass_conf" label="${pass_conf_label}" err="${errors.pass_conf}"/>
            </div>
            <button class="btn waves-effect waves-light lime" type="submit" name="action">
                <fmt:message key="change-password.button.change"/>
            </button>
        </form>
    </div>
</t:wrapper>