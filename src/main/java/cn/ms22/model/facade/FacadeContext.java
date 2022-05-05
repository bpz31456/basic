package cn.ms22.model.facade;

public class FacadeContext {
    public static void main(String[] args) {
        FacadeContext facadeContext = new FacadeContext();
//        facadeContext.multiInterface();

        facadeContext.facade();
    }


    public void facade(){
        LoginFacade facade = new LoginFacade(new Login(),new Wallet());
        facade.login();
    }
    public void multiInterface() {
        Login login = new Login();
        Wallet wallet = new Wallet();
        login.register();
        wallet.reset();
    }
}
