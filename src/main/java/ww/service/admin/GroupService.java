package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Group;
import ww.dao.GroupMapper;

public interface GroupService extends BaseService {
	
	public GroupMapper getMapper();

	public List<Group> getList();
	
	public List<Group> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Group getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Group data);
	
	public boolean Delete(java.lang.Integer id);
	
}
