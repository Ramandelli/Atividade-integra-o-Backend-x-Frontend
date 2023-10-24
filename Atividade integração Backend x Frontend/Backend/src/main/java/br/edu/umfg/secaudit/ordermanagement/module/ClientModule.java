package br.edu.umfg.secaudit.ordermanagement.module;

import br.edu.umfg.secaudit.ordermanagement.config.JdbcConfig;
import br.edu.umfg.secaudit.ordermanagement.config.SystemConfig;
import br.edu.umfg.secaudit.ordermanagement.conversor.ClientCsvConversor;
import br.edu.umfg.secaudit.ordermanagement.conversor.CsvConversor;
import br.edu.umfg.secaudit.ordermanagement.domain.Client;
import br.edu.umfg.secaudit.ordermanagement.repository.CrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.csv.CsvCrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.memory.InMemoryCrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.jdbc.ClientRepository;
import br.edu.umfg.secaudit.ordermanagement.sequence.CsvSequence;
import br.edu.umfg.secaudit.ordermanagement.sequence.InMemorySequence;
import br.edu.umfg.secaudit.ordermanagement.sequence.NoSequence;
import br.edu.umfg.secaudit.ordermanagement.sequence.Sequence;
import br.edu.umfg.secaudit.ordermanagement.service.CrudService;
import br.edu.umfg.secaudit.ordermanagement.validation.ClientValidator;
import br.edu.umfg.secaudit.ordermanagement.validation.CrudValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientModule {

    @Bean
    public CrudService<Client> clientService(CrudRepository<Client> clientRepository,
                                             CrudValidator<Client> clientValidator) {
        return new CrudService<>(clientRepository, clientValidator);
    }

    @Bean
    public Sequence clientSequence(SystemConfig config) {
        var value = config.getPersisteceStrategy();
        return switch (value) {
            case IN_MEMORY -> new InMemorySequence();
            case CSV -> new CsvSequence("db/sequences/seq_client");
            case DATABASE -> new NoSequence();
        };
    }

    @Bean
    public CrudValidator<Client> clientValidator() {
        return new ClientValidator();
    }

    @Bean
    public CrudRepository<Client> clientRepository(SystemConfig config,
                                                   JdbcConfig jdbcConfig,
                                                   CsvConversor<Client> clientCsvConversor,
                                                   Sequence clientSequence) {
        var value = config.getPersisteceStrategy();
        return switch (value) {
            case IN_MEMORY -> new InMemoryCrudRepository<>();
            case CSV -> new CsvCrudRepository<>("db/tables/clients", clientCsvConversor, clientSequence);
            case DATABASE -> new ClientRepository(jdbcConfig);
        };
    }

    @Bean
    public CsvConversor<Client> clientCsvConversor() {
        return new ClientCsvConversor();
    }

}
