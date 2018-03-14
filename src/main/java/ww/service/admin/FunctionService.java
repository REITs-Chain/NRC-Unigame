package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Function;
import ww.dao.FunctionMapper;

public interface FunctionService extends BaseService {
	
	public FunctionMapper getMapper();

	public List<Function> getList();
	
	public List<Function> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Function getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Function data);
	
	public boolean Delete(java.lang.Integer id);
	
}
