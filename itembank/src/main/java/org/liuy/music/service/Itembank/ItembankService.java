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
import org.liuy.music.entity.Task;
import org.liuy.music.entity.User;
import org.liuy.music.repository.ItembankDao;
import org.liuy.music.repository.ItembankkindDao;
import org.liuy.music.repository.ItembanklevelDao;
import org.liuy.music.repository.ItembankrangeDao;
import org.liuy.music.repository.TaskDao;
import org.liuy.music.repository.UserDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

@Component
@Transactional
public class ItembankService {
	private UserDao userDao;
	private ItembankDao itembankDao;	
	private ItembankkindDao itembankkindDao;
	private ItembankrangeDao itembankrangeDao;
	private ItembanklevelDao itembanklevelDao;

	public Itembank getItembank(Long id) {
		return itembankDao.findOne(id);
	}
	
	
	public List<Itembankkind> gogetAllItembankkind() {
		//Sort s =  new Sort(Sort.Direction.DESC, "id");
		Sort s =  new Sort(Sort.Direction.ASC, "id");
		return itembankkindDao.findAll(s);		
	}
	
	public List<Itembanklevel> gogetAllItembanklevel() {
		//Sort s =  new Sort(Sort.Direction.DESC, "id");
		Sort s =  new Sort(Sort.Direction.ASC, "id");
		return itembanklevelDao.findAll(s);		
	}
	
	
	public List<Itembankrange> gogetAllItembankrange() {
		//Sort s =  new Sort(Sort.Direction.DESC, "id");
		Sort s =  new Sort(Sort.Direction.ASC, "id");
		return itembankrangeDao.findAll(s);		
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
	public Page<Itembank> getUserItembankList(Long userId, 
			int pageNumber, int pageSize, String sortType) {
	 			
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
 		//Specification<Itembank> spec = buildSpecification(userId , searchParams);
 		//return itembankDao.findAll(spec, pageRequest);
		
 		return itembankDao.findByUserIdOrderByIdDesc(userId, pageRequest);
	}
	
	/**
	 * 得到所需的 Itembank 列表
	 * 
	 * 
	 * */
	public   List<Itembank> gogetUserItembankList(List<Long> itembankIds) {
		List <Itembank>  ibs = new ArrayList<Itembank>();
		 
		Iterator<Long> idsi = itembankIds.iterator();
	
	    while(idsi.hasNext()){
	    	Itembank ib= itembankDao.findById(idsi.next());
	    	if(ib==null){
	    		
	    	}else{
	    		ibs.add(ib);
	    	}	         
	    }
	    
	    return ibs;
		
	}
	
	
	/**
	 * 得到所需的 Itembank 列表
	 * 	itemrange1
	 * 	itemclassify
	 * 	itemrange2
	 * 
	 * 
	 * */
	public   List<Itembank> gogetItembankList( Long userid  ,String itemclassify , String itemrange1 , String itemrange2 ) {
		User u = userDao.findOne(userid) ;
		System.out.println("role:"+ u.getRoles());
		System.out.println("--in gogetItembankList（）  itemclassify:"+ itemclassify);
		//itemclassify= itemclassify.substring(1);
		
		List <Itembank> ibl ;
		if (("全部".equals(itemclassify))&&("全部".equals(itemrange1))&&("全部".equals(itemrange2)) ){
			ibl = itembankDao.findByUserId(userid);			
		}else if(("全部".equals(itemclassify))&&("全部".equals(itemrange1))){
			ibl = itembankDao.findByUserIdAndItemrange2(userid , itemrange2);		
		}else if(("全部".equals(itemclassify))&&("全部".equals(itemrange2))){		
			ibl = itembankDao.findByUserIdAndItemrange1(userid , itemrange1);	
		}else if(("全部".equals(itemrange1))&&("全部".equals(itemrange2))){
			ibl = itembankDao.findByUserIdAndItemclassify(userid , itemclassify);
		}else if("全部".equals(itemrange1)){
			ibl = itembankDao.findByUserIdAndItemclassifyAndItemrange2(userid , itemclassify,itemrange2);
		}else if("全部".equals(itemrange2)){
			ibl = itembankDao.findByUserIdAndItemrange1AndItemclassify(userid , itemrange1, itemclassify);
		}else if("全部".equals(itemclassify)){
			ibl = itembankDao.findByUserIdAndItemrange1AndItemrange2(userid , itemrange1, itemrange2);
		}else{
			ibl = itembankDao.findByUserIdAndItemrange1AndItemrange2AndItemclassify(userid , itemrange1, itemrange2,itemclassify);
		}
		
		//return itembankDao.findByItemclassifyOrderByIdDesc(itemclassify);
		return ibl ;
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

	public ItembankDao getItembankDao() {
		return itembankDao;
	}
	@Autowired
	public void setItembankDao(ItembankDao itembankDao) {
		this.itembankDao = itembankDao;
	}


	public ItembankkindDao getItembankkindDao() {
		return itembankkindDao;
	}

	@Autowired
	public void setItembankkindDao(ItembankkindDao itembankkindDao) {
		this.itembankkindDao = itembankkindDao;
	}


	public ItembankrangeDao getItembankrangeDao() {
		return itembankrangeDao;
	}
	@Autowired
	public void setItembankrangeDao(ItembankrangeDao itembankrangeDao) {
		this.itembankrangeDao = itembankrangeDao;
	}


	public ItembanklevelDao getItembanklevelDao() {
		return itembanklevelDao;
	}

	@Autowired
	public void setItembanklevelDao(ItembanklevelDao itembanklevelDao) {
		this.itembanklevelDao = itembanklevelDao;
	}


	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
	
	

}
