package com.gmail.vladyslavnicko.shop.DTO;

import com.gmail.vladyslavnicko.shop.model.User;
import lombok.Data;

@Data
public class UserInfo {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;

    private String street;
    private String city;
    private String state;
    private String zip;

    public UserInfo getUserInfoFromUser(User user) {
        UserInfo info = new UserInfo();
        info.setId(user.getId());
        info.setFirstName(user.getFirstName());
        info.setLastName(user.getLastName());
        info.setEmail(user.getEmail());
        info.setDateOfBirth(String.valueOf(user.getDateOfBirth()));
        info.setCity(user.getAddress().getCity());
        info.setState(user.getAddress().getState());
        info.setStreet(user.getAddress().getStreet());
        info.setZip(user.getAddress().getZip());
        return info;
    }

}
