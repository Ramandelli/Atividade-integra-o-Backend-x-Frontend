package br.edu.umfg.secaudit.ordermanagement.repository.jdbc;

import br.edu.umfg.secaudit.ordermanagement.exception.PersistenceException;
import br.edu.umfg.secaudit.ordermanagement.repository.CrudRepository;
import br.edu.umfg.secaudit.ordermanagement.config.JdbcConfig;
import br.edu.umfg.secaudit.ordermanagement.domain.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ProductRepository implements CrudRepository<Product> {

    private final JdbcConfig jdbcConfig;

    public ProductRepository(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    @Override
    public void insert(Product product) {
        var sql = "INSERT INTO product (name, description, initial_inventory) VALUES (?, ?, ?)";
        try (var conn = jdbcConfig.connection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.name);
            ps.setString(2, product.description);
            ps.setDouble(3, product.initialInventory);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível incluir o produto.");
        }
    }

    @Override
    public void update(Product product) {
        var sql = "UPDATE product SET name=?, description=?, initial_inventory=? WHERE id=?";

        try (var conn = jdbcConfig.connection();
             var ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.name);
            ps.setString(2, product.description);
            ps.setDouble(3, product.initialInventory);
            ps.setLong(4, product.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível alterar o produto.");
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM product WHERE id=?";
        try (var conn = jdbcConfig.connection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível deletar o produto.");
        }
    }

    @Override
    public Product findById(long id) {
        String selectSQL = "SELECT * FROM product WHERE id = ?";

        try (var conn = jdbcConfig.connection();
             var preparedStatement = conn.prepareStatement(selectSQL)) {

            preparedStatement.setLong(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.name = resultSet.getString("name");
                    product.description = resultSet.getString("description");
                    product.initialInventory = resultSet.getDouble("initial_inventory");
                    return product;
                } else {
                    return null;
                }
            }

        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível encontrar o produto.");

        }
    }

    @Override
    public Collection<Product> findAll() {
        try (var connection = jdbcConfig.connection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM product");
             var resultSet = preparedStatement.executeQuery()) {

            var products = new ArrayList<Product>();

            while (resultSet.next()) {
                var product = new Product();
                product.setId(resultSet.getLong("id"));
                product.name = resultSet.getString("name");
                product.description = resultSet.getString("description");
                product.initialInventory = resultSet.getDouble("initial_inventory");
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new PersistenceException("Não foi possível encontrar os produtos.");
        }
    }
}