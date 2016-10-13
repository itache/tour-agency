package me.itache.model.entity;

import me.itache.dao.Identified;

import java.util.Date;

public class Tour implements Identified {
    private Long id;
    private int price;
    private boolean isHot;
    private Tour.Type type;
    private HotelLevel hotelLevel;
    private int persons;
    private Date start;
    private int duration;
    private String countryCode;
    private int discountStep;
    private int maxDiscount;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public HotelLevel getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(HotelLevel hotelLevel) {
        this.hotelLevel = hotelLevel;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getDiscountStep() {
        return discountStep;
    }

    public void setDiscountStep(int discountStep) {
        this.discountStep = discountStep;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public enum Type {
        VACATION,
        EXCURSION,
        SHOPPING
    }

    public enum HotelLevel {
        THREE_STARS,
        FOUR_STARS,
        FIVE_STARS
    }

}
