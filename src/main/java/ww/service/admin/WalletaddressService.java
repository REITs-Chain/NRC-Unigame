package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Walletaddress;
import ww.dao.WalletaddressMapper;

public interface WalletaddressService extends BaseService {
	
	public WalletaddressMapper getMapper();

	public List<Walletaddress> getList();
	
	public List<Walletaddress> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Walletaddress getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Walletaddress data);
	
	public boolean Delete(java.lang.Integer id);
	
}
