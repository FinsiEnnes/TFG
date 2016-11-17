package es.udc.rs.app.model.domain;

import java.util.Date;

public class PhaseSort {

	private Long id;
	private Project project;
	private String name;
	private Date ini;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIni() {
		return ini;
	}
	public void setIni(Date ini) {
		this.ini = ini;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
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
		PhaseSort other = (PhaseSort) obj;
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
	
	
}
