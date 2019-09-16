package graduation.raitrest.service;

import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import graduation.raitrest.repository.MenuDetailsRepository;
import graduation.raitrest.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static graduation.raitrest.util.DateTimeUtil.adjustEndDateTime;
import static graduation.raitrest.util.DateTimeUtil.adjustStartDateTime;
import static graduation.raitrest.util.Util.menuDetail_2_MenuDetailTo;
import static graduation.raitrest.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuDetailsService {
    @Autowired
    private final MenuDetailsRepository menuDetailsRepository;

    public MenuDetailsService(MenuDetailsRepository menuDetailsRepository) {
        this.menuDetailsRepository = menuDetailsRepository;
    }

    public MenuDetails get(int id) throws NotFoundException {
        return checkNotFoundWithId(menuDetailsRepository.get(id), id);
    }

    public MenuDetails get(int id, int managerId) throws NotFoundException {
        return checkNotFoundWithId(menuDetailsRepository.get(id, managerId), id);
    }

    public List<MenuDetailTo> getAll() {
        return menuDetail_2_MenuDetailTo(menuDetailsRepository.getAll());
    }

    public List<MenuDetails> getAll(int managerId) {
        return menuDetailsRepository.getAll( managerId);
    }



    protected List<MenuDetails> getFilterByDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return menuDetailsRepository. getAllByDateTime(startDateTime, endDateTime);
    }
    protected List<MenuDetails> getFilterByDateTimesByRestaurantIdAndManagerId(LocalDateTime startDateTime, LocalDateTime endDateTime,int restaurantID,int managerId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return menuDetailsRepository. getAllByDateTimeByRestaurantIdAndManagerId(startDateTime, endDateTime,restaurantID,managerId);
    }
    protected List<MenuDetails> getFilterByDateTimesByRestaurantId(LocalDateTime startDateTime, LocalDateTime endDateTime,int restaurantID) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return menuDetailsRepository. getAllByDateTimeByRestaurantId(startDateTime, endDateTime,restaurantID);
    }

    protected List<MenuDetails> getFilterByDateTimesByManagerId(LocalDateTime startDateTime, LocalDateTime endDateTime,int managerId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return menuDetailsRepository. getAllByDateTimeByManagerId(startDateTime, endDateTime,managerId);
    }


    public List<MenuDetails> getFilterByDate( LocalDate startDate,  LocalDate endDate) {
        return getFilterByDateTimes(adjustStartDateTime(startDate), adjustEndDateTime(endDate));
    }
    public List<MenuDetails> getFilterByDateByManager( LocalDate startDate,  LocalDate endDate,int managerId) {
        return getFilterByDateTimesByManagerId(adjustStartDateTime(startDate), adjustEndDateTime(endDate),managerId);
    }

    public List<MenuDetails> getFilterByDateByRestaurant( LocalDate startDate,  LocalDate endDate,int  restaurantID) {
        return getFilterByDateTimesByRestaurantId(adjustStartDateTime(startDate), adjustEndDateTime(endDate),restaurantID);
    }


    public void update(MenuDetails menu, int managerId) {
        Assert.notNull(menu, "menu must not be null");
        Assert.notNull(menu.getRestaurant(), "menu#restaurant must not be null");
        checkNotFoundWithId(menuDetailsRepository.save(menu, managerId), menu.getId());
    }

    public void update(MenuDetails menu, int restaurantId, int managerId) {
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(menuDetailsRepository.save(menu,restaurantId, managerId), menu.getId());
    }

    public MenuDetails create(MenuDetails menu, int restaurantId, int managerId) {
        Assert.notNull(menu, "menu must not be null");
        return menuDetailsRepository.save(menu,restaurantId, managerId);
    }
    public MenuDetails create(MenuDetails menu,  int managerId) {
        Assert.notNull(menu, "menu must not be null");
        Assert.notNull(menu.getRestaurant(), "menu#restaurant must not be null");
        return menuDetailsRepository.save(menu, managerId);
    }

    public void delete(int id, int managerId) {
        checkNotFoundWithId(menuDetailsRepository.delete(id, managerId), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(menuDetailsRepository.delete(id), id);
    }

}
