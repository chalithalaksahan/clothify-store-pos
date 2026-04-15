package service.custom.impl;

import jakarta.inject.Inject;
import Entity.User;
import Entity.UserCredential;
import repository.custom.UserRepository;
import service.custom.UserService;
import util.PasswordUtil;

import java.time.LocalDate;

public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public void createUser(String email, String rawPassword) {


        try {
            String hashed = PasswordUtil.encrypt(rawPassword);

            UserCredential credential = new UserCredential();
            credential.setPassword(hashed);
            credential.setEmail(email);

            User user = new User();

            user.setUserId("EMP-0001");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setHireDate(LocalDate.now());
            user.setSalary(250000.00);
            user.setContactNo(777279953);
            user.setUserRole(1);
            user.setActive(true);

            // set bidirectional association so the owning side (UserCredential) references the User
            credential.setUser(user);
            user.setUserCredential(credential);

            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}