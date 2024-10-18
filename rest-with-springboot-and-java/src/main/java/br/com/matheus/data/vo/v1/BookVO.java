package br.com.matheus.data.vo.v1;

import br.com.matheus.model.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.github.dozermapper.core.Mapping;
import jakarta.persistence.Column;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "title" ,"author", "launchdate", "price"})
@XmlRootElement
public class BookVO extends RepresentationModel<BookVO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Mapping("id")
	private Long key;
	private String author;
	@JsonProperty("launch_date")
	@JacksonXmlProperty(localName = "launch_date")
	@Column(name = "launch_date")
	private Date launchdate;
	private Double price;
	private String title;

	public BookVO() {

	}

	public long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLaunchdate() {
		return launchdate;
	}

	public void setLaunchdate(Date launchdate) {
		this.launchdate = launchdate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookVO other = (BookVO) obj;
		return key == other.key;
	}

}
