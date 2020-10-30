package br.com.jogodavelha.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.com.jogodavelha.server.Server;
import br.com.jogodavelha.server.ServerInterface;

public class ServerUtil{
	
	public static void createServer(Server server) throws RemoteException, MalformedURLException{
		ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);

        java.rmi.registry.LocateRegistry.createRegistry(1099);	
        Naming.rebind("rmi://localhost/Service", stub);

        System.out.println("Server ready");		
	}
	
}
