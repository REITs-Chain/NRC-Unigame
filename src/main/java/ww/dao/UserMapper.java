package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.User;

@Repository
public interface UserMapper {
	public User getById(java.lang.Integer id);
	
    public List<model.User> getByType(java.lang.Integer type);
    public List<model.User> getByAreaNum(java.lang.String areaNum);
    public List<model.User> getByPhoneNum(java.lang.String phoneNum);
    public List<model.User> getByEmail(java.lang.String email);
    public List<model.User> getByPassword(java.lang.String password);
    public List<model.User> getByPassword_his(java.lang.String password_his);
    public List<model.User> getByNickName(java.lang.String nickName);
    public List<model.User> getByRegTime(java.util.Date regTime);
    public List<model.User> getByRealName(java.lang.String realName);
    public List<model.User> getByGender(java.lang.Integer gender);
    public List<model.User> getByIdType(java.lang.String idType);
    public List<model.User> getByIdNum(java.lang.String idNum);
    public List<model.User> getByIdPhoto1(java.lang.String idPhoto1);
    public List<model.User> getByIdPhoto2(java.lang.String idPhoto2);
    public List<model.User> getByPhoto(java.lang.String photo);
    public List<model.User> getByAvatar(java.lang.String avatar);
    public List<model.User> getByStatus(java.lang.Integer status);
    public List<model.User> getByCertificationStatus(java.lang.Integer certificationStatus);
    public List<model.User> getByCertificationTime(java.util.Date certificationTime);
    public List<model.User> getByLoginLock(java.lang.Boolean loginLock);
    public List<model.User> getByLoginTimes(java.lang.Integer loginTimes);
    public List<model.User> getByWalletLock(java.lang.Integer walletLock);
    public List<model.User> getByQq(java.lang.String qq);
    public List<model.User> getByWeixin(java.lang.String weixin);
    public List<model.User> getByBankCardVerifStatus(java.lang.Integer bankCardVerifStatus);
    public List<model.User> getByInviteCode(java.lang.String inviteCode);
    public List<model.User> getByParentInviteCode(java.lang.String parentInviteCode);
    public List<model.User> getByWalletAddress(java.lang.String walletAddress);
    public List<model.User> getByWalletAddress_his(java.lang.String walletAddress_his);
    public List<model.User> getByAesKey(java.lang.String aesKey);
    public List<model.User> getByAesIv(java.lang.String aesIv);
    public List<model.User> getByDefaultLanguage(java.lang.String defaultLanguage);
    public List<model.User> getByNewUserReward(java.lang.Integer newUserReward);
    public List<model.User> getByWalletBackup(java.lang.String walletBackup);
    public List<model.User> getByTransPassword(java.lang.String transPassword);
    public List<model.User> getByRongCloudToken(java.lang.String rongCloudToken);


    public List<User> getAll();
    public List<User> getList(String whereAndOrderby);
    public List<User> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<User> getList3(String where,int limit_begin,int limit_len);
    public List<User> getList4(String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(User obj);
    public void update(User obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
