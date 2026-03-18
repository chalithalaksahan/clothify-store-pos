package config;

import com.google.inject.AbstractModule;
import repository.custom.LoginRepository;
import repository.custom.SupplierRepository;
import repository.custom.UserRepository;
import repository.custom.impl.LoginRepositoryImpl;
import repository.custom.impl.SupplierRepositoryImpl;
import repository.custom.impl.UserRepositoryImpl;
import service.custom.EmployeeService;
import service.custom.LoginService;
import service.custom.SupplierService;
import service.custom.UserService;
import service.custom.impl.EmployeeServiceImpl;
import service.custom.impl.LoginServiceImpl;
import service.custom.impl.SupplierServiceImpl;
import service.custom.impl.UserServiceImpl;


public class AppModule extends AbstractModule {

    @Override
    protected void configure(){
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(LoginRepository.class).to(LoginRepositoryImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(SupplierService.class).to(SupplierServiceImpl.class);
        bind(SupplierRepository.class).to(SupplierRepositoryImpl.class);
        bind(EmployeeService.class).to(EmployeeServiceImpl.class);
    }

}
