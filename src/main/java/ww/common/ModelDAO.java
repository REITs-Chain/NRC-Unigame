package ww.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 * 此类已经被丢弃，请使用DbModel类代替
 * @author yyw
 *
 */
public class ModelDAO {
	
	public class DbField {
		public String name="";
		public String type="";
		public String note="";
	}
	
	public class JoinInfo{
		public String tableName="";
		public String joinOn="";
		public String tableAlia="";
	}
	
	private WwJdbcDAO jdbcDao;	
	
	private String tableName="";
	private String where="";
	private String group="";
	private String order="";
	private String limit="";
	private List<JoinInfo> m_leftJoin=null;
	
	public String Message="";
	
	public ModelDAO(){
		jdbcDao=new WwJdbcDAO();		
	}
	
	public ModelDAO M(String tableName){
		this.tableName=tableName;
		
		//清空条件
		this.where="";
		this.group="";
		this.order="";
		this.limit="";
		if(m_leftJoin!=null)
			m_leftJoin.clear();
		
		return this;
	}
	
	public ModelDAO where(String where){
		this.where=where;
		
		return this;
	}
	
	public ModelDAO group(String group){
		this.group=group;
		
		return this;
	}
	
	public ModelDAO orderBy(String order){
		this.order=order;
		
		return this;
	}
	
	public ModelDAO limit(int begin,int len){
		this.limit=" limit "+begin+","+len;
		
		return this;
	}
	
	public ModelDAO leftJoin(String tableName,String on){
		if(m_leftJoin==null)
			m_leftJoin=new ArrayList<JoinInfo>(0);
		
		JoinInfo join=new JoinInfo();
		join.tableName=tableName;
		join.joinOn=on;
		int ii=m_leftJoin.size()+2;//t1是主表
		join.tableAlia="t"+ii;	
		
		m_leftJoin.add(join);
		return this;
	}
	
	public long count(){
		return count("*");
	}
	public long count(String field){
		if(this.tableName.isEmpty()){
			Message="没有表名";
			return -1;
		}

		if(field==null||field.isEmpty())
			field="*";
		
		String sql="";
		sql+="select count("+field+") as sl from "+this.tableName+" ";
		if(!this.where.isEmpty()&&!this.where.trim().equals("")){
			sql+=" where "+this.where;
		}
		
		List<SqlMap> list=this.jdbcDao.queryToSqlMaps(sql);
		return (long)list.get(0).get("sl");
	}
	
	public SqlList select(){
		if(m_leftJoin!=null&&m_leftJoin.size()>0){
			return this._select2("");
		}else{
			return this._select1("");
		}
	}
	public SqlList select(String fields){
		if(m_leftJoin!=null&&m_leftJoin.size()>0){
			return this._select2(fields);
		}else{
			return this._select1(fields);
		}
	}
	public SqlMap selectOne(){
		SqlList list=null;
		if(m_leftJoin!=null&&m_leftJoin.size()>0){
			list= this._select2("");
		}else{
			list= this._select1("");
		}
		if(list!=null&&list.size()>0)
			return list.get(0);
		
		return null;
	}
	public SqlMap selectOne(String fields){
		SqlList list=null;
		if(m_leftJoin!=null&&m_leftJoin.size()>0){
			list= this._select2(fields);
		}else{
			list= this._select1(fields);
		}
		if(list!=null&&list.size()>0)
			return list.get(0);
		
		return null;
	}
	
