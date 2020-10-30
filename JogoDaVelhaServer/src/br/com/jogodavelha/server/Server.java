package br.com.jogodavelha.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

import br.com.jogodavelha.client.ClienteInterface;
import br.com.jogodavelha.model.Jogada;
import br.com.jogodavelha.model.Player;
import br.com.jogodavelha.util.ServerUtil;

public class Server implements ServerInterface {

    public Server() {
    	try {
    		ServerUtil.createServer(this);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    Map<Integer,ClienteInterface> conectados = new HashMap<Integer,ClienteInterface>();
	static int idPlayer = 1;   
	 
    public void sayHello(ClienteInterface cliente)  throws RemoteException{
    	cliente.exibeResposta("Veio!");
    }
    
    public Player conectar(ClienteInterface client) throws RemoteException {	
		Player player = new Player();
		try{
			if(conectados.size() == 2){
				verificarConexao();
				player.setMsg("Servidor com o número máximo de usuários");
				player.setCodErro(2);
	    	}else{
	    		conectarPlayer(player, client);
	    		sincronizarPlayers(player, client);
	    		definirPlayers(player, client);
	    	}		
			return player;
		}catch (Exception e) {
			return reconectar(client);
		}
	}
    
    private void conectarPlayer(Player player, ClienteInterface client) throws RemoteException {
    	player.setNome(client.getPlayer().getNome());
		player.setIdPlayer(idPlayer);
		conectados.put(idPlayer,client);
		idPlayer++;
		System.out.println("Player conectado!");
    }
    
    private void sincronizarPlayers(Player player, ClienteInterface client) throws RemoteException {
    	int contadorMsg = 0;
		while(conectados.size() <= 1){
			if(contadorMsg == 0){
				System.out.println("Aguardado player 2...");
			}
			contadorMsg++;
		}
		System.out.println("Players sincronizados!");
    }
    
    private void definirPlayers(Player player, ClienteInterface client) throws RemoteException {
    	if(player.getIdPlayer() == 1){
			player.setVez(true);
			player.setNomeAdversario(conectados.get(2).getPlayer().getNome());
		}else{
			player.setVez(false);
			player.setNomeAdversario(conectados.get(1).getPlayer().getNome());
		}
    }
    
    private Player reconectar(ClienteInterface client) throws RemoteException {
    	conectados.clear();
		idPlayer = 1;
		return conectar(client);
    }
    
    private void verificarConexao() throws Exception{
    	for(Integer key : conectados.keySet()){
    		conectados.get(key).exibeResposta("OK");
    		conectados.get(key).exibeResposta("OK");
    	}
    }
    
    public Jogada jogar(Jogada jogada,ClienteInterface clienteInterface) {
		// TODO Auto-generated method stub
		Jogada retornoJogada = new Jogada();
		try{
			if(verificarVencedor(jogada)){
				retornoJogada.setMsg("Player "+jogada.getPlayer().getNome()+" Venceu !");
			}else if(verificarFimDoJogo(jogada)){
				retornoJogada.setMsg("Fim de jogo.");
			}else{
				retornoJogada.setMsg("");
			}
			retornoJogada.setListaBotoes(jogada.getListaBotoes());
		
			if(jogada.getPlayer().getIdPlayer() == 1) {
				conectados.get(2).construirTabuleiro(retornoJogada);
			}else {
				conectados.get(1).construirTabuleiro(retornoJogada);
			}			
		}catch (Exception e) {
			return tratarErroAoJogar(retornoJogada);
		}
		return retornoJogada;
	}
    
    private Jogada tratarErroAoJogar(Jogada retornoJogada) {
    	retornoJogada.setMsg("Conexão interrompida, o jogo será reiniciado.");
		conectados.clear();
		idPlayer = 1;
		return retornoJogada;
    }
	
	
	private boolean verificarFimDoJogo(Jogada jogadaVO) throws Exception{
		for(JButton botao : jogadaVO.getListaBotoes()){
			if("".equals(botao.getText())){
				return false;
			}
		}
		return true;
	}
	
	private boolean verificarVencedor(Jogada jogadaVO) throws Exception{
		
		String simbolo = "";
		if(jogadaVO.getPlayer().getIdPlayer() == 1){
			simbolo = "X";
		}else{
			simbolo = "O";
		}
		
        String[][] matriz = new String[3][3];
		
		matriz[0][0] = jogadaVO.getListaBotoes().get(0).getText();
		matriz[1][0] = jogadaVO.getListaBotoes().get(1).getText();
		matriz[2][0] = jogadaVO.getListaBotoes().get(2).getText();
		matriz[0][1] = jogadaVO.getListaBotoes().get(3).getText();
		matriz[1][1] = jogadaVO.getListaBotoes().get(4).getText();
		matriz[2][1] = jogadaVO.getListaBotoes().get(5).getText();
		matriz[0][2] = jogadaVO.getListaBotoes().get(6).getText();
		matriz[1][2] = jogadaVO.getListaBotoes().get(7).getText();
		matriz[2][2] = jogadaVO.getListaBotoes().get(8).getText();
		
		for(int i = 0; i < 3; i++){
			if(simbolo.equals(matriz[i][0]) && simbolo.equals(matriz[i][1]) && simbolo.equals(matriz[i][2])){
				return true;
			}
			if(simbolo.equals(matriz[0][i]) && simbolo.equals(matriz[1][i]) && simbolo.equals(matriz[2][i])){
				return true;
			}
		}
		
		if(simbolo.equals(matriz[0][0]) && simbolo.equals( matriz[1][1]) && simbolo.equals(matriz[2][2])){
			return true;
		}
		if(simbolo.equals(matriz[0][2]) && simbolo.equals(matriz[1][1]) && simbolo.equals(matriz[2][0])){
			return true;
		}
		
		return false;
	}
}
