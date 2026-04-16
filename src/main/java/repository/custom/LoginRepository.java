package repository.custom;

import Entity.UserCredential;
import repository.SuperRepository;

import java.util.Optional;

public interface LoginRepository extends SuperRepository {
    Optional<UserCredential> findByEmail(String email);
}