	private SqlList _select1(String fields){
		if(this.tableName.isEmpty()){
			Message="没有表名";
			return new SqlList();
		}
		
		String sql="";
		if(fields.isEmpty()||fields.equals(""))
			sql+="select * from "+this.tableName+" t1";
		else
			sql+="select "+fields+" from "+this.tableName+" t1";
		
		if(!this.where.isEmpty()&&!this.where.trim().equals("")){
			sql+=" where "+this.where;
		}
		
		if(!this.group.isEmpty()&&!this.group.trim().equals("")){
			sql+=" group by "+this.group;
		}
		
		if(!this.order.isEmpty()&&!this.order.trim().equals("")){
			sql+=" order by "+this.order;
		}
		
		//limit必须放在最后
		if(!this.limit.isEmpty()&&!this.limit.trim().equals("")){
			sql+=this.limit;
		}
		
		SqlList list=this.jdbcDao.query(sql);
		
		return list;
	}
	
	private SqlList _select2(String fields){
		if(this.tableName.isEmpty()){
			Message="没有表名";
			return new SqlList();
		}
		String fieldstr=fields;
		if(fields.isEmpty()||fields.equals("")){		
			fieldstr="t1.*";
		    for(int i=0;i<m_leftJoin.size();i++){
		    	fieldstr+=","+m_leftJoin.get(i).tableAlia+".*";
		    }
		}
		
		String sql="";
		sql+="select "+fieldstr+" from "+this.tableName+" t1 ";
		
		for(int i=0;i<m_leftJoin.size();i++){
	    	sql+=" left join "+m_leftJoin.get(i).tableName+" "+m_leftJoin.get(i).tableAlia+" on "+m_leftJoin.get(i).joinOn;
	    }
		
		if(!this.where.isEmpty()&&!this.where.trim().equals("")){
			sql+=" where "+this.where;
		}
		
		if(!this.group.isEmpty()&&!this.group.trim().equals("")){
			sql+=" group by "+this.group;
		}
		
		if(!this.order.isEmpty()&&!this.order.trim().equals("")){
			sql+=" order by "+this.order;
		}
		
		//limit必须放在最后
		if(!this.limit.isEmpty()&&!this.limit.trim().equals("")){
			sql+=this.limit;
		}
		
		SqlList list=this.jdbcDao.query(sql);
		
		return list;
	}
	
	public <T> List<T> select(Class<T> resultClass){
		if(this.tableName.isEmpty()){
			Message="没有表名";
			return new ArrayList<T>();
		}
		
		String sql="";
		sql+="select * from "+this.tableName+" ";
		if(!this.where.isEmpty()&&!this.where.trim().equals("")){
			sql+=" where "+this.where;
		}
		
		if(!this.order.isEmpty()&&!this.order.trim().equals("")){
			sql+=" order by "+this.order;
		}
		
		//limit必须放在最后
		if(!this.limit.isEmpty()&&!this.limit.trim().equals("")){
			sql+=this.limit;
		}
		
		List<T> list=this.jdbcDao.queryToListT(sql,resultClass);
		
		return list;
	}
	
	/**
	 * 输入新记录并返回新ID
	 * @param data
	 * @return
	 */
	public long insert(SqlMap data){
		if(this.tableName.isEmpty()||this.tableName.trim().equals("")){
			Message="没有表名";
			return -1;
		}
		
		String nameList="";
		String valueList="";
		Set<String> keys=data.keySet();
		
		for(String key : keys){
			if(!nameList.equals("")){
				nameList+=",";
				valueList+=",";
			}
			nameList+=key;
			Object v=data.get(key);
			if(v==null){
				valueList+="null";
			}else if(v.getClass().getName().endsWith("String")){
				valueList+="'"+sqlParamString(v.toString())+"'";
			}else if(v.getClass().getName().endsWith("Date")){
				Date d=(Date)v;
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //("yyyy-MM-dd HH:mm:ss");  
				String d_str=format1.format(d);

				valueList+="'"+d_str+"'";
			}else{
				valueList+=sqlParamString(v.toString());
			}
		}
		
		try{
			String sql="";
			sql+="insert into "+this.tableName+"("+nameList+") values("+valueList+")";
			
			long id=this.jdbcDao.insertAndGetKey(sql);		
			return id;
		}catch(Exception e){
			Message=e.getMessage();
			return -1;
		}
	}
	
