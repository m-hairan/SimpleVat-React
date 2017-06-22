package com.simplevat.dao;

import com.simplevat.entity.User;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import javax.transaction.TransactionRequiredException;

/**
 *
 * @author Hiren
 */
public interface UserDao {

    @Nullable
    User getUser(@Nonnull final int userId);

    void saveUser(@Nonnull final User user);

    void updateUser(@Nonnull final User user)throws IllegalArgumentException, TransactionRequiredException;
    
    void deleteUser(@Nonnull final User user);

    Optional<User> getUserByEmail(String emailAddress);
    
    List<User> findAll();
}
