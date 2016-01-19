package org.liuy.music.service.Itembank;

import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.Items;
import org.liuy.music.entity.ReturnResponse;
import org.liuy.music.repository.ItembankDao;
import org.liuy.music.repository.ItemsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class IaccessauthService {
	
	//retCode 返回值 ：-1 鉴权失败 不具备权限 
	//				-2 程序异常 产生错误
	//				 1 鉴权成功 具备权限 retInfo 返回信息
	//				 0 数据为空
	public static final  int rCanNotDo = -1 ;
	public static final  int rErrCode = -2 ;
	public static final  int rCanDo = 1 ;
	public static final  int rBeNull = 0 ;
	
	private ItembankDao itembankDao;
	private ItemsDao itemsDao;
	
	 /**
	  * 判断用户的读权限
	  * 
	  * */
	public ReturnResponse checkUserRead(Long CurrentUserId ,Long  itembankId ) {
		Integer retCode = rCanNotDo; 
		String retInfo ="鉴权失败" ;
		ReturnResponse  rr = new ReturnResponse();
		Itembank  ib = itembankDao.findById(itembankId);
		if( ib == null){
			rr.setRetCode(rBeNull);
			rr.setRetInfo("查不到相关题目");	
		}else{			
			long ouserId = ib.getUserId().longValue();  //得到属主的id值			
			long cuserId = CurrentUserId.longValue() ;
			if (cuserId == ouserId){
				rr.setRetCode(rCanDo);
				rr.setRetInfo("请阅读题目");//成功
			}else{
				//可以做更进一步的鉴权
				rr.setRetCode(rCanNotDo);
				rr.setRetInfo("无权读取数据");					
			}
		}

		return rr;
	}
	
	
	 /**
	  * 判断用户的更新权限
	  * 
	  * */
	public ReturnResponse checkUserUpdate(Long CurrentUserId ,Itembank  itembank) {
		ReturnResponse  rr = new ReturnResponse();		 
		Long itembankIdl = itembank.getId() ;
		rr = checkUserUpdate(CurrentUserId , itembankIdl) ;  
		return rr;
	}
	
	 /**
	  * 判断用户的更新权限
	  * 
	  * */
	public ReturnResponse checkUserUpdate(Long CurrentUserId ,String  itembankId) {
		ReturnResponse  rr = new ReturnResponse();
	 
		try {
			Long itembankIdl = Long.parseLong(itembankId);
			rr = checkUserUpdate(CurrentUserId , itembankIdl) ;  
		} catch (Exception e) {
			rr.setRetCode(rErrCode);
			rr.setRetInfo("itembankId从String转换到Long失败");
		}
		return rr;
	}
	
	 /**
	  * 判断用户的更新权限
	  * 
	  * */
	public ReturnResponse checkUserUpdate(Long CurrentUserId ,Long  itembankId) {
		Integer retCode = rCanNotDo; 
		String retInfo ="鉴权失败" ;
		ReturnResponse  rr = new ReturnResponse();
		Itembank  ib = itembankDao.findById(itembankId);
		if( ib == null){
			rr.setRetCode(rBeNull);
			rr.setRetInfo("查不到相关信息");	
		}else{
			long ouserId = ib.getUserId().longValue();  //得到属主的id值
			long cuserId = CurrentUserId.longValue() ;
			if (cuserId == ouserId){
				rr.setRetCode(rCanDo);
				rr.setRetInfo("更新成功");	
			}else{
				//可以做更进一步的鉴权
				rr.setRetCode(rCanNotDo);
				rr.setRetInfo("无权更新数据");					
			}
		}

		return rr;
	}
	
	 /**
	  * 判断用户的删除权限
	  * 
	  * */
	public ReturnResponse checkUserDel(Long CurrentUserId ,Itembank  itembank) {
		ReturnResponse  rr = new ReturnResponse();
		Long itembankIdl = itembank.getId() ;
		rr = checkUserDel(CurrentUserId , itembankIdl) ;		 
		return rr;
	}
	
	 /**
	  * 判断用户的删除权限
	  * 
	  * */
	public ReturnResponse checkUserDel(Long CurrentUserId ,Long itembankId) {
		Integer retCode = rCanNotDo; 
		String retInfo ="鉴权失败" ;
		ReturnResponse  rr = new ReturnResponse();
		Itembank  ib = itembankDao.findById(itembankId);
		if( ib == null){
			rr.setRetCode(rBeNull);
			rr.setRetInfo("查不到相关信息");	
		}else{
			long ouserId = ib.getUserId().longValue();  //得到属主的id值
			long cuserId = CurrentUserId.longValue() ;
			if (cuserId == ouserId){
				rr.setRetCode(rCanDo);
				rr.setRetInfo("删除成功");	
			}else{
				//可以做更进一步的鉴权
				rr.setRetCode(rCanNotDo);
				rr.setRetInfo("无权删除数据");					
			}
		}

		return rr;
	}
	
	 /**
	  * items
	  * 判断用户的删除权限
	  * 
	  * */
	public ReturnResponse checkItemsUserDel(Long CurrentUserId ,Items  items) {
		ReturnResponse  rr = new ReturnResponse();
		Long itemsIdl = items.getId() ;
		rr = checkItemsUserDel(CurrentUserId , itemsIdl) ;		 
		return rr;
	}
	
	 /**
	  * items
	  * 判断用户的删除权限
	  * 
	  * */
	public ReturnResponse checkItemsUserDel(Long CurrentUserId ,Long itemsId) {
		Integer retCode = rCanNotDo; 
		String retInfo ="鉴权失败" ;
		ReturnResponse  rr = new ReturnResponse();
		Items  ib = itemsDao.findById(itemsId);
		if( ib == null){
			rr.setRetCode(rBeNull);
			rr.setRetInfo("查不到相关信息");	
		}else{
			long ouserId = ib.getUserId().longValue();  //得到属主的id值
			long cuserId = CurrentUserId.longValue() ;
			if (cuserId == ouserId){
				rr.setRetCode(rCanDo);
				rr.setRetInfo("删除成功");	
			}else{
				//可以做更进一步的鉴权
				rr.setRetCode(rCanNotDo);
				rr.setRetInfo("鉴权失败,无权删除数据");					
			}
		}

		return rr;
	}
	
	
	
	 /**
	  * items
	  * 
	  * 判断items的
	  * 用户的更新权限
	  * 
	  * */
	public ReturnResponse checkItemsUserUpdate(Long CurrentUserId ,Items  items) {
		ReturnResponse  rr = new ReturnResponse();		 
		Long itemsIdl = items.getId() ;
		rr = checkItemsUserUpdate(CurrentUserId , itemsIdl) ;  
		return rr;
	}
	
	 /**
	  * 
	  * items
	  * 判断用户的更新权限
	  * 
	  * */
	public ReturnResponse checkItemsUserUpdate(Long CurrentUserId ,Long  itemsId) {
		Integer retCode = rCanNotDo; 
		String retInfo ="鉴权失败" ;
		ReturnResponse  rr = new ReturnResponse();
		Items  ib = itemsDao.findById(itemsId);
		if( ib == null){
			rr.setRetCode(rBeNull);
			rr.setRetInfo("查不到相关信息");	
		}else{
			long ouserId = ib.getUserId().longValue();  //得到属主的id值
			long cuserId = CurrentUserId.longValue() ;
			if (cuserId == ouserId){
				rr.setRetCode(rCanDo);
				rr.setRetInfo("更新成功");	
			}else{
				//可以做更进一步的鉴权
				rr.setRetCode(rCanNotDo);
				rr.setRetInfo("无权更新数据");					
			}
		}

		return rr;
	}
	
	
	 /**
	  * 判断用户的读权限
	  * items
	  * */
	public ReturnResponse checkItemsUserRead(Long CurrentUserId ,Long  itemsId ) {
		Integer retCode = rCanNotDo; 
		String retInfo ="鉴权失败" ;
		ReturnResponse  rr = new ReturnResponse();
		Items  ib = itemsDao.findById(itemsId);
		if( ib == null){
			rr.setRetCode(rBeNull);
			rr.setRetInfo("查不到相关题目");	
		}else{			
			long ouserId = ib.getUserId().longValue();  //得到属主的id值			
			long cuserId = CurrentUserId.longValue() ;
			if (cuserId == ouserId){
				rr.setRetCode(rCanDo);
				rr.setRetInfo("请阅读题目");//成功
			}else{
				//可以做更进一步的鉴权
				rr.setRetCode(rCanNotDo);
				rr.setRetInfo("无权读取数据");					
			}
		}

		return rr;
	}

	public ItembankDao getItembankDao() {
		return itembankDao;
	}
	@Autowired
	public void setItembankDao(ItembankDao itembankDao) {
		this.itembankDao = itembankDao;
	}


	public ItemsDao getItemsDao() {
		return itemsDao;
	}

	@Autowired
	public void setItemsDao(ItemsDao itemsDao) {
		this.itemsDao = itemsDao;
	}
	
	
	
}