	public int update(SqlMap data){		
		if(this.tableName.isEmpty()||this.tableName.trim().equals("")){
			Message="没有表名";
			return -1;
		}
		if(this.where.isEmpty()||this.where.trim().equals("")){
			Message="没有指定更新条件";
			return -1;
		}
		
		try{
			String setList="";
			Set<String> keys=data.keySet();
			
			for(String key : keys){
				if(!setList.equals("")){
					setList+=",";
				}			
				Object v=data.get(key);
				String v_str="";
				if(v==null){
					v_str="null";
				}else if(v.getClass().getName().endsWith("String")){
					v_str="'"+sqlParamString(v.toString())+"'";
				}else if(v.getClass().getName().endsWith("Date")){
					Date d=(Date)v;
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  //("yyyy-MM-dd HH:mm:ss");  
					String d_str=format1.format(d);
	
					v_str+="'"+d_str+"'";
				}else{
					v_str=sqlParamString(v.toString());
				}
				setList+=key+"="+v_str;
			}
			
			String sql="";
			sql+="update "+this.tableName+" set "+setList+" where "+this.where;
			
			int id=this.jdbcDao.updateSql(sql);
			
			return id;
		}catch(Exception e){
			Message=e.getMessage();
			return -1;
		}
	}
	
	/**
	 * 使用此方法，需要处理sql注入
	 * @param set_list
	 * @return
	 */
	public int update(String set_list){
		if(this.tableName.isEmpty()||this.tableName.trim().equals("")){
			Message="没有表名";
			return -1;
		}
		if(this.where.isEmpty()||this.where.trim().equals("")){
			Message="没有指定更新条件";
			return -1;
		}
		
		String sql="";
		sql+="update "+this.tableName+" set "+set_list+" where "+this.where;
		
		int id=this.jdbcDao.updateSql(sql);
		
		return id;
	}
	
	public long save(SqlMap data){
		if(this.tableName.isEmpty()||this.tableName.trim().equals("")){
			Message="没有表名";
			return -1;
		}
		if(this.where.isEmpty()||this.where.trim().equals("")){
			Message="没有指定更新条件";
			return -1;
		}		
		
		if(this.count()>0){//更新
			Message="update";
			return this.update(data);
		}else{//插入
			Message="insert";
			return this.insert(data);
		}		
	}
	
	public int Delete(){
		if(this.tableName.isEmpty()||this.tableName.trim().equals("")){
			Message="没有表名";
			return -1;
		}
		
		String ww="";
		if(this.where.isEmpty()||this.where.trim().equals("")){
			ww="";
		}else{
			ww="where "+this.where;
		}
		
		String sql="";
		sql+="delete from "+this.tableName+" "+ww;
		
		int res=this.jdbcDao.updateSql(sql);
		
		return res;
	}
	
	public SqlList query(String sql){
		SqlList list=this.jdbcDao.query(sql);
		
		return list;
	}
	
	public SqlRowSet queryToRowSet(String sql){
		SqlRowSet rs=this.jdbcDao.queryToRowSet(sql);
		
		return rs;
	}
	
	/**
	 * 分页查询，pageIndex：查询第几页，从0开始
	 * @param sql
	 * @param pageIndex：查询第几页，从0开始
	 * @param pageSize：每页的记录数
	 * @return
	 */
	public SqlList queryByPaging(String sql,int pageIndex,int pageSize){
		int begin=pageIndex*pageSize;
		sql=sql+" limit "+begin+","+pageSize;
		
		SqlList list=this.jdbcDao.query(sql);
		
		return list;
	}
	
	public SqlMap queryToFirst(String sql){
		SqlList list=this.jdbcDao.query(sql);
		if(list.size()<=0)
			return null;
		return list.get(0);
	}
	
