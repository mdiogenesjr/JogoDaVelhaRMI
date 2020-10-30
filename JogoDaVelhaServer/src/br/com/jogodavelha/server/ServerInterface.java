package br.com.jogodavelha.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.jogodavelha.client.ClientInterface;
import br.com.jogodavelha.model.Jogada;
import br.com.jogodavelha.model.Player;

public interface ServerInterface extends Remote {
    void sayHello(ClientInterface cliente) throws RemoteException;
    public Player conectar(ClientInterface c) throws RemoteException;
    public Jogada jogar(Jogada jogadaVO, ClientInterface cliente) throws RemoteException;
}
