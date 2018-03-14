package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.UserGroup;

@Repository
public interface UserGroupMapper {
	public UserGroup getById(java.lang.Integer id);
	
    public List<model.UserGroup> getByUserId(java.lang.Integer userId);
    public List<model.UserGroup> getByGroupId(java.lang.Integer groupId);
    public List<model.UserGroup> getByType(java.lang.String type);


    public List<UserGroup> getAll();
    public List<UserGroup> getList(String whereAndOrderby);
    public List<UserGroup> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<UserGroup> getList3(String where,int limit_begin,int limit_len);
    public List<UserGroup> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(UserGroup obj);
    public void update(UserGroup obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
