package ua.com.foxminded.webuniversity.dao;

import java.util.List;

public interface GenericDAO<E, K> {
    
    E create(E entity);

    E update(E entity);

    E findOne(K id);

    List<E> findAll();

    void delete(K id);

}
