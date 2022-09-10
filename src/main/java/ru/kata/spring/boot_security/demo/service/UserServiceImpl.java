package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDaoImpl dao;

    @Autowired
    public UserServiceImpl(UserDaoImpl dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        dao.saveUser(user);
    }

    @Override
    public User getUserById(int id) {
        return dao.getUserById(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUserById(int id) {
        dao.deleteUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return dao.findUserByEmail(email);
    }

    @Override
    public List<Role> getRoles() {
        return dao.getRoles();
    }

    public Set<Role> findRolesByName(String roleName) {
        return dao.findRolesByName(roleName);
    }
}

