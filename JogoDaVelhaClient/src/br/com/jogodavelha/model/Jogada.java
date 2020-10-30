package br.com.jogodavelha.model;

import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;

public class Jogada implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5118468923123175645L;
	private String msg;
	private List<JButton> listaBotoes;
	private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<JButton> getListaBotoes() {
		return listaBotoes;
	}

	public void setListaBotoes(List<JButton> listaBotoes) {
		this.listaBotoes = listaBotoes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
