package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Linker;

@Repository
public interface LinkerMapper {
	public Linker getById(java.lang.String id);
	
    public List<model.Linker> getByUserId(java.lang.Integer userId);
    public List<model.Linker> getByLinkerName(java.lang.String linkerName);
    public List<model.Linker> getByLinkerAddress(java.lang.String linkerAddress);


    public List<Linker> getAll();
    public List<Linker> getList(String whereAndOrderby);
    public List<Linker> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Linker obj);
    public void update(Linker obj);
	public void delete(java.lang.String id);
	public void deleteWhere(String where);
}
