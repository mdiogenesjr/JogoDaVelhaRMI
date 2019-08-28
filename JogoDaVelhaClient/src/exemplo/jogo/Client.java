package exemplo.jogo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9082168972786651024L;
	
	List<JButton> listaBotoes = new ArrayList<JButton>();
	private JPanel contentPane;
	private JTextField nomeJogador;
	private JTextField nomeAdversario;
	
	JButton btnNewButton = new JButton("");
	JButton btnNewButton_1 = new JButton("");
	JButton btnNewButton_2 = new JButton("");
	JButton btnNewButton_3 = new JButton("");
	JButton btnNewButton_4 = new JButton("");
	JButton btnNewButton_5 = new JButton("");
	JButton btnNewButton_6 = new JButton("");
	JButton btnNewButton_7 = new JButton("");
	JButton btnNewButton_8 = new JButton("");
	JButton btnConectar = new JButton("Conectar");
	
	ServerInterface servidor;
	ClienteInterface cliente;
	private Player player;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public Client() throws Exception{	
		
		setTitle("Jogo da Velha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {

			ClienteInterfaceImpl clienteInterfaceImpl = new ClienteInterfaceImpl();
			player = new Player();
			clienteInterfaceImpl.setView(this);
			cliente = (ClienteInterface) UnicastRemoteObject.exportObject(clienteInterfaceImpl, 0);
			Jogada jogada = new Jogada();
			jogada.setListaBotoes(null);
			construirTabuleiro(jogada);
			
			JLabel labelJogador = new JLabel("Nome do Jogador");
			labelJogador.setBounds(26, 27, 120, 20);
			contentPane.add(labelJogador);
			
			nomeJogador = new JTextField();
			nomeJogador.setBounds(26, 58, 120, 14);
			contentPane.add(nomeJogador);
			nomeJogador.setColumns(15);
			
			JLabel labelAdversario = new JLabel("Nome do Adversario");
			labelAdversario.setBounds(356, 27, 120, 20);
			contentPane.add(labelAdversario);
			
			nomeAdversario = new JTextField();
			nomeAdversario.setBounds(356, 58, 120, 14);
			contentPane.add(nomeAdversario);
			nomeAdversario.setColumns(15);
			nomeAdversario.setEnabled(false);
			
			btnConectar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {	
					try {	

						if("".equals(nomeJogador.getText())){
							JOptionPane.showMessageDialog(null,"Informe o nome do jogador !");
						}else{
							Registry registry = LocateRegistry.getRegistry();
							servidor = (ServerInterface) registry.lookup("Service");
							player.setNome(nomeJogador.getText());
							Player retorno = servidor.conectar(cliente);
							if(retorno.getCodErro() == 0){
								nomeJogador.setEnabled(false);
								nomeAdversario.setText(retorno.getNomeAdversario());
								player = retorno;
								if(player.isVez()){
									desbloquearTabuleiro();
								}
							}else{
								if(retorno.getMsg() != null && !"".equals(retorno.getMsg())){
									JOptionPane.showMessageDialog(null,retorno.getMsg());
								}
							}
						}	
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,"Erro ao Conectar");
					} 	
				}
			});
			btnConectar.setBounds(207, 53, 89, 23);
			contentPane.add(btnConectar);
		}
		catch (NotBoundException e) {
			JOptionPane.showMessageDialog(null,"Servidor offline");
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Servidor offline");
		}
	}
	
	public void inserirAcaoBotoes(JButton button, int x, int y, int width, int height){
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				try {		
					cliqueJogar(button);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Erro na comunicação");
				} 	
			}
		});
		button.setBounds(x, y, width, height);
		contentPane.add(button);
	}
	
	public void construirTabuleiro(Jogada jogada){
		
		if(null != jogada.getListaBotoes()){
			
			btnNewButton.setText(jogada.getListaBotoes().get(0).getText());
			btnNewButton_1.setText(jogada.getListaBotoes().get(1).getText());
			btnNewButton_2.setText(jogada.getListaBotoes().get(2).getText());
			btnNewButton_3.setText(jogada.getListaBotoes().get(3).getText());
			btnNewButton_4.setText(jogada.getListaBotoes().get(4).getText());
			btnNewButton_5.setText(jogada.getListaBotoes().get(5).getText());
			btnNewButton_6.setText(jogada.getListaBotoes().get(6).getText());
			btnNewButton_7.setText(jogada.getListaBotoes().get(7).getText());
			btnNewButton_8.setText(jogada.getListaBotoes().get(8).getText());
			
			for(JButton botao : listaBotoes){
				if("".equals(botao.getText())){
					botao.setEnabled(true);
				}else{
					botao.setEnabled(false);
				}
			}
			
			if(!"".equals(jogada.getMsg())){
				JOptionPane.showMessageDialog(null,jogada.getMsg());
				try {
					limpaTabuleiro(listaBotoes);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Erro na comunicação");
				}
			}
			
		}else{

			inserirAcaoBotoes(btnNewButton, 75, 128, 89, 50);
			inserirAcaoBotoes(btnNewButton_1, 75, 204, 89, 50);
			inserirAcaoBotoes(btnNewButton_2, 75, 280, 89, 50);
			inserirAcaoBotoes(btnNewButton_3, 207, 128, 89, 50);
			inserirAcaoBotoes(btnNewButton_4, 207, 204, 89, 50);
			inserirAcaoBotoes(btnNewButton_5, 207, 280, 89, 50);
			inserirAcaoBotoes(btnNewButton_6, 340, 128, 89, 50);
			inserirAcaoBotoes(btnNewButton_7, 340, 204, 89, 50);
			inserirAcaoBotoes(btnNewButton_8, 340, 280, 89, 50);
			
			listaBotoes.add(btnNewButton);
			listaBotoes.add(btnNewButton_1);
			listaBotoes.add(btnNewButton_2);
			listaBotoes.add(btnNewButton_3);
			listaBotoes.add(btnNewButton_4);
			listaBotoes.add(btnNewButton_5);
			listaBotoes.add(btnNewButton_6);
			listaBotoes.add(btnNewButton_7);
			listaBotoes.add(btnNewButton_8);
			
			bloquearTabuleiro();
		}
	}
	
	public void cliqueJogar(JButton botao) throws Exception{
		
		trataEventoBotao(botao,player);
		Jogada retornoJogada = new Jogada();
		retornoJogada.setListaBotoes(listaBotoes);
		retornoJogada.setPlayer(player);
		String msg = servidor.jogar(retornoJogada,cliente).getMsg();
		
		if(!"".equals(msg)){
			JOptionPane.showMessageDialog(null,msg);
			limpaTabuleiro(listaBotoes);
			bloquearTabuleiro();
		}else{
			bloquearTabuleiro();
		}
	}
	
	public void desbloquearTabuleiro(){
		for(JButton botao : listaBotoes){
			botao.setEnabled(true);
		}
	}
	
	public void bloquearTabuleiro() {
		for(JButton botao : listaBotoes){
			botao.setEnabled(false);
		}	
	}	
	
	public void trataEventoBotao(JButton botao,Player c){
		botao.setEnabled(false);
		if(c.getIdPlayer() == 1){
			botao.setText("X");
		}else{
			botao.setText("O");
		}
	}
	
	public void limpaTabuleiro(List<JButton> listaBotoes) throws Exception{
		for(JButton botao : listaBotoes){
			botao.setText("");
			botao.setEnabled(true);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
}
