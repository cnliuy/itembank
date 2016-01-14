package org.liuy.music.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import org.liuy.music.entity.Itembankkind;
import org.liuy.music.entity.Itembanklevel;


public interface ItembanklevelDao extends PagingAndSortingRepository<Itembanklevel, Long>  , JpaSpecificationExecutor<Itembanklevel> {
	 
	List <Itembanklevel> findAll(Sort sort);
	 
 
}
