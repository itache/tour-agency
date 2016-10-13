package me.itache.model.bean;

import me.itache.helpers.LocaleManager;
import me.itache.model.entity.Tour;
import me.itache.model.entity.TourDescription;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Represents tour entity holder with description for several locales
 */
public class TourBean {
    private Tour tour;
    private HashMap<Locale, TourDescription> descriptions = new HashMap<>();

    public Long getId() {
        return tour.getId();
    }

    public int getPrice() {
        return tour.getPrice();
    }

    public Tour.Type getType() {
        return tour.getType();
    }

    public Date getStart() {
        return tour.getStart();
    }

    public Date getEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(tour.getStart());
        c.add(Calendar.DATE, tour.getDuration());  // number of days to add
        return c.getTime();  // dt is now the new date
    }

    public Tour.HotelLevel getHotelLevel() {
        return tour.getHotelLevel();
    }

    public int getPersons() {
        return tour.getPersons();
    }

    public int getDuration() {
        return tour.getDuration();
    }

    public boolean isHot() {
        return tour.isHot();
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getCountry() {
        Locale locale = LocaleManager.instance().getCurrentLocale();
        Locale countryLocale = new Locale("", tour.getCountryCode());
        return countryLocale.getDisplayCountry(locale);
    }

    public boolean isOutdated() {
        Calendar cal = Calendar.getInstance();
        return (tour.getStart().getTime() - cal.getTime().getTime()) <= 0;
    }

    public TourDescription getDescription(Locale locale) {
        return descriptions.get(locale);
    }

    public TourDescription getDescription() {
        return descriptions.get(LocaleManager.instance().getCurrentLocale());
    }

    public HashMap<Locale, TourDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescription(Locale locale, TourDescription description) {
        this.descriptions.put(locale, description);
    }
}