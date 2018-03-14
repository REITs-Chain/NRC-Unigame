package ww.service.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import ww.service.BaseService;
import ww.common.SqlMap;

import model.Article;
import ww.dao.ArticleMapper;

public interface ArticleService extends BaseService {
	
	public ArticleMapper getMapper();

	public List<Article> getList();
	
	public List<Article> getList(String whereAndOrderby);
	
	public int getCount(String where);
	
	public ModelAndView getPaging(Integer page,String where);

	public List<SqlMap> selectSql(String sql);
	
	public Article getById(java.lang.Integer id);
	
	public boolean isExist(java.lang.Integer id);
	
	public boolean Save(Article data);
	
	public boolean Delete(java.lang.Integer id);
	
}
