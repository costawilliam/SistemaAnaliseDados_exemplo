package entidades;

import java.util.ArrayList;
import java.util.List;

public class Vendas {
	int id;
	List<ItemVenda> item = new ArrayList<>();
	String vendedor;

	public Vendas() {

	}

	public Vendas(int id, List<ItemVenda> item, String vendedor) {
		this.id = id;
		this.item = item;
		this.vendedor = vendedor;
	}

	public static Vendas retornaVenda(String texto) {
		Vendas v;

		String[] valores = texto.split("ç");

		int id = Integer.parseInt(valores[1]);

		List<ItemVenda> item = new ArrayList<>();
		item.addAll(ItemVenda.retornaItem(valores[2]));

		String vendedor = valores[3];

		v = new Vendas(id, item, vendedor);

		return v;
	}

	public static int retornaVendaMaisCara(List<Vendas> vendas) {

		int id = 0;
		double valorVenda = -1;
		double maiorVenda = 0;

		for (Vendas v : vendas) {
			valorVenda = ItemVenda.valorTotalVendaItem(v.getItem());

			if (valorVenda > maiorVenda) {
				id = v.getId();
				maiorVenda = valorVenda;
			}
		}

		return id;
	}

	public static double retornaValorVenda(Vendas v) {
		double total = ItemVenda.valorTotalVendaItem(v.getItem());

		return total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemVenda> getItem() {
		return item;
	}

	public void setItem(List<ItemVenda> item) {
		this.item = item;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

}
