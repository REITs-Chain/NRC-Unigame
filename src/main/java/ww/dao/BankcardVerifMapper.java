package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.BankcardVerif;

@Repository
public interface BankcardVerifMapper {
	public BankcardVerif getById(java.lang.Integer id);
	
    public List<model.BankcardVerif> getByUserId(java.lang.Integer userId);
    public List<model.BankcardVerif> getByBankCardNum(java.lang.String bankCardNum);
    public List<model.BankcardVerif> getByBankName(java.lang.String bankName);
    public List<model.BankcardVerif> getByName(java.lang.String name);
    public List<model.BankcardVerif> getByIdNumber(java.lang.String idNumber);
    public List<model.BankcardVerif> getByVerifTime(java.util.Date verifTime);


    public List<BankcardVerif> getAll();
    public List<BankcardVerif> getList(String whereAndOrderby);
    public List<BankcardVerif> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(BankcardVerif obj);
    public void update(BankcardVerif obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
