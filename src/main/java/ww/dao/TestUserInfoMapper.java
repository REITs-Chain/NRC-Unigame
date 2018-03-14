package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.TestUserInfo;

@Repository
public interface TestUserInfoMapper {
	public TestUserInfo getById(java.lang.Integer userId);
	
    public List<model.TestUserInfo> getByUserId(java.lang.Integer userId);
    public List<model.TestUserInfo> getByRealName(java.lang.String realName);
    public List<model.TestUserInfo> getByIdNum(java.lang.String idNum);
    public List<model.TestUserInfo> getByPhoneNum(java.lang.String phoneNum);
    public List<model.TestUserInfo> getByStatus(java.lang.Integer status);
    public List<model.TestUserInfo> getByCreateTime(java.util.Date createTime);
    public List<model.TestUserInfo> getByNote(java.lang.String note);


    public List<TestUserInfo> getAll();
    public List<TestUserInfo> getList(String whereAndOrderby);
    public List<TestUserInfo> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<TestUserInfo> getList3(String where,int limit_begin,int limit_len);
    public List<TestUserInfo> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(TestUserInfo obj);
    public void update(TestUserInfo obj);
	public void delete(java.lang.Integer userId);
	public void deleteWhere(String where);
}
