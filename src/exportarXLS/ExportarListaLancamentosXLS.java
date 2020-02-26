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
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import model.entities.Lancamento;
import model.entities.ServicoImpressao;

public class ExportarListaLancamentosXLS {

	private WritableCellFormat tahomaBoldUnderline;
	private WritableCellFormat tahoma;

	// Método responsável pela definição das labels
	private void criaLabel(WritableSheet sheet, int total, ServicoImpressao servicoImpressao) throws WriteException {
		// Cria o tipo de fonte como TIMES e tamanho
		WritableFont tahoma10pt = new WritableFont(WritableFont.TAHOMA, 10);

		// Define o formato da célula
		tahoma = new WritableCellFormat(tahoma10pt);

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
		sheet.mergeCells(0, 0, 6, 0);
		sheet.mergeCells(0, 1, 6, 1);

		addCaption(sheet, 0, 0,
				servicoImpressao.getNomeDoServico());
		addCaption(sheet, 1, 0, "");
		addCaption(sheet, 2, 0, "");
		addCaption(sheet, 3, 0, "");
		addCaption(sheet, 4, 0, "");
		addCaption(sheet, 5, 0, "");
		addCaption(sheet, 6, 0, "");
		
		addCaption(sheet, 0, 1, "Saldo Atual: "
						+ String.valueOf(servicoImpressao.getConta().getSaldo()) + " / Produzidos no período: "
						+ String.valueOf(total));
		addCaption(sheet, 1, 1, "");
		addCaption(sheet, 2, 1, "");
		addCaption(sheet, 3, 1, "");
		addCaption(sheet, 4, 1, "");
		addCaption(sheet, 5, 1, "");
		addCaption(sheet, 6, 1, "");
		

		addCaption(sheet, 0, 2, "#");
		addCaption(sheet, 1, 2, "Data do lançamento");
		addCaption(sheet, 2, 2, "Saldo Anterior");
		addCaption(sheet, 3, 2, "Operação");
		addCaption(sheet, 4, 2, "Valor Lançamento");
		addCaption(sheet, 5, 2, "Saldo Atual");
		addCaption(sheet, 6, 2, "Detalhes");

	}

	private void defineConteudo(WritableSheet sheet, List<Lancamento> listaLancamentos)
			throws WriteException, RowsExceededException {

		int i = 3;
		int contador = 1;

		NumberFormat tresDigitos = new DecimalFormat("000");
		DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");

		for (Lancamento l : listaLancamentos) {

			addLabel(sheet, 0, i, tresDigitos.format(contador));
			addLabel(sheet, 1, i, String.valueOf(formatBR.format(l.getDataDoLancamento())));
			addNumero(sheet, 2, i, l.getSaldoAnterior());
			addLabel(sheet, 3, i, l.getTipoDoLancamento());
			addNumero(sheet, 4, i, l.getQuantidadeDoPedido());
			addNumero(sheet, 5, i, l.getSaldoAtual());
			addLabel(sheet, 6, i, l.getObservacoesLancamento());

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

	private void addNumero(WritableSheet planilha, int coluna, int linha, Integer integer)
			throws WriteException, RowsExceededException {
		Number numero;
		numero = new Number(coluna, linha, integer, tahoma);
		planilha.addCell(numero);
	}

	private void addLabel(WritableSheet planilha, int coluna, int linha, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(coluna, linha, s, tahoma);
		planilha.addCell(label);
	}

	public void exportarListaLancamentoXLS(String inputArquivo, ObservableList<Lancamento> listaLancamentos,
			ServicoImpressao servicoImpressao, int total) throws IOException, WriteException {

		// Cria um novo arquivo
		File arquivo = new File(inputArquivo);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("pt", "BR"));
		WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);

		// Define um nome para a planilha
		workbook.createSheet("Lista Lançamentos", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		criaLabel(excelSheet, total, servicoImpressao);
		defineConteudo(excelSheet, listaLancamentos);

		workbook.write();
		workbook.close();

		Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + inputArquivo);

	}

}
