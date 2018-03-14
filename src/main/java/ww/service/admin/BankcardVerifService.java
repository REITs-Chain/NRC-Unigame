package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.BankcardVerif;
import ww.dao.BankcardVerifMapper;

public interface BankcardVerifService extends BaseService {
	
	public BankcardVerifMapper getMapper();

	public List<BankcardVerif> getList();
	
	public List<BankcardVerif> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public BankcardVerif getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(BankcardVerif data);
	
	public boolean Delete(java.lang.Integer id);
	
}
