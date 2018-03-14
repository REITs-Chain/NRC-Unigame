package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Group;

@Repository
public interface GroupMapper {
	public Group getById(java.lang.Integer id);
	
    public List<model.Group> getByName(java.lang.String name);
    public List<model.Group> getByName_en(java.lang.String name_en);
    public List<model.Group> getByIcon(java.lang.String icon);
    public List<model.Group> getByGroupQRcode(java.lang.String groupQRcode);
    public List<model.Group> getByMinStarLevel(java.lang.Integer minStarLevel);
    public List<model.Group> getByStatus(java.lang.Integer status);
    public List<model.Group> getByCreateTime(java.util.Date createTime);
    public List<model.Group> getByCreatePerson(java.lang.String createPerson);
    public List<model.Group> getByManager(java.lang.String manager);
    public List<model.Group> getByManagerNum(java.lang.Integer managerNum);
    public List<model.Group> getByDescribe(java.lang.String describe);
    public List<model.Group> getByCommunityId(java.lang.Integer communityId);
    public List<model.Group> getBySynchron(java.lang.Integer synchron);


    public List<Group> getAll();
    public List<Group> getList(String whereAndOrderby);
    public List<Group> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<Group> getList3(String where,int limit_begin,int limit_len);
    public List<Group> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Group obj);
    public void update(Group obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
