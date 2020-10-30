package br.com.jogodavelha.util;

import java.rmi.server.UnicastRemoteObject;

import br.com.jogodavelha.client.ClienteInterface;
import br.com.jogodavelha.client.ClienteInterfaceImpl;
import br.com.jogodavelha.view.View;

public class ClientUtil {
	
	public static ClienteInterface getClient(View view) throws Exception{
		ClienteInterfaceImpl clienteInterfaceImpl = new ClienteInterfaceImpl(view);
		return (ClienteInterface) UnicastRemoteObject.exportObject(clienteInterfaceImpl, 0);
	}

}
