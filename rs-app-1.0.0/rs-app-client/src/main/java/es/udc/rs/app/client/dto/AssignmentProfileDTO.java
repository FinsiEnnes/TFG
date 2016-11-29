package es.udc.rs.app.client.dto;


public class AssignmentProfileDTO {

	private Long id;
	private Long idTask;
	private Long idProfCatg;
	private Integer units;
	private Integer hoursPerPerson;
	private Integer hoursTotal;
	private Integer cost;
	
	public AssignmentProfileDTO() {

	}
	
	public AssignmentProfileDTO(Long id, Long idTask, Long idProfCatg,
			Integer units, Integer hoursPerPerson, Integer hoursTotal,
			Integer cost) {
		this.id = id;
		this.idTask = idTask;
		this.idProfCatg = idProfCatg;
		this.units = units;
		this.hoursPerPerson = hoursPerPerson;
		this.hoursTotal = hoursTotal;
		this.cost = cost;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdTask() {
		return idTask;
	}
	public void setIdTask(Long idTask) {
		this.idTask = idTask;
	}
	public Long getIdProfCatg() {
		return idProfCatg;
	}
	public void setIdProfCatg(Long idProfCatg) {
		this.idProfCatg = idProfCatg;
	}
	public Integer getUnits() {
		return units;
	}
	public void setUnits(Integer units) {
		this.units = units;
	}
	public Integer getHoursPerPerson() {
		return hoursPerPerson;
	}
	public void setHoursPerPerson(Integer hoursPerPerson) {
		this.hoursPerPerson = hoursPerPerson;
	}
	public Integer getHoursTotal() {
		return hoursTotal;
	}
	public void setHoursTotal(Integer hoursTotal) {
		this.hoursTotal = hoursTotal;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idProfCatg == null) ? 0 : idProfCatg.hashCode());
		result = prime * result + ((idTask == null) ? 0 : idTask.hashCode());
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
		AssignmentProfileDTO other = (AssignmentProfileDTO) obj;
		if (idProfCatg == null) {
			if (other.idProfCatg != null)
				return false;
		} else if (!idProfCatg.equals(other.idProfCatg))
			return false;
		if (idTask == null) {
			if (other.idTask != null)
				return false;
		} else if (!idTask.equals(other.idTask))
			return false;
		return true;
	}
	
}
