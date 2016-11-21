package es.udc.rs.app.client.dto;


public class ProvinceDTO {

	private Long id;
	private String name;
	private String idCountry;
	private String country;
	
	public ProvinceDTO() {

	}
	
	public ProvinceDTO(Long id, String name, String idCountry, String country) {
		this.id = id;
		this.name = name;
		this.idCountry = idCountry;
		this.country = country;
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

	public String getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(String idCountry) {
		this.idCountry = idCountry;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCountry == null) ? 0 : idCountry.hashCode());
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
		ProvinceDTO other = (ProvinceDTO) obj;
		if (idCountry == null) {
			if (other.idCountry != null)
				return false;
		} else if (!idCountry.equals(other.idCountry))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
	
}
