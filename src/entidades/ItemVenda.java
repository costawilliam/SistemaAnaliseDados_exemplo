package entidades;

import java.util.ArrayList;
import java.util.List;

public class ItemVenda {
	int id;
	int quantidade;
	double preco;

	public ItemVenda() {

	}

	public ItemVenda(int id, int quantidade, double preco) {
		this.id = id;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public static List<ItemVenda> retornaItem(String texto) {
		List<ItemVenda> i = new ArrayList<>();

		String[] itens = texto.substring(1, texto.length() - 2).split(",");

		for (String item : itens) {
			String[] valores = item.split("-");

			int id = Integer.parseInt(valores[0]);
			int quantidade = Integer.parseInt(valores[1]);
			double preco = Double.parseDouble(valores[2]);

			i.add(new ItemVenda(id, quantidade, preco));
		}

		return i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public static double valorTotalVendaItem(List<ItemVenda> item) {
		double total = 0;
		for (ItemVenda i : item) {
			total += i.getPreco() * i.getQuantidade();
		}

		return total;
	}

}
