package br.com.jogodavelha.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.jogodavelha.model.Jogada;
import br.com.jogodavelha.model.Player;

public interface ClienteInterface extends Remote {
	public void construirTabuleiro(Jogada jogada) throws RemoteException;
	public Player getPlayer() throws RemoteException;
	public void setPlayer(Player player) throws RemoteException;
	public void exibeResposta(String msg) throws RemoteException;
}
