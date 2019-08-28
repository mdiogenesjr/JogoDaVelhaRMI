package exemplo.jogo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteInterface extends Remote {
	public void exibeResposta(String msg) throws RemoteException;
	public void construirTabuleiro(Jogada jogada) throws RemoteException;
	public Player getPlayer() throws RemoteException;
	public void setPlayer(Player player) throws RemoteException;
}
