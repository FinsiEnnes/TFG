package es.udc.rs.app.client.dto;

public class CustomerDTO {

	private Long id;
	private String name;
	private String country;
	private String province;
	private String type;
	private String category;
	private String size;
	
	public CustomerDTO() {
		
	}
	
	public CustomerDTO(Long id, String name, String country, String province,
			String type, String category, String size) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.province = province;
		this.type = type;
		this.category = category;
		this.size = size;
	}
	
	public CustomerDTO(String name, String country, String province,
			String type, String category, String size) {
		super();
		this.name = name;
		this.country = country;
		this.province = province;
		this.type = type;
		this.category = category;
		this.size = size;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		CustomerDTO other = (CustomerDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", name=" + name + ", country="
				+ country + ", province=" + province + ", type=" + type
				+ ", category=" + category + ", size=" + size + "]";
	}

}
