package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Rawasset;
import ww.dao.RawassetMapper;

public interface RawassetService extends BaseService {
	
	public RawassetMapper getMapper();

	public List<Rawasset> getList();
	
	public List<Rawasset> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Rawasset getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Rawasset data);
	
	public boolean Delete(java.lang.Integer id);
	
}
