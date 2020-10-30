package br.com.jogodavelha.util;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.com.jogodavelha.server.ServerInterface;

public class ServerUtil{
	
	public static ServerInterface connect() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry();
		return (ServerInterface) registry.lookup("Service");		
	}
	
}
