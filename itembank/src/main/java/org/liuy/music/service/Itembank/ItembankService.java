package org.liuy.music.service.Itembank;

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
import org.liuy.music.entity.Task;
import org.liuy.music.repository.ItembankDao;
import org.liuy.music.repository.TaskDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

@Component
@Transactional
public class ItembankService {

	private ItembankDao itembankDao;

	public Itembank getItembank(Long id) {
		return itembankDao.findOne(id);
	}

	public void saveItembank(Itembank entity) {
		itembankDao.save(entity);
	}

	public void deleteItembank(Long id) {
		itembankDao.delete(id);
	}

	public List<Itembank> getAllItembank() {
		return (List<Itembank>) itembankDao.findAll();
	}

//	public Page<Itembank> getItembank(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
//			String sortType) {
//		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
//		//Specification<Itembank> spec = buildSpecification(Id, searchParams);
//
//		return itembankDao.findAll(spec, pageRequest);
//	}
	
	//getUserItembank(userId, searchParams, pageNumber, pageSize, sortType);
	//public Page<Itembank> getUserItembank(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
	public Page<Itembank> getUserItembank(Long userId,  int pageNumber, int pageSize, String sortType) {
	 			
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
 		//Specification<Itembank> spec = buildSpecification(Id, searchParams);
 		//return itembankDao.findAll(spec, pageRequest);
 		return itembankDao.findAll(pageRequest);
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
//	private Specification<Itembank> buildSpecification(Long userId, Map<String, Object> searchParams) {
//		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
//		Specification<Task> spec = DynamicSpecifications.bySearchFilter(filters.values(), Task.class);
//		return spec;
//	}

	public ItembankDao getItembankDao() {
		return itembankDao;
	}
	@Autowired
	public void setItembankDao(ItembankDao itembankDao) {
		this.itembankDao = itembankDao;
	}

	

}
