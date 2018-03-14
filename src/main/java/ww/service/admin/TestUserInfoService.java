package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.TestUserInfo;
import ww.dao.TestUserInfoMapper;

public interface TestUserInfoService extends BaseService {
	
	public TestUserInfoMapper getMapper();

	public List<TestUserInfo> getList();
	
	public List<TestUserInfo> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public TestUserInfo getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(TestUserInfo data);
	
	public boolean Delete(java.lang.Integer id);
	
}
