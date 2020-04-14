package backup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BackupBancoPostgres {

	public static List<String> realizaBackup(String ferramenta, String host, String porta, String user, String endereco,
			String banco, String mensagemSenha, String senha) throws IOException, InterruptedException {

		final List<String> comandos = new ArrayList<String>();
		List<String> areaTexto = new ArrayList<String>();

		// comandos.add("C:\\Program Files\\PostgreSQL\\9.4\\bin\\pg_dump.exe"); // esse
		// é meu caminho w10 64bits
		comandos.add(ferramenta); // esse é meu caminho w10 64bits
		comandos.add("-i");
		comandos.add("-h");
		// comandos.add("localhost"); // ou comandos.add("192.168.0.1");
		comandos.add(host); // ou comandos.add("192.168.0.1");
		comandos.add("-p");
		// comandos.add("5432");
		comandos.add(porta);
		comandos.add("-U");
		// comandos.add("postgres");
		comandos.add(user);
		comandos.add("-F");
		comandos.add("c");
		comandos.add("-b");
		comandos.add("-v");
		comandos.add("-f");

		// comandos.add("c:\\temp\\testeBackup.backup"); // endereco do arquivo gravado
		comandos.add(endereco); // endereco do arquivo gravado

		// comandos.add("testeBackup"); // nome do banco
		comandos.add(banco); // nome do banco

		ProcessBuilder pb = new ProcessBuilder(comandos);

		// pb.environment().put("PGPASSWORD", "1006"); // "PGPASSWORD", e senha
		pb.environment().put(mensagemSenha, senha); // "PGPASSWORD", e senha

		areaTexto.add("Iniciando backup\n");

		try {

			final Process process = pb.start();

			final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = r.readLine();

			while (line != null) {

				areaTexto.add(line);
				line = r.readLine();

			}

			r.close();

			process.waitFor();
			process.destroy();

			areaTexto.add("\nBackup realizado com sucesso.");

		} catch (IOException e) {

			e.printStackTrace();

			areaTexto.add("\nBackup não realizado. " + e.getMessage());

		} catch (InterruptedException ie) {

			ie.printStackTrace();

			areaTexto.add("\nBackup não realizado. " + ie.getMessage());

		}

		return areaTexto;

	}

	public static List<String> realizaRestore(String ferramenta, String host, String porta, String user,
			String endereco, String banco, String mensagemSenha, String senha)
			throws IOException, InterruptedException {

		final List<String> comandos = new ArrayList<String>();
		List<String> areaTexto = new ArrayList<String>();

		comandos.add(ferramenta); // esse é meu caminho w10 64bits
		comandos.add("-i");
		comandos.add("-h");
		// comandos.add("localhost"); // ou comandos.add("192.168.0.1");
		comandos.add(host); // ou comandos.add("192.168.0.1");
		comandos.add("-p");
		// comandos.add("5432");
		comandos.add(porta);
		comandos.add("-U");
		// comandos.add("postgres");
		comandos.add(user);
		comandos.add("-d");
		// comandos.add("testeBackup");
		comandos.add(banco);
		comandos.add("-v");

		// comandos.add("c:\\temp\\testeBackup.backup");
		comandos.add(endereco);

		ProcessBuilder pb = new ProcessBuilder(comandos);

		// pb.environment().put("PGPASSWORD", "1006"); // Somente coloque sua senha
		pb.environment().put(mensagemSenha, senha); // Somente coloque sua senha

		areaTexto.add("Iniciando restauração\n");

		try {
			final Process process = pb.start();

			final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = r.readLine();

			while (line != null) {

				areaTexto.add(line);
				line = r.readLine();

			}

			r.close();

			process.waitFor();
			process.destroy();

			areaTexto.add("\nRestore realizado com sucesso.");

		} catch (IOException e) {

			e.printStackTrace();

			areaTexto.add("\nRestore não realizado." + e.getMessage());

		} catch (InterruptedException ie) {

			ie.printStackTrace();

			areaTexto.add("\nRestore não realizado." + ie.getMessage());
		}

		return areaTexto;

	}

}
