package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Tx_vin;

@Repository
public interface Tx_vinMapper {
	public Tx_vin getById(java.lang.Long id);
	
    public List<model.Tx_vin> getByParentId(java.lang.Long parentId);
    public List<model.Tx_vin> getByTxid(java.lang.String txid);
    public List<model.Tx_vin> getByVout(java.lang.Double vout);
    public List<model.Tx_vin> getBySequence(java.lang.Long sequence);
    public List<model.Tx_vin> getByCoinbase(java.lang.String coinbase);


    public List<Tx_vin> getAll();
    public List<Tx_vin> getList(String whereAndOrderby);
    public List<Tx_vin> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Tx_vin obj);
    public void update(Tx_vin obj);
	public void delete(java.lang.Long id);
	public void deleteWhere(String where);
}
