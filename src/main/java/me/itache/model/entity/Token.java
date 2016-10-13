package me.itache.model.entity;

import me.itache.constant.Parameter;
import me.itache.constant.Path;
import me.itache.dao.Identified;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Token implements Identified {
    private static final int EXPIRATION = 60 * 24;

    protected Long id;

    protected String token;

    protected Long userId;

    protected Date expiryDate;

    public Token() {
        super();
    }

    public Token(String token, Long userId) {
        super();
        this.token = token;
        this.userId = userId;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public static String getPasswordResetLink(HttpServletRequest request, Token resetToken) {
        return "http://" + request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath() + Path.CHANGE_PASSWORD +"?" + Parameter.TOKEN + "=" + resetToken.getToken();
    }

    public static String getConfirmationLink(HttpServletRequest request, Token newToken) {
        return "http://" + request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath() + Path.CONFIRM_REGISTRATION +"?" + Parameter.TOKEN + "=" + newToken.getToken();
    }
}
