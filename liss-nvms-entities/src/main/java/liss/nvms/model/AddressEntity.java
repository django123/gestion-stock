package liss.nvms.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import liss.nvms.abstracts.TimeStampEntity;
import lombok.Data;

@Entity(name = "Address")
@Table(name = "address")
@Data
public class AddressEntity extends TimeStampEntity{
	
	@Column(name = "name")
	@JsonProperty(value = "name", required = true)
	private String name;
	
	@Column(name="code")
	@JsonProperty(value = "code")
    private String code;
	
	@Column(name="postal_code")
	@JsonProperty(value = "postal_code", required = false )
    private String postalCode;
	
	@Column(name = "street")
	@JsonProperty(value = "street", required = true)
	private String street;
	
	@ManyToOne
	@JsonProperty(value = "Customer", required = false)
	@JoinColumn(name = "customer_uuid")
	private CustomerEntity customer;

}
