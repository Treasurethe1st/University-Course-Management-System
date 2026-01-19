package com.university.persistence;

import java.io.*;

public class FileRepository<T> implements DataRepository<T> {

    private String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(T data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
            System.out.println("Data saved to " + filePath);
        }
    }

    @Override
    public T load() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (T) ois.readObject();
        }
    }
}
