package config;

import com.google.inject.AbstractModule;
import repository.custom.LoginRepository;
import repository.custom.UserCredentialRepository;
import repository.custom.impl.LoginRepositoryImpl;
import repository.custom.impl.UserCredentialRepositoryImpl;
import service.custom.LoginService;
import service.custom.UserCredentialService;
import service.custom.UserService;
import service.custom.impl.LoginServiceImpl;
import service.custom.impl.UserCredentialServiceImpl;
import service.custom.impl.UserServiceImpl;


public class AppModule  extends AbstractModule {

    @Override
    protected void configure(){
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(LoginRepository.class).to(LoginRepositoryImpl.class);
        bind(UserCredentialRepository.class).to(UserCredentialRepositoryImpl.class);
        bind(UserCredentialService.class).to(UserCredentialServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);

    }

}
