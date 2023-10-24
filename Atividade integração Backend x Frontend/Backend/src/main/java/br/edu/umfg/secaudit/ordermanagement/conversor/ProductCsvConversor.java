package br.edu.umfg.secaudit.ordermanagement.conversor;

import br.edu.umfg.secaudit.ordermanagement.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductCsvConversor implements CsvConversor<Product> {

    @Override
    public Product convert(String[] data) {
        var product = new Product();
        product.setId(Long.parseLong(data[0]));
        product.name = data[1];
        product.description = data[2];
        product.initialInventory = Double.parseDouble(data[3]);
        return product;
    }

    @Override
    public List<Object> convert(Product product) {
        var data = new ArrayList<>();
        data.add(product.getId());
        data.add(product.name);
        data.add(product.description);
        data.add(product.initialInventory);
        return data;
    }

}
