package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Loginlog;
import ww.dao.LoginlogMapper;

public interface LoginlogService extends BaseService {
	
	public LoginlogMapper getMapper();

	public List<Loginlog> getList();
	
	public List<Loginlog> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Loginlog getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Loginlog data);
	
	public boolean Delete(java.lang.Integer id);
	
}
