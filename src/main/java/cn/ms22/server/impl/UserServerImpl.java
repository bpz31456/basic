package cn.ms22.server.impl;

import cn.ms22.entry.Student;
import cn.ms22.server.UserServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServerImpl extends UnicastRemoteObject implements UserServer {
    public UserServerImpl() throws RemoteException {
    }

    @Override
    public String getUserInfo(String username) throws RemoteException {
        Student student = new Student();
        student.setAge(18);
        student.setName("zhangsan");
        student.setSex("nv");
        return username==null||username.trim().length()==0||!username.equals("zhangsan")?"未找到相应user。":student.toString();
    }
}
