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

@Entity
@Table(name = "Customer")
public class Customer {

	private Long id;
	private String name;
	private Province province;
	private BusinessType type;
	private BusinessCategory category;
	private BusinessSize size;
	
	// Primary key: id.
	// Not null attribute: all.
	// Unique attribute: name.
	// Business key: name, province, type, category, size.
	
	public Customer() {
		
	}
	
	public Customer(Long id, String name, Province province, BusinessType type, 
					BusinessCategory category, BusinessSize size) {
		this.id = id;
		this.name = name;
		this.province = province;
		this.type = type;
		this.category = category;
		this.size = size;
	}
	
	public Customer(String name, Province province, BusinessType type, 
			BusinessCategory category, BusinessSize size) {
		this.name = name;
		this.province = province;
		this.type = type;
		this.category = category;
		this.size = size;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCustomer")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nameCustomer", nullable = true, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "idProvince", foreignKey = @ForeignKey(name = "fk_customer_country"))
	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	@ManyToOne
	@JoinColumn(name = "idBusinessType", foreignKey = @ForeignKey(name = "fk_customer_bType"))
	public BusinessType getType() {
		return type;
	}

	public void setType(BusinessType type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "idBusinessCatg", foreignKey = @ForeignKey(name = "fk_customer_bCatg"))
	public BusinessCategory getCategory() {
		return category;
	}

	public void setCategory(BusinessCategory category) {
		this.category = category;
	}

	@ManyToOne
	@JoinColumn(name = "idBusinessSize", foreignKey = @ForeignKey(name = "fk_customer_bSize"))
	public BusinessSize getSize() {
		return size;
	}

	public void setSize(BusinessSize size) {
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
		Customer other = (Customer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", idProvince="
				+ province.getId() + ", idType=" + type.getId() + ", idCategory=" + category.getId()
				+ ", idSize=" + size.getId() + "]";
	}

}
