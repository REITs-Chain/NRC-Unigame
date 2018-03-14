package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.User;
import ww.dao.UserMapper;

public interface UserService extends BaseService {
	
	public UserMapper getMapper();

	public List<User> getList();
	
	public List<User> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public User getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(User data);
	
	public boolean Delete(java.lang.Integer id);
	
}
