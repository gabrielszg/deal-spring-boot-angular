package me.dio.service.impl;

import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;
import me.dio.service.exception.BussinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("This Account number already exists.");
        }
        return userRepository.save(userToCreate);
    }

    @Override
    public User update(Long id, User userToUpdate) {
        User user = this.findById(id);

        if (user.getId().equals(userToUpdate.getId())) {
            throw new BussinessException("Update IDs must be the same.");
        }

        user.setName(userToUpdate.getName());
        user.setAccount(userToUpdate.getAccount());
        user.setCard(userToUpdate.getCard());
        user.setFeatures(userToUpdate.getFeatures());
        user.setNews(userToUpdate.getNews());

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = this.findById(id);
        userRepository.delete(user);
    }
}
