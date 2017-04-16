package com.simplevat.controller.user;

import com.simplevat.entity.User;
import com.simplevat.service.UserService;
import com.simplevat.user.model.UserModel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Hiren
 */
@RequestScoped
@ManagedBean
@Component
public class UserController {

    @Getter
    private UserModel currentUser;

    @Autowired
    private UserService userService;

    @PostConstruct
    void initController() {

        final User user = new User();
        currentUser = convertToModel(user);

    }
    
//    public void 

    @Nonnull
    private User convertToEntity(@Nonnull final UserModel userModel) {
        final LocalDateTime dob = LocalDateTime.ofInstant(userModel.getDateOfBirth().toInstant(), ZoneId.systemDefault());
        final User user = new User();

        // todo: chnage when company module is done.
        user.setCompanyId(0);
        user.setCreatedBy(0);
        user.setDateOfBirth(dob);
        user.setDeleteFlag(Boolean.FALSE);
        user.setEmailId(userModel.getUserEmailId());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setLastUpdatedBy(0);
        user.setRoleCode(0);
        return user;
    }

    @Nonnull
    private UserModel convertToModel(@Nonnull final User user) {
        final UserModel userModel = new UserModel();

        // todo: chnage when company module is done.
        userModel.setDateOfBirth(null != user.getDateOfBirth() ? Date.from(user.getDateOfBirth().atZone(ZoneId.systemDefault()).toInstant()) : null);
        userModel.setUserEmailId(user.getEmailId());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        return userModel;
    }

}
