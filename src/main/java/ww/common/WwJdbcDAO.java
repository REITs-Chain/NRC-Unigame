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
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;



public class WwJdbcDAO {

	private JdbcTemplate jdbcTpl;
	
	public WwJdbcDAO(){
		jdbcTpl=WwSpringContextHelper.getBean("jdbcTemplate");
	}
	
	public JdbcTemplate getJdbcTemplate(){
		return this.jdbcTpl;
	}
	
	public SqlList query(String sql){
		final SqlList list=new SqlList(); //一定要用final定义
		jdbcTpl.query(sql,new RowCallbackHandler() {   
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
	
	public List<SqlMap> queryToSqlMaps(String sql){
		List<SqlMap> list=jdbcTpl.query(sql, new RowMapper<SqlMap>(){
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
	
	public List<Map<String,Object>> queryToMaps(String sql){
		List<Map<String,Object>> list=jdbcTpl.queryForList(sql);
		return list;
	}
	
	public SqlRowSet queryToRowSet(String sql){
		SqlRowSet list=jdbcTpl.queryForRowSet(sql);
		return list;
	}
	
	public <T> List<T> queryToListT(String sql,final Class<T> resultClass){
		List<T> list=jdbcTpl.query(sql, new RowMapper<T>(){
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
	
	public void execSql(String sql){
		jdbcTpl.execute(sql);
	}
	
	public int updateSql(String sql){
		return jdbcTpl.update(sql);
	}
	
	/**
	 * 增加新记录并且获取主键
	 * @param sql sql语句
	 * @param params 参数
	 * @return 主键
	 */
	public Long insertAndGetKey(final String sql) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTpl.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {				
                PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                return ps;
			}
		}, keyHolder);
		
		Long generatedId = keyHolder.getKey().longValue(); 
		return generatedId;
	}

}
