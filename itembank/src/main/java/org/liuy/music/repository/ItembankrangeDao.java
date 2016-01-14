package org.liuy.music.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import org.liuy.music.entity.Itembankrange;


public interface ItembankrangeDao extends PagingAndSortingRepository<Itembankrange, Long>  , JpaSpecificationExecutor<Itembankrange> {
	List <Itembankrange> findAll(Sort sort);
	 
	
 
}
