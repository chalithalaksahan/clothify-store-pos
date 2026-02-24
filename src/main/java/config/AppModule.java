package config;

import com.google.inject.AbstractModule;
import model.UserCredential;
import repository.custom.LoginRepository;
import repository.custom.UserCredentialRepository;
import repository.custom.impl.LoginRepositoryImpl;
import repository.custom.impl.UserCredentialRepositoryImpl;
import service.custom.LoginService;
import service.custom.UserCredentialService;
import service.custom.impl.LoginServiceImpl;
import service.custom.impl.UserCredentialServiceImpl;

public class AppModule  extends AbstractModule {

    @Override
    protected void configure(){
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(LoginRepository.class).to(LoginRepositoryImpl.class);
        bind(UserCredentialRepository.class).to(UserCredentialRepositoryImpl.class);
        bind(UserCredentialService.class).to(UserCredentialService.class);

    }

}
