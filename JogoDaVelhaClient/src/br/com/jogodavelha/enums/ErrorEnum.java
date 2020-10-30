package br.com.jogodavelha.enums;

public enum ErrorEnum {
	
	SUCCESS(0,"Sucesso"),
	MAX_USERS(2,"Servidor com o número máximo de usuários");
	
	private int id;
    private String descricao;	
    
    ErrorEnum(int id, String descricao) {
    	this.id = id;
    	this.descricao = descricao;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescricao() {
        return descricao;
    }

}
