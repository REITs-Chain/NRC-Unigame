package ww.common;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;



public class WwNamedJdbcDAO {

	private NamedParameterJdbcTemplate jdbcTpl;
	
	public WwNamedJdbcDAO(){
		jdbcTpl=WwSpringContextHelper.getBean("namedJdbcTemplate");		
	}
	
	public NamedParameterJdbcTemplate getJdbcTemplate(){
		return this.jdbcTpl;
	}
	
	public DataSourceTransactionManager getTransManager(){
		DataSourceTransactionManager transManager=WwSpringContextHelper.getBean("txManager");
		return transManager;
	}
	
	public SqlList query(String sql,Map<String,?> paramMap){
		final SqlList list=new SqlList(); //一定要用final定义
		jdbcTpl.query(sql,paramMap,new RowCallbackHandler() {   
		    public void processRow(ResultSet rs) throws SQLException {   
		    	SqlMap sm = new SqlMap();     
		    	ResultSetMetaData rmd= rs.getMetaData();
				for(int i=1;i<=rmd.getColumnCount();i++){
					sm.put(rmd.getColumnLabel(i), rs.getObject(i));
				}
			    list.add(sm);   
		    }   
		});
		return list;
	}
	
	public List<SqlMap> queryToSqlMaps(String sql,Map<String,?> paramMap){
		List<SqlMap> list=jdbcTpl.query(sql,paramMap, new RowMapper<SqlMap>(){
			@Override
			public SqlMap mapRow(ResultSet rs, int rowNum) throws SQLException {
				SqlMap sm=new SqlMap();
				ResultSetMetaData rmd= rs.getMetaData();
				for(int i=1;i<=rmd.getColumnCount();i++){
					sm.put(rmd.getColumnLabel(i), rs.getObject(i));
				}
				return sm;
			}			
		});
		return list;
	}
	
	public List<Map<String,Object>> queryToMaps(String sql,Map<String,?> paramMap){
		List<Map<String,Object>> list=jdbcTpl.queryForList(sql,paramMap);
		return list;
	}	
	
	public <T> List<T> queryToListT(String sql,Map<String,?> paramMap,final Class<T> resultClass){
		List<T> list=jdbcTpl.query(sql,paramMap, new RowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				T result=null;
				try {
					result = resultClass.newInstance();	
				} catch (Exception e) {
					return null;
				}
				try{
					ResultSetMetaData rmd= rs.getMetaData();
					for(int i=1;i<=rmd.getColumnCount();i++){
						String name=rmd.getColumnName(i);
						Object value=rs.getObject(i);
						Field f = resultClass.getDeclaredField(name);						
						if(f!=null){						
							f.setAccessible(true);
							f.set(result, value);
						}
					}				
					return result;
				} catch (Exception e) {
					return result;
				}				
			}			
		});
		return list;
	}
	
	public SqlRowSet queryToRowSet(String sql,Map<String,?> paramMap){
		SqlRowSet list=jdbcTpl.queryForRowSet(sql,paramMap);
		return list;
	}
	
	public Boolean execute(String sql,Map<String,?> paramMap){
		Boolean res=jdbcTpl.execute(sql,paramMap,new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement preparedstatement)
					throws SQLException, DataAccessException {
				
				preparedstatement.execute();
				//ResultSet rs = preparedstatement.getResultSet();
				//rs.next();
				//return rs.getInt(1);
				
				return true;
			}
		});
		
		return res;
	}
	
	public int update(String sql,Map<String,?> paramMap){
		return jdbcTpl.update(sql,paramMap);
	}
	
	/**
	 * 增加新记录并且获取主键
	 * @param sql sql语句
	 * @param params 参数
	 * @return 主键
	 */
	public Number insertAndGetKey(final String sql,Map<String,?> paramMap) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource ps=((SqlParameterSource) (new MapSqlParameterSource(paramMap)));
		jdbcTpl.update(sql, ps, keyHolder);		
		Number generatedId = keyHolder.getKey();
		return generatedId;
	}

}
