package repository.custom;

import model.UserCredential;

public interface UserRepository {
    void save(UserCredential uc);
}
