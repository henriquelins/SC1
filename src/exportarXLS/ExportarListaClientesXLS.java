package exportarXLS;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javafx.collections.ObservableList;
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
import model.entities.Unidade;

public class ExportarListaClientesXLS {

	private WritableCellFormat tahomaBoldUnderline;
	private WritableCellFormat tahoma;

	// Método responsável pela definição das labels
	private void criaLabel(WritableSheet sheet, String vendedor) throws WriteException {
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
		sheet.mergeCells(0, 0, 7, 0);
		sheet.mergeCells(0, 1, 7, 1);

		addCaption(sheet, 0, 0, "Lista de Clientes");

		if (vendedor == null) {

			vendedor = "PESQUISA";
		}

		addCaption(sheet, 0, 1, "Vendedor: " + vendedor);

		addCaption(sheet, 0, 2, "#");
		addCaption(sheet, 1, 2, "Nome Fantasia");
		addCaption(sheet, 2, 2, "Razão Social");
		addCaption(sheet, 3, 2, "CNPJ");
		addCaption(sheet, 4, 2, "Cliente Desde");
		addCaption(sheet, 5, 2, "Unidade");
		addCaption(sheet, 6, 2, "E-mail");
		addCaption(sheet, 7, 2, "Fone Celular");

	}

	private void defineConteudo(WritableSheet sheet, List<Cliente> listaClientes)
			throws WriteException, RowsExceededException {

		int i = 3;
		int contador = 1;

		Unidade unidade = new Unidade();

		for (Cliente c : listaClientes) {

			NumberFormat tresDigitos = new DecimalFormat("000");
			DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");

			addLabel(sheet, 0, i, String.valueOf(tresDigitos.format(contador)));
			addLabel(sheet, 1, i, c.getNomeFantasia());
			addLabel(sheet, 2, i, c.getRazaoSocial());
			addLabel(sheet, 3, i, c.getCnpjCliente());
			addLabel(sheet, 4, i, formatBR.format(c.getDataInicioCliente()));
			addLabel(sheet, 5, i, unidade.nomeUnidade(c.getUnidadeDeAtendimento()));
			addLabel(sheet, 6, i, c.getContato().getEmailCliente());
			addLabel(sheet, 7, i, c.getContato().getFoneCelular());

			i++;
			contador++;
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

	public void exportarListaClientesXLS(String inputArquivo, ObservableList<Cliente> listaClientes, String vendedor)
			throws IOException, WriteException {

		// Cria um novo arquivo
		File arquivo = new File(inputArquivo);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("pt", "BR"));
		WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);

		// Define um nome para a planilha
		workbook.createSheet("Lista Clientes", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		criaLabel(excelSheet, vendedor);
		defineConteudo(excelSheet, listaClientes);

		workbook.write();
		workbook.close();

		Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + inputArquivo);

	}

}
