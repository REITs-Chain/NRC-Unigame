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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 参数占位符格式： ":key" 如   where id=:id 
 * 绑定参数时不能带":"冒号 如  bind("id",12)  
 * @author yyw
 *
 */
public class DbModel {
	
	public class DbField {
		public String name="";
		public String type="";
		public String note="";
	}
	
	public class JoinInfo{
		public String joinKey="";
		public String tableName=""; 
		public String joinOn="";
	}
	
	private WwNamedJdbcDAO jdbcDao;	
	
	private String tableName="";//表名可以带别名
	private String tableName_only=""; //不带别名
	private String where="";
	private String group="";
	private String order="";
	private String limit="";
	private List<JoinInfo> m_Joins=null;
	private HashMap paramMap=new HashMap<String, Object>();
	
	private String _sql="";//query和execute必须的独立的 sql语句
	
	public String Message="";
	
	private DataSourceTransactionManager transManager=null;
	private TransactionStatus transStatus=null;
	
	public DbModel(){
		jdbcDao=new WwNamedJdbcDAO();	
	}
	
	public void beginTrans(){
		if(transManager==null)
			transManager=this.jdbcDao.getTransManager();
		//开启新事务
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		transStatus = transManager.getTransaction(def);
//		try {
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		jdbcTemplate.update("INSERT INTO USER VALUES('Spring008', 'caterpillar', 'M', 29)");
//		jdbcTemplate.update("INSERT INTO USER VALUES('Spring009', 'momor', 'F', 26)");
//		jdbcTemplate.update("INSERT INTO USER VALUES('Spring010, 'beckyday', 'F', 35)");
//		} catch (DataAccessException ex) {
//		transactionManager.rollback(status); // 也可以執行status.setRollbackOnly();
//		throw ex;
//		}
//		transactionManager.commit(status);
		
	}
	
	public boolean rollback(){
		try{
			transManager.rollback(this.transStatus);	
			//this.transStatus.flush();
			this.transStatus=null;
			return true;
		}catch(Exception e){
			this.Message=e.getMessage();
			return false;
		}	
	}
	public boolean commit(){
		try{
			transManager.commit(this.transStatus);	
			//this.transStatus.flush();
			this.transStatus=null;
			return true;
		}catch(Exception e){
			this.Message=e.getMessage();
			return false;
		}		
	}
	
	/**
	 * 指定表名，默认别名t1
	 * @param tableName
	 * @return
	 */
	public DbModel table(String tableName){
		this.tableName=tableName;
		this.tableName_only=this.tableName.trim().split(" ")[0];
		
		//清空条件
		this.clear();
		
		return this;
	}
	
	public void clear(){
		this.Message="";
		
		this.paramMap.clear();
		this.where="";
		this.group="";
		this.order="";
		this.limit="";
		if(m_Joins!=null)
			m_Joins.clear();
	}
	
	public DbModel bind(String paramKey,Object paramValue){
		paramMap.put(paramKey, paramValue);
		
		return this;
	}
	
	public DbModel where(String where){
		this.where=where;
		
		return this;
	}
	
	public DbModel group(String group){
		this.group=group;
		
		return this;
	}
	
	public DbModel orderBy(String order){
		this.order=order;
		
		return this;
	}
	
	public DbModel limit(int begin,int len){
		this.limit=" limit "+begin+","+len;
		
		return this;
	}
	
	public DbModel leftJoin(String tableName,String on){
		if(m_Joins==null)
			m_Joins=new ArrayList<JoinInfo>(0);
		
		JoinInfo join=new JoinInfo();
		join.joinKey="left join";
		join.tableName=tableName;
		join.joinOn=on;		
		
		m_Joins.add(join);
		return this;
	}
	
	public DbModel innerJoin(String tableName,String on){
		if(m_Joins==null)
			m_Joins=new ArrayList<JoinInfo>(0);
		
		JoinInfo join=new JoinInfo();
		join.joinKey="inner join";
		join.tableName=tableName;
		join.joinOn=on;
		
		m_Joins.add(join);
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
		
		List<SqlMap> list=this.jdbcDao.queryToSqlMaps(sql,paramMap);
		return (long)list.get(0).get("sl");
	}
	
