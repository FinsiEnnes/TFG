package es.udc.rs.app.client.dto;


public class HistoryProjectDTO {
	
	private Long id;
	private Long idProject;
	private String idState;
	private String nameState;
	private String ini;
	private String end;
	private String comment;
	

	public HistoryProjectDTO() {
		
	}
	
	public HistoryProjectDTO(Long id, Long idProject, String idState, String nameState,
			String ini, String end, String comment) {
		this.id = id;
		this.idProject = idProject;
		this.idState = idState;
		this.nameState = nameState;
		this.ini = ini;
		this.end = end;
		this.comment = comment;
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

	public String getIdState() {
		return idState;
	}

	public void setIdState(String idState) {
		this.idState = idState;
	}

	public String getNameState() {
		return nameState;
	}

	public void setNameState(String nameState) {
		this.nameState = nameState;
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
		result = prime * result
				+ ((idProject == null) ? 0 : idProject.hashCode());
		result = prime * result + ((idState == null) ? 0 : idState.hashCode());
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
		HistoryProjectDTO other = (HistoryProjectDTO) obj;
		if (idProject == null) {
			if (other.idProject != null)
				return false;
		} else if (!idProject.equals(other.idProject))
			return false;
		if (idState == null) {
			if (other.idState != null)
				return false;
		} else if (!idState.equals(other.idState))
			return false;
		return true;
	}
	
}
