package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;
    private final RoleRepository roleRepo;

    @Autowired
    public UserDaoImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
//        Role roleUser = findRoleByRoleName("ROLE_USER");
//        user.addRole(roleUser);
        entityManager.persist(user);
    }

    @Override
    public List <User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void deleteUserById(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    @Transactional
    public User findUserByUsername(String email) throws UsernameNotFoundException  {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        if (email == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = query.getSingleResult();
        user.getRoles().size();
        return user;
    }

    public Role findRoleByRoleName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.roleName = :rolename", Role.class);
        query.setParameter("rolename", name);
        return query.getSingleResult();
    }

    public List<Role> getRoles() {
        return roleRepo.findAll();
    }
}
