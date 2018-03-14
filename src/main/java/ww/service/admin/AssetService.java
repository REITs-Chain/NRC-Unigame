package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Asset;
import ww.dao.AssetMapper;

public interface AssetService extends BaseService {
	
	public AssetMapper getMapper();

	public List<Asset> getList();
	
	public List<Asset> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Asset getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Asset data);
	
	public boolean Delete(java.lang.Integer id);
	
}
