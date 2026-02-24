package config;

import com.google.inject.AbstractModule;
import repository.custom.LoginRepository;
import repository.custom.impl.LoginRepositoryImpl;
import service.custom.LoginService;
import service.custom.impl.LoginServiceImpl;

public class AppModule  extends AbstractModule {

    @Override
    protected void configure(){
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(LoginRepository.class).to(LoginRepositoryImpl.class);

    }

}
