package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Message;
import ww.dao.MessageMapper;

public interface MessageService extends BaseService {
	
	public MessageMapper getMapper();

	public List<Message> getList();
	
	public List<Message> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Message getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Message data);
	
	public boolean Delete(java.lang.Integer id);
	
}
