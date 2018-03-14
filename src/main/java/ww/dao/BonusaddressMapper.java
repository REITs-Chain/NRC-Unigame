package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Bonusaddress;

@Repository
public interface BonusaddressMapper {
	public Bonusaddress getById(java.lang.Integer id);
	
    public List<model.Bonusaddress> getByUserId(java.lang.Integer userId);
    public List<model.Bonusaddress> getByType(java.lang.String type);
    public List<model.Bonusaddress> getByAddress(java.lang.String address);
    public List<model.Bonusaddress> getBySource(java.lang.String source);


    public List<Bonusaddress> getAll();
    public List<Bonusaddress> getList(String whereAndOrderby);
    public List<Bonusaddress> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Bonusaddress obj);
    public void update(Bonusaddress obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
