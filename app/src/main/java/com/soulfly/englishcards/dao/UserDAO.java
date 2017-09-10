package com.soulfly.englishcards.dao;

import com.soulfly.englishcards.model.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by Elena on 18.03.2017.
 */

public class UserDAO extends BaseDaoImpl<User, Integer> {

    public UserDAO(ConnectionSource connectionSource,
                   Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
