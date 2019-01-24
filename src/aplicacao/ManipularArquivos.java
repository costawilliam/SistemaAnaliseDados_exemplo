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
import java.util.Scanner;

import entidades.Clientes;
import entidades.Vendas;
import entidades.Vendedores;

public class ManipularArquivos {
	
	public static final String DIRETORIOENTRADA  = System.getProperty("user.dir") + "\\data\\in";
	public static final String DIRETORIOSAIDA = System.getProperty("user.dir") + "\\data\\out";
	public static Scanner sc = new Scanner(System.in);
	public static List<String> listarArquivosDat() {
		File diretorio = null;
		File[] arquivos;
		List<String> arquivosDat = new ArrayList<>();

		try {
			diretorio = new File(DIRETORIOENTRADA);

			if (!diretorio.exists()) {
				diretorio.mkdirs();
			}

			arquivos = diretorio.listFiles();

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
	
	public static boolean verificarPastas() {		
		File diretorio = new File(DIRETORIOENTRADA);
		Boolean retorno = true;

		if (!diretorio.exists()) {
			try {
				diretorio.mkdirs();
				retorno = true;
			} catch (Exception e) {
				System.err.printf("Erro ao criar diret�rio em %s. \n %s", DIRETORIOSAIDA, e.getMessage());
				retorno = false;
			}
		}

		diretorio = new File(DIRETORIOSAIDA);
		if (!diretorio.exists()) {
			try {
				diretorio.mkdirs();
				retorno = true;
			} catch (Exception e) {
				System.err.printf("Erro ao criar diret�rio em %s. \n %s", DIRETORIOSAIDA, e.getMessage());
				retorno = false;
			}
		}
		return retorno;		
	}
	
	public static void gravarArquivo(String nomeArquivo, List<Vendedores> vendedores, List<Clientes> clientes,
			List<Vendas> vendas) throws IOException{

		String nomeArquivoSaida = DIRETORIOSAIDA + "\\" + nomeArquivo + ".done.dat";

		FileWriter arq = null;
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

					
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			arq.close();
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

	public static void processarArquivos(String nomeArquivo) {		
		List<Vendedores> vendedores = new ArrayList<>();
		List<Clientes> clientes = new ArrayList<>();
		List<Vendas> vendas = new ArrayList<>();
		List<String> arquivosDat = new ArrayList<>();
		String dados = "";
		String[] dadosLinha;

		arquivosDat = listarArquivosDat();

		if (arquivosDat.isEmpty()) {
			System.out.println("Não há dados para realizar o processamento");
		} else {

			for (String caminhoArquivo : arquivosDat) {
				dados += lerArquivo(caminhoArquivo);
			}

			dadosLinha = dados.split("\n");

			for (String dado : dadosLinha) {
				if (! dado.isEmpty()) {
	
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
						System.out.println("Não reconhecido - " + dado);
						break;
					}
				}
			}
			
			try {
				gravarArquivo(nomeArquivo, vendedores, clientes, vendas);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
			System.out.println("Arquivos processados com sucesso!");
		}
	}	
}
