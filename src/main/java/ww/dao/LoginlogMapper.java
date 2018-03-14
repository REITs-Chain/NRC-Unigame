package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Loginlog;

@Repository
public interface LoginlogMapper {
	public Loginlog getById(java.lang.Integer id);
	
    public List<model.Loginlog> getByUserId(java.lang.Integer userId);
    public List<model.Loginlog> getByLoginTime(java.util.Date loginTime);
    public List<model.Loginlog> getByIp(java.lang.String ip);
    public List<model.Loginlog> getByLocation(java.lang.String location);
    public List<model.Loginlog> getByStatus(java.lang.String status);


    public List<Loginlog> getAll();
    public List<Loginlog> getList(String whereAndOrderby);
    public List<Loginlog> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Loginlog obj);
    public void update(Loginlog obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
