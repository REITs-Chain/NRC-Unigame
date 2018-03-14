package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.UserRisk;
import ww.dao.UserRiskMapper;

public interface UserRiskService extends BaseService {
	
	public UserRiskMapper getMapper();

	public List<UserRisk> getList();
	
	public List<UserRisk> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public UserRisk getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(UserRisk data);
	
	public boolean Delete(java.lang.Integer id);
	
}
