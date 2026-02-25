package service.custom.impl;

import jakarta.inject.Inject;
import model.UserCredential;
import repository.custom.UserCredentialRepository;
import service.custom.UserService;
import util.PasswordUtil;

public class UserServiceImpl implements UserService {

    @Inject
    UserCredentialRepository userCredentialRepository;


    @Override
    public void createUser(String email, String rawPassword) {
        UserCredential uc = new UserCredential();
        uc.setEmail(email);
        uc.setPassword(PasswordUtil.encrypt(rawPassword));
        userCredentialRepository.save(uc);
    }
}