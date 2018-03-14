package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Bonusaddress;
import ww.dao.BonusaddressMapper;

public interface BonusaddressService extends BaseService {
	
	public BonusaddressMapper getMapper();

	public List<Bonusaddress> getList();
	
	public List<Bonusaddress> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Bonusaddress getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Bonusaddress data);
	
	public boolean Delete(java.lang.Integer id);
	
}
