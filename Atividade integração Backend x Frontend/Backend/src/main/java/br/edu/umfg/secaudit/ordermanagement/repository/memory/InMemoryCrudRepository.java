package br.edu.umfg.secaudit.ordermanagement.repository.memory;

import br.edu.umfg.secaudit.ordermanagement.exception.PersistenceException;
import br.edu.umfg.secaudit.ordermanagement.repository.CrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCrudRepository<T extends Entity> implements CrudRepository<T> {

    private final Map<Long, T> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public void insert(T entity) {
        long id = idGenerator.getAndIncrement();
        entity.setId(id);
        database.put(id, entity);
    }

    public void update(T entity) {
        long id = entity.getId();
        if (!database.containsKey(id)) {
            throw new PersistenceException("Não foi possível alterar o registro. Registro não encontrado para alteração.");
        }
        database.put(id, entity);
    }

    public void delete(long id) {
        if (!database.containsKey(id)) {
            throw new PersistenceException("Não foi possível deletar o registro. Registro não encontrado para deleção.");
        }
        database.remove(id);
    }

    public T findById(long id) {
        return database.get(id);
    }

    public Collection<T> findAll() {
        return database.values();
    }
}
