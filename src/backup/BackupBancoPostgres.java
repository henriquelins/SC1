package backup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BackupBancoPostgres {

	public static List<String> realizaBackup(String ferramenta, String host, String porta, String user, String endereco,
			String banco, String mensagemSenha, String senha) {

		final List<String> comandos = new ArrayList<String>();
		List<String> areaTexto = new ArrayList<String>();

		// comandos.add("C:\\Program Files\\PostgreSQL\\9.4\\bin\\pg_dump.exe"); // esse
		// é meu caminho w10 64bits
		comandos.add(ferramenta); // esse é meu caminho w10 64bits
		// comandos.add("-i");
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

		areaTexto.add("Iniciando processo\n");

		Process process = null;
		try {
			process = pb.start();

			final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = r.readLine();

			while (line != null) {

				areaTexto.add(line);
				line = r.readLine();

			}

			if (process.exitValue() == 1) {

				areaTexto.add("\nBackup não realizado. Cod. Processo: " + process.exitValue());

			} else {

				areaTexto.add("\nBackup realizado com sucesso. Cod. Processo: " + process.exitValue());

			}

			r.close();
			process.waitFor();

			process.destroy();

			return areaTexto;

		} catch (RuntimeException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block

			areaTexto.add("\nBackup não realizado. Cod. Processo: " + process.exitValue() + " " + e.getMessage());

			return areaTexto;
		}

	}

	public static List<String> realizaRestore(String ferramenta, String host, String porta, String user,
			String endereco, String banco, String mensagemSenha, String senha) {

		final List<String> comandos = new ArrayList<String>();
		List<String> areaTexto = new ArrayList<String>();

		comandos.add(ferramenta); // esse é meu caminho w10 64bits
		//comandos.add("-i");
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

		areaTexto.add("Iniciando processo\n");

		Process process = null;

		try {

			process = pb.start();

			final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = r.readLine();

			while (line != null) {

				areaTexto.add(line);
				line = r.readLine();

			}

			if (process.exitValue() == 1) {

				areaTexto.add("\nRestore não realizado. Cod. Processo: " + process.exitValue());

			} else {

				areaTexto.add("\nRestore realizado com sucesso. Cod. Processo: " + process.exitValue());

			}

			r.close();

			process.waitFor();
			process.destroy();

			return areaTexto;

		} catch (RuntimeException | IOException | InterruptedException e) {

			e.printStackTrace();

			areaTexto.add("\nRestore não realizado. Cod. Processo: " + process.exitValue() + " " + e.getMessage());

			return areaTexto;

		}

	}

}
