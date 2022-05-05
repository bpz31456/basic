package cn.ms22.client;

import cn.ms22.server.UserServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class UserClient {
    public static void main(String[] args) {
        try {
            UserServer userServer = (UserServer) Naming.lookup("rmi://localhost:8888/userServer");
            String info = userServer.getUserInfo("23");
            System.out.println("info:" + info);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
