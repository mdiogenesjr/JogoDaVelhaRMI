package br.com.jogodavelha.client;

import java.rmi.RemoteException;

import br.com.jogodavelha.model.Jogada;
import br.com.jogodavelha.model.Player;
import br.com.jogodavelha.view.View;

public class ClientInterfaceImpl implements ClientInterface{
	
	private View view;

	public ClientInterfaceImpl(View view) throws Exception {
		this.view = view;
	}
	
	public void atualizarTabuleiro(Jogada jogada)throws RemoteException{
		view.atualizarTabuleiro(jogada);
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Player getPlayer() {
		return view.getPlayer();
	}
	
	public void exibeResposta(String msg) throws RemoteException{
		// TODO Auto-generated method stub
		System.out.println(msg);
	}

}
