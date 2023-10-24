package br.edu.umfg.secaudit.ordermanagement.conversor;

import java.util.List;

public interface CsvConversor<T> {

    T convert(String[] data);

    List<Object> convert(T data);

}
