package me.itache.constant;

/**
 * Contains servlet urls
 */
public class Path {
    /* Tour paths */
    public static final String TOUR_LIST = "/tour/list";
    public static final String TOUR_SET_HOT = "/tour/set_hot";
    public static final String TOUR_SET_REGULAR = "/tour/set_regular";
    public static final String TOUR_EDIT = "/tour/edit";
    public static final String TOUR_NEW = "/tour/new";
    public static final String TOUR_DELETE = "/tour/delete";
    public static final String TOUR_VIEW = "/tour/view";
    public static final String TOUR_UPDATE_IMAGE = "/tour/update_image";
    public static final String TOUR_UPLOAD_IMAGE = "/images/upload_tour_image";
    /* Order paths */
    public static final String ORDER_NEW = "/order/new";
    public static final String ORDER_LIST = "/order/list";
    public static final String ORDER_CUSTOMER_LIST = "/customer/orders";
    public static final String ORDER_SET_STATUS = "/order/set_status";
    /* User paths */
    public static final String ENTER = "/enter";
    public static final String USER_LIST = "/user/list";
    public static final String USER_BLOCK = "/user/block";
    public static final String USER_UNBLOCK = "/user/unblock";
    public static final String USER_BLOCKED_PAGE = "/user/blocked";
    public static final String LOGOUT = "/logout";
    /*Common paths*/
    public static final String DEFAULT = "/";
    public static final String SET_LANG = "/set_lang";
    public static final String SEND_CONTACT_MAIL = "/contact";
    public static final String TOKEN_RESEND = "/user/resend_token";
    public static final String CONFIRM_REGISTRATION = "/user/confirm";
    public static final String RESET_PASSWORD = "/user/reset_password";
    public static final String CHANGE_PASSWORD = "/user/change_password";
}
