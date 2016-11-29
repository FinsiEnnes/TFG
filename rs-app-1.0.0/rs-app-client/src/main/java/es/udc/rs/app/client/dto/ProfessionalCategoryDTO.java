package es.udc.rs.app.client.dto;

public class ProfessionalCategoryDTO {

	private Long id;
	private String name;
	private Integer minExp;
	private String level;
	private Integer sal;
	private String salExtra;
	
	public ProfessionalCategoryDTO() {
	}

	
	public ProfessionalCategoryDTO(Long id, String name, Integer minExp,
			String level, Integer sal, String salExtra) {
		this.id = id;
		this.name = name;
		this.minExp = minExp;
		this.level = level;
		this.sal = sal;
		this.salExtra = salExtra;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getMinExp() {
		return minExp;
	}


	public void setMinExp(Integer minExp) {
		this.minExp = minExp;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public Integer getSal() {
		return sal;
	}


	public void setSal(Integer sal) {
		this.sal = sal;
	}


	public String getSalExtra() {
		return salExtra;
	}


	public void setSalExtra(String salExtra) {
		this.salExtra = salExtra;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
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
		ProfessionalCategoryDTO other = (ProfessionalCategoryDTO) obj;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
