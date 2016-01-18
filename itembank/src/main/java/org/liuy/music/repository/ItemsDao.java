package org.liuy.music.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.Items;


public interface ItemsDao extends PagingAndSortingRepository<Items, Long> {
	Items findById(Long id);	
	Page <Items> findByUserIdOrderByIdDesc  (Long userId, Pageable pageRequest);	
	List <Items> findByUserId(Long userId);
}
