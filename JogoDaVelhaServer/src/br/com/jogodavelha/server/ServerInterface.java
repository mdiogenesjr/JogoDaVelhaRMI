package br.com.jogodavelha.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.jogodavelha.client.ClienteInterface;
import br.com.jogodavelha.model.Jogada;
import br.com.jogodavelha.model.Player;

public interface ServerInterface extends Remote {
    void sayHello(ClienteInterface cliente) throws RemoteException;
    public Player conectar(ClienteInterface c) throws RemoteException;
    public Jogada jogar(Jogada jogadaVO, ClienteInterface cliente) throws RemoteException;
}
