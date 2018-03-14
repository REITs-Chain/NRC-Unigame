package ww.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.googlecode.ehcache.annotations.Cacheable;

import model.Admin;
import ww.authen.AdminValid;
import ww.common.ModelDAO;
import ww.common.SqlMap;
import ww.security.common.LoginInfo;

public class DataRealm implements InitializingBean {
	
	@Autowired
	private AdminValid adminValid;

	public DataRealm() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 登录时调用，获取用户信息
	 * @param userName
	 * @return
	 */
	public LoginInfo getLoginInfo(String userName){

		Admin admin=adminValid.getAdmin(userName);
		if(admin==null)
			return null;
		
		LoginInfo li=new LoginInfo();
		li.userName=userName;
		li.password=admin.getFpassword();
		li.user=admin;
		
		return li;				
	}
	
	/**
	 * 验证权限时用，获取用户相关的权限信息
	 * @param userName
	 * @return
	 */
	@Cacheable(cacheName="method_wwsecurity_cache") //启用缓存功能
	public List<String> getPermssions(String userName){
		Admin admin=adminValid.getAdmin(userName);

		ModelDAO dao=new ModelDAO();
		
		ArrayList<String> list=new ArrayList<String>(100);
		
		SqlRowSet rs=dao.queryToRowSet("select fperms from w_admin_perms where fadminid="+admin.getFid());
		while(rs.next()){
			list.add(rs.getString(1));
		}
		
		
		StringBuffer sql=new StringBuffer();
		sql.append("select t1.fperms from w_role_perms t1");
		sql.append(" left JOIN w_admin_role t2 on t1.froleid=t2.froleid");
		sql.append(" where t2.fadminid="+admin.getFid());
				
		SqlRowSet rs2=dao.queryToRowSet(sql.toString());
		while(rs2.next()){
			list.add(rs2.getString(1));
		}
		
		return list;				
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}
