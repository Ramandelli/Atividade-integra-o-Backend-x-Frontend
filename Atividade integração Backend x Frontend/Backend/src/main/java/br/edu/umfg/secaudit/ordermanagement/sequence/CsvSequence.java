package br.edu.umfg.secaudit.ordermanagement.sequence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CsvSequence implements Sequence {

    private final Path path;

    public CsvSequence(String seqName) {
        this.path = Path.of(seqName + ".csv");
    }

    @Override
    public long nextValue() {
        try {
            var value = "0";
            if (Files.exists(path)){
                value = Files.readString(path);
            }
            long converted = Long.parseLong(value);
            converted++;
            Files.writeString(path, String.valueOf(converted), StandardOpenOption.CREATE);
            return converted;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
