package aplicacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import entidades.Clientes;
import entidades.Vendas;
import entidades.Vendedores;

public class Program {
	public static final String diretorioEntrada = System.getProperty("user.dir") + "\\data\\in";
	public static final String diretorioSaida = System.getProperty("user.dir") + "\\data\\out";

	public static void main(String[] args) throws IOException {

		processarArquivos();
		
		WatchService watchService = FileSystems.getDefault().newWatchService();		
		Path directory = Paths.get(diretorioEntrada);		
		WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
	
		while(true) {
			for(WatchEvent<?> event : watchKey.pollEvents()) {
				System.out.println("Arquivos modificados " + event.kind());
				
				processarArquivos();				
				
				System.out.println("Processo concluído!");
			}
		}
	
	}
	
	private static void processarArquivos() {
		List<Vendedores> vendedores = new ArrayList<>();
		List<Clientes> clientes = new ArrayList<>();
		List<Vendas> vendas = new ArrayList<>();
		List<String> arquivosDat = new ArrayList<>();
		String dados = "";
		String[] dadosLinha;
		
		arquivosDat = listarArquivosDat();

		for (String caminhoArquivo : arquivosDat) {
			dados += lerArquivo(caminhoArquivo);
		}

		

		dadosLinha = dados.split("\n");

		for (String dado : dadosLinha) {

			String tipo = dado.substring(0, 3);

			switch (tipo) {
			case "001":
				vendedores.add(Vendedores.retornaVendedor(dado));
				break;
			case "002":
				clientes.add(Clientes.retornaCliente(dado));
				break;
			case "003":
				vendas.add(Vendas.retornaVenda(dado));
				break;
			default:
				System.out.println("não reconhecido - " + dado);
				break;
			}
		}

		gravarArquivo("teste", vendedores, clientes, vendas);
	}

	public static List<String> listarArquivosDat() {
		File caminho = null;
		File[] arquivos;
		List<String> arquivosDat = new ArrayList<>();

		try {
			caminho = new File(diretorioEntrada);
			arquivos = caminho.listFiles();

			for (File arq : arquivos) {
				String extensao = arq.toString().substring(arq.toString().lastIndexOf("."), arq.toString().length());
				if (extensao.equals(".dat")) {
					arquivosDat.add(arq.toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return arquivosDat;
	}

	public static void gravarArquivo(String nomeArquivo, List<Vendedores> vendedores, List<Clientes> clientes,
			List<Vendas> vendas) {
		String nomeArquivoSaida = diretorioSaida + "\\" + nomeArquivo + ".done.dat";

		
		FileWriter arq;
		try {
			arq = new FileWriter(nomeArquivoSaida);
			PrintWriter gravarArq = new PrintWriter(arq);

			gravarArq.printf("Quantidade de clientes no arquivo de entrada: " + clientes.size());
			gravarArq.printf("\n");

			gravarArq.printf("Quantidade de vendedores no arquivo de entrada: " + vendedores.size());
			gravarArq.printf("\n");

			gravarArq.printf("Quantidade de vendas no arquivo de entrada: " + vendas.size());
			gravarArq.printf("\n");

			gravarArq.printf("ID da venda mais cara: " + Vendas.retornaVendaMaisCara(vendas));
			gravarArq.printf("\n");

			gravarArq.printf("O pior vendedor: " + Vendedores.retornaPiorVendedor(vendedores, vendas));
			gravarArq.printf("\n");

			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String lerArquivo(String caminho) {
		String texto = "";

		try {
			BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));

			String linha = myBuffer.readLine();
			texto += linha;

			while (linha != null) {
				texto += "\n";
				linha = myBuffer.readLine();

				if (linha != null) {
					texto += linha;
				}
			}
			myBuffer.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		} catch (Exception e) {
			System.err.printf("Erro inesperado ao abrir o arquivo:  %s.\n", e.getMessage());
		}

		return texto;
	}

}
