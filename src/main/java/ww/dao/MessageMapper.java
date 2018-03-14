package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Message;

@Repository
public interface MessageMapper {
	public Message getById(java.lang.Integer id);
	
    public List<model.Message> getByUserId(java.lang.Integer userId);
    public List<model.Message> getByTitle(java.lang.String title);
    public List<model.Message> getByContent(java.lang.String content);
    public List<model.Message> getByCreatetime(java.util.Date createtime);
    public List<model.Message> getByStatus(java.lang.Integer status);


    public List<Message> getAll();
    public List<Message> getList(String whereAndOrderby);
    public List<Message> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Message obj);
    public void update(Message obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
