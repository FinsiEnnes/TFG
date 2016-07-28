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
@Table(name = "HistoryProject", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idProject", "idState" }) })
public class HistoryProject {
	
	private Long id;
	private Project project;
	private State state;
	private Date ini;
	private Date end;
	private String comment;

	// Primary key: id.
	// Not null attribute: project, state, ini.
	// Unique attribute: (project, state).
	// Business key: project, state.
	
	public HistoryProject() {
		
	}
	
	public HistoryProject(Long id, Project project, State state, Date ini, Date end, String comment) {
		this.id = id;
		this.project = project;
		this.state = state;
		this.ini = ini;
		this.end = end;
		this.comment = comment;
	}
	
	public HistoryProject(Project project, State state, Date ini, Date end, String comment) {
		this.project = project;
		this.state = state;
		this.ini = ini;
		this.end = end;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idHProject")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idProject", foreignKey = @ForeignKey(name = "fk_historyproject_idProject"))
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne
	@JoinColumn(name = "idState", foreignKey = @ForeignKey(name = "fk_historyproject_idState"))
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Column(name = "iniHProject", nullable = true)
	@Type(type = "date")
	public Date getIni() {
		return ini;
	}

	public void setIni(Date ini) {
		this.ini = ini;
	}

	@Column(name = "endHProject")
	@Type(type = "date")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Column(name = "commentHProject")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project == null) ? 0 : project.getId().hashCode());
		result = prime * result + ((state == null) ? 0 : state.getId().hashCode());
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
		HistoryProject other = (HistoryProject) obj;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoryProject [id=" + id + ", idProject=" + project.getId() + ", idState="
				+ state.getId() + ", ini=" + ini + ", end=" + end + ", comment="
				+ comment + "]";
	}

}
