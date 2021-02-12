package il.ac.afeka.cloud.logic;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import il.ac.afeka.cloud.data.PostDao;
import il.ac.afeka.cloud.data.PostEntity;
import il.ac.afeka.cloud.data.ProductEntity;
import il.ac.afeka.cloud.data.UserEntity;
import il.ac.afeka.cloud.enums.AllPostsFilterType;
import il.ac.afeka.cloud.enums.ByProductFilterType;
import il.ac.afeka.cloud.enums.ByUserFilterType;
import il.ac.afeka.cloud.enums.CreationTime;
import il.ac.afeka.cloud.enums.SortBy;
import il.ac.afeka.cloud.enums.SortOrder;
import il.ac.afeka.cloud.layout.PostBoundary;

@Service
public class MongoDbBlogService implements BlogService {
	private final String DEFAULT_SECONDARY_SORT = "_id";
	private PostDao postDao;
	
	@Autowired
	public MongoDbBlogService(PostDao postDao) {
		this.postDao = postDao;
	}

	@Override
	public PostBoundary create(PostBoundary postBoundary) {
		postBoundary.setPostingTimestamp(new Date());
		return new PostBoundary(this.postDao.save(postBoundary.toEntity()));
	}

	@Override
	public PostBoundary[] getAllByUser(String email, ByUserFilterType filterType, String filterValue, SortBy sortBy,
			SortOrder sortOrder, int size, int page) {
		PageRequest pageRequest = PageRequest.of(
				page,
				size,
				sortOrder == SortOrder.ASC ? Direction.ASC : Direction.DESC,
				sortBy.toString(),
				DEFAULT_SECONDARY_SORT);
		
		List<PostEntity> dbRes;
		
		if (filterType == null)
			dbRes = this.postDao.findAllByUser(new UserEntity(email), pageRequest);
		else
			switch (filterType) {
			case byLanguage:
				dbRes = this.postDao.findAllByUserAndLanguage(
						new UserEntity(email),
						filterValue,
						pageRequest);
				break;
			case byProduct:
				dbRes = this.postDao.findAllByUserAndProduct(
						new UserEntity(email),
						new ProductEntity(filterValue),
						pageRequest);
				break;
			case byCreation:
				dbRes = this.postDao.findAllByUserAndPostingTimestampGreaterThanEqual(
						new UserEntity(email),
						new Date(new Date().getTime() - CreationTime.valueOf(filterValue).millisecs()),
						pageRequest);
				break;

			default:
				dbRes = this.postDao.findAllByUser(new UserEntity(email), pageRequest);
				break;
			}
				
		return dbRes
				.stream()
				.map(PostBoundary::new)
				.collect(Collectors.toList())
				.toArray(new PostBoundary[0]);
	}

	@Override
	public PostBoundary[] getAllByProduct(String productId, ByProductFilterType filterType, String filterValue,
			SortBy sortBy, SortOrder sortOrder, int size, int page) {
		PageRequest pageRequest = PageRequest.of(
				page,
				size,
				sortOrder == SortOrder.ASC ? Direction.ASC : Direction.DESC,
				sortBy.toString(),
				DEFAULT_SECONDARY_SORT);
		
		List<PostEntity> dbRes;
		
		if (filterType == null)
			dbRes = this.postDao.findAllByProduct(new ProductEntity(productId), pageRequest);
		else
			switch (filterType) {
			case byLanguage:
				dbRes = this.postDao.findAllByProductAndLanguage(
						new ProductEntity(productId),
						filterValue,
						pageRequest);
				break;
			case byCreation:
				dbRes = this.postDao.findAllByProductAndPostingTimestampGreaterThanEqual(
						new ProductEntity(productId),
						new Date(new Date().getTime() - CreationTime.valueOf(filterValue).millisecs()),
						pageRequest);
				break;

			default:
				dbRes = this.postDao.findAllByProduct(new ProductEntity(productId), pageRequest);
				break;
			}
				
		return dbRes
				.stream()
				.map(PostBoundary::new)
				.collect(Collectors.toList())
				.toArray(new PostBoundary[0]);
	}

	@Override
	public PostBoundary[] getAllPosts(AllPostsFilterType filterType, String filterValue, SortBy sortBy,
			SortOrder sortOrder, int size, int page) {
			
			List<PostEntity> dbRes;
			
			if (filterType == null)
				throw new RuntimeException("Filter type url paramater is required and must be \'byCount\' or \'byCreation\'.");
			else
				switch (filterType) {
				case byCount:
					try {
						int countSize = Integer.parseInt(filterValue);
							
						dbRes = this.postDao.findAll(
								PageRequest.of(
										0,
										countSize,
										Direction.DESC,
										SortBy.postingTimestamp.toString(),
										DEFAULT_SECONDARY_SORT))
								.toList();
					} catch (Exception e) {
						throw new RuntimeException("postsCount must be an Integer value");
					}
					break;
				case byCreation:
					dbRes = this.postDao.findAllByPostingTimestampGreaterThanEqual(
							new Date(new Date().getTime() - CreationTime.valueOf(filterValue).millisecs()),
							PageRequest.of(
									page,
									size,
									sortOrder == SortOrder.ASC ? Direction.ASC : Direction.DESC,
									sortBy.toString(),
									DEFAULT_SECONDARY_SORT));
					break;
				default:
					throw new RuntimeException("Filter type url paramater is required and must be \'byCount\' or \'byCreation\'.");
				}
					
			return dbRes
					.stream()
					.map(PostBoundary::new)
					.collect(Collectors.toList())
					.toArray(new PostBoundary[0]);
	}

	@Override
	public void deleteAll() {
		this.postDao.deleteAll();
	}

}
