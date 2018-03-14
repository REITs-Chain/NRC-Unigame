package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Tx_vout;

@Repository
public interface Tx_voutMapper {
	public Tx_vout getById(java.lang.Long id);
	
    public List<model.Tx_vout> getByParentid(java.lang.Long parentid);
    public List<model.Tx_vout> getByUserId(java.lang.Long userId);
    public List<model.Tx_vout> getByRootAddress(java.lang.String rootAddress);
    public List<model.Tx_vout> getByAssetid(java.lang.Integer assetid);
    public List<model.Tx_vout> getByValue(java.lang.Double value);
    public List<model.Tx_vout> getByVout_n(java.lang.Long vout_n);
    public List<model.Tx_vout> getByType(java.lang.String type);
    public List<model.Tx_vout> getByAddress(java.lang.String address);


    public List<Tx_vout> getAll();
    public List<Tx_vout> getList(String whereAndOrderby);
    public List<Tx_vout> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Tx_vout obj);
    public void update(Tx_vout obj);
	public void delete(java.lang.Long id);
	public void deleteWhere(String where);
}
