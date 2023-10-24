package br.edu.umfg.secaudit.ordermanagement.repository.jdbc;

import br.edu.umfg.secaudit.ordermanagement.exception.PersistenceException;
import br.edu.umfg.secaudit.ordermanagement.repository.CrudRepository;
import br.edu.umfg.secaudit.ordermanagement.config.JdbcConfig;
import br.edu.umfg.secaudit.ordermanagement.domain.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ClientRepository implements CrudRepository<Client> {

    private final JdbcConfig jdbcConfig;

    public ClientRepository(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    @Override
    public void insert(Client client) {
        var sql = "INSERT INTO client (firstname, lastname, document, birth) VALUES (?, ?, ?, ?)";
        try (var conn = jdbcConfig.connection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.firstname);
            ps.setString(2, client.lastname);
            ps.setString(3, client.document);
            ps.setDate(4, java.sql.Date.valueOf(client.birth));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível incluir o cliente");
        }
    }

    @Override
    public void update(Client client) {
        var sql = "UPDATE client SET firstname=?, lastname=?, document=?, birth=? WHERE id=?";

        try (var conn = jdbcConfig.connection();
             var ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.firstname);
            ps.setString(2, client.lastname);
            ps.setString(3, client.document);
            ps.setDate(4, java.sql.Date.valueOf(client.birth));
            ps.setLong(5, client.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível alterar o cliente");
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM client WHERE id=?";
        try (var conn = jdbcConfig.connection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível deletar o cliente");
        }
    }

    @Override
    public Client findById(long id) {
        String selectSQL = "SELECT * FROM client WHERE id = ?";

        try (var conn = jdbcConfig.connection();
             var preparedStatement = conn.prepareStatement(selectSQL)) {

            preparedStatement.setLong(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client();
                    client.setId(resultSet.getLong("id"));
                    client.firstname = resultSet.getString("firstname");
                    client.lastname = resultSet.getString("lastname");
                    client.document = resultSet.getString("document");
                    client.birth = resultSet.getDate("birth").toLocalDate();
                    return client;
                } else {
                    return null;
                }
            }

        } catch (SQLException ex) {
            throw new PersistenceException("Não foi possível encontrar o cliente");
        }
    }

    @Override
    public Collection<Client> findAll() {
        try (var connection = jdbcConfig.connection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM client");
             var resultSet = preparedStatement.executeQuery()) {

            var clients = new ArrayList<Client>();

            while (resultSet.next()) {
                var client = new Client();
                client.setId(resultSet.getLong("id"));
                client.firstname = resultSet.getString("firstname");
                client.lastname = resultSet.getString("lastname");
                client.document = resultSet.getString("document");
                client.birth = resultSet.getDate("birth").toLocalDate();

                clients.add(client);
            }

            return clients;
        } catch (SQLException e) {
            throw new PersistenceException("Não foi possível buscar os clientes");
        }
    }

}
