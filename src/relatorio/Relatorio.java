package relatorio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import model.entities.Cliente;
import model.entities.Lancamento;
import model.entities.ServicoImpressao;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Relatorio {

	public void relatorioListaClientesPDF(List<Cliente> listaClientes, String vendedor) {

		InputStream fonte = Relatorio.class.getResourceAsStream("/report/RelatorioListaClientes.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(listaClientes);

		JasperReport report = null;

		try {

			report = JasperCompileManager.compileReport(fonte);

		} catch (JRException e) {

			e.printStackTrace();
		}

		JasperPrint jasperPrint = null;

		try {

			jasperPrint = JasperFillManager.fillReport(report, null, jrds);

		} catch (JRException e) {

			e.printStackTrace();
		}

		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/temp/Clientes.pdf");
		} catch (JRException e) {
			
			e.printStackTrace();
		}

		try {
			Runtime.getRuntime().exec("cmd /c start C:/temp/Clientes.pdf");
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		File file = new File("C:/temp/Clientes.pdf");
		file.deleteOnExit();

	}

	public void relatorioListaLancamentosPDF(ObservableList<Lancamento> listaVerLancamentos, ServicoImpressao servicoImpressao, int total) {
		
		InputStream fonte = Relatorio.class.getResourceAsStream("/report/RelatorioListaLancamentos.jrxml");

		JRDataSource jrds = new JRBeanCollectionDataSource(listaVerLancamentos);

		JasperReport report = null;

		try {

			report = JasperCompileManager.compileReport(fonte);

		} catch (JRException e) {

			e.printStackTrace();
		}

		JasperPrint jasperPrint = null;
		
		Map<String, Object> parametros = new HashMap<String, Object>();  
		parametros.put("nomeServico", servicoImpressao.getNomeDoServico());
		parametros.put("total", String.valueOf(total));
		
		try {

			jasperPrint = JasperFillManager.fillReport(report, parametros, jrds);

		} catch (JRException e) {

			e.printStackTrace();
		}

		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/temp/Lancamento.pdf");
		} catch (JRException e) {
			
			e.printStackTrace();
		}

		try {
			Runtime.getRuntime().exec("cmd /c start C:/temp/Lancamento.pdf");
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		File file = new File("C:/temp/Lancamento.pdf");
		file.deleteOnExit();
	}
}
