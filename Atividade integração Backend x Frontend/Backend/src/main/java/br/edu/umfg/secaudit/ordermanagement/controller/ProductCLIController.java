package br.edu.umfg.secaudit.ordermanagement.controller;

import br.edu.umfg.secaudit.ordermanagement.service.CrudService;
import br.edu.umfg.secaudit.ordermanagement.domain.Product;
import org.springframework.shell.command.annotation.Command;

@Command(group = "Cadastro de Produtos")
public class ProductCLIController extends AbstractCLIController {

    public static final String LIST_FORMAT = "|%1$-10s|%2$-30s|%3$-100s|%4$-20s|\n";
    private static final int TERMINAL_SIZE = 164;

    private final CrudService<Product> productService;

    public ProductCLIController(CrudService<Product> productService, Terminal terminal) {
        super(terminal);
        this.productService = productService;
    }

    @Command(alias = "ip", command = "incluir-produto", description = "Adiciona um novo produto na base de dados")
    public void incluir() {
        runWhile(() -> {
            terminal.printText("Incluindo um novo produto");

            Product product = new Product();
            product.name = terminal.readText("Nome");
            product.description = terminal.readText("Descrição");
            product.initialInventory = terminal.readDouble("Estoque inicial");

            productService.create(product);

            terminal.printText("Produto inserido com sucesso!");
        });
    }


    @Command(alias = "ap", command = "alterar-produto", description = "Altera um produto na base de dados")
    public void alterar() {
        runWhile(() -> {
            terminal.printText("Alterando um produto");

            var productId = terminal.readInteger("ID (que deseja alterar)");

            var product = productService.findById(productId);

            product.name = terminal.readTextOrReuse("Nome", product.name);
            product.description = terminal.readTextOrReuse("Descrição", product.description);
            product.initialInventory = terminal.readDoubleOrReuse("Estoque Inicial", product.initialInventory);

            productService.update(product);

            terminal.printText("Produto alterado com sucesso");
        });
    }

    @Command(alias = "dp", command = "deletar-produto", description = "Remove um produto na base de dados")
    public void deletar() {
        runWhile(() -> {
            terminal.printText("Deletando um produto");

            var id = terminal.readInteger("ID (que deseja deletar)");

            productService.remove(id);

            terminal.printText("Produto removido com sucesso");
        });
    }

    @Command(alias = "bp", command = "buscar-produto", description = "Busca um único produto por um ID informado.")
    public void buscarPorId() {
        runWhile(() -> {
            var id = terminal.readInteger("ID (que deseja buscar)");
            var product = productService.findById(id);
            terminal.printSeparatorLine(TERMINAL_SIZE);

            terminal.printTableTitle("Produto", TERMINAL_SIZE);
            terminal.printSeparatorLine(TERMINAL_SIZE);

            terminal.printRow(LIST_FORMAT, "ID", "Nome", "Descrição", "Estoque Inicial");
            terminal.printSeparatorLine(TERMINAL_SIZE);

            terminal.printRow(LIST_FORMAT, product.getId(), product.name, product.description, product.initialInventory);
            terminal.printSeparatorLine(TERMINAL_SIZE);
        });
    }

    @Command(alias = "lp", command = "listar-produtos", description = "Listagem de todos os produtos registrados")
    public void listarTodos() {
        runWhile(() -> {
            var products = productService.findAll();
            terminal.printSeparatorLine(TERMINAL_SIZE);

            terminal.printTableTitle("Produtos", TERMINAL_SIZE);
            terminal.printSeparatorLine(TERMINAL_SIZE);

            terminal.printRow(LIST_FORMAT, "ID", "Nome", "Descrição", "Estoque Inicial");
            terminal.printSeparatorLine(TERMINAL_SIZE);

            for (Product product : products) {
                terminal.printRow(LIST_FORMAT, product.getId(), product.name, product.description, product.initialInventory);
            }
            terminal.printSeparatorLine(TERMINAL_SIZE);
        });
    }

}
