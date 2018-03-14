package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.ReportLoss;
import ww.dao.ReportLossMapper;

public interface ReportLossService extends BaseService {
	
	public ReportLossMapper getMapper();

	public List<ReportLoss> getList();
	
	public List<ReportLoss> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public ReportLoss getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(ReportLoss data);
	
	public boolean Delete(java.lang.Integer id);
	
}
