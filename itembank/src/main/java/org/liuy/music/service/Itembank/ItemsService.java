package org.liuy.music.service.Itembank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.Itembankkind;
import org.liuy.music.entity.Itembanklevel;
import org.liuy.music.entity.Itembankrange;
import org.liuy.music.entity.Items;
import org.liuy.music.entity.Task;
import org.liuy.music.entity.User;
import org.liuy.music.repository.ItembankDao;
import org.liuy.music.repository.ItembankkindDao;
import org.liuy.music.repository.ItembanklevelDao;
import org.liuy.music.repository.ItembankrangeDao;
import org.liuy.music.repository.ItemsDao;
import org.liuy.music.repository.TaskDao;
import org.liuy.music.repository.UserDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

@Component
@Transactional
public class ItemsService {
	private UserDao userDao;
	private ItemsDao itemsDao;	
	 

	public Items getItems(Long id) {
		return itemsDao.findOne(id);
	}
	
	
	
	public void saveItems(Items entity) {
		itemsDao.save(entity);
	}

	public void deleteItems(Long id) {
		itemsDao.delete(id);
	}

	public List<Items> getAllItems() {
		return (List<Items>) itemsDao.findAll();
	}

	public Page<Items> getUserItemsList(Long userId, 
			int pageNumber, int pageSize, String sortType) {
	 			
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
 		//Specification<Itembank> spec = buildSpecification(userId , searchParams);
 		//return itembankDao.findAll(spec, pageRequest);
		
 		return itemsDao.findByUserIdOrderByIdDesc(userId, pageRequest);
	}
	
	

	

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} 
//		else if ("title".equals(sortType)) {
//			sort = new Sort(Direction.ASC, "title");
//		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Itembank> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("userId", new SearchFilter("userId", Operator.EQ, userId));
		Specification<Itembank> spec = DynamicSpecifications.bySearchFilter(filters.values(), Itembank.class);
		return spec;
	}

	public ItemsDao getItemsDao() {
		return itemsDao;
	}


	@Autowired
	public void setItemsDao(ItemsDao itemsDao) {
		this.itemsDao = itemsDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}	
	

}
