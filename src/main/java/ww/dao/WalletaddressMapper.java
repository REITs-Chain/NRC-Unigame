package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Walletaddress;

@Repository
public interface WalletaddressMapper {
	public Walletaddress getById(java.lang.Integer id);
	
    public List<model.Walletaddress> getByUserId(java.lang.Integer userId);
    public List<model.Walletaddress> getByType(java.lang.Integer type);
    public List<model.Walletaddress> getByAddress(java.lang.String address);


    public List<Walletaddress> getAll();
    public List<Walletaddress> getList(String whereAndOrderby);
    public List<Walletaddress> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Walletaddress obj);
    public void update(Walletaddress obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
