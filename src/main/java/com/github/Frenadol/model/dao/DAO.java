package com.github.Frenadol.model.dao;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T,K> extends Closeable {
    T saveCharacter(T entity);
    T findById_character(T id);
    T findByName(K name);
    T findByCategory(K category);
    T findByType(K type);
    T findByRarety(K rarety);
    List<T> findAll();

}
