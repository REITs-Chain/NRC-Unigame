package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Function;

@Repository
public interface FunctionMapper {
	public Function getById(java.lang.Integer fid);
	
    public List<model.Function> getByFid(java.lang.Integer fid);
    public List<model.Function> getByFnumber(java.lang.String fnumber);
    public List<model.Function> getByFname(java.lang.String fname);
    public List<model.Function> getByFparentId(java.lang.Integer fparentId);
    public List<model.Function> getByFlevel(java.lang.Integer flevel);
    public List<model.Function> getByFisGroup(java.lang.Boolean fisGroup);
    public List<model.Function> getByFindex(java.lang.Integer findex);
    public List<model.Function> getByFurl(java.lang.String furl);
    public List<model.Function> getByFicon(java.lang.String ficon);
    public List<model.Function> getByFvisible(java.lang.Boolean fvisible);


    public List<Function> getAll();
    public List<Function> getList(String whereAndOrderby);
    public List<Function> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Function obj);
    public void update(Function obj);
	public void delete(java.lang.Integer fid);
	public void deleteWhere(String where);
}