	public SqlList select(){
		return this._select("");
	}
	public SqlList select(String fields){
		return this._select(fields);
	}
	public SqlMap selectOne(){
		SqlList list=this._select("");
		if(list!=null&&list.size()>0)
			return list.get(0);
		
		return null;
	}
	public SqlMap selectOne(String fields){
		SqlList list=this._select(fields);
		if(list!=null&&list.size()>0)
			return list.get(0);
		
		return null;
	}	
	
	private SqlList _select(String fields){
		if(this.tableName.isEmpty()){
			Message="没有表名";
			return new SqlList();
		}
		String fieldstr=fields;
		if(fields.isEmpty()){		
			fieldstr="*";
		}
		
		String sql="";
		sql+="select "+fieldstr+" from "+this.tableName+" ";
		
		if(m_Joins!=null){
			for(int i=0;i<m_Joins.size();i++){
		    	sql+=" "+m_Joins.get(i).joinKey+" "+m_Joins.get(i).tableName+" on "+m_Joins.get(i).joinOn;
		    }
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
		
		SqlList list=this.jdbcDao.query(sql,paramMap);
		
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
		
		List<T> list=this.jdbcDao.queryToListT(sql,paramMap,resultClass);
		
		return list;
	}
	
	/**
	 * 输入新记录并返回新ID
	 * @param data
	 * @return
	 */
	public long insert(SqlMap data){
		if(this.tableName_only.isEmpty()||this.tableName_only.trim().equals("")){
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
			valueList+=":"+key;
			Object v=data.get(key);			
			paramMap.put(key, v);
		}
		
		try{
			String sql="";
			sql+="insert into "+this.tableName_only+"("+nameList+") values("+valueList+")";
			
			long id=this.jdbcDao.insertAndGetKey(sql,paramMap).longValue();		
			return id;
		}catch(Exception e){
			Message=e.getMessage();
			return -1;
		}
	}	
	
	/**
	 * 返回-1更新失败
	 * @param data
	 * @return 返回-1更新失败
	 */
	public int update(SqlMap data){		
		if(this.tableName_only.isEmpty()||this.tableName_only.trim().equals("")){
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
				
				setList+=key+"=:"+key;
				Object v=data.get(key);			
				paramMap.put(key, v);
			}
			
			String sql="";
			sql+="update "+this.tableName_only+" set "+setList+" where "+this.where;
			
			int id=this.jdbcDao.update(sql,paramMap);
			
			return id;
		}catch(Exception e){
			Message=e.getMessage();
			return -1;
		}
	}
	
	/**
	 * 返回-1更新失败
	 * @param set_list
	 * @return
	 */
	public int update(String set_list){
		if(this.tableName_only.isEmpty()||this.tableName_only.trim().equals("")){
			Message="没有表名";
			return -1;
		}
		if(this.where.isEmpty()||this.where.trim().equals("")){
			Message="没有指定更新条件";
			return -1;
		}
		
		String sql="";
		sql+="update "+this.tableName_only+" set "+set_list+" where "+this.where;
		try{
			int id=this.jdbcDao.update(sql,paramMap);
			return id;
		}catch(Exception e){
			Message=e.getMessage();
			return -1;
		}
	}
	
	public long save(SqlMap data){
		if(this.tableName_only.isEmpty()||this.tableName_only.trim().equals("")){
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
		if(this.tableName_only.isEmpty()||this.tableName_only.trim().equals("")){
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
		sql+="delete from "+this.tableName_only+" "+ww;
		
		try{
			int res=this.jdbcDao.update(sql,paramMap);
			return res;
		}catch(Exception e){
			Message=e.getMessage();
			return -1;
		}
	}
	
	/******开始 独立的 sql语句*****************************************************/
	
	/**
	 * 指定独立的sql语句
	 * @param sql
	 * @return
	 */
	public DbModel sql(String sql){
		this._sql=sql;
		return this;
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * @return
	 */
	public SqlList query(){
		SqlList list=this.jdbcDao.query(this._sql,paramMap);
		
		return list;
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * @param resultClass
	 * @return
	 */
	public <T> List<T> query(Class<T> resultClass){
		List<T> list=this.jdbcDao.queryToListT(this._sql,paramMap,resultClass);
		
		return list;
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * @return
	 */
	public SqlRowSet queryToRowSet(){
		SqlRowSet rs=this.jdbcDao.queryToRowSet(this._sql,paramMap);
		
		return rs;
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * sql语句中不能包含limit子句
	 * 分页查询，pageIndex：查询第几页，从0开始
	 * @param sql
	 * @param pageIndex：查询第几页，从0开始
	 * @param pageSize：每页的记录数
	 * @return
	 */
	public SqlList queryByPaging(int pageIndex,int pageSize){
		int begin=pageIndex*pageSize;
		String sql2=this._sql+" limit "+begin+","+pageSize;
		
		SqlList list=this.jdbcDao.query(sql2,paramMap);
		
		return list;
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * @return
	 */
	public SqlMap queryToFirst(){
		SqlList list=this.jdbcDao.query(this._sql,paramMap);
		if(list.size()<=0)
			return null;
		return list.get(0);
	}	
	
	public long queryCount(){
		return queryCount("*");
	}
	public long queryCount(String field){

		if(field==null||field.isEmpty())
			field="*";
		
		String sql="";
		sql+="select count("+field+") as sl from ("+this._sql+")www1 ";
		
		SqlList list=this.jdbcDao.query(sql, paramMap);
		return (long)list.get(0).get("sl");
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * 执行非查询语句
	 * @return
	 */
	public boolean execute(){
		try{
			Boolean bb=this.jdbcDao.execute(this._sql,paramMap);
			if(bb==null)
				return false;
			return bb;
		}catch(Exception e){
			this.Message=e.getMessage();
			return false;
		}		
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * 执行非查询语句
	 * @return 返回插入或更新记录数量
	 */
	public int updateSql(){
		try{
			int res=this.jdbcDao.update(this._sql,paramMap);
			return res;
		}catch(Exception e){
			this.Message=e.getMessage();
			return -1;
		}		
	}
	
	/**
	 * 请使用sql()指定需要执行sql语句
	 * @return 返回新的自增主键值
	 */
	public long insertSql(){
		try {
			return this.jdbcDao.insertAndGetKey(this._sql,paramMap).longValue();
		} catch (Exception e) {
			this.Message=e.getMessage();
			return -1;
		}
	}
	
	/*****结束 独立的 sql语句 ************************************************************/
	
	public SqlList getFields(String tableName){
		
		String dbname="";
		try {
			Connection con=((JdbcTemplate)this.jdbcDao.getJdbcTemplate().getJdbcOperations()).getDataSource().getConnection();
			dbname = con.getCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query="Select COLUMN_NAME as fieldName, DATA_TYPE as fieldType, COLUMN_COMMENT fieldNote";
        query+=" from INFORMATION_SCHEMA.COLUMNS ";
        query+=" Where table_name = '"+tableName+"'";
        query+=" AND table_schema = '"+dbname+"'";
				
		SqlList fieldList=this.sql(query).query();
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
		SqlMap sm=this.sql(sql2).queryToFirst();
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
		SqlRowSet rs=this.sql(sql).queryToRowSet();
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
	 * 对SQL关键词进行防止sql注入处理，如：表名、字段名，如果是参数防sql注入请使用sqlParamString函数。
	 * @param param
	 * @return
	 */
	public static String validSqlPark(String sqlPark){
		String str=sqlPark.replaceAll("'", "");
		str=str.replaceAll("\r", "");
		str=str.replaceAll("\n", "");
		str=str.replaceAll(";", "");
		str=str.replaceAll(" ", "");
		str=str.replaceAll("#", "");
		str=str.replaceAll("--", "");
		return str;
	}
	
	/**
	 * 对SQL关键词进行防止sql注入处理，如：表名、字段名，如果是参数防sql注入请使用sqlParamString函数。
	 * @param param
	 * @return
	 */
	public String _validSqlPark(String sqlPark){
		return validSqlPark(sqlPark);
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
