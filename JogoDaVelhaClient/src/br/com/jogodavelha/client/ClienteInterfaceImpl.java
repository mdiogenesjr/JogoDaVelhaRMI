package br.com.jogodavelha.client;

import java.rmi.RemoteException;

import br.com.jogodavelha.model.Jogada;
import br.com.jogodavelha.model.Player;
import br.com.jogodavelha.view.View;

public class ClienteInterfaceImpl implements ClienteInterface{

	public void exibeResposta(String msg) throws RemoteException{
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
	private View view;

	public ClienteInterfaceImpl() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void construirTabuleiro(Jogada jogada)throws RemoteException{
		view.construirTabuleiro(jogada);
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

	public void setPlayer(Player player) {
		this.view.setPlayer(player);
	}

}
