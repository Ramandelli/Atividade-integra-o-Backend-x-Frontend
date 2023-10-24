package br.edu.umfg.secaudit.ordermanagement.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {

    public PersistenceStrategy getPersisteceStrategy() {
        String persistenceStrategy = System.getenv("PERSISTENCE_STRATEGY");
        if (persistenceStrategy == null || persistenceStrategy.isBlank()) {
            return PersistenceStrategy.IN_MEMORY;
        }
        return PersistenceStrategy.valueOf(persistenceStrategy);
    }

}
