package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.CommunityManager;
import ww.dao.CommunityManagerMapper;

public interface CommunityManagerService extends BaseService {
	
	public CommunityManagerMapper getMapper();

	public List<CommunityManager> getList();
	
	public List<CommunityManager> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public CommunityManager getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(CommunityManager data);
	
	public boolean Delete(java.lang.Integer id);
	
}
