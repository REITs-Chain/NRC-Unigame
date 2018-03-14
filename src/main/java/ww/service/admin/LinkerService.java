package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Linker;
import ww.dao.LinkerMapper;

public interface LinkerService extends BaseService {
	
	public LinkerMapper getMapper();

	public List<Linker> getList();
	
	public List<Linker> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Linker getById(java.lang.String id);
	
	public boolean isExist(java.lang.String id);
	
	public boolean Save(Linker data);
	
	public boolean Delete(java.lang.String id);
	
}
