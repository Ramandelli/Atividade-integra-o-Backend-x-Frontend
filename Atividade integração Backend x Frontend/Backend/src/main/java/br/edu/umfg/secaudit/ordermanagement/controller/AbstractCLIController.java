package br.edu.umfg.secaudit.ordermanagement.controller;

public abstract class AbstractCLIController {

    protected final Terminal terminal;

    protected AbstractCLIController(Terminal terminal) {
        this.terminal = terminal;
    }

    protected void runWhile(Runnable runnable) {
        do {
            try {
                runnable.run();
            } catch (Exception ex) {
                terminal.printError(ex);
            }
        } while (terminal.wishToContinue());
    }

}
