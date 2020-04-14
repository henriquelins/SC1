package exportarXLS;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import model.entities.Cliente;

public class ExportarListaSaldoClienteXLS {

	private WritableCellFormat tahomaBoldUnderline;
	private WritableCellFormat tahoma;

	// Método responsável pela definição das labels
	private void criaLabel(WritableSheet sheet, Cliente cliente) throws WriteException {
		// Cria o tipo de fonte como TIMES e tamanho
		WritableFont Tahoma10pt = new WritableFont(WritableFont.TAHOMA, 10);

		// Define o formato da célula
		tahoma = new WritableCellFormat(Tahoma10pt);

		// Efetua a quebra automática das células
		// times.setWrap(true);

		// Cria a fonte em negrito com underlines
		WritableFont tahoma10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD, false);
		// UnderlineStyle.SINGLE);
		tahomaBoldUnderline = new WritableCellFormat(tahoma10ptBoldUnderline);

		// Efetua a quebra automática das células
		// timesBoldUnderline.setWrap(true);
		tahomaBoldUnderline.setAlignment(Alignment.CENTRE);
		tahomaBoldUnderline.setBackground(Colour.GRAY_25);

		CellView cv = new CellView();
		cv.setFormat(tahoma);// Bom pessoal, é isso ai, qualquer dúvida é só avisar.
		cv.setFormat(tahomaBoldUnderline);

		// Escreve os cabeçalhos

		// MESCLAR CÉLULAS: colunaInicial, linhaInicial, colunaFinal, linhaFinal

		// sheet.mergeCells(0, 0, 7, 0);

		DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
		String dataBr = formatBR.format(new Date(System.currentTimeMillis()));

		addCaption(sheet, 0, 0, "Saldo do Cliente: " + cliente.getNomeFantasia().toUpperCase());
		addCaption(sheet, 0, 1, "Data da Pesquisa: " + dataBr);
	}

	private void defineConteudo(WritableSheet sheet, List<String> listaSaldoCliente)
			throws WriteException, RowsExceededException {

		int i = 2;

		for (String s : listaSaldoCliente) {

			addLabel(sheet, 0, i, s.toUpperCase());

			i++;
		}

	}

// Adiciona cabecalho
	private void addCaption(WritableSheet planilha, int coluna, int linha, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(coluna, linha, s, tahomaBoldUnderline);
		planilha.addCell(label);
	}

	private void addLabel(WritableSheet planilha, int coluna, int linha, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(coluna, linha, s, tahoma);
		planilha.addCell(label);
	}

	public void exportarListaSaldoClienteXLS(String inputArquivo, List<String> listaSaldoCliente, Cliente cliente)
			throws IOException, WriteException {

		// Cria um novo arquivo
		File arquivo = new File(inputArquivo);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("pt", "BR"));
		WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);

		// Define um nome para a planilha
		workbook.createSheet("Lista Saldo Clientes", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		criaLabel(excelSheet, cliente);
		defineConteudo(excelSheet, listaSaldoCliente);

		workbook.write();
		workbook.close();

		Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + inputArquivo);

	}

}
