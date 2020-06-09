package gui.util;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Constraints {

	// Passar string para int

	public static Integer tryParseToInt(String str) {

		try {

			return Integer.parseInt(str);

		} catch (NumberFormatException e) {

			return null;

		}

	}

	// Passar textfields para int

	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*")) {
				txt.setText(oldValue);
			}
		});
	}

	// Definir o tamanho max do textField

	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}

	// Passar textfields para double

	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
				txt.setText(oldValue);
			}
		});
	}

	// Definir o tamanho max do passwordField

	public static void setPasswordFieldMaxLength(PasswordField psw, int max) {
		psw.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				psw.setText(oldValue);
			}
		});
	}

	// Passar localDate para SqlDate

	public static Date setLocalDateToDateSql(LocalDate localDate) {

		Date clienteDesde = java.sql.Date.valueOf(localDate);
		return clienteDesde;

	}

	// Passar Date para localDate

	public static LocalDate setDateToLocalDate(Date date) {

		LocalDate clienteDesde = date.toLocalDate();
		return clienteDesde;

	}

	// Passar SqlDate para string formatada

	public static String setDateFormat(Date Data) {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formato.format(Data);

		return dataFormatada;

	}

	// Passar o boolean entrega para string formatada

	public static String setBooleanEntrega(boolean boleano) {

		String entrega = "";

		if (boleano == true) {

			entrega = "SIM";

		} else {

			entrega = "NÃO";
		}

		return entrega;
	}

	// tres dígitos

	public static String tresDigitos(int numero) {

		NumberFormat tresDigitos = new DecimalFormat("000");

		return tresDigitos.format(numero);
	}

	// quatro dígitos

	public static String quatroDigitos(int numero) {

		NumberFormat quatroDigitos = new DecimalFormat("0000");

		return quatroDigitos.format(numero);
	}

	// cnpj

	public static String cnpj(Integer numero) {

		NumberFormat cnpj = new DecimalFormat("00.000.000/0000-00");

		return cnpj.format(numero);
	}
	
	// dinheiro
	
	public static String dinheiro(Double numero) {

		NumberFormat dinheiro = new DecimalFormat("R$ 0.00");

		return dinheiro.format(numero);
	}
	
	public static String dinheiro2(Double numero) {

		NumberFormat dinheiro = new DecimalFormat("R$ 0.00");

		return dinheiro.format(numero);
	}
	
	
	
	// Mascara de CEP

	public static void mascaraCEP(TextField textField) {

		// String val = "";

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 6) {
					textField.setText(textField.getText().substring(0, 5));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 9)
					event.consume();

				if (textField.getText().length() == 5) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraNumeroInteiro(TextField textField) {

		textField.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					if (!newValue.matches("\\d*")) {
						textField.setText(newValue.replaceAll("[^\\d]", ""));
					}
				});

	}

	public static void mascaraNumero(TextField textField) {

		textField.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					newValue = newValue.replaceAll(",", ".");
					if (newValue.length() > 0) {
						try {
							Double.parseDouble(newValue);
							textField.setText(newValue.replaceAll(",", "."));
						} catch (Exception e) {
							textField.setText(oldValue);
						}
					}
				});

	}

	public static void mascaraData(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 6) {
					textField.setText(textField.getText().substring(0, 5));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 10)
					event.consume();

				if (textField.getText().length() == 2) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 5) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d/*")) {
				textField.setText(textField.getText().replaceAll("[^\\d/]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraData(DatePicker datePicker) {

		datePicker.getEditor().setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando
				if (datePicker.getEditor().getText().length() == 3) {
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 2));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}
				if (datePicker.getEditor().getText().length() == 6) {
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 5));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}

			} else { // escrevendo

				if (datePicker.getEditor().getText().length() == 10)
					event.consume();

				if (datePicker.getEditor().getText().length() == 2) {
					datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}
				if (datePicker.getEditor().getText().length() == 5) {
					datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}

			}
		});

		datePicker.getEditor().setOnKeyReleased((KeyEvent evt) -> {

			if (!datePicker.getEditor().getText().matches("\\d/*")) {
				datePicker.getEditor().setText(datePicker.getEditor().getText().replaceAll("[^\\d/]", ""));
				datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
			}
		});

	}

	public static void mascaraCPF(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText().substring(0, 7));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 12) {
					textField.setText(textField.getText().substring(0, 11));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d.-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d.-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraCNPJ(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText().substring(0, 6));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText().substring(0, 10));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 16) {
					textField.setText(textField.getText().substring(0, 15));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 18)
					event.consume();

				if (textField.getText().length() == 2) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 6) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 10) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 15) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d./-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d./-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraEmail(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@"
					.contains(event.getCharacter()) == false) {
				event.consume();
			}

			if ("@".equals(event.getCharacter()) && textField.getText().contains("@")) {
				event.consume();
			}

			if ("@".equals(event.getCharacter()) && textField.getText().length() == 0) {
				event.consume();
			}
		});

	}

	public static void mascaraTelefoneCelular(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 10 && textField.getText().substring(9, 10).equals("-")) {
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 1) {
					textField.setText("");
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 0) {
					textField.setText("(" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ")" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9) != "-") {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 13 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8) + textField.getText().substring(9, 10) + "-"
							+ textField.getText().substring(10, 13) + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}

			}

		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d()-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d()-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraTelefoneFixo(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 10 && textField.getText().substring(9, 10).equals("-")) {
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 1) {
					textField.setText("");
				}

			} else { // escrevendo

				if (textField.getText().length() == 13)
					event.consume();

				if (textField.getText().length() == 0) {
					textField.setText("(" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ")" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 8 && textField.getText().substring(7, 8) != "-") {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 12 && textField.getText().substring(7, 8).equals("-")) {
					textField.setText(textField.getText().substring(0, 7) + textField.getText().substring(8, 9) + "-"
							+ textField.getText().substring(9, 12) + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}

			}

		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d()-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d()-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}
	
	public static void mascaraMonetaria(TextField textField) {
		
		textField.setAlignment(Pos.CENTER_RIGHT);
		
		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 10 && textField.getText().substring(9, 10).equals("-")) {
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 1) {
					textField.setText("");
				}

			} else { // escrevendo

				if (textField.getText().length() == 0) {
					textField.setText("R$ " + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				
				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + " " + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
			

			}

		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d()-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d()-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}
	
	
	
	
	public static void mascaraDinheiro(TextField textField) {
	
		textField.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
        String mascara = "R$ ###.###";
        String alphaAndDigits = textField.getText().replaceAll("[\\-\\.]","");
        StringBuilder resultado = new StringBuilder();
        int i = 0;
        int quant = 0;

        if (number2.intValue() > number.intValue()) {
            if (textField.getText().length() <= mascara.length()) {
                while (i<mascara.length()) {
                    if (quant < alphaAndDigits.length()) {
                        if ("#".equals(mascara.substring(i,i+1))) {
                            resultado.append(alphaAndDigits.substring(quant,quant+1));
                            quant++;
                        } else {
                           resultado.append(mascara.substring(i,i+1));
                        }
                    }
                i++;    
                }
                textField.setText(resultado.toString());
            }
            if (textField.getText().length() > mascara.length()) {
            	textField.setText(textField.getText(0,mascara.length()));
            }    
        }
    });
	
	}
	
	/**
	 * Monta a mascara para Moeda.
	 *
	 * @param textField TextField
	 */
	public static void monetaryField(final TextField textField) {
		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
				textField.setText(value);
				positionCaret(textField);

				textField.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observableValue, String oldValue,
							String newValue) {
						if (newValue.length() > 17)
							textField.setText(oldValue);
					}
				});
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
					Boolean fieldChange) {
				if (!fieldChange) {
					final int length = textField.getText().length();
					if (length > 0 && length < 3) {
						textField.setText(textField.getText() + "00");
					}
				}
			}
		});
	}

	/**
	 * Devido ao incremento dos caracteres das mascaras eh necessario que o cursor
	 * sempre se posicione no final da string.
	 *
	 * @param textField TextField
	 */
	private static void positionCaret(final TextField textField) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// Posiciona o cursor sempre a direita.
				textField.positionCaret(textField.getText().length());
			}
		});
	}
    
    public static boolean isValidEmailAddressRegex(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
    
    public static boolean isCNPJ(String CNPJ) {
    	// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
    	    if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
    	        CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
    	        CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
    	        CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
    	        CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
    	       (CNPJ.length() != 14))
    	       return(false);
    	 
    	    char dig13, dig14;
    	    int sm, i, r, num, peso;
    	 
    	// "try" - protege o código para eventuais erros de conversao de tipo (int)
    	    try {
    	// Calculo do 1o. Digito Verificador
    	      sm = 0;
    	      peso = 2;
    	      for (i=11; i>=0; i--) {
    	// converte o i-ésimo caractere do CNPJ em um número:
    	// por exemplo, transforma o caractere '0' no inteiro 0
    	// (48 eh a posição de '0' na tabela ASCII)
    	        num = (int)(CNPJ.charAt(i) - 48);
    	        sm = sm + (num * peso);
    	        peso = peso + 1;
    	        if (peso == 10)
    	           peso = 2;
    	      }
    	 
    	      r = sm % 11;
    	      if ((r == 0) || (r == 1))
    	         dig13 = '0';
    	      else dig13 = (char)((11-r) + 48);
    	 
    	// Calculo do 2o. Digito Verificador
    	      sm = 0;
    	      peso = 2;
    	      for (i=12; i>=0; i--) {
    	        num = (int)(CNPJ.charAt(i)- 48);
    	        sm = sm + (num * peso);
    	        peso = peso + 1;
    	        if (peso == 10)
    	           peso = 2;
    	      }
    	 
    	      r = sm % 11;
    	      if ((r == 0) || (r == 1))
    	         dig14 = '0';
    	      else dig14 = (char)((11-r) + 48);
    	 
    	// Verifica se os dígitos calculados conferem com os dígitos informados.
    	      if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
    	         return(true);
    	      else return(false);
    	    } catch (InputMismatchException erro) {
    	        return(false);
    	    }
    	  }
    	 
    	  public static String imprimeCNPJ(String CNPJ) {
    	// máscara do CNPJ: 99.999.999.9999-99
    	    return(CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." +
    	      CNPJ.substring(5, 8) + "." + CNPJ.substring(8, 12) + "-" +
    	      CNPJ.substring(12, 14));
    	  }

	
}