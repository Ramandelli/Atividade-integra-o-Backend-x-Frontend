package br.edu.umfg.secaudit.ordermanagement.domain;

import br.edu.umfg.secaudit.ordermanagement.repository.Entity;

import java.time.LocalDate;

public class Client implements Entity {

    public String firstname;
    public String lastname;
    public String document;
    public LocalDate birth;
    private long id;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }


}
