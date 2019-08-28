package exemplo.jogo;

import java.io.Serializable;

public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556665796714674422L;
	private boolean vez;
	private int idPlayer;
	private String msg;
	private String nome;
	private int codErro;
	private String nomeAdversario;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isVez() {
		return vez;
	}
	public void setVez(boolean vez) {
		this.vez = vez;
	}
	
	public int getIdPlayer() {
		return idPlayer;
	}
	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCodErro() {
		return codErro;
	}
	public void setCodErro(int codErro) {
		this.codErro = codErro;
	}
	public String getNomeAdversario() {
		return nomeAdversario;
	}
	public void setNomeAdversario(String nomeAdversario) {
		this.nomeAdversario = nomeAdversario;
	}
}
