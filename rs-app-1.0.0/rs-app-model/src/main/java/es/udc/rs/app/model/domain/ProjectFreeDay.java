package es.udc.rs.app.model.domain;

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

@Entity
@Table(name = "ProjectFreeDay", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idProject", "idFreeDay" }) })
public class ProjectFreeDay {
	
	private Long id;
	private Project project;
	private FreeDay freeDay;
	
	// Primary key: id.
	// Not null attribute: project, freeDay.
	// Unique attribute: (project, freeDay).
	// Business key: project, freeDay.
	
	public ProjectFreeDay() {
		
	}
	
	public ProjectFreeDay(Long id, Project project, FreeDay freeDay) {
		this.id = id;
		this.project = project;
		this.freeDay = freeDay;
	}
	
	public ProjectFreeDay(Project project, FreeDay freeDay) {
		this.project = project;
		this.freeDay = freeDay;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProjectFreeDay")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idProject", foreignKey = @ForeignKey(name = "fk_projectFreeDay_project"))
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne
	@JoinColumn(name = "idFreeDay", foreignKey = @ForeignKey(name = "fk_projectFreeDay_freeday"))
	public FreeDay getFreeDay() {
		return freeDay;
	}

	public void setFreeDay(FreeDay freeDay) {
		this.freeDay = freeDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((freeDay == null) ? 0 : freeDay.getId().hashCode());
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
		ProjectFreeDay other = (ProjectFreeDay) obj;
		if (freeDay == null) {
			if (other.freeDay != null)
				return false;
		} else if (!freeDay.equals(other.freeDay))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

}
