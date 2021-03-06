package org.bmn.jdbc.repository;

import java.util.List;

public interface JdbcRepository<T, ID> {

    List<T> findAll();

    List<T> findAllAndSort();

    List<T> findAllByValue(String value);

    List<T> saveAll(Iterable<T> var1);

    T save(T var1);

    T findById(ID var1);

    boolean existsById(ID var1);

    boolean deleteById(ID var1);

    boolean delete(T var1);

    void deleteAll(Iterable<? extends T> var1);
}
