package graduation.raitrest.util;

import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.MenuDetailTo;
import graduation.raitrest.model.to.RestaurantTo;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    private Util() {
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }
/*
    public MenuDetails(Integer id,  Restaurant restaurant, String typeDish, String description,
                       String quantity, BigDecimal price, LocalDateTime dateTime) {
    */
    public static MenuDetailTo menuDetail_2_MenuDetailTo (MenuDetails menuDetail) {
        return new MenuDetailTo(menuDetail.getId(), menuDetail.getRestaurant().getId(),
                menuDetail.getTypeDish(), menuDetail.getDescription(),
                menuDetail.getQuantity(),menuDetail.getPrice(),menuDetail.getDateTime());
    }

    public static List<MenuDetailTo> menuDetail_2_MenuDetailTo(List<MenuDetails> menuDetails) {
        return menuDetails.stream().map(menuDetail -> menuDetail_2_MenuDetailTo(menuDetail)).collect(Collectors.toList());
    }

    public static RestaurantTo restaurant_2_RestaurantTo (Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(),restaurant.getName(), restaurant.getAddress(),restaurant.getOwner(),restaurant.getRegistered(),
                restaurant.getDescription(),restaurant.getManager().getId());
    }

    public static List<RestaurantTo> restaurant_2_RestaurantTo(List<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> restaurant_2_RestaurantTo(restaurant)).collect(Collectors.toList());
    }

}
