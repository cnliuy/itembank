package org.liuy.music.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import org.liuy.music.entity.Itembankkind;


public interface ItembankkindDao extends PagingAndSortingRepository<Itembankkind, Long>  , JpaSpecificationExecutor<Itembankkind> {
	 
	List <Itembankkind> findAll(Sort sort);
	 
 
}
