package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Tx;

@Repository
public interface TxMapper {
	public Tx getById(java.lang.Long id);
	
    public List<model.Tx> getByParentid(java.lang.Long parentid);
    public List<model.Tx> getByTxid(java.lang.String txid);
    public List<model.Tx> getByVersion(java.lang.Long version);
    public List<model.Tx> getByLocktime(java.lang.Integer locktime);
    public List<model.Tx> getByOutvalue(java.lang.Double outvalue);
    public List<model.Tx> getByBlockhash(java.lang.String blockhash);
    public List<model.Tx> getByConfirmations(java.lang.Long confirmations);
    public List<model.Tx> getByTime(java.lang.Integer time);
    public List<model.Tx> getByBlocktime(java.lang.Integer blocktime);
    public List<model.Tx> getByYear(java.lang.Integer year);
    public List<model.Tx> getByMonth(java.lang.Integer month);
    public List<model.Tx> getByDay(java.lang.Integer day);


    public List<Tx> getAll();
    public List<Tx> getList(String whereAndOrderby);
    public List<Tx> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Tx obj);
    public void update(Tx obj);
	public void delete(java.lang.Long id);
	public void deleteWhere(String where);
}
