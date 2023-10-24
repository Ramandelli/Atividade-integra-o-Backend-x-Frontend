package br.edu.umfg.secaudit.ordermanagement.validation;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.edu.umfg.secaudit.ordermanagement.domain.Client;

public class ClientValidator implements CrudValidator<Client> {

    private static final int MIN_AGE = 18;
    private static final int MIN_FIRSTNAME_SIZE = 3;
    private static final int MAX_FIRSTNAME_SIZE = 15;
    private static final int MIN_LASTNAME_SIZE = 5;
    private static final int MAX_LASTNAME_SIZE = 15;

    private final CPFValidator cpfValidator = new CPFValidator();

    @Override
    public void validate(Client client) {
        Preconditions.checkNotBlank(client.firstname, "Deve ser informado o primeiro nome!");
        Preconditions.checkNotBlank(client.lastname, "Deve ser informado o segundo nome!");
        Preconditions.checkNotBlank(client.document, "Deve ser informado o CPF!");
        Preconditions.checkNotNull(client.birth, "Deve ser informado o nascimento!");
        Preconditions.checkAge(client.birth, MIN_AGE, "Apenas maiores de 18 anos devem ser cadastrados!");
        Preconditions.checkSize(client.firstname, MIN_FIRSTNAME_SIZE, MAX_FIRSTNAME_SIZE, "Deve ser informado um nome entre 3 e 15 dígitos!");
        Preconditions.checkSize(client.lastname, MIN_LASTNAME_SIZE, MAX_LASTNAME_SIZE, "Deve ser informado um sobrenome entre 5 e 15 dígitos!");
        validateCpf(client.document);
    }

    private void validateCpf(String document) {
        try {
            cpfValidator.assertValid(document);
        } catch (InvalidStateException ex) {
            throw new IllegalArgumentException("Deve ser informado um CPF válido.");
        }
    }
}
