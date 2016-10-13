package me.itache.service;

import me.itache.constant.Common;
import me.itache.constant.Message;
import me.itache.dao.DAOFactory;
import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Pagination;
import me.itache.dao.modifier.Sorter;
import me.itache.helpers.CountryManager;
import me.itache.helpers.LocaleManager;
import me.itache.model.bean.TourBean;
import me.itache.model.entity.Tour;
import me.itache.utils.db.JdbcTransactionManager;
import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.dao.GenericDAO;
import me.itache.exception.ServiceException;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.TourDescription;
import me.itache.model.meta.TourDescriptionMeta;
import me.itache.model.meta.TourMeta;
import me.itache.utils.imagesource.CloudinaryImageSource;
import me.itache.utils.imagesource.ImageSource;
import me.itache.utils.db.TransactionManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Provides business logic for tour actions
 */
public class TourService {
    private static final Logger LOG = Logger.getLogger(TourService.class);
    public static final int THUMB_WIDTH = 500;
    public static final int THUMB_HEIGHT = 500;

    private static String IMAGE_PATH;
    private static boolean IS_INIT;

    private GenericDAO<Tour> tourDAO = DAOFactory.instance().getTourDao();
    private HashMap<Locale, GenericDAO<TourDescription>> descriptionsDaoMap;

    /**
     * @param imagePath path to tour images
     */
    public static void init(String imagePath) {
        IMAGE_PATH = imagePath;
        IS_INIT = true;
    }

    /**
     * Creates service for current locale
     */
    public TourService() {
        this(LocaleManager.instance().getCurrentLocale());
    }

    /**
     * Creates service for given locales
     * @param locales
     */
    public TourService(Locale... locales) {
        if(!IS_INIT) {
            LOG.error(Message.ERR_TOUR_SERVICE_NOT_INIT);
            throw new IllegalStateException(Message.ERR_TOUR_SERVICE_NOT_INIT);
        }
        descriptionsDaoMap = new HashMap<>();
        for (Locale locale : locales) {
            descriptionsDaoMap.put(locale, DAOFactory.instance().getTourDescriptionDao(locale));
        }
    }

    /**
     * Returns all tours that satisfied given condition, in specified order for given page.
     * Hot tours always on top of the list.
     *
     * @param sorter
     * @param pagination
     * @param conditions
     * @return list of tours
     */
    public List<TourBean> get(Sorter sorter, Pagination pagination, EntityCondition[] conditions) {
        Locale locale = LocaleManager.instance().getCurrentLocale();
        GenericDAO<Tour> tourDAO = DAOFactory.instance().getTourDao(locale);
        pagination.setItemsCount(tourDAO.count(conditions));
        //Hot tours always on top
        sorter.addPrevailCondition(TourMeta.IS_HOT.getColumnName(), Sorter.Order.DESC);
        List<Tour> tours = tourDAO.get(sorter, pagination, conditions);
        return tours.stream().map(this::buildBean).collect(Collectors.toList());
    }

    /**
     * Set tour hot.
     * @param id tour id
     */
    public void setHot(Long id) {
        changeTourStatus(id, true);
    }

    /**
     * Set tour regular.
     * @param id tour id
     */
    public void setRegular(Long id) {
        changeTourStatus(id, false);
    }

    /**
     * @param id
     * @return tour with given id or null if tour not exists
     */
    public TourBean getByPk(Long id) {
        Tour tour = tourDAO.getByPK(id);
        return buildBean(tour);
    }

    /**
     * Updates tour info from request parameters.
     *
     * @param tour_id
     * @param parameterMap
     */
    public void update(Long tour_id, Map<String, RequestParameter> parameterMap) {
        TransactionManager manager = new JdbcTransactionManager();
        manager.execute(() -> {
            Tour tour = createTour(parameterMap);
            tour.setId(tour_id);
            updateDescriptions(tour.getId(), parameterMap);
            tourDAO.update(tour);
            return null;
        });
        CountryManager.refreshAvailable();
    }

    /**
     * Delete tour with given id.
     *
     * @param id
     * @return true if tour was deleted and false otherwise
     */
    public boolean delete(long id) throws IOException {
        TransactionManager transactionManager = new JdbcTransactionManager();
        boolean isDelete = transactionManager.execute(() -> {
            boolean result = tourDAO.delete(id);
            for (Map.Entry<Locale, GenericDAO<TourDescription>> descriptionDAO : descriptionsDaoMap.entrySet()) {
                descriptionDAO.getValue().delete(EntityCondition.eq(TourDescriptionMeta.TOUR_ID, id));
            }
            return result;
        });
        ImageSource source = new CloudinaryImageSource();
        source.delete(getTourImageName(id));
        source.delete(getTourImageThumbName(id));
        return isDelete;
    }

    /**
     * Creates tour from request parameters.
     *
     * @param parameterMap
     * @return new tour id
     */
    public long create(Map<String, RequestParameter> parameterMap) {
        TransactionManager transactionManager = new JdbcTransactionManager();
        long tourID =  transactionManager.execute(() -> {
            Tour tour = createTour(parameterMap);
            tour = tourDAO.persist(tour);
            createDescriptions(tour.getId(), parameterMap);
            return tour.getId();
        });
        CountryManager.refreshAvailable();
        return tourID;
    }

