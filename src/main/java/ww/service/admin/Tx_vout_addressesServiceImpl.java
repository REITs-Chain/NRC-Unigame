package ww.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseServiceImpl;
import ww.common.SqlMap;

import model.Tx_vout_addresses;
import ww.dao.Tx_vout_addressesMapper;

@Service
public class Tx_vout_addressesServiceImpl extends BaseServiceImpl implements Tx_vout_addressesService  {
	
	@Autowired
	private Tx_vout_addressesMapper mapper;
	
	public Tx_vout_addressesMapper getMapper(){
		return mapper;
	}

	@Transactional
	public List<Tx_vout_addresses> getList(){    
		try{
			return mapper.getAll();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public List<Tx_vout_addresses> getList(String whereAndOrderby){
		try{
			return mapper.getList(whereAndOrderby);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public int getCount(String where){
		return mapper.getCount(where);
	}
	
	@Transactional
	public ModelAndView getPaging(Integer page,String where){ 
		ModelAndView mv=new ModelAndView();	
		
		int pageRows=20;//每页显示行数
		if(page==null){
			page=1;
		}		
		try{
			int allRows=mapper.getCount(where);
			if(allRows<=0){
				mv.addObject("page", 1);
				mv.addObject("totalPages", 1);
				mv.addObject("pageRows", pageRows);
				mv.addObject("list", new ArrayList<Tx_vout_addresses>());
				return mv;
			}
			int allPages=allRows%pageRows>0?allRows/pageRows+1:allRows/pageRows;			
			
			if(allPages==1)
				page=1;
			if(page>allPages)
				page=allPages;			
			
			int begin=pageRows*(page-1);
			int len=pageRows;		
			
			String parkSql=where+" limit "+begin+","+len;			
			List<Tx_vout_addresses> list=mapper.getList(parkSql);
			if(list==null||list.size()<=0)
				list=new ArrayList<Tx_vout_addresses>();
			
			mv.addObject("page", page);
			mv.addObject("totalPages", allPages);
			mv.addObject("pageRows", pageRows);
			mv.addObject("list", list);
			return mv;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

    @Transactional
	public List<SqlMap> selectSql(String sql){
		try{
			List<SqlMap> map=mapper.selectSql(sql);
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public Tx_vout_addresses getById(java.lang.Long id){		
		try{
			return mapper.getById(id);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isExist(java.lang.Long id){
		Tx_vout_addresses ad=mapper.getById(id);
		if(ad==null){
			return false;		
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean Save(Tx_vout_addresses data){	
	    if(data==null)
			return false;
		try{
			if(isExist(data.getId())){
				mapper.update(data);
			}else{
				mapper.insert(data);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean Delete(java.lang.Long id){		
		try{
			mapper.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}	
	
}
