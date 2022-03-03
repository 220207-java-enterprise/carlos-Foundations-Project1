package com.revature.erm.daos;

import java.util.List;

public interface CrudDAO<T> {

    void save(T newObject);

    T getById(String id);

    List<T> getAll();

    void update(T updatedObj);

    void deleteById(T objectToBeDeleted);//TODO Ryan said he used (T objectToBeDeleted)
}