    /**
     * Upload image for tour with given id from request.
     *
     * @param id
     * @param request
     */
    public void uploadTourImage(long id, HttpServletRequest request){
        try {
            ImageSource source = new CloudinaryImageSource();
            byte[] content = ImageSource.getUploadedImageAsStream(request);
            source.save(content, getTourImageName(id));
            source.createThumb(content, getTourImageThumbName(id), THUMB_WIDTH, THUMB_HEIGHT);
        } catch (Exception e) {
            LOG.error("Can not save tour image" + e.getMessage());
            throw new ServiceException(e);
        }
        LOG.info("Image uploaded for tour:" + id);
    }

    /**
     * Updates discount step, maximal discount and tour status from request parameters.
     * @param id
     * @param parametersMap
     */
    public void updateStatusAndDiscount(long id, HashMap<String, RequestParameter> parametersMap) {
        Tour tour = tourDAO.getByPK(id);
        tour.setHot(Boolean.valueOf(parametersMap.get(Parameter.TOUR_HOT).getValue()));
        tour.setDiscountStep(Integer.parseInt(parametersMap.get(Parameter.TOUR_DISCOUNT_STEP).getValue()));
        tour.setMaxDiscount(Integer.parseInt(parametersMap.get(Parameter.TOUR_MAX_DISCOUNT).getValue()));
        tourDAO.update(tour);
    }

    private void createDescriptions(long tour_id, Map<String, RequestParameter> parameterMap) {
        for (Map.Entry<Locale, GenericDAO<TourDescription>> descriptionDAO : descriptionsDaoMap.entrySet()) {
            TourDescription description = new TourDescription();
            Locale locale = descriptionDAO.getKey();
            description.setTourId(tour_id);
            bindDescription(parameterMap, description, locale);
            descriptionDAO.getValue().persist(description);
        }
    }

    private void bindDescription(Map<String, RequestParameter> parameterMap, TourDescription description, Locale locale) {
        description.setTitle(parameterMap
                .get(Parameter.TOUR_NAME + "_" + locale.toLanguageTag()).getValue());
        description.setDescription(parameterMap
                .get(Parameter.TOUR_DESCRIPTION + "_" + locale.toLanguageTag()).getValue());
    }

    private void updateDescriptions(long tour_id, Map<String, RequestParameter> parameterMap) {
        for (Map.Entry<Locale, GenericDAO<TourDescription>> descriptionDAO : descriptionsDaoMap.entrySet()) {
            TourDescription description = getTourDescription(tour_id, descriptionDAO.getValue());
            Locale locale = descriptionDAO.getKey();
            bindDescription(parameterMap, description, locale);
            descriptionDAO.getValue().update(description);
        }
    }

    private TourBean buildBean(Tour tour) {
        if(tour == null) {
            return null;
        }
        TourBean bean = new TourBean();
        bean.setTour(tour);
        for (Map.Entry<Locale, GenericDAO<TourDescription>> descriptionDAO : descriptionsDaoMap.entrySet()) {
            //TODO DAO method getOne()
            TourDescription description = getTourDescription(tour.getId(), descriptionDAO.getValue());
            bean.setDescription(descriptionDAO.getKey(), description);
        }
        return bean;
    }

    private TourDescription getTourDescription(Long id, GenericDAO<TourDescription> dao) {
        return dao.get(EntityCondition.eq(TourDescriptionMeta.TOUR_ID, String.valueOf(id))).get(0);
    }

    private void changeTourStatus(Long id, boolean isHot) {
        Tour tour = tourDAO.getByPK(id);
        if (tour != null) {
            tour.setHot(isHot);
            tourDAO.update(tour);
            LOG.info(Message.INFO_TOUR_CHANGE_STATUS + ". ID: " + tour.getId() + "Hot: " + isHot);
        }
    }

    private Tour createTour(Map<String, RequestParameter> parameterMap) {
        //TODO RequestEntityMapper
        Tour tour = new Tour();
        tour.setPrice(Integer.parseInt(parameterMap.get(Parameter.TOUR_PRICE).getValue()));
        tour.setHot(Boolean.parseBoolean(parameterMap.get(Parameter.TOUR_HOT).getValue()));
        tour.setType(Tour.Type.valueOf(parameterMap.get(Parameter.TOUR_TYPE).getValue().toUpperCase()));
        tour.setHotelLevel(Tour.HotelLevel.valueOf(parameterMap.get(Parameter.TOUR_HOTEL_LEVEL).getValue().toUpperCase()));
        tour.setPersons(Integer.parseInt(parameterMap.get(Parameter.TOUR_PERSONS).getValue()));
        DateFormat df = new SimpleDateFormat(Common.DATE_PATTERN);
        df.setLenient(false);
        try {
            tour.setStart(df.parse(parameterMap.get(Parameter.TOUR_START).getValue()));
        } catch (ParseException e) {
            LOG.error(Message.ERR_CANNOT_PARSE_DATE + parameterMap.get(Parameter.TOUR_START).getValue());
            throw new ServiceException(Message.ERR_CANNOT_PARSE_DATE, e);
        }
        tour.setDuration(Integer.parseInt(parameterMap.get(Parameter.TOUR_DURATION).getValue()));
        tour.setCountryCode(parameterMap.get(Parameter.TOUR_COUNTRY_CODE).getValue());
        tour.setDiscountStep(Integer.parseInt(parameterMap.get(Parameter.TOUR_DISCOUNT_STEP).getValue()));
        tour.setMaxDiscount(Integer.parseInt(parameterMap.get(Parameter.TOUR_MAX_DISCOUNT).getValue()));
        return tour;
    }

    private String getTourImageThumbName(long id) {
        return "tour_" + id + "_thumb.jpg";
    }

    private String getTourImageName(long id) {
        return "tour_" + id + ".jpg";
    }
}
