package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Admin;

@Repository
public interface AdminMapper {
	public Admin getById(java.lang.Integer fid);
	
    public List<model.Admin> getByFid(java.lang.Integer fid);
    public List<model.Admin> getByFnumber(java.lang.String fnumber);
    public List<model.Admin> getByFname(java.lang.String fname);
    public List<model.Admin> getByFpassword(java.lang.String fpassword);
    public List<model.Admin> getByFlevel(java.lang.String flevel);
    public List<model.Admin> getByFcreatetime(java.util.Date fcreatetime);
    public List<model.Admin> getByFnote(java.lang.String fnote);
    public List<model.Admin> getByFclose(java.lang.Boolean fclose);


    public List<Admin> getAll();
    public List<Admin> getList(String whereAndOrderby);
    public List<Admin> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Admin obj);
    public void update(Admin obj);
	public void delete(java.lang.Integer fid);
	public void deleteWhere(String where);

	public void updatePwd(Admin a);

	public void updateOkPwd(Admin a);
}
