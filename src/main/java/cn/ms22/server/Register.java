package cn.ms22.server;

import cn.ms22.server.impl.UserServerImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Register {
    public static void main(String[] args) {
        try {
            UserServer userServer = new UserServerImpl();
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://localhost:8888/userServer",userServer);
            System.out.println("Register Running.");
        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
