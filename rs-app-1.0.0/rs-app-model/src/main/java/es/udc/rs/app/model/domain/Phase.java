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
@Table(name = "Phase", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idProject", "namePhase" }) })
public class Phase {
	
	private Long id;
	private Project project;
	private String name;
	private Date iniPlan;
	private Date endPlan;
	private String comment;
	
	// Primary key: id.
	// Not null attribute: project, name.
	// Unique attribute: (project, name).
	// Business key: project, name. 
	
	public Phase() {
		
	}
	
	public Phase(Long id, Project project, String name, Date iniPlan, Date endPlan, String comment) {
		this.id = id;
		this.project = project;
		this.name = name;
		this.iniPlan = iniPlan;
		this.endPlan = endPlan;
		this.comment = comment;
	}
	
	public Phase(Project project, String name, Date iniPlan, Date endPlan, String comment) {
		this.project = project;
		this.name = name;
		this.iniPlan = iniPlan;
		this.endPlan = endPlan;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPhase")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "idProject", foreignKey = @ForeignKey(name = "fk_phase_idProject"))
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	@Column(name = "namePhase", nullable = true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "iniPlanPhase", nullable = true)
	@Type(type = "date")
	public Date getIniPlan() {
		return iniPlan;
	}

	public void setIniPlan(Date iniPlan) {
		this.iniPlan = iniPlan;
	}

	@Column(name = "endPlanPhase", nullable = true)
	@Type(type = "date")
	public Date getEndPlan() {
		return endPlan;
	}

	public void setEndPlan(Date endPlan) {
		this.endPlan = endPlan;
	}

	@Column(name = "commentPhase")
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Phase other = (Phase) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		return "Phase [id=" + id + ", idProject=" + project.getId() + ", name=" + name
				+ ", comment=" + comment + "]";
	}

}
