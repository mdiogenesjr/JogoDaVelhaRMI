package br.com.jogodavelha.util;

import java.util.List;

import javax.swing.JButton;

public class ViewUtil {
	
	public static void desbloquearTabuleiro(List<JButton> listaBotoes){
		for(JButton botao : listaBotoes){
			botao.setEnabled(true);
		}
	}
	
	public static void bloquearTabuleiro(List<JButton> listaBotoes) {
		for(JButton botao : listaBotoes){
			botao.setEnabled(false);
		}	
	}	
	
	public static void limpaTabuleiro(List<JButton> listaBotoes) throws Exception{
		for(JButton botao : listaBotoes){
			botao.setText("");
			botao.setEnabled(true);
		}
	}

}
