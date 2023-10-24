package br.edu.umfg.secaudit.ordermanagement.module;

import br.edu.umfg.secaudit.ordermanagement.config.JdbcConfig;
import br.edu.umfg.secaudit.ordermanagement.config.SystemConfig;
import br.edu.umfg.secaudit.ordermanagement.conversor.CsvConversor;
import br.edu.umfg.secaudit.ordermanagement.conversor.ProductCsvConversor;
import br.edu.umfg.secaudit.ordermanagement.domain.Product;
import br.edu.umfg.secaudit.ordermanagement.repository.CrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.csv.CsvCrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.memory.InMemoryCrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.jdbc.ProductRepository;
import br.edu.umfg.secaudit.ordermanagement.sequence.CsvSequence;
import br.edu.umfg.secaudit.ordermanagement.sequence.InMemorySequence;
import br.edu.umfg.secaudit.ordermanagement.sequence.NoSequence;
import br.edu.umfg.secaudit.ordermanagement.sequence.Sequence;
import br.edu.umfg.secaudit.ordermanagement.service.CrudService;
import br.edu.umfg.secaudit.ordermanagement.validation.CrudValidator;
import br.edu.umfg.secaudit.ordermanagement.validation.ProductValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductModule {

    @Bean
    public CrudService<Product> productService(CrudRepository<Product> productCrudRepository,
                                               CrudValidator<Product> productCrudValidator) {
        return new CrudService<>(productCrudRepository, productCrudValidator);
    }

    @Bean
    public Sequence productSequence(SystemConfig config) {
        var value = config.getPersisteceStrategy();
        return switch (value) {
            case IN_MEMORY -> new InMemorySequence();
            case CSV -> new CsvSequence("db/sequences/seq_product");
            case DATABASE -> new NoSequence();
        };
    }

    @Bean
    public CrudValidator<Product> productValidator() {
        return new ProductValidator();
    }

    @Bean
    public CrudRepository<Product> productRepository(SystemConfig config, JdbcConfig jdbcConfig,
                                                     CsvConversor<Product> productCsvConversor,
                                                     Sequence productSequence) {
        var value = config.getPersisteceStrategy();
        return switch (value) {
            case IN_MEMORY -> new InMemoryCrudRepository<>();
            case CSV -> new CsvCrudRepository<>("db/tables/clients", productCsvConversor, productSequence);
            case DATABASE -> new ProductRepository(jdbcConfig);
        };
    }

    @Bean
    public CsvConversor<Product> productCsvConversor() {
        return new ProductCsvConversor();
    }

}
