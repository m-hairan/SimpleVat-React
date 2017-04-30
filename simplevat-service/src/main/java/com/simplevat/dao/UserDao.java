package com.simplevat.dao;

import com.simplevat.entity.User;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 *
 * @author Hiren
 */
public interface UserDao {

    @Nullable
    User getUser(@Nonnull final int userId);

    void saveUser(@Nonnull final User user);

    void updateUser(@Nonnull final User user);

    Optional<User> getUserByEmail(String emailAddress);
}
