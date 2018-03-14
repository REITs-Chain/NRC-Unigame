package ww.authen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ww.service.admin.AdminService;
import model.Admin;

@Component
public class AdminValid {

	@Autowired
	private AdminService adminService;
	
	public Admin valid(String userName,String password){
		if(userName.equals(""))
			return null;
		List<Admin> list=adminService.getList(" where fname='"+userName+"' and fpassword='"+password+"'");
		if(list==null || list.size()!=1)
			return null;
		
		Admin admin=list.get(0);
		if(admin.getFname().equals(userName)&&admin.getFpassword().equals(password)){
			return admin;
		}else{
			return null;
		}
		
	}
	
	public Admin getAdmin(String userName){
		if(userName.equals(""))
			return null;
		List<Admin> list=adminService.getList(" where fname='"+userName+"'");
		if(list==null || list.size()!=1)
			return null;
		
		Admin admin=list.get(0);
		if(!admin.getFname().equals(userName))
			return null;
		
		return admin;
		
	}
	
}
