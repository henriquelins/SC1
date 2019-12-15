package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import properties.PropertiesFile;

public class DB {

	private static Connection conn = null;

	public static Connection getConnection() {

		if (conn == null) {

			try {

				String url = PropertiesFile.loadPropertiesDB().getProperty("dburl");
				conn = DriverManager.getConnection(url, PropertiesFile.loadPropertiesDB());

			}

			catch (SQLException e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir o banco de dados", e.getLocalizedMessage(),
						AlertType.ERROR);

			}
		}

		return conn;
	}

	public static void closeConnection() {

		if (conn != null) {

			try {

				conn.close();

			} catch (SQLException e) {

				throw new DbException(e.getMessage());

			}

		}

	}

	public static void closeStatement(Statement st) {

		if (st != null) {

			try {

				st.close();

			} catch (SQLException e) {

				throw new DbException(e.getMessage());

			}

		}

	}

	public static void closeResultSet(ResultSet rs) {

		if (rs != null) {

			try {

				rs.close();

			} catch (SQLException e) {

				throw new DbException(e.getMessage());

			}

		}

	}

}
