package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Chatrecord;

@Repository
public interface ChatrecordMapper {
	public Chatrecord getById(java.lang.Integer id);
	
    public List<model.Chatrecord> getBySender(java.lang.String sender);
    public List<model.Chatrecord> getByReceiver(java.lang.String receiver);
    public List<model.Chatrecord> getBySendcontent(java.lang.String sendcontent);
    public List<model.Chatrecord> getBySendtime(java.util.Date sendtime);
    public List<model.Chatrecord> getByRemark(java.lang.String remark);
    public List<model.Chatrecord> getByImage(java.lang.String image);


    public List<Chatrecord> getAll();
    public List<Chatrecord> getList(String whereAndOrderby);
    public List<Chatrecord> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Chatrecord obj);
    public void update(Chatrecord obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
