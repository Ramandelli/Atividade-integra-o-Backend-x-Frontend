package br.edu.umfg.secaudit.ordermanagement.validation;

import br.edu.umfg.secaudit.ordermanagement.repository.Entity;

public interface CrudValidator<T extends Entity> {
    void validate(T entity);
}
