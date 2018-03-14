package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.UserRisk;

@Repository
public interface UserRiskMapper {
	public UserRisk getById(java.lang.Integer id);
	
    public List<model.UserRisk> getByUserId(java.lang.Integer userId);
    public List<model.UserRisk> getByStatus(java.lang.Integer status);
    public List<model.UserRisk> getByScore(java.lang.Integer score);
    public List<model.UserRisk> getByType(java.lang.Integer type);
    public List<model.UserRisk> getByTime(java.util.Date time);


    public List<UserRisk> getAll();
    public List<UserRisk> getList(String whereAndOrderby);
    public List<UserRisk> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(UserRisk obj);
    public void update(UserRisk obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
