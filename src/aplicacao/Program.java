package aplicacao;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Program {

	public static void main(String[] args) throws IOException {
		String nomeArquivoSaida = "Teste";

		if (ManipularArquivos.verificarPastas() == true) {
			ManipularArquivos.processarArquivos(nomeArquivoSaida);

			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path directory = Paths.get(ManipularArquivos.DIRETORIOENTRADA);
			WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

			while (true) {
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					ManipularArquivos.processarArquivos(nomeArquivoSaida);
				}
			}
		}
	}
}