	public <T> List<T> query(String sql,Class<T> resultClass){
		List<T> list=this.jdbcDao.queryToListT(sql,resultClass);
		
		return list;
	}
	
	/**
	 * 使用此方法，需要处理sql注入
	 * @param sql
	 * @return
	 */
	public boolean exec(String sql){
		try{
			this.jdbcDao.execSql(sql);
			return true;
		}catch(Exception e){
			this.Message=e.getMessage();
			return false;
		}		
	}
	
	/**
	 * 使用此方法，需要处理sql注入
	 * @param sql
	 * @return
	 */
	public long insertSql(String sql){
		return this.jdbcDao.insertAndGetKey(sql);
	}
	
	public SqlList getFields(String tableName){
		
		String dbname="";
		try {
			Connection con=this.jdbcDao.getJdbcTemplate().getDataSource().getConnection();
			dbname = con.getCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query="Select COLUMN_NAME as fieldName, DATA_TYPE as fieldType, COLUMN_COMMENT fieldNote";
        query+=" from INFORMATION_SCHEMA.COLUMNS ";
        query+=" Where table_name = '"+tableName+"'";
        query+=" AND table_schema = '"+dbname+"'";
				
		SqlList fieldList=this.query(query);
		return fieldList;
	}
	
	/**
	 * 获取总行数和分页后的总页数，
	 * @param sql 函数将在sql的外面加select count(*) from (sql)www1来统计 
	 * @param pageSize
	 * @return long[0]:总行数,long[1]:总页数
	 */
	public long[] countPages(String sql,int pageSize){
		String sql2="select count(*) as totalRows from ( "+sql+" )www1";
		SqlMap sm=this.queryToFirst(sql2);
		long totalRows= sm.getLong("totalRows");
		
		long ye=totalRows%pageSize;
		long pages=0;
		if(ye==0)
			pages=totalRows/pageSize;
		else
			pages=(totalRows-ye)/pageSize+1;
		
		long[] result=new long[2];
		result[0]=totalRows;
		result[1]=pages;
		
		return result;
	}
	
	/**
	 * 获取总行数和分页后的总页数，
	 * @param sql 直接执行sql，函数不外加统计SQL语句，sql必须是可以统计总行数的sql语句
	 * @param pageSize
	 * @return long[0]:总行数,long[1]:总页数
	 */
	public long[] countPagesDirect(String sql,int pageSize){
		SqlRowSet rs=this.queryToRowSet(sql);
		long totalRows=0;
		if(rs.next()){
			totalRows= rs.getLong(1);
		}else{
			return new long[]{0,0};
		}
		
		long ye=totalRows%pageSize;
		long pages=0;
		if(ye==0)
			pages=totalRows/pageSize;
		else
			pages=(totalRows-ye)/pageSize+1;
		
		long[] result=new long[2];
		result[0]=totalRows;
		result[1]=pages;
		
		return result;
	}
	
	public long computePages(long totalRows,int pageSize){
		long ye=totalRows%pageSize;
		long pages=0;
		if(ye==0)
			pages=totalRows/pageSize;
		else
			pages=(totalRows-ye)/pageSize+1;
		
		return pages;
	}
	
	/**
	 * 去掉特殊字符:' \r \n
	 * @param src
	 * @return
	 */
	public String rmSpecChars(String src){
		String str=src.replaceAll("'", "\'");
		str=str.replaceAll("\r", "");
		str=str.replaceAll("\n", "");
		return str;
	}
	
	/**
	 * 参数化字符串，防止sql注入，只有mysql数据库有效
	 * @param param
	 * @return
	 */
	public String _sqlParamString(String param){
		return sqlParamString(param);
	}
	
	/**
	 * 参数化字符串，防止sql注入，只有mysql数据库有效
	 * @param param
	 * @return
	 */
	public static String sqlParamString(String param){
		if(param==null)
			return param;
		String str=param.replaceAll("'", "\'");
		
		return str;
	}

}
