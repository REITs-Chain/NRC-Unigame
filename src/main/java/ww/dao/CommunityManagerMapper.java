package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.CommunityManager;

@Repository
public interface CommunityManagerMapper {
	public CommunityManager getById(java.lang.Integer id);
	
    public List<model.CommunityManager> getByCommunityId(java.lang.Integer communityId);
    public List<model.CommunityManager> getByUserId(java.lang.Integer userId);
    public List<model.CommunityManager> getByManagerType(java.lang.Integer managerType);


    public List<CommunityManager> getAll();
    public List<CommunityManager> getList(String whereAndOrderby);
    public List<CommunityManager> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<CommunityManager> getList3(String where,int limit_begin,int limit_len);
    public List<CommunityManager> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(CommunityManager obj);
    public void update(CommunityManager obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
