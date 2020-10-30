package br.com.jogodavelha.util;

import java.rmi.server.UnicastRemoteObject;

import br.com.jogodavelha.client.ClientInterface;
import br.com.jogodavelha.client.ClientInterfaceImpl;
import br.com.jogodavelha.view.View;

public class ClientUtil {
	
	public static ClientInterface getClient(View view) throws Exception{
		ClientInterfaceImpl clienteInterfaceImpl = new ClientInterfaceImpl(view);
		return (ClientInterface) UnicastRemoteObject.exportObject(clienteInterfaceImpl, 0);
	}

}
