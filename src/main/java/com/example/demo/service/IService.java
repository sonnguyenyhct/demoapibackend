package com.example.demo.service;

import java.util.Optional;

public interface IService<T> {
    Iterable<T> getAll();
    T save(T t);
    Optional<T> findByID(Long id);
    void remove(Long id);
}
