package il.ac.afeka.cloud.data;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostDao extends PagingAndSortingRepository<PostEntity, String> {
	
	public List<PostEntity> findAllByUser(UserEntity user, PageRequest pageRequest);
	public List<PostEntity> findAllByUserAndLanguage(UserEntity user, String language, PageRequest pageRequest);
	public List<PostEntity> findAllByUserAndProduct(UserEntity user, ProductEntity product, PageRequest pageRequest);
	public List<PostEntity> findAllByUserAndPostingTimestampGreaterThanEqual(UserEntity user, Date date, PageRequest pageRequest);
	
	public List<PostEntity> findAllByProduct(ProductEntity product, PageRequest pageRequest);
	public List<PostEntity> findAllByProductAndLanguage(ProductEntity product, String language, PageRequest pageRequest);
	public List<PostEntity> findAllByProductAndPostingTimestampGreaterThanEqual(ProductEntity productEntity, Date date,
			PageRequest pageRequest);
	
	public List<PostEntity> findAllByPostingTimestampGreaterThanEqual(Date date, PageRequest pageRequest);
}
