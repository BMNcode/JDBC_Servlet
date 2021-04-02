package org.bmn.jdbc.repository;

import java.util.List;

public interface JdbcRepository<T, ID> {

    List<T> findAll();

    List<T> findAllAndSort();

    List<T> findAllByValue(String value);

    <S extends T> List<S> saveAll(Iterable<S> var1);

    int save(T var1);

    T findById(ID var1);

    boolean existsById(ID var1);

    void deleteById(ID var1);

    void delete(T var1);

    void deleteAll(Iterable<? extends T> var1);

    void deleteAll();
}
