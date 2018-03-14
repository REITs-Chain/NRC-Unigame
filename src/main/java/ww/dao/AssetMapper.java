package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Asset;

@Repository
public interface AssetMapper {
	public Asset getById(java.lang.Integer id);
	
    public List<model.Asset> getBySname(java.lang.String sname);
    public List<model.Asset> getByName(java.lang.String name);
    public List<model.Asset> getByName_en(java.lang.String name_en);
    public List<model.Asset> getByCirculation(java.lang.Long circulation);
    public List<model.Asset> getByIssueAddress(java.lang.String issueAddress);
    public List<model.Asset> getByIssueTime(java.util.Date issueTime);
    public List<model.Asset> getByStatus(java.lang.Integer status);
    public List<model.Asset> getByImageUrl(java.lang.String imageUrl);
    public List<model.Asset> getByProdutIntroUrl(java.lang.String produtIntroUrl);
    public List<model.Asset> getByIssueDatum(java.lang.String issueDatum);
    public List<model.Asset> getByTitle(java.lang.String title);
    public List<model.Asset> getByContent(java.lang.String content);


    public List<Asset> getAll();
    public List<Asset> getList(String whereAndOrderby);
    public List<Asset> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<Asset> getList3(String where,int limit_begin,int limit_len);
    public List<Asset> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Asset obj);
    public void update(Asset obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
