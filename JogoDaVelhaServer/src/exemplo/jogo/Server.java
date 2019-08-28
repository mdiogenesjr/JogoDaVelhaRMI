package exemplo.jogo;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class Server implements ServerInterface {

    public Server() {}

    List<ClienteInterface> conectados = new ArrayList<ClienteInterface>();
	static int idPlayer = 1;
	 
    public void sayHello(ClienteInterface cliente)  throws RemoteException{
    	cliente.exibeResposta("Veio!");
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);

            java.rmi.registry.LocateRegistry.createRegistry(1099);	
            Naming.rebind("rmi://localhost/Service", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
    public Player conectar(ClienteInterface client) throws RemoteException {	
		Player player = new Player();
		try{
			if(conectados.size() == 2){
				verificaConexao();
				player.setMsg("Servidor com o n�mero m�ximo de usu�rios");
				player.setCodErro(2);
	    	}else{
	    		player.setNome(client.getPlayer().getNome());
	    		player.setIdPlayer(idPlayer);
	    		idPlayer++;
	    		conectados.add(client);
	    		System.out.println("Player conectado!");
	    		int contadorMsg = 0;
	    		while(conectados.size() <= 1){
	    			//espera
	    			if(contadorMsg == 0){
	    				System.out.println("Aguardado player 2...");
	    			}
	    			contadorMsg++;
	    		}
	    		System.out.println("Players sincronizados!");
	    		if(player.getIdPlayer() == 1){
	    			player.setVez(true);
	    			for(ClienteInterface conectado : conectados){
						if(conectado.getPlayer().getIdPlayer() != player.getIdPlayer()){
							player.setNomeAdversario(conectado.getPlayer().getNome());
						}
					}
	    		}else{
	    			player.setVez(false);
	    			int indexPlayerDois = conectados.indexOf(client);
	    			if(indexPlayerDois == 0){
	    				player.setNomeAdversario(conectados.get(1).getPlayer().getNome());
	    			}else{
	    				player.setNomeAdversario(conectados.get(0).getPlayer().getNome());
	    			}	
	    		}
	    	}		
			return player;
		}catch (Exception e) {
			conectados.clear();
			idPlayer = 1;
			return conectar(client);
		}
	}
    
    public void verificaConexao() throws Exception{
    	for(ClienteInterface conectado : conectados){
    		conectado.exibeResposta("OK");
    		conectado.exibeResposta("OK");
    	}
    }
    
    public Jogada jogar(Jogada jogada,ClienteInterface c) throws RemoteException {
		// TODO Auto-generated method stub
		Jogada retornoJogada = new Jogada();
		try{
			if(verificaVencedor(jogada)){
				retornoJogada.setMsg("Player "+jogada.getPlayer().getNome()+" Venceu !");
			}else if(verificaFimDoJogo(jogada)){
				retornoJogada.setMsg("Fim de jogo.");
			}else{
				retornoJogada.setMsg("");
			}
			retornoJogada.setListaBotoes(jogada.getListaBotoes());
			for(ClienteInterface conectado : conectados){
				if(conectado.getPlayer().getIdPlayer() != jogada.getPlayer().getIdPlayer()){
					conectado.construirTabuleiro(retornoJogada);
				}
			}
			
		}catch (Exception e) {
			retornoJogada.setMsg("Conex�o interrompida, o jogo ser� reiniciado.");
			conectados.clear();
			idPlayer = 1;
			return retornoJogada;
		}
		return retornoJogada;
	}
	
	
	private boolean verificaFimDoJogo(Jogada jogadaVO) throws Exception{
		for(JButton botao : jogadaVO.getListaBotoes()){
			if("".equals(botao.getText())){
				return false;
			}
		}
		return true;
	}
	
	private boolean verificaVencedor(Jogada jogadaVO) throws Exception{
		
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
