package service.custom.impl;

import model.UserCredential;
import repository.custom.UserCredentialRepository;

import java.util.Optional;

public class UserCredentialServiceImpl implements UserCredentialRepository {

    @Override
    public Optional<UserCredential> findByEmail(String email) {
        return Optional.empty();
    }
}
