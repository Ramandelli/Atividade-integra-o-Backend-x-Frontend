package br.edu.umfg.secaudit.ordermanagement.domain;

import br.edu.umfg.secaudit.ordermanagement.repository.Entity;

public class Product implements Entity {

    public String name;
    public String description;
    public double initialInventory;
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
