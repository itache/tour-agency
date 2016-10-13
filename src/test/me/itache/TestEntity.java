package me.itache;

import me.itache.dao.Identified;

import java.util.Date;

/**
 *
 */
public class TestEntity implements Identified {
    private Long integerUnsigned;
    private Integer integer;
    private String varchar;
    private String text;
    private TestEnum testEnum;
    private Date date;
    private Date dateTime;

    public enum TestEnum{
        ONE,TWO;
    }

    @Override
    public Long getId() {
        return integerUnsigned;
    }

    @Override
    public void setId(Long id) {

    }

    public Long getIntegerUnsigned() {
        return integerUnsigned;
    }

    public void setIntegerUnsigned(Long integerUnsigned) {
        this.integerUnsigned = integerUnsigned;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getVarchar() {
        return varchar;
    }

    public void setVarchar(String varchar) {
        this.varchar = varchar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TestEnum getTestEnum() {
        return testEnum;
    }

    public void setTestEnum(TestEnum testEnum) {
        this.testEnum = testEnum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
