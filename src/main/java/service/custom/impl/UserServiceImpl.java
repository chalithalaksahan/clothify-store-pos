package service.custom.impl;

import jakarta.inject.Inject;
import model.User;
import model.UserCredential;
import repository.custom.UserRepository;
import service.custom.UserService;
import util.PasswordUtil;

public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public void createUser(String email, String rawPassword) {
        // Create and link a User first
        User user = new User();
        user.setFirstName("Staff");
        user.setLastName("Main");
        user.setUserRole(2);
        user.setActive(true);

        UserCredential uc = new UserCredential();
        uc.setEmail(email);
        uc.setPassword(PasswordUtil.encrypt(rawPassword));
        uc.setUser(user);

        userRepository.save(uc);
    }
}