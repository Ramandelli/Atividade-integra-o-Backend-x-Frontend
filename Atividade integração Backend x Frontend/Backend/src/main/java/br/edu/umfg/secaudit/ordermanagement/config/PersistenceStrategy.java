package br.edu.umfg.secaudit.ordermanagement.config;

public enum PersistenceStrategy {
    IN_MEMORY,
    DATABASE,
    CSV;

    public boolean inMemory() {
        return IN_MEMORY.equals(this);
    }
}
