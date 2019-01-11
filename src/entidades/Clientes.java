package entidades;

public class Clientes {
	String cnpj;
	String nome;
	String areaNegocio;
	
	public Clientes() {
		
	}
	
	public Clientes(String cnpj, String nome, String areaNegocio) {
		this.cnpj = cnpj;
		this.nome = nome;
		this.areaNegocio = areaNegocio;
	}
	
	public static Clientes retornaCliente(String texto) {		
		Clientes c;
		
		String[] valores = texto.split("ç");
		
		String cnpj = valores[1];
		String nome = valores[2];
		String areaNegocio = valores[3];
		
		c = new Clientes(cnpj, nome, areaNegocio);
		
		return c;			
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public String getNome() {
		return nome;
	}

	public String getAreaNegocio() {
		return areaNegocio;
	}
	
}
