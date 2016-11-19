package es.udc.rs.app.client.dto;

public class MilestoneDTO {

	private Long id;
	private Long idPhase;
	private String namePhase;
	private String name;
	private String datePlan;
	private String dateReal;
	private String comment;
	
	public MilestoneDTO() {
		
	}

	public MilestoneDTO(Long id, Long idPhase, String namePhase, String name,
			String datePlan, String dateReal, String comment) {
		this.id = id;
		this.idPhase = idPhase;
		this.namePhase = namePhase;
		this.name = name;
		this.datePlan = datePlan;
		this.dateReal = dateReal;
		this.comment = comment;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdPhase() {
		return idPhase;
	}
	public void setIdPhase(Long idPhase) {
		this.idPhase = idPhase;
	}
	
	public String getNamePhase() {
		return namePhase;
	}
	public void setNamePhase(String namePhase) {
		this.namePhase = namePhase;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDatePlan() {
		return datePlan;
	}
	public void setDatePlan(String datePlan) {
		this.datePlan = datePlan;
	}
	
	public String getDateReal() {
		return dateReal;
	}
	public void setDateReal(String dateReal) {
		this.dateReal = dateReal;
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
		result = prime * result + ((idPhase == null) ? 0 : idPhase.hashCode());
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
		MilestoneDTO other = (MilestoneDTO) obj;
		if (idPhase == null) {
			if (other.idPhase != null)
				return false;
		} else if (!idPhase.equals(other.idPhase))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
	
}
