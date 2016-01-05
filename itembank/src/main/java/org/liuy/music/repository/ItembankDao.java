package org.liuy.music.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.Task;

public interface ItembankDao extends PagingAndSortingRepository<Itembank, Long>, JpaSpecificationExecutor<Itembank> {

	Itembank findById(Long id);

	//@Modifying
	//@Query("delete from Task task where task.user.id=?1")
	//void deleteByUserId(Long id);
}
