package cn.ms22.model.facade;

/**
 * 门面模式，把多个接口封装为少量的接口调用
 */
public class LoginFacade {
    private Login login;
    private Wallet wallet;
    public LoginFacade(Login login,Wallet wallet){
        this.login = login;
        this.wallet = wallet;
    }

    public void login(){
        login.register();
        wallet.reset();
    }
}
