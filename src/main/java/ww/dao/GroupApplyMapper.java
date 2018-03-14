package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.GroupApply;

@Repository
public interface GroupApplyMapper {
	public GroupApply getById(java.lang.Integer id);
	
    public List<model.GroupApply> getByStatus(java.lang.Integer status);
    public List<model.GroupApply> getByGroupHeadId(java.lang.Integer groupHeadId);
    public List<model.GroupApply> getByApplyUserId(java.lang.Integer applyUserId);
    public List<model.GroupApply> getByContent(java.lang.String content);
    public List<model.GroupApply> getByGroupId(java.lang.Integer groupId);
    public List<model.GroupApply> getByApplyTime(java.util.Date applyTime);


    public List<GroupApply> getAll();
    public List<GroupApply> getList(String whereAndOrderby);
    public List<GroupApply> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<GroupApply> getList3(String where,int limit_begin,int limit_len);
    public List<GroupApply> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(GroupApply obj);
    public void update(GroupApply obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
