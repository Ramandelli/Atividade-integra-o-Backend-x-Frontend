package br.edu.umfg.secaudit.ordermanagement.conversor;

import br.edu.umfg.secaudit.ordermanagement.domain.Client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientCsvConversor implements CsvConversor<Client> {

    @Override
    public Client convert(String[] data) {
        var client = new Client();
        client.setId(Long.parseLong(data[0]));
        client.firstname = data[1];
        client.lastname = data[2];
        client.document = data[3];
        client.birth = LocalDate.parse(data[4]);
        return client;
    }

    @Override
    public List<Object> convert(Client client) {
        var data = new ArrayList<>();
        data.add(client.getId());
        data.add(client.firstname);
        data.add(client.lastname);
        data.add(client.document);
        data.add(client.birth);
        return data;
    }

}
