package ru.danilov.voting.voting.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.danilov.voting.voting.dto.PersonDTO;
import ru.danilov.voting.voting.dto.VoteDTO;
import ru.danilov.voting.voting.dto.restaurant.DishDTO;
import ru.danilov.voting.voting.dto.restaurant.LunchMenuDTO;
import ru.danilov.voting.voting.dto.restaurant.LunchMenuItemDTO;
import ru.danilov.voting.voting.dto.restaurant.RestaurantDTO;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
@Component
public class ModelMapperUtil {

    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Vote convertToVote(VoteDTO voteDTO) {
        return this.modelMapper.map(voteDTO, Vote.class);
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return this.modelMapper.map(person, PersonDTO.class);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }

    public RestaurantDTO convertToRestaurantDTO(Restaurant restaurant) {
        return this.modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public LunchMenuDTO convertToLunchMenuDTO(LunchMenu lunchMenu) {
        List<LunchMenuItemDTO> lunchMenuItemsDTO = convertToListLunchMenuItemDTO(lunchMenu.getLunchMenuItems());
        LunchMenuDTO lunchMenuDTO = new LunchMenuDTO(convertToRestaurantDTO(lunchMenu.getRestaurant()), lunchMenuItemsDTO);

        return lunchMenuDTO;
    }
    public LunchMenuItemDTO convertToLunchMenuItemDTO(LunchMenuItem lunchMenuItem) {
        DishDTO dishDTO = modelMapper.map(lunchMenuItem.getDish(), DishDTO.class);

        return new LunchMenuItemDTO(dishDTO, lunchMenuItem.getPrice());
    }

    public List<LunchMenuItemDTO> convertToListLunchMenuItemDTO(List<LunchMenuItem> lunchMenuItems) {
        List<LunchMenuItemDTO> lunchMenuItemsDTO = new ArrayList<>(lunchMenuItems.size());
        for(LunchMenuItem lunchMenuItem : lunchMenuItems) {
            lunchMenuItemsDTO.add(convertToLunchMenuItemDTO(lunchMenuItem));
        }

        return lunchMenuItemsDTO;
    }

    public List<RestaurantDTO> convertToListRestaurantDTO(List<Restaurant> restaurants) {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>(restaurants.size());
        for(Restaurant restaurant : restaurants) {
            restaurantDTOList.add(convertToRestaurantDTO(restaurant));
        }

        return restaurantDTOList;
    }

    public List<LunchMenuDTO> convertToListLunchMenuDTO(List<LunchMenu> lunchMenus) {
        List<LunchMenuDTO> lunchMenuDTOList = new ArrayList<>(lunchMenus.size());
        for(LunchMenu lunchMenu: lunchMenus) {
            lunchMenuDTOList.add(convertToLunchMenuDTO(lunchMenu));
        }

        return lunchMenuDTOList;
    }


}
