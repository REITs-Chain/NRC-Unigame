package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Tx_vin;
import ww.dao.Tx_vinMapper;

public interface Tx_vinService extends BaseService {
	
	public Tx_vinMapper getMapper();

	public List<Tx_vin> getList();
	
	public List<Tx_vin> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Tx_vin getById(java.lang.Long id);
	
	public boolean isExist(java.lang.Long id);
	
	public boolean Save(Tx_vin data);
	
	public boolean Delete(java.lang.Long id);
	
}
