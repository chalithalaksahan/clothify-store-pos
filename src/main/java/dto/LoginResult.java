package dto;

import lombok.AllArgsConstructor;
import model.User;

@AllArgsConstructor
public class LoginResult {
    private final boolean success;
    private final String message;
    private  final User user;

    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public User getUser() {
        return user;
    }


}
