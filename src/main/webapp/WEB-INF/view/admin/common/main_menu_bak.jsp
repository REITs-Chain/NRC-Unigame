<%@ page language="java" import="java.util.*,ww.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="model.Admin"%>
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
              <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
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
            <li class="active treeview">
              <a href="#">
                <i class="fa fa-dashboard"></i> 
                <span>用户管理</span> 
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu" style="margin-left: 10px;">
                <li class="active"><a href="admin/User/list"><i class="fa fa-circle-o"></i> 用户信息</a></li>
              </ul>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-files-o"></i>
                <span>校园代理</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="admin/School/list"><i class="fa fa-circle-o"></i> 添加校园</a></li>
              </ul>
            </li>
            <li class="treeview">
              <a href="pages/widgets.html">
                <i class="fa fa-th"></i> 
                <span>战队管理</span> 
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="admin/CorpsInfo/list"><i class="fa fa-circle-o"></i> 战队列表</a></li>
                <li><a href="admin/TeamInfo/list"><i class="fa fa-circle-o"></i> 小组列表</a></li>                
                <li><a href="#"><i class="fa fa-circle-o"></i> 对阵列表</a></li>
              </ul>
            </li>
            <li class="treeview">
              <a href="pages/widgets.html">
                <i class="fa fa-th"></i> 
                <span>赛事活动管理</span> 
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <!-- <li><a href="admin/RegPerson/list"><i class="fa fa-circle-o"></i> 单人报名</a></li>
                <li><a href="admin/RegTeam/list"><i class="fa fa-circle-o"></i> 组队报名</a></li>
                <li><a href="admin/TeamInfo/list"><i class="fa fa-circle-o"></i> 参赛队伍</a></li>
                <li><a href="admin/AgainstInfo/list"><i class="fa fa-circle-o"></i> 对阵信息</a></li> -->
                <li><a href="admin/MatchInfo/list"><i class="fa fa-circle-o"></i> 赛事信息</a></li>
                <li><a href="admin/MatchAppeal/list"><i class="fa fa-circle-o"></i> 赛事申诉</a></li>
                <li><a href="admin/MatchTeamSign/list"><i class="fa fa-circle-o"></i> 队伍签到</a></li>
                <!-- <li><a href="admin/RegTeam/match"><i class="fa fa-circle-o"></i> 队伍匹配</a></li> -->
                <li><a href="admin/Activity/list"><i class="fa fa-circle-o"></i> 活动专区</a></li>
                <li><a href="admin/Server/list"><i class="fa fa-circle-o"></i> 服务器配置</a></li>
              </ul>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-pie-chart"></i>
                <span>新闻管理</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="admin/ActInfo/list"><i class="fa fa-circle-o"></i> 新闻列表</a></li>
              </ul>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-laptop"></i>
                <span>消息管理</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="admin/Message/list"><i class="fa fa-circle-o"></i> 消息发送</a></li>
                <li><a href="admin/MessageUser/list"><i class="fa fa-circle-o"></i> 消息接收人</a></li>
              </ul>
            </li>
            <!-- <li class="treeview">
              <a href="#">
                <i class="fa fa-laptop"></i>
                <span>商品中心</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="admin/GoodsInfo/list"><i class="fa fa-circle-o"></i> 商品道具</a></li>
                <li><a href="admin/GoodsBuy/list"><i class="fa fa-circle-o"></i> 商品购买</a></li> 
                <li><a href="admin/GoodsUse/list"><i class="fa fa-circle-o"></i> 商品使用</a></li>
              </ul>
            </li> -->            
            <li class="treeview">
              <a href="#">
                <i class="fa fa-table"></i> 
                <span>栏目管理</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="admin/Images/list"><i class="fa fa-circle-o"></i> 图片管理</a></li>
                <li><a href="admin/Article/list"><i class="fa fa-circle-o"></i> 文章管理</a></li>
              </ul>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-edit"></i> 
                <span>系统设置</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="#"><i class="fa fa-circle-o"></i> 管理员信息</a></li>
                <li><a href="#"><i class="fa fa-circle-o"></i> 管理员权限</a></li>
              </ul>
            </li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>
