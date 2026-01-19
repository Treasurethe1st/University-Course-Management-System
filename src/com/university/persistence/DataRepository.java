package com.university.persistence;

import java.io.IOException;

public interface DataRepository<T> {
    void save(T data) throws IOException;
    T load() throws IOException, ClassNotFoundException;
}
