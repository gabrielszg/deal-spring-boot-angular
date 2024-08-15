package me.dio.service;

import me.dio.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(Long id);
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);
}
