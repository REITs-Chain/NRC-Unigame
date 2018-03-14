package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Tx_vout_addresses;
import ww.dao.Tx_vout_addressesMapper;

public interface Tx_vout_addressesService extends BaseService {
	
	public Tx_vout_addressesMapper getMapper();

	public List<Tx_vout_addresses> getList();
	
	public List<Tx_vout_addresses> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Tx_vout_addresses getById(java.lang.Long id);
	
	public boolean isExist(java.lang.Long id);
	
	public boolean Save(Tx_vout_addresses data);
	
	public boolean Delete(java.lang.Long id);
	
}
