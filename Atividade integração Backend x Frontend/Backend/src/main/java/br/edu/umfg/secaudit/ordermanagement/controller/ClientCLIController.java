package br.edu.umfg.secaudit.ordermanagement.controller;

import br.edu.umfg.secaudit.ordermanagement.service.CrudService;
import br.edu.umfg.secaudit.ordermanagement.domain.Client;
import org.springframework.shell.command.annotation.Command;

@Command(group = "Cadastro de Cliente")
public class ClientCLIController extends AbstractCLIController {

    public static final String LIST_FORMAT = "|%1$-10s|%2$-15s|%3$-15s|%4$-15s|%5$-15s|\n";
    public static final int TERMINAL_SIZE = 75;

    private final CrudService<Client> clientService;

    public ClientCLIController(CrudService<Client> clientService, Terminal terminal) {
        super(terminal);
        this.clientService = clientService;
    }

    @Command(alias = "ic", command = "incluir-cliente", description = "Adiciona um novo cliente na base de dados")
    public void incluir() {
        runWhile(() -> {
            terminal.printText("Incluindo um novo cliente");

            var cliente = new Client();
            cliente.firstname = terminal.readText("Nome");
            cliente.lastname = terminal.readText("Sobrenome");
            cliente.birth = terminal.readLocalDate("Nascimento");
            cliente.document = terminal.readText("CPF (sem pontuacão)");

            clientService.create(cliente);

            terminal.printText("Cliente inserido com sucesso!");
        });
    }

    @Command(alias = "ac", command = "alterar-cliente", description = "Altera um cliente na base de dados")
    public void update() {
        runWhile(() -> {
            terminal.printText(" Alterando um cliente");

            var clientId = terminal.readInteger("ID (que deseja alterar)");

            var cliente = clientService.findById(clientId);

            cliente.firstname = terminal.readTextOrReuse("Nome", cliente.firstname);
            cliente.lastname = terminal.readTextOrReuse("Sobrenome", cliente.lastname);
            cliente.birth = terminal.readLocalDateOrReuse("Nascimento", cliente.birth);
            cliente.document = terminal.readTextOrReuse("CPF (sem pontuacão)", cliente.document);

            clientService.update(cliente);

            terminal.printText(" Cliente alterado com sucesso");
        });
    }

    @Command(alias = "dc", command = "deletar-cliente", description = "Remove um cliente na base de dados")
    public void deletar() {
        runWhile(() -> {
            terminal.printText("Deletando um cliente");
            var id = terminal.readInteger("ID (que deseja deletar)");
            clientService.remove(id);
            terminal.printText("Cliente removido com sucesso");
        });
    }

    @Command(alias = "bc", command = "buscar-cliente", description = "Busca um único cliente por um ID informado.")
    public void buscarPorId() {
        runWhile(() -> {
            var id = terminal.readInteger("ID (que deseja buscar)");
            var cliente = clientService.findById(id);

            terminal.printSeparatorLine(TERMINAL_SIZE);
            terminal.printTableTitle("Cliente", TERMINAL_SIZE);
            terminal.printRow(LIST_FORMAT, "ID", "Nome", "Sobrenome", "CPF", "Nascimento");
            terminal.printSeparatorLine(TERMINAL_SIZE);
            String nascimento = terminal.formatarData(cliente.birth);
            terminal.printRow(LIST_FORMAT, cliente.getId(), cliente.firstname, cliente.lastname, cliente.document, nascimento);
            terminal.printSeparatorLine(TERMINAL_SIZE);
        });
    }

    @Command(alias = "lc", command = "listar-clientes", description = "Listagem de todos os clientes registrados")
    public void listarTodos() {
        runWhile(() -> {
            var clientes = clientService.findAll();
            terminal.printSeparatorLine(TERMINAL_SIZE);

            terminal.printTableTitle("Clientes", TERMINAL_SIZE);
            terminal.printSeparatorLine(TERMINAL_SIZE);

            terminal.printRow(LIST_FORMAT, "ID", "Nome", "Sobrenome", "CPF", "Nascimento");
            terminal.printSeparatorLine(TERMINAL_SIZE);

            for (Client cliente : clientes) {
                String nascimento = terminal.formatarData(cliente.birth);
                terminal.printRow(LIST_FORMAT, cliente.getId(), cliente.firstname, cliente.lastname, cliente.document, nascimento);
            }
            terminal.printSeparatorLine(TERMINAL_SIZE);
        });
    }


}
