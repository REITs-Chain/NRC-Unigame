package ww.authen;

import java.util.Date;

import ww.common.ModelDAO;
import ww.common.SqlMap;

public class LoginUserUtil {
	public static LoginUser getByUsername(String username){
		ModelDAO dao=new ModelDAO();
		
		SqlMap u=dao.M("t_user").where("phoneNum='"+ModelDAO.sqlParamString(username)+"'").selectOne();
		if(u==null){
			return null;
		}
		LoginUser user=readUser(u);
		
		return user;
	}
	
	public static LoginUser getById(long userId){
		ModelDAO dao=new ModelDAO();
		
		SqlMap u=dao.M("t_user").where("id="+userId).selectOne();
		if(u==null){
			return null;
		}
		LoginUser user=readUser(u);
		
		return user;
	}
	
	public static LoginUser getByWalletAddress(String walletAddress){
		ModelDAO dao=new ModelDAO();
		
		SqlMap u=dao.M("t_user").where("walletAddress='"+ModelDAO.sqlParamString(walletAddress)+"'").selectOne();
		if(u==null){
			return null;
		}
		LoginUser user=readUser(u);
		
		return user;
	}
	
	private static LoginUser readUser(SqlMap smUser){		
		SqlMap u=smUser;
		if(u==null){
			return null;
		}
		LoginUser user=new LoginUser();
		user.setUserid(u.getInt("id"));
		user.setType(smUser.getInt("type"));
		user.setUsername(u.getString("phoneNum"));
		user.setPassword(u.getString("password"));
		user.setLoginTimes(u.getInt("loginTimes"));
		user.setLock(u.getBoolean("lock"));
		user.setNickName(u.getString("nickName"));
		user.setRealName(u.getString("realName"));
		user.setIdNum(u.getString("idNum"));
		user.setAvatar(u.getString("avatar"));
		user.setPassword(u.getString("password"));
		user.setCertificationStatus(u.getInt("certificationStatus"));
		user.setBankCardVerifStatus(u.getInt("bankCardVerifStatus"));
		user.setWalletAddress(u.getString("walletAddress"));
		user.setAesKey(u.getString("aesKey"));
		user.setAesIv(u.getString("aesIv"));
		user.setDefaultLanguage(u.getString("defaultLanguage"));
		user.setTransPassword(u.getString("transPassword"));
		user.setAreaNum(u.getString("areaNum"));
		return user;
	}
	
	public static void updateLoginTimes(int newTimes,int userid){
		ModelDAO dao=new ModelDAO();		
		dao.M("t_user").where("id="+userid).update("loginTimes="+newTimes);
	}
	
	public static void SaveLoginLog(int userId,String ip,String location,String status){
		ModelDAO dao=new ModelDAO();		
		SqlMap data=new SqlMap();
		data.put("userId", userId);
		data.put("loginTime", ww.common.WwSystem.now());
		data.put("ip", ip);
		data.put("location", location);
		data.put("status", status);
		long res=dao.M("t_loginlog").insert(data);
		if(res!=1){
			System.out.println(dao.Message);
		}
		
	}
	
	
}
