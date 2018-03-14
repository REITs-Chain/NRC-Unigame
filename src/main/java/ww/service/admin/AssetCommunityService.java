package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.AssetCommunity;
import ww.dao.AssetCommunityMapper;

public interface AssetCommunityService extends BaseService {
	
	public AssetCommunityMapper getMapper();

	public List<AssetCommunity> getList();
	
	public List<AssetCommunity> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public AssetCommunity getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(AssetCommunity data);
	
	public boolean Delete(java.lang.Integer id);
	
}
