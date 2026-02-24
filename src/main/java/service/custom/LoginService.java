package service.custom;

import dto.LoginResult;
import service.SuperService;

public interface LoginService extends SuperService {
    LoginResult login(String email, String password);
}
