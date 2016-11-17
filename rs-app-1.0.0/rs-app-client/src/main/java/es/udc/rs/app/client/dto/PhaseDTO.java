package es.udc.rs.app.client.dto;

import java.util.Date;


public class PhaseDTO {
	
	private Long id;
	private Long idProject;
	private String name;
	private Date ini;
	private Date end;
	
		
	public PhaseDTO() {
	}
		
	public PhaseDTO(Long idProject, String name, Date ini, Date end) {
		this.idProject = idProject;
		this.name = name;
		this.ini = ini;
		this.end = end;
	}
	
	public PhaseDTO(Long id, Long idProject, String name, Date ini, Date end) {
		super();
		this.id = id;
		this.idProject = idProject;
		this.name = name;
		this.ini = ini;
		this.end = end;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdProject() {
		return idProject;
	}
	public void setIdProject(Long idProject) {
		this.idProject = idProject;
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
				+ ((idProject == null) ? 0 : idProject.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PhaseDTO other = (PhaseDTO) obj;
		if (idProject == null) {
			if (other.idProject != null)
				return false;
		} else if (!idProject.equals(other.idProject))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
