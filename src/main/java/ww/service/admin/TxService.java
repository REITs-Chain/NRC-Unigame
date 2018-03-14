package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Tx;
import ww.dao.TxMapper;

public interface TxService extends BaseService {
	
	public TxMapper getMapper();

	public List<Tx> getList();
	
	public List<Tx> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Tx getById(java.lang.Long id);
	
	public boolean isExist(java.lang.Long id);
	
	public boolean Save(Tx data);
	
	public boolean Delete(java.lang.Long id);
	
}
