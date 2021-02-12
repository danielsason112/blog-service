package il.ac.afeka.cloud.layout;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import il.ac.afeka.cloud.data.PostEntity;
import il.ac.afeka.cloud.data.ProductEntity;
import il.ac.afeka.cloud.data.UserEntity;

public class PostBoundary {
	@NotNull
	@Valid
	private UserBoundary user;
	@NotNull
	@Valid
	private ProductBoundary product;
	private Date postingTimestamp;
	@NotBlank
	private String language;
	@NotNull
	private Map<String, Object> postContent;
	private Map<String, Object> moreProperties;
	
	public PostBoundary() {
	}

	public PostBoundary(PostEntity entity) {
		super();

		if (entity.getUser() != null)
			this.user = new UserBoundary(entity.getUser());
		if (entity.getProduct() != null)
			this.product = new ProductBoundary(entity.getProduct());
		if (entity.getPostingTimestamp() != null)
			this.postingTimestamp = entity.getPostingTimestamp();
		if (entity.getLanguage() != null)
			this.language = entity.getLanguage();
		this.postContent = entity.getPostContent();
		this.moreProperties = entity.getMoreProperties();
	}

	public PostBoundary(UserBoundary user, ProductBoundary product, Date postingTimestamp, String language,
			Map<String, Object> postContent, Map<String, Object> moreProperties, List<String> comments) {
		super();
		this.user = user;
		this.product = product;
		this.postingTimestamp = postingTimestamp;
		this.language = language;
		this.postContent = postContent;
		this.moreProperties = moreProperties;
	}

	public UserBoundary getUser() {
		return user;
	}

	public void setUser(UserBoundary user) {
		this.user = user;
	}

	public ProductBoundary getProduct() {
		return product;
	}

	public void setProduct(ProductBoundary product) {
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

	@JsonAnyGetter
	public Map<String, Object> getMoreProperties() {
		return moreProperties;
	}
	
	@JsonAnySetter
	public void add(String key, Object value) {
		if (this.moreProperties == null) this.moreProperties = new HashMap<String, Object>();
		this.moreProperties.put(key, value);
	}
	
	public void setMoreProperties(Map<String, Object> moreProperties) {
		this.moreProperties = moreProperties;
	}
	
	@Override
	public String toString() {
		return "PostBoundary [user=" + user + ", product=" + product + ", postingTimestamp=" + postingTimestamp
				+ ", language=" + language + ", postContent=" + postContent + ", moreProperties=" + moreProperties
				+ "]";
	}
	
	public PostEntity toEntity() {
		PostEntity entity = new PostEntity();
		entity.setUser(new UserEntity(this.getUser().getEmail()));
		entity.setProduct(new ProductEntity(this.getProduct().getId()));
		entity.setPostingTimestamp(this.getPostingTimestamp());
		entity.setLanguage(this.getLanguage());
		if (this.postContent != null)
			entity.setPostContent(new HashMap<String, Object>(this.postContent));
		if (this.moreProperties != null)
			entity.setMoreProperties(new HashMap<String, Object>(this.moreProperties));
		
		return entity;		
	}

}
