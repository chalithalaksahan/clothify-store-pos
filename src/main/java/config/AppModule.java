package config;

import com.google.inject.AbstractModule;
import mapper.CategoryMapper;
import mapper.ProductMapper;
import mapper.impl.CategoryMapperImpl;
import mapper.impl.ProductMapperImpl;
import repository.custom.*;
import repository.custom.impl.*;
import service.custom.*;
import service.custom.impl.*;


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
        bind(EmployeeRepository.class).to(EmployeeRepositoryImpl.class);

        bind(CategoryService.class).to(CategoryServiceImpl.class);
        bind(CategoryRepository.class).to(CategoryRepositoryImpl.class);

        bind(ProductService.class).to(ProductServiceImpl.class);
        bind(ProductRepository.class).to(ProductRepositoryImpl.class);

        bind(VariantRepository.class).to(VariantRepositoryImpl.class);

        bind(CategoryMapper.class).to(CategoryMapperImpl.class);
        bind(ProductMapper.class).to(ProductMapperImpl.class);

    }
}