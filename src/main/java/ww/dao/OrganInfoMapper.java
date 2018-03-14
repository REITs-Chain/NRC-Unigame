package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.OrganInfo;

@Repository
public interface OrganInfoMapper {
	public OrganInfo getById(java.lang.Long id);
	
    public List<model.OrganInfo> getByType(java.lang.Integer type);
    public List<model.OrganInfo> getByUserId(java.lang.Long userId);
    public List<model.OrganInfo> getByName(java.lang.String name);
    public List<model.OrganInfo> getByName_en(java.lang.String name_en);
    public List<model.OrganInfo> getByUrl(java.lang.String url);
    public List<model.OrganInfo> getByRegTime(java.util.Date regTime);
    public List<model.OrganInfo> getByContent(java.lang.String content);


    public List<OrganInfo> getAll();
    public List<OrganInfo> getList(String whereAndOrderby);
    public List<OrganInfo> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<OrganInfo> getList3(String where,int limit_begin,int limit_len);
    public List<OrganInfo> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(OrganInfo obj);
    public void update(OrganInfo obj);
	public void delete(java.lang.Long id);
	public void deleteWhere(String where);
}
