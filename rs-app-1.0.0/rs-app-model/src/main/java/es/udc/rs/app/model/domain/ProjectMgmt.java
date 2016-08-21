package es.udc.rs.app.model.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "ProjectMgmt", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idProject", "idHPerson"}) })
public class ProjectMgmt {

	private Long id;
	private Project project;
	private HistoryPerson historyPerson;
	private Date ini;
	private Date end;
	
	// Primary key: id.
	// Not null attribute: project, historyPerson, ini.
	// Unique attribute: (project, historyPerson).
	// Business key: project, historyPerson.
	
	public ProjectMgmt(Long id, Project project, HistoryPerson historyPerson, Date ini, Date end) {
		this.id = id;
		this.project = project;
		this.historyPerson = historyPerson;
		this.ini = ini;
		this.end = end;
	}
	
	public ProjectMgmt(Project project, HistoryPerson historyPerson, Date ini, Date end) {
		this.project = project;
		this.historyPerson = historyPerson;
		this.ini = ini;
		this.end = end;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProjecMgmt")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idProject", foreignKey = @ForeignKey(name = "fk_projectmgmt_project"))
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne
	@JoinColumn(name = "idHPerson", foreignKey = @ForeignKey(name = "fk_projectmgmt_person"))
	public HistoryPerson getHistoryPerson() {
		return historyPerson;
	}

	public void setHistoryPerson(HistoryPerson historyPerson) {
		this.historyPerson = historyPerson;
	}

	@Column(name = "iniProjectMgmt", nullable = true)
	@Type(type = "date")
	public Date getIni() {
		return ini;
	}

	public void setIni(Date ini) {
		this.ini = ini;
	}

	@Column(name = "endProjectMgmt")
	@Type(type = "date")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((historyPerson == null) ? 0 : historyPerson.getId().hashCode());
		result = prime * result + ((project == null) ? 0 : project.getId().hashCode());
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
		ProjectMgmt other = (ProjectMgmt) obj;
		if (historyPerson == null) {
			if (other.historyPerson != null)
				return false;
		} else if (!historyPerson.equals(other.historyPerson))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectMgmt [id=" + id + ", idProject=" + project.getId()
				+ ", idHistoryPerson=" + historyPerson.getId() + ", ini=" + ini
				+ ", end=" + end + "]";
	}
	
}
