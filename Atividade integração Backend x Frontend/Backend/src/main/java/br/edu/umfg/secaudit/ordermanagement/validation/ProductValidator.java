package br.edu.umfg.secaudit.ordermanagement.validation;

import br.edu.umfg.secaudit.ordermanagement.domain.Product;

public class ProductValidator implements CrudValidator<Product> {

    @Override
    public void validate(Product product) {
        Preconditions.checkNotBlank(product.name, "Deve ser informado um nome!");
        Preconditions.checkNotBlank(product.description, "Deve ser informado uma descrição!");
        Preconditions.checkCondition(product.initialInventory >= 0, "Estoque inicial deve ser zero/positivo!");
    }

}
