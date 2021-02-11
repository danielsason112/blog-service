package il.ac.afeka.cloud.logic;

import il.ac.afeka.cloud.enums.AllPostsFilterType;
import il.ac.afeka.cloud.enums.ByProductFilterType;
import il.ac.afeka.cloud.enums.ByUserFilterType;
import il.ac.afeka.cloud.enums.SortBy;
import il.ac.afeka.cloud.enums.SortOrder;
import il.ac.afeka.cloud.layout.PostBoundary;

public interface BlogService {

	public PostBoundary create(PostBoundary postBoundary);

	public PostBoundary[] getAllByUser(String email, ByUserFilterType filterType, String filterValue, SortBy sortBy,
			SortOrder sortOrder, int size, int page);

	public PostBoundary[] getAllByProduct(String productId, ByProductFilterType filterType, String filterValue,
			SortBy sortBy, SortOrder sortOrder, int size, int page);

	public PostBoundary[] getAllPosts(AllPostsFilterType filterType, String filterValue, SortBy sortBy,
			SortOrder sortOrder, int size, int page);

	public void deleteAll();

}
