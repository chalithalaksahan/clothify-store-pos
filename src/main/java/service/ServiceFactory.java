package service;

import repository.custom.impl.LoginRepositoryImpl;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance==null?instance=new ServiceFactory():instance;
    }
    public <T extends SuperService>T getServiceType(ServiceType serviceType){
        switch (serviceType){
            case LOGIN:
                return (T) new LoginRepositoryImpl();
            default:
                return null;
        }
    }
}
