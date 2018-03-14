package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Rawasset;

@Repository
public interface RawassetMapper {
	public Rawasset getById(java.lang.Integer id);
	
    public List<model.Rawasset> getByIdNum(java.lang.String idNum);
    public List<model.Rawasset> getByAsset(java.lang.Double asset);


    public List<Rawasset> getAll();
    public List<Rawasset> getList(String whereAndOrderby);
    public List<Rawasset> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Rawasset obj);
    public void update(Rawasset obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
