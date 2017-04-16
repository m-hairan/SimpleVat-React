package com.simplevat.dao;

import com.simplevat.entity.User;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author Hiren
 */
public interface UserDao {

    @Nullable
    User getUser(@Nonnull final int userId);

    void saveUser(@Nonnull final User user);

    void updateUser(@Nonnull final User user);

}
