package br.com.matheus.integrationTests.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement //Para poder serializar como Xml qnd precisar
public class PersonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonProperty("first_name")
	@JacksonXmlProperty(localName = "first_name")
	private String firstName;
	@JsonProperty("last_name")
	@JacksonXmlProperty(localName = "last_name")
	private String lastName;
	private String address;
	private String gender;
	private Boolean enabled;

	public PersonVO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getEnabled() {return enabled;}

	public void setEnabled(Boolean enabled) {this.enabled = enabled;}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersonVO personVO = (PersonVO) o;
		return Objects.equals(id, personVO.id) && Objects.equals(firstName, personVO.firstName) && Objects.equals(lastName, personVO.lastName) && Objects.equals(address, personVO.address) && Objects.equals(gender, personVO.gender) && Objects.equals(enabled, personVO.enabled);
	}
}
