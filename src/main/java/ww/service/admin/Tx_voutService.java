package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Tx_vout;
import ww.dao.Tx_voutMapper;

public interface Tx_voutService extends BaseService {
	
	public Tx_voutMapper getMapper();

	public List<Tx_vout> getList();
	
	public List<Tx_vout> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Tx_vout getById(java.lang.Long id);
	
	public boolean isExist(java.lang.Long id);
	
	public boolean Save(Tx_vout data);
	
	public boolean Delete(java.lang.Long id);
	
}
