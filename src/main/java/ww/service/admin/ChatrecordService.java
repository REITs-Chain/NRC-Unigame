package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Chatrecord;
import ww.dao.ChatrecordMapper;

public interface ChatrecordService extends BaseService {
	
	public ChatrecordMapper getMapper();

	public List<Chatrecord> getList();
	
	public List<Chatrecord> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Chatrecord getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Chatrecord data);
	
	public boolean Delete(java.lang.Integer id);
	
}
