package me.itache.constant;

/**
 * System messages holder.
 */
public class Message {

    public static final String ERR_TOO_MANY_OBJECTS_FOUND = "Found too many objects. Expected just one.";
    public static final String ERR_CANNOT_EXECUTE_QUERY = "Can not execute query";
    public static final String ERR_TOO_MANY_OBJECTS_UPDATED = "Updated too many objects. Expected just one.";
    public static final String ERR_TOO_MANY_OBJECTS_DELETED = "Deleted too many objects. Expected just one.";
    public static final String ERR_USER_NOT_SET = "Current user equal null";
    public static final String ERR_CANNOT_UPDATE_TOUR = "Can not update tour";
    public static final String ERR_CANNOT_PARSE_DATE = "Can not parse date";
    public static final String ERR_TRANSACTION_EXISTS = "Invalid state. Last transaction not end.";
    public static final String ERR_TRANSACTION_NOT_EXISTS = "Invalid state. Transaction not begin.";
    public static final String ERR_POOL_INIT_ALREADY = "Connection pool has been initialized already";
    public static final String ERR_POOL_NOT_INIT_ALREADY = "Connection pool has not been initialized yet";
    public static final String ERR_LOCALE_NOT_SUPPORTED = "Locale not supported";
    public static final String ERR_TRANSACTION_ROLLBACK = "Transaction was rolled back.";
    public static final String ERR_CANNOT_CREATE_ORDER = "Can not create order.Tour not found ";
    public static final String ERR_TOUR_SERVICE_NOT_INIT = "Tour service has not been initialize.";
    public static final String ERR_COMMAND_NOT_ALLOWED = "User with role %s not allowed do this command";
    public static final String ERR_CANNOT_SEND_MAIL = "Can not send mail";
    public static final String ERR_EMAIL_DUPLICATE = "Found duplicated email";
    public static final String ERR_CANNOT_FIND_USER_BY_TOKEN = "Can not find user by token user id : ";

    public static final String WRN_OBJECT_NOT_FOUND = "Object with given id not found";

    public static final String INFO_OBJECT_CREATED = "Object was created";
    public static final String INFO_TOUR_CHANGE_STATUS = "Tour was set as hot";


}
