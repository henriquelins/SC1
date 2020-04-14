package properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import db.DbException;
import gui.util.Strings;

public class PropertiesFile {

	public static Properties loadPropertiesDB() { 

		try (InputStream input = new FileInputStream(Strings.getPropertiesBD())) {

			Properties props = new Properties();
			props.load(input);

			return props;

		}

		catch (IOException e) {

			throw new DbException(e.getMessage());

		}
	}

	public static void writePropertiesDB(String dburl, String password, String user) {

		try (OutputStream output = new FileOutputStream(Strings.getPropertiesBD())) {

			Properties prop = new Properties();

			prop.setProperty("dburl", dburl);
			prop.setProperty("user", user);
			prop.setProperty("password", password);

			prop.store(output, null);

		} catch (IOException io) {

			throw new DbException(io.getMessage());

		}

	}
 
	public static Properties loadPropertiesSocket() {

		try (InputStream imput = new FileInputStream(Strings.getPropertiesSocket())) {

			Properties props = new Properties();
			props.load(imput);

			return props;

		}

		catch (IOException e) {

			throw new DbException(e.getMessage());

		}
	}

	public static void writePropertiesSocket(String socketPort) {

		try (OutputStream output = new FileOutputStream(Strings.getPropertiesSocket())) {

			Properties prop = new Properties();

			prop.setProperty("socketPort", socketPort);

			prop.store(output, null);

		} catch (IOException io) {

			throw new DbException(io.getMessage());

		}

	}

	public static Properties loadPropertiesBackup() {

		try (InputStream input = new FileInputStream(Strings.getPropertiesBackup())) {

			Properties props = new Properties();
			props.load(input);

			return props;

		}

		catch (IOException e) {

			throw new DbException(e.getMessage());

		}
	}

	public static void writePropertiesBackup(String enderecoBackup, String host, String nomeBanco,
			String programaBackup, String senha, String user, String porta) {

		try (OutputStream output = new FileOutputStream(Strings.getPropertiesBackup())) {

			Properties prop = new Properties();

			prop.setProperty("enderecoBackup", enderecoBackup);
			prop.setProperty("host", host);
			prop.setProperty("nomeBanco", nomeBanco);
			prop.setProperty("programaBackup", programaBackup);
			prop.setProperty("senha", senha);
			prop.setProperty("user", user);
			prop.setProperty("porta", porta);

			prop.store(output, null);

		} catch (IOException io) {

			throw new DbException(io.getMessage());

		}

	}

	public static Properties loadPropertiesRestore() {

		try (InputStream input = new FileInputStream(Strings.getPropertiesRestore())) {

			Properties props = new Properties();
			props.load(input);

			return props;

		}

		catch (IOException e) {

			throw new DbException(e.getMessage());

		}
	}

	public static void writePropertiesRestore(String enderecoRestore, String host, String nomeBanco,
			String programaRestore, String senha, String user, String porta) {

		try (OutputStream output = new FileOutputStream(Strings.getPropertiesRestore())) {

			Properties prop = new Properties();

			prop.setProperty("enderecoRestore", enderecoRestore);
			prop.setProperty("host", host);
			prop.setProperty("nomeBanco", nomeBanco);
			prop.setProperty("programaRestore", programaRestore);
			prop.setProperty("senha", senha);
			prop.setProperty("user", user);
			prop.setProperty("porta", porta);

			prop.store(output, null);

		} catch (IOException io) {

			throw new DbException(io.getMessage());

		}

	}

}
