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
@Table(name = "Incident")
public class Incident {
	
	private Long id;
	private Damage damage;
	private String reason;
	private String result;
	
	// Primary key: id.
	// Not null attribute: damage, reason, result.
	// Unique attribute: reason.
	// Business key: reason. 
	
	public Incident() {
		
	}
	
	public Incident(Long id, Damage damage, String reason, String result) {
		this.id = id;
		this.damage = damage;
		this.reason = reason;
		this.result = result;
	}
	
	public Incident(Damage damage, String reason, String result) {
		this.damage = damage;
		this.reason = reason;
		this.result = result;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idIncident")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idDamage", foreignKey = @ForeignKey(name = "fk_incident_damage"))
	public Damage getDamage() {
		return damage;
	}

	public void setDamage(Damage damage) {
		this.damage = damage;
	}

	@Column(name = "reasonIncident", nullable = true, unique = true)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "resultIncident", nullable = true)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
		Incident other = (Incident) obj;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Incident [id=" + id + ", idDamage=" + damage.getId() + ", reason="
				+ reason + ", result=" + result + "]";
	}

}
