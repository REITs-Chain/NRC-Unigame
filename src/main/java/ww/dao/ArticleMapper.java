package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.Article;

@Repository
public interface ArticleMapper {
	public Article getById(java.lang.Integer id);
	
    public List<model.Article> getByTitle(java.lang.String title);
    public List<model.Article> getByContent(java.lang.String content);
    public List<model.Article> getByRelaseTime(java.util.Date relaseTime);
    public List<model.Article> getByPublisher(java.lang.Integer publisher);
    public List<model.Article> getByPhoto(java.lang.String photo);
    public List<model.Article> getByBrief(java.lang.String brief);


    public List<Article> getAll();
    public List<Article> getList(String whereAndOrderby);
    public List<Article> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(Article obj);
    public void update(Article obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
