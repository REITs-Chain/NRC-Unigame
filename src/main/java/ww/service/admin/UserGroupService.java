package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.UserGroup;
import ww.dao.UserGroupMapper;

public interface UserGroupService extends BaseService {
	
	public UserGroupMapper getMapper();

	public List<UserGroup> getList();
	
	public List<UserGroup> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public UserGroup getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(UserGroup data);
	
	public boolean Delete(java.lang.Integer id);
	
}
