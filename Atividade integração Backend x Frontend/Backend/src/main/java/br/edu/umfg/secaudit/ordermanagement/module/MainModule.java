package br.edu.umfg.secaudit.ordermanagement.module;

import br.edu.umfg.secaudit.ordermanagement.controller.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainModule {

    @Bean
    public Terminal nossoTerminal() {
        return new Terminal();
    }

}
