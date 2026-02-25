package repository.custom;

import model.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository {
    Optional<UserCredential> findByEmail(String email);

    void save(UserCredential uc);
}
