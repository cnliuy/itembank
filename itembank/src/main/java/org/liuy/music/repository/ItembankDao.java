package org.liuy.music.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.Task;

public interface ItembankDao extends PagingAndSortingRepository<Itembank, Long> , JpaSpecificationExecutor<Itembank> {

	Itembank findById(Long id);
	
	
	Page <Itembank> findByUserIdOrderByIdDesc  (Long userId, Pageable pageRequest);
	
	List <Itembank> findByItemclassifyOrderByIdDesc (String itemclassify) ;
	
	List <Itembank> findByUserId(Long userId);
	List <Itembank> findByUserIdAndItemrange1(Long userId ,String itemrange1);
	List <Itembank> findByUserIdAndItemclassify(Long userId ,String itemclassify);
	List <Itembank> findByUserIdAndItemrange2(Long userId ,String itemrange2);
	List <Itembank> findByUserIdAndItemrange1AndItemrange2(Long userId ,String itemrange1,String itemrange2);
	List <Itembank>	findByUserIdAndItemclassifyAndItemrange2(Long userId , String itemclassify,String itemrange2);
	List <Itembank> findByUserIdAndItemrange1AndItemclassify(Long userId ,String itemrange1,String itemclassify);
	List <Itembank> findByUserIdAndItemrange2AndItemclassify(Long userId ,String itemrange2,String itemclassify);
	List <Itembank> findByUserIdAndItemrange1AndItemrange2AndItemclassify(Long userId ,String itemrange1,String itemrange2,String itemclassify);
	//@Modifying
	//@Query("delete from Task task where task.user.id=?1")
	//void deleteByUserId(Long id);
}
