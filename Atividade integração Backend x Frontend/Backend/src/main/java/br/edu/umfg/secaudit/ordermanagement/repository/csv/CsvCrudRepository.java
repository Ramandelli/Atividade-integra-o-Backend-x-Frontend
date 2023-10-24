package br.edu.umfg.secaudit.ordermanagement.repository.csv;

import br.edu.umfg.secaudit.ordermanagement.conversor.CsvConversor;
import br.edu.umfg.secaudit.ordermanagement.exception.PersistenceException;
import br.edu.umfg.secaudit.ordermanagement.repository.CrudRepository;
import br.edu.umfg.secaudit.ordermanagement.repository.Entity;
import br.edu.umfg.secaudit.ordermanagement.sequence.Sequence;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CsvCrudRepository<T extends Entity> implements CrudRepository<T> {

    public static final String CSV_SEPARATOR = ",";
    private final Path path;
    private final CsvConversor<T> conversor;
    private final Sequence sequence;

    public CsvCrudRepository(String filename, CsvConversor<T> conversor, Sequence sequence) {
        this.path = Path.of(filename + ".csv");
        this.conversor = conversor;
        this.sequence = sequence;
    }

    @Override
    public void insert(T entity) {
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            entity.setId(sequence.nextValue());
            var data = conversor.convert(entity);
            var csvString = buildCsvString(data);
            byte[] contentBytes = csvString.getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.wrap(contentBytes);
            fileChannel.write(buffer);
            fileChannel.force(true);
        } catch (IOException e) {
            throw new PersistenceException("Não foi possível inserir");
        }
    }

    @Override
    public void update(T entity) {
        try {
            delete(entity.getId());
            insert(entity);
        } catch (PersistenceException ex) {
            throw new PersistenceException("Não foi possível alterar");
        }
    }

    @Override
    public void delete(long id) {
        try {
            var lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            var remainingLines = new ArrayList<String>();
            var idAsString = String.valueOf(id);
            for (String line : lines) {
                if (!line.startsWith(idAsString + ",")) {
                    remainingLines.add(line);
                }
            }
            Files.write(path, remainingLines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new PersistenceException("Não foi possível reomver");
        }
    }

    @Override
    public T findById(long id) {
        for (T data : findAll()) {
            if (data.getId() == id) {
                return data;
            }
        }
        return null;
    }

    @Override
    public Collection<T> findAll() {
        try {
            var result = new HashSet<T>();
            var lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                var fields = line.split(CSV_SEPARATOR);
                var converted = conversor.convert(fields);
                result.add(converted);
            }
            return result;
        } catch (IOException e) {
            throw new PersistenceException("Não foi possível consultar");
        }
    }

    private String buildCsvString(List<Object> data) {
        StringBuilder csvContent = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            csvContent.append(data.get(i));
            if (i < data.size() - 1) {
                csvContent.append(CSV_SEPARATOR);
            }
        }
        csvContent.append("\n");
        return csvContent.toString();
    }


}
