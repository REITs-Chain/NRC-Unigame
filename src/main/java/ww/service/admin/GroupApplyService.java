package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.GroupApply;
import ww.dao.GroupApplyMapper;

public interface GroupApplyService extends BaseService {
	
	public GroupApplyMapper getMapper();

	public List<GroupApply> getList();
	
	public List<GroupApply> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public GroupApply getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(GroupApply data);
	
	public boolean Delete(java.lang.Integer id);
	
}
