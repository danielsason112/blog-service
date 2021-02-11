package il.ac.afeka.cloud.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import il.ac.afeka.cloud.enums.AllPostsFilterType;
import il.ac.afeka.cloud.enums.ByProductFilterType;
import il.ac.afeka.cloud.enums.ByUserFilterType;
import il.ac.afeka.cloud.enums.SortBy;
import il.ac.afeka.cloud.enums.SortOrder;
import il.ac.afeka.cloud.logic.BlogService;

@RestController
public class BlogController {
	private BlogService blogService;

	@Autowired
	public BlogController(BlogService blogService) {
		this.blogService = blogService;
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/blog",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PostBoundary create(@RequestBody PostBoundary postBoundary) {
		return this.blogService.create(postBoundary);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/blog/byUser/{email}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PostBoundary[] getAllByUser(
			@PathVariable("email") String email,
			@RequestParam(name = "filterType", required = false) ByUserFilterType filterType,
			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") SortBy sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") SortOrder sortOrder,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.blogService.getAllByUser(
				email,
				filterType,
				filterValue,
				sortBy,
				sortOrder,
				size,
				page);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/blog/byProduct/{productId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PostBoundary[] getAllByProduct(
			@PathVariable("productId") String productId,
			@RequestParam(name = "filterType", required = false) ByProductFilterType filterType,
			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") SortBy sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") SortOrder sortOrder,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.blogService.getAllByProduct(
				productId,
				filterType,
				filterValue,
				sortBy,
				sortOrder,
				size,
				page);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/blog",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PostBoundary[] getAllPosts(
			@RequestParam(name = "filterType", required = true) AllPostsFilterType filterType,
			@RequestParam(name = "filterValue", required = true) String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") SortBy sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") SortOrder sortOrder,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.blogService.getAllPosts(
				filterType,
				filterValue,
				sortBy,
				sortOrder,
				size,
				page);
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/blog")
	public void deleteAll() {
		this.blogService.deleteAll();
	}
}
