package email;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import model.entities.Cliente;
import model.entities.ServicoImpressao;
import model.entities.Vendedor;
import model.services.ClienteService;
import model.services.VendedorService;

public class Email implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEmail;
	private String nomeEmail;
	private String descricaoEmail;
	private byte[] imagemAssinaturaEmail;
	private String nomeServidor;
	private String hostName;
	private Integer smtpPort;
	private String authentication;

	public Email() {
	}

	public Email(Integer idEmail, String nomeEmail, String descricaoEmail, byte[] imagemAssinaturaEmail,
			String nomeServidor, String hostName, Integer smtpPort, String authentication) {
		this.idEmail = idEmail;
		this.nomeEmail = nomeEmail;
		this.descricaoEmail = descricaoEmail;
		this.imagemAssinaturaEmail = imagemAssinaturaEmail;
		this.nomeServidor = nomeServidor;
		this.hostName = hostName;
		this.smtpPort = smtpPort;
		this.authentication = authentication;
	}

	public Integer getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}

	public String getNomeEmail() {
		return nomeEmail;
	}

	public void setNomeEmail(String nomeEmail) {
		this.nomeEmail = nomeEmail;
	}

	public String getDescricaoEmail() {
		return descricaoEmail;
	}

	public void setDescricaoEmail(String descricaoEmail) {
		this.descricaoEmail = descricaoEmail;
	}

	public byte[] getImagemAssinaturaEmail() {
		return imagemAssinaturaEmail;
	}

	public void setImagemAssinaturaEmail(byte[] imagemAssinaturaEmail) {
		this.imagemAssinaturaEmail = imagemAssinaturaEmail;
	}

	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authentication == null) ? 0 : authentication.hashCode());
		result = prime * result + ((descricaoEmail == null) ? 0 : descricaoEmail.hashCode());
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + ((idEmail == null) ? 0 : idEmail.hashCode());
		result = prime * result + Arrays.hashCode(imagemAssinaturaEmail);
		result = prime * result + ((nomeEmail == null) ? 0 : nomeEmail.hashCode());
		result = prime * result + ((nomeServidor == null) ? 0 : nomeServidor.hashCode());
		result = prime * result + ((smtpPort == null) ? 0 : smtpPort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (authentication == null) {
			if (other.authentication != null)
				return false;
		} else if (!authentication.equals(other.authentication))
			return false;
		if (descricaoEmail == null) {
			if (other.descricaoEmail != null)
				return false;
		} else if (!descricaoEmail.equals(other.descricaoEmail))
			return false;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (idEmail == null) {
			if (other.idEmail != null)
				return false;
		} else if (!idEmail.equals(other.idEmail))
			return false;
		if (!Arrays.equals(imagemAssinaturaEmail, other.imagemAssinaturaEmail))
			return false;
		if (nomeEmail == null) {
			if (other.nomeEmail != null)
				return false;
		} else if (!nomeEmail.equals(other.nomeEmail))
			return false;
		if (nomeServidor == null) {
			if (other.nomeServidor != null)
				return false;
		} else if (!nomeServidor.equals(other.nomeServidor))
			return false;
		if (smtpPort == null) {
			if (other.smtpPort != null)
				return false;
		} else if (!smtpPort.equals(other.smtpPort))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Email [idEmail=" + idEmail + ", nomeEmail=" + nomeEmail + ", descricaoEmail=" + descricaoEmail
				+ ", imagemAssinaturaEmail=" + Arrays.toString(imagemAssinaturaEmail) + ", nomeServidor=" + nomeServidor
				+ ", hostName=" + hostName + ", smtpPort=" + smtpPort + ", authentication=" + authentication + "]";
	}

	public void enviarEmailLancamento(ServicoImpressao servicoImpressao, String status, String texto) {

		new Thread() {

			@Override
			public void run() {

				Cliente cliente = new ClienteService().buscarPeloId(servicoImpressao.getIdCliente());
				Vendedor vendedor = new VendedorService().buscarPeloId(cliente.getCod_vendedor());

				// email html
				HtmlEmail email = new HtmlEmail();

				// email.setDebug(true);

				email.setSSL(true);

				// host de envio smtp
				email.setHostName("smtp.gmail.com");
				// porta smtp
				email.setSmtpPort(587);
				// login e senha do e-mail
				email.setAuthentication("riquelins@gmail.com", "j232729h");
				// seu e-mail e nome
				try {
					email.setFrom("riquelins@gmail.com", "Henrique");
				} catch (EmailException e) {

					e.printStackTrace();
				}
				// assunto do e-mail
				email.setSubject("Prezado Cliente ");
				// mensagem

				String id = null;
				try {
					id = email.embed(new File("D:\\images\\HENRIQUE.jpg"));
				} catch (EmailException e) {

					e.printStackTrace();
				}

				StringBuilder builder = new StringBuilder();
				builder.append("<h1><font color=00044E>CLIENTE: " + cliente.getNomeFantasia() + ". </h1></ font>");
				builder.append("<h1></h1>");
				builder.append("<p><font color=00044E>Aviso ao vendedor <b>" + vendedor.getNomeVendedor()
						+ "</b>, o lançamento do servico <b>" + servicoImpressao.getNomeDoServico()
						+ "</b>.</ font><p>");
				builder.append("<p><font color=00044E> " + texto + "</ font><p>");
				builder.append("<p><font color=00044E> Status do serviço:  <b>" + status + "</b></ font><p>");
				builder.append("<html> - <img src=\"cid:" + id + "\"></html>");

				try {
					email.addTo("henriquelins@msn.com", "Henrique");
				} catch (EmailException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					email.setHtmlMsg(builder.toString());
				} catch (EmailException e) {

					e.printStackTrace();
				}

				// enviar
				try {
					email.send();

					// Alerts.showAlert("Enviar e-mail", "Envio de e-mail", "E-mail enviado com
					// sucesso!",
					// AlertType.INFORMATION);

				} catch (EmailException e) {

					e.printStackTrace();

					// Alerts.showAlert("Enviar e-mail", "Envio de e-mail", "Erro ao enviar e-mail!
					// " + e.getMessage(),
					// AlertType.ERROR);
				}

				// Mensagem de envio correto

			}

		}.start();

	}

}
