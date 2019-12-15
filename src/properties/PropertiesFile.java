package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import db.DbException;
import gui.util.Strings;

public class PropertiesFile {

	public static Properties loadPropertiesDB() {

		try (FileInputStream fs = new FileInputStream(Strings.getPropertiesBD())) {

			Properties props = new Properties();
			props.load(fs);
			return props;

		}

		catch (IOException e) {

			throw new DbException(e.getMessage());

		}
	}

	public static Properties salvarPropertiesDB(String dburl, String password, String user) {
		
		Properties propSalvar = new Properties();
	   
		try {
			
	    	FileInputStream arquivoIn = new FileInputStream(Strings.getPropertiesBD());
	    	propSalvar.load(arquivoIn);
	    	arquivoIn.close();
	        FileOutputStream arquivoOut = new FileOutputStream(Strings.getPropertiesBD());
	        
	        propSalvar.setProperty("dburl", dburl);
	        propSalvar.setProperty("password", password);
	        propSalvar.setProperty("user", user);
	        
	        propSalvar.store(arquivoOut, null);
	        
	    } catch (Exception e) {
	    	
	        JOptionPane.showMessageDialog(null, "Não Foi possível salvar dados no arquivo config.properties\n"+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    
	        System.out.println("Não Foi possível salvar dados no arquivo config.properties");
	        
	    }
		
		return propSalvar;
		
	}

	public static void writePropertiesDB(String dburl, String password, String user) {

		Properties props = new Properties();

		try {

			props.load(props.getClass().getResourceAsStream(Strings.getPropertiesBD()));

		} catch (IOException e1) {

			e1.printStackTrace();

		}

		props.setProperty("dburl", dburl);
		props.setProperty("password", password);
		props.setProperty("user", user);

		File file = new File("./properties/db.properties2");
		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(file);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		try {

			props.storeToXML(fos, "UTF-8");

		} catch (IOException e) {

			e.printStackTrace();
		}

		try {

			fos.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static Properties loadPropertiesSocket() {

		try (FileInputStream fs = new FileInputStream(Strings.getPropertiesSocket())) {

			Properties props = new Properties();
			props.load(fs);

			return props;

		}

		catch (IOException e) {

			throw new DbException(e.getMessage());

		}
	}

}
