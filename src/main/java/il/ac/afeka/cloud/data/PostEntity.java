package il.ac.afeka.cloud.data;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class PostEntity {
	private String id;
	@NotNull
	private UserEntity user;
	@NotNull
	private ProductEntity product;
	@NotNull
	private Date postingTimestamp;
	@NotBlank
	private String language;
	@NotNull
	private Map<String, Object> postContent;
	private Map<String, Object> moreProperties;
	
	public PostEntity() {
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public Date getPostingTimestamp() {
		return postingTimestamp;
	}

	public void setPostingTimestamp(Date postingTimestamp) {
		this.postingTimestamp = postingTimestamp;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Map<String, Object> getPostContent() {
		return postContent;
	}

	public void setPostContent(Map<String, Object> postContent) {
		this.postContent = postContent;
	}

	public Map<String, Object> getMoreProperties() {
		return moreProperties;
	}

	public void setMoreProperties(Map<String, Object> moreProperties) {
		this.moreProperties = moreProperties;
	}

	@Override
	public String toString() {
		return "PostEntity [id=" + id + ", user=" + user + ", product=" + product + ", postingTimestamp="
				+ postingTimestamp + ", language=" + language + ", postContent=" + postContent + ", moreProperties="
				+ moreProperties + "]";
	}

}
