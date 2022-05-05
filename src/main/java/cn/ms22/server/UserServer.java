package cn.ms22.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserServer extends Remote {
    public String getUserInfo(String username) throws RemoteException;
}
