package exemplo.jogo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void sayHello(ClienteInterface cliente) throws RemoteException;
	public Player conectar(ClienteInterface c) throws RemoteException;
	public Jogada jogar(Jogada jogadaVO, ClienteInterface cliente) throws RemoteException;
}
