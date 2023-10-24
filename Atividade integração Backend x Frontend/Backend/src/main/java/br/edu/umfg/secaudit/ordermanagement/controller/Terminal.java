package br.edu.umfg.secaudit.ordermanagement.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Terminal {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String TEXT_FORMAT = "> %s\n";
    private static final String ERROR_FORMAT = "ERROR> %s\n";
    private static final String LABEL_FORMAT = ">> %s: ";
    private static final String REUSE_LABEL_FORMAT = ">> %s [%s]: ";
    private static final String LABEL_DATE_FORMAT = ">> %s (%s): ";
    private static final String REUSE_LABEL_DATE_FORMAT = ">> %s (%s) [%s]: ";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public int readInteger(String label) {
        while (true) {
            try {
                System.out.format(LABEL_FORMAT, label);
                var value = SCANNER.nextLine();
                var parsed = Integer.parseInt(value);
                return parsed;
            } catch (NumberFormatException ex) {
                System.out.println("ERRO > Favor informar um valor válido! Ex: 1234");
            }
        }
    }

    public double readDouble(String label) {
        while (true) {
            try {
                System.out.format(LABEL_FORMAT, label);
                var value = SCANNER.nextLine();
                var parsed = Double.parseDouble(value);
                return parsed;
            } catch (NumberFormatException ex) {
                System.out.println("ERRO > Favor informar um valor válido! Ex: 1234");
            }
        }
    }

    public double readDoubleOrReuse(String label, double reuse) {
        while (true) {
            try {
                System.out.format(REUSE_LABEL_FORMAT, label, reuse);
                var value = SCANNER.nextLine();
                if (value.isBlank()) {
                    return reuse;
                }
                var parsed = Double.parseDouble(value);
                return parsed;
            } catch (NumberFormatException ex) {
                System.out.println("ERRO > Favor informar um valor válido! Ex: 1234");
            }
        }
    }

    public String readText(String label) {
        System.out.format(LABEL_FORMAT, label);
        return SCANNER.nextLine();
    }

    public String readTextOrReuse(String label, String reuse) {
        System.out.format(REUSE_LABEL_FORMAT, label, reuse);
        var read = SCANNER.nextLine();
        if (read.isBlank()) {
            return reuse;
        }
        return read;
    }

    public LocalDate readLocalDate(String label) {
        while (true) {
            try {
                System.out.format(LABEL_DATE_FORMAT, label, DATE_PATTERN);
                return LocalDate.parse(SCANNER.nextLine(), DATE_FORMAT);
            } catch (DateTimeParseException ex) {
                System.out.println("ERRO > Favor informar um valor válido! Ex: 10/12/1995");
            }
        }
    }

    public LocalDate readLocalDateOrReuse(String label, LocalDate reuse) {
        while (true) {
            try {
                System.out.format(REUSE_LABEL_DATE_FORMAT, label, DATE_PATTERN, reuse.format(DATE_FORMAT));
                var read = SCANNER.nextLine();
                if (read.isBlank()) {
                    return reuse;
                }
                return LocalDate.parse(read, DATE_FORMAT);
            } catch (DateTimeParseException ex) {
                System.out.println("ERRO > Favor informar um valor válido! Ex: 10/12/1995");
            }
        }
    }

    public boolean wishToContinue() {
        System.out.println("Deseja continuar? [S]im / [N]ão");
        var wishContinue = false;
        var invalid = true;
        while (invalid) {
            var answer = SCANNER.nextLine();
            if (!answer.equalsIgnoreCase("S") && !answer.equalsIgnoreCase("N")) {
                printWarning("Opcão inválida. Tente novamente.");
            } else {
                wishContinue = answer.equalsIgnoreCase("S");
                invalid = false;
                clear();
            }
        }
        return wishContinue;
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
    }

    public void printWarning(String message) {
        System.out.println("> ATENCÃO >>>  " + message);
    }

    public String formatarData(LocalDate nascimento) {
        return nascimento.format(DATE_FORMAT);
    }

    public void printSeparatorLine(int size) {
        var text = new StringBuilder();
        for (int i = 1; i < size; i++) {
            text.append("-");
        }
        var format = "+%s+\n";
        printRow(format, text.toString());
    }

    public void printTableTitle(String title, int size) {
        int padding = (size - 8) / 2;
        var format = "|%" + padding + "s%s%" + padding + "s|\n";
        printRow(format, "", title, "");
    }

    public void printRow(String format, Object... data) {
        System.out.format(format, data);
    }

    public void printText(String text) {
        System.out.format(TEXT_FORMAT, text);
    }

    public void printError(Exception ex) {
        System.out.format(ERROR_FORMAT, ex.getMessage());
    }
}
