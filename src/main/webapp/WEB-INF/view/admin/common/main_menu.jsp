<%@page import="model.Admin"%>
<%@ page language="java" import="java.util.*,ww.common.WwSystem,ww.common.WwFunctionMenu" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	Admin admin=(Admin)request.getSession().getAttribute("admin");
%>
	  <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <!-- Sidebar user panel -->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="public/adminLTE/dist/img/nrc-01.png" class="img-circle" alt="用户头像" />
            </div>
            <div class="pull-left info">
              <p><%=admin.getFname() %></p>
              <a href="javascript:;"><i class="fa fa-circle text-success"></i> 在线</a>
            </div>
          </div>
          <!-- search form -->
          <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
              <input type="text" name="q" class="form-control" placeholder="搜索..." />
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
          </form>
          <!-- /.search form -->
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li class="header">主菜单</li>
            <% 
            	String curUrl=(String)session.getAttribute("ww_raw_url");	//"admin/User/list";
            	if(curUrl==null)
            		curUrl="";
            	curUrl=curUrl.toLowerCase();
                Map<Integer,WwFunctionMenu> menus=(Map<Integer,WwFunctionMenu>)session.getAttribute("main_menus");
                if(menus==null){
            		menus=WwFunctionMenu.getFunctions();
            		session.setAttribute("main_menus", menus);
           		}
            	
            	Iterator iter = menus.entrySet().iterator();
            	while(iter.hasNext()){
            		Map.Entry entry = (Map.Entry) iter.next();
                    WwFunctionMenu menu = (WwFunctionMenu)entry.getValue();
                    String title=menu.fun.getFname();
                    
		            String url=menu.fun.getFurl();
		            if(url.isEmpty())
		            	url="javascript:;";
		            String icon=menu.fun.getFicon();
		            if(icon.isEmpty())
		            	icon="fa-dashboard";
		            
		            String active="";
                    if(menu.findSubMenu(curUrl)!=null)
                    	active="active";
             %>
            <li class="<%=active %> treeview">
              <a href="<%=url%>">
                <i class="fa <%=icon%>"></i> 
                <span><%=title %></span> 
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu" style="margin-left: 10px;">
              <%    if(menu.submenus.size()>0){
              			Iterator iter2 = menu.submenus.entrySet().iterator();
		            	while(iter2.hasNext()){
		            		Map.Entry entry2 = (Map.Entry) iter2.next();
		                    WwFunctionMenu submenu = (WwFunctionMenu)entry2.getValue();
		                    String title2=submenu.fun.getFname();
		                    String url2=submenu.fun.getFurl();
		                    if(url2.isEmpty())
		            			url2="javascript:;";
		                    String icon2=submenu.fun.getFicon();
		                    if(icon2.isEmpty()){
		                    	icon2="fa-circle-o";
		                    
		                    String active2="";
		                    if(!curUrl.isEmpty()&&!url2.isEmpty()&&curUrl.endsWith(url2.toLowerCase()))
		                    	active2="class=\"active\"";
              %>              
                <li <%=active2 %>><a href="<%=url2%>"><i class="fa <%=icon2%>"></i> <%=title2%></a></li>              
              <%  		
           					}	
           				}  
              		}
              %>
              </ul>
            </li> 
              <%
            	}
              %>           
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>
