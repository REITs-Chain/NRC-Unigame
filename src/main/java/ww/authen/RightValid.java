package ww.authen;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ww.common.ModelDAO;
import ww.common.SqlList;
import ww.common.SqlMap;
import model.Admin;

public class RightValid {
	public static boolean Valid(String frameName,String right,HttpServletRequest request,HttpServletResponse response){
		//权限验证
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		String message="";
		if(admin.getFname().equalsIgnoreCase("ww")||admin.getFname().equalsIgnoreCase("amdin")){
			//超级用户不需要权限验证
			return true;
		}else{
			//检查是否有权限
			ModelDAO dao=new ModelDAO();
			if(isRight(dao,admin.getFid(),frameName,right)){
				return true;
			}
			SqlMap data1=dao.M("w_frame").where("fname='"+frameName+"'").selectOne();
			SqlMap data2=dao.M("w_right").where("fname='"+right+"'").selectOne();
			if(data1!=null&&data2!=null)
				message="你没有【"+data1.getString("ftitle")+"】的【"+data2.getString("ftitle")+"】权限,请与系统管理者联系！";
			else
				message="你没有相关权限，请与系统管理者联系！";
			try {
				//String rootUrl=WwSystem.getRoot(request);
				//response.sendRedirect(rootUrl+"login?message=未登陆");
				RequestDispatcher dispatcher=request.getRequestDispatcher("/admin/FrameRight/rightError?message="+message);
				dispatcher.forward(request, response);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}
	}
	
	public static boolean Valid(Admin admin,String frameName,String right){
		//权限验证
		//String message="";
		if(admin.getFname().equalsIgnoreCase("ww")||admin.getFname().equalsIgnoreCase("amdin")){
			//超级用户不需要权限验证
			return true;
		}else{
			//检查是否有权限
			ModelDAO dao=new ModelDAO();
			if(isRight(dao,admin.getFid(),frameName,right)){
				return true;
			}
			return false;
		}
	}
	
	//判断是否有权限
	public static boolean isRight(ModelDAO dao,int adminId,String frameName,String right){		
		SqlList list=dao.query("select count(*) as count from w_admin_right t2 where t2.fadminid="+adminId+" and t2.fframename='"+frameName+"' and t2.fright='"+right+"'");
		SqlMap data=list.get(0);
		if(data.getInt("count")>0)
			return true;
		else
			return false;
	}
	
	//判断是否有指定的表单
	public static boolean hasFrame(ModelDAO dao,String frameName){		
		SqlList list=dao.query("select count(*) as count from w_frame t2 where t2.fname='"+frameName+"'");
		SqlMap data=list.get(0);
		if(data.getInt("count")>0)
			return true;
		else
			return false;
	}
	
	//判断是否有指定的权限项(不是验证)
	public static boolean hasRight(ModelDAO dao,String right){		
		SqlList list=dao.query("select count(*) as count from w_right t2 where t2.fname='"+right+"'");
		SqlMap data=list.get(0);
		if(data.getInt("count")>0)
			return true;
		else
			return false;
	}
	
}
