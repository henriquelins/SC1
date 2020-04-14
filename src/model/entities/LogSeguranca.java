package model.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class LogSeguranca implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id_logSeguranca;
	private String logado;
	private String descricao;
	private Date dataLog;
	private Time horaLog;

	public LogSeguranca() {

	}

	public LogSeguranca(int id_logSeguranca, String logado, String descricao, Date dataLog, Time horaLog) {

		this.id_logSeguranca = id_logSeguranca;
		this.logado = logado;
		this.descricao = descricao;
		this.dataLog = dataLog;
		this.horaLog = horaLog;

	}

	public int getId_logSeguranca() {
		return id_logSeguranca;
	}

	public void setId_logSeguranca(int id_logSeguranca) {
		this.id_logSeguranca = id_logSeguranca;
	}

	public String getLogado() {
		return logado;
	}

	public void setLogado(String logado) {
		this.logado = logado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataLog() {
		return dataLog;
	}

	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}

	public Time getHoraLog() {
		return horaLog;
	}

	public void setHoraLog(Time horaLog) {
		this.horaLog = horaLog;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataLog == null) ? 0 : dataLog.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((horaLog == null) ? 0 : horaLog.hashCode());
		result = prime * result + id_logSeguranca;
		result = prime * result + ((logado == null) ? 0 : logado.hashCode());
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
		LogSeguranca other = (LogSeguranca) obj;
		if (dataLog == null) {
			if (other.dataLog != null)
				return false;
		} else if (!dataLog.equals(other.dataLog))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (horaLog == null) {
			if (other.horaLog != null)
				return false;
		} else if (!horaLog.equals(other.horaLog))
			return false;
		if (id_logSeguranca != other.id_logSeguranca)
			return false;
		if (logado == null) {
			if (other.logado != null)
				return false;
		} else if (!logado.equals(other.logado))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogSeguranca [id_logSeguranca=" + id_logSeguranca + ", logado=" + logado + ", descricao=" + descricao
				+ ", dataLog=" + dataLog + ", horaLog=" + horaLog + "]";
	}

}
