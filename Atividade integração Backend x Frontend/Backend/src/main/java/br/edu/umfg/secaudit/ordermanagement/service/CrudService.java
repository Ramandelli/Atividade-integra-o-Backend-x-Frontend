package br.edu.umfg.secaudit.ordermanagement.service;

import br.edu.umfg.secaudit.ordermanagement.exception.NotFoundException;
import br.edu.umfg.secaudit.ordermanagement.repository.CrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.Entity;
import br.edu.umfg.secaudit.ordermanagement.validation.CrudValidator;

import java.util.Collection;

public class CrudService<T extends Entity> {

    private final CrudRepository<T> crudRepository;
    private final CrudValidator<T> crudValidator;

    public CrudService(CrudRepository<T> crudRepository, CrudValidator<T> crudValidator) {
        this.crudRepository = crudRepository;
        this.crudValidator = crudValidator;
    }

    public void create(T entity) {
        crudValidator.validate(entity);
        crudRepository.insert(entity);
    }

    public void update(T entity) {
        crudValidator.validate(entity);
        crudRepository.update(entity);
    }

    public void remove(long id) {
        var entity = crudRepository.findById(id);
        if (entity != null) {
            crudRepository.delete(id);
        }
        throw new NotFoundException("Não foi encontrado um registro com o ID informado!");
    }

    public T findById(long id) {
        var entity = crudRepository.findById(id);
        if (entity != null) {
            return entity;
        }
        throw new NotFoundException("Não foi encontrado um registro com o ID informado!");
    }

    public Collection<T> findAll() {
        return crudRepository.findAll();
    }
}
