package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Admin;
import ww.dao.AdminMapper;

public interface AdminService extends BaseService {
	
	public AdminMapper getMapper();

	public List<Admin> getList();
	
	public List<Admin> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Admin getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Admin data);
	
	public boolean Delete(java.lang.Integer id);

	public void updatePwd(Admin a);

	public List<Admin> getByFname(String fname);

	public void updateOkPwd(Admin a);

	
}
