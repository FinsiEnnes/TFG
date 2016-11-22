package es.udc.rs.app.client.dto;


public class ProjectMgmtDTO {

	
	private Long id;
	private Long idProject;
	private Long idHistoryPerson;
	private String nameManager;
	private String profCatgManager;
	private String levelManager;
	private String ini;
	private String end;
	
	
	
	public ProjectMgmtDTO() {
		
	}

	public ProjectMgmtDTO(Long id, Long idProject, Long idHistoryPerson, String nameManager,
			String profCatgManager, String levelManager, String ini, String end) {
		this.id = id;
		this.idProject = idProject;
		this.idHistoryPerson = idHistoryPerson;
		this.nameManager = nameManager;
		this.profCatgManager = profCatgManager;
		this.levelManager = levelManager;
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

	public Long getIdHistoryPerson() {
		return idHistoryPerson;
	}

	public void setIdHistoryPerson(Long idHistoryPerson) {
		this.idHistoryPerson = idHistoryPerson;
	}

	public void setIdProject(Long idProject) {
		this.idProject = idProject;
	}

	public String getNameManager() {
		return nameManager;
	}

	public void setNameManager(String nameManager) {
		this.nameManager = nameManager;
	}

	public String getProfCatgManager() {
		return profCatgManager;
	}

	public void setProfCatgManager(String profCatgManager) {
		this.profCatgManager = profCatgManager;
	}

	public String getLevelManager() {
		return levelManager;
	}

	public void setLevelManager(String levelManager) {
		this.levelManager = levelManager;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idHistoryPerson == null) ? 0 : idHistoryPerson.hashCode());
		result = prime * result
				+ ((idProject == null) ? 0 : idProject.hashCode());
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
		ProjectMgmtDTO other = (ProjectMgmtDTO) obj;
		if (idHistoryPerson == null) {
			if (other.idHistoryPerson != null)
				return false;
		} else if (!idHistoryPerson.equals(other.idHistoryPerson))
			return false;
		if (idProject == null) {
			if (other.idProject != null)
				return false;
		} else if (!idProject.equals(other.idProject))
			return false;
		return true;
	}	

}
