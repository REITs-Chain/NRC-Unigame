package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.AssetCommunity;

@Repository
public interface AssetCommunityMapper {
	public AssetCommunity getById(java.lang.Integer id);
	
    public List<model.AssetCommunity> getByName(java.lang.String name);
    public List<model.AssetCommunity> getByName_en(java.lang.String name_en);
    public List<model.AssetCommunity> getByIcon(java.lang.String icon);
    public List<model.AssetCommunity> getByCreateTime(java.util.Date createTime);
    public List<model.AssetCommunity> getByAssetId(java.lang.Integer assetId);


    public List<AssetCommunity> getAll();
    public List<AssetCommunity> getList(String whereAndOrderby);
    public List<AssetCommunity> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<AssetCommunity> getList3(String where,int limit_begin,int limit_len);
    public List<AssetCommunity> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(AssetCommunity obj);
    public void update(AssetCommunity obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
