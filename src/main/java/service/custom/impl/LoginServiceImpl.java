package service.custom.impl;

import dto.LoginResult;
import jakarta.inject.Inject;
import model.UserCredential;
import repository.custom.LoginRepository;
import service.custom.LoginService;
import util.PasswordUtil;

import java.util.Optional;


public class LoginServiceImpl implements LoginService {

    @Inject
    LoginRepository loginRepository;


    @Override
    public LoginResult login(String email, String password) {
        //---Input Validation ---
        if(email ==null || email.trim().isEmpty()){
            return new LoginResult(false,"Email cannot be empty",null);
        }
        if (password == null || password.trim().isEmpty()){
            return new LoginResult(false,"Password cannot be empty",null);
        }
        if (email.trim().length()< 3){
            return new LoginResult(false,"Email must be at least 3 characters long",null);
        }
        if (password.trim().length() < 6) {
            return new LoginResult(false, "Password must be at least 6 characters long", null);
        }

        //- Find user in DB ---
        Optional<UserCredential> optional = loginRepository.findByEmail(email);

        if (optional.isEmpty()) {
            return new LoginResult(false, "Invalid username or password", null);
        }


        UserCredential credential = optional.get();

        // -- Guard against missing user ---
        if (credential.getUser() == null) {
            return new LoginResult(false, "Account has no linked user profile. Contact admin.", null);
        }

        // --Check if account is active ---
        if (!credential.getUser().isActive()) {
            return new LoginResult(false, "Your account is disabled. Contact admin.", null);
        }

        if (!PasswordUtil.check(password, credential.getPassword())) {
            return new LoginResult(false, "Invalid username or password", null);
        }
        //--Login success ---
        return new LoginResult(true, "Login successful", credential.getUser());

    }
}
