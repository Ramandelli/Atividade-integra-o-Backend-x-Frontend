package br.edu.umfg.secaudit.ordermanagement.repository;

import java.util.Collection;

public interface CrudRepository<T extends Entity> {

    void insert(T entity);

    void update(T entity);

    void delete(long id);

    T findById(long id);

    Collection<T> findAll();
}
