package entidades;

import java.util.List;

public class Vendedores {
	String cpf;
	String nome;
	double Salario;
	
	public Vendedores() {
		
	}

	public Vendedores(String cpf, String nome, double salario) {
		this.cpf = cpf;
		this.nome = nome;
		Salario = salario;
	}
	
	public static Vendedores retornaVendedor(String texto) {		
		Vendedores v;
		
		String[] valores = texto.split("ç");
		
		String cpf = valores[1];
		String nome = valores[2];
		double salario = Double.parseDouble(valores[3]);
		
		v = new Vendedores(cpf, nome, salario);
		
		return v;				
	}
	
	public static String retornaPiorVendedor(List<Vendedores> vendedores, List<Vendas> vendas) {		
		double menorvenda = 0;
		double saldoVendas = 0;
		
		String nomePiorVendedor = "";		
		
		for(Vendedores vendedor : vendedores) {
			for(Vendas v : vendas) {				
				if(v.getVendedor().equals(vendedor.getNome())) {
					saldoVendas += Vendas.retornaValorVenda(v);
				}				
			}
			
			if(saldoVendas < menorvenda) {
				nomePiorVendedor = vendedor.getNome();
			}			
		}		
		
		return nomePiorVendedor;
	}

	public String getCpf() {
		return cpf;
	}


	public String getNome() {
		return nome;
	}

	public double getSalario() {
		return Salario;
	}
	
}
