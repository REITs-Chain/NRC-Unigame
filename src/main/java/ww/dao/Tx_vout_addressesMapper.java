package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Tx_vout_addresses;

@Repository
public interface Tx_vout_addressesMapper {
	public Tx_vout_addresses getById(java.lang.Long id);
	
    public List<model.Tx_vout_addresses> getByParentid(java.lang.Long parentid);
    public List<model.Tx_vout_addresses> getByAddress(java.lang.String address);


    public List<Tx_vout_addresses> getAll();
    public List<Tx_vout_addresses> getList(String whereAndOrderby);
    public List<Tx_vout_addresses> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Tx_vout_addresses obj);
    public void update(Tx_vout_addresses obj);
	public void delete(java.lang.Long id);
	public void deleteWhere(String where);
}
