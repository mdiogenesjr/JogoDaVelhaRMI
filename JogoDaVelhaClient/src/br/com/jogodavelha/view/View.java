package br.com.jogodavelha.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.jogodavelha.client.ClienteInterface;
import br.com.jogodavelha.model.Jogada;
import br.com.jogodavelha.model.Player;
import br.com.jogodavelha.server.ServerInterface;
import br.com.jogodavelha.util.ClientUtil;
import br.com.jogodavelha.util.ServerUtil;

public class View extends JFrame{

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
	
	private ServerInterface server;
	private ClienteInterface client;
	private Player player;
	
	/**
	 * Create the frame.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public View() throws Exception{			
		construirTabuleiro();
		player = new Player();
		client = ClientUtil.getClient(this);
		iniciarTabuleiro();
	}
	
	private void iniciarTabuleiro() {
		Jogada jogada = new Jogada();
		jogada.setListaBotoes(null);
		atualizarTabuleiro(jogada);
	}
	
	private void conectar() throws RemoteException, NotBoundException {
		server = ServerUtil.connect();
		player = server.conectar(client);
		if(player.getCodErro() == 0){
			nomeJogador.setEnabled(false);
			nomeAdversario.setText(player.getNomeAdversario());
			if(player.isVez()){
				desbloquearTabuleiro();
			}
		}else{ 
			if(null != player.getMsg() && !"".equals(player.getMsg())){
				JOptionPane.showMessageDialog(null,player.getMsg());
			}
		}
	}
	
	private void inserirAcaoBotoes(JButton button, int x, int y, int width, int height){
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				try {		
					jogar(button);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Erro na comunicação");
				} 	
			}
		});
		button.setBounds(x, y, width, height);
		contentPane.add(button);
	}
	
	public void atualizarTabuleiro(Jogada jogada){
		
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
	
	private void construirTabuleiro(){
		setTitle("Jogo da Velha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
						player.setNome(nomeJogador.getText());
						conectar();
					}	
				} catch (NotBoundException e) {
					JOptionPane.showMessageDialog(null,"Erro ao Conectar");
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null,"Servidor offline");
				} 
			}
		});
		
		btnConectar.setBounds(207, 53, 89, 23);
		contentPane.add(btnConectar);
	}
	
	private void jogar(JButton botao) throws Exception{
		
		tratarEventoBotao(botao,player);
		Jogada jogada = new Jogada();
		jogada.setListaBotoes(listaBotoes);
		jogada.setPlayer(player);
		String msg = server.jogar(jogada,client).getMsg();
		
		if(!"".equals(msg)){
			JOptionPane.showMessageDialog(null,msg);
			limpaTabuleiro(listaBotoes);
			bloquearTabuleiro();
		}else{
			bloquearTabuleiro();
		}
	}
	
	private void desbloquearTabuleiro(){
		for(JButton botao : listaBotoes){
			botao.setEnabled(true);
		}
	}
	
	private void bloquearTabuleiro() {
		for(JButton botao : listaBotoes){
			botao.setEnabled(false);
		}	
	}	
	
	private void tratarEventoBotao(JButton botao,Player c){
		botao.setEnabled(false);
		if(c.getIdPlayer() == 1){
			botao.setText("X");
		}else{
			botao.setText("O");
		}
	}
	
	private void limpaTabuleiro(List<JButton> listaBotoes) throws Exception{
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
