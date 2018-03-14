package ww.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ww.common.SqlMap;

import model.ReportLoss;

@Repository
public interface ReportLossMapper {
	public ReportLoss getById(java.lang.Integer id);
	
    public List<model.ReportLoss> getByUserId(java.lang.Integer userId);
    public List<model.ReportLoss> getByWalletAddressOld(java.lang.String walletAddressOld);
    public List<model.ReportLoss> getByWalletAddressNew(java.lang.String walletAddressNew);
    public List<model.ReportLoss> getByReportTime(java.util.Date reportTime);
    public List<model.ReportLoss> getByStatus(java.lang.Integer status);
    public List<model.ReportLoss> getByCheckAdminId(java.lang.Integer checkAdminId);
    public List<model.ReportLoss> getByCheckTime(java.util.Date checkTime);
    public List<model.ReportLoss> getByLossAdminId(java.lang.Integer lossAdminId);
    public List<model.ReportLoss> getByToWalletAddress(java.lang.String toWalletAddress);
    public List<model.ReportLoss> getByLossTime(java.util.Date lossTime);
    public List<model.ReportLoss> getByLossError(java.lang.String lossError);
    public List<model.ReportLoss> getByNote(java.lang.String note);


    public List<ReportLoss> getAll();
    public List<ReportLoss> getList(String whereAndOrderby);
    public List<ReportLoss> getList2(String where,String orderby,int limit_begin,int limit_len);
    public List<SqlMap> selectSql(String sql);
    public int getCount(String where);
    public int insert(ReportLoss obj);
    public void update(ReportLoss obj);
	public void delete(java.lang.Integer id);
	public void deleteWhere(String where);
}
