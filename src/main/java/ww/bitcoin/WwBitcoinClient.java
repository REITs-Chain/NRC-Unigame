package ww.bitcoin;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

import ru.paradoxs.bitcoin.client.AccountInfo;
import ru.paradoxs.bitcoin.client.AddressInfo;
import ru.paradoxs.bitcoin.client.ServerInfo;
import ru.paradoxs.bitcoin.client.TransactionInfo;
import ru.paradoxs.bitcoin.client.ValidatedAddressInfo;
import ru.paradoxs.bitcoin.client.WorkInfo;
import ru.paradoxs.bitcoin.client.exceptions.BitcoinClientException;
import ru.paradoxs.bitcoin.http.HttpSession;
import ww.common.WwHttpRequest;
import ww.common.WwSystem;

public class WwBitcoinClient
{
	public String error="";
	
  private static BigDecimal getBigDecimal(JSONObject jsonObject, String key)
    throws Exception
  {
    String string = jsonObject.getString(key);
    BigDecimal bigDecimal = new BigDecimal(string);
    return bigDecimal;
  }
  
  private WwHttpSession session = null;
  
  public static WwBitcoinClient inst(){
	  Config cfg=Config.getInst();
	  WwBitcoinClient client=new WwBitcoinClient(cfg.nodeIP,cfg.rpcUser,cfg.rpcPassword,cfg.nodePort);
	  return client;
  }
  
  /**
   * 链接到运营节点10.0.0.44(123.207.174.20)公测
   * 正式：172.17.160.249(59.110.172.243)
   * @return
   */
  public static WwBitcoinClient instMainNode(){
	  Config cfg=Config.getInst();
	  WwBitcoinClient client=new WwBitcoinClient(cfg.mainNodeIP,cfg.mainNodeRpcUser,cfg.mainNodeRpcPassword,cfg.mainNodePort);
	  return client;
  }
  
  public WwBitcoinClient(Config cfg)
  {
	  this(cfg.nodeIP,cfg.rpcUser,cfg.rpcPassword,cfg.nodePort);
  }
  
  public WwBitcoinClient(String host, String login, String password, int port)
  {
    try
    {
      Credentials credentials = new UsernamePasswordCredentials(login, password);
      URI uri = new URI("http", null, host, port, null, null, null);
      this.session = new WwHttpSession(uri, credentials);
    }
    catch (URISyntaxException e)
    {
      throw new BitcoinClientException("This host probably doesn't have correct syntax: " + host, e);
    }
  }
  
  public WwBitcoinClient(String host, String login, String password)
  {
    this(host, login, password, 8332);
  }
  
  /***ww begin****/
  
  /**
   * 执行rpc命令，返回值可能是:json字符串或纯字符串
   * @param funName
   * @param params
   * @return
   */
  public String command(String funName,Object... params)
  {
    try
    {
    	JSONObject request;
      if(params==null||params.length<=0){
	      request = createRequest(funName);
      }else{
    	  JSONArray parameters =JSONArray.fromObject(params);// new JSONArray().element(params);
	      request = createRequest(funName, parameters);
      }	  
      JSONObject response = this.session.sendAndReceive(request);
      if(response==null){
    	  return "{success:false,msg:\""+this.session.getError()+"\"}";
      }
      String result = response.getString("result");
      
      return result;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("执行"+funName+"异常", e);
    }
  }
  
  /**
   * 获取指定地址address的根地址，如果address为空则生成新的根地址
   * @param address
   * @return 
   */
  public String getPrimeAddress(String address)
  {
	  if(address==null||address.isEmpty())
		  return getPrimeAddress();
    try
    {
    	JSONArray parameters = new JSONArray().element(address);
      JSONObject request = createRequest("getprimeaddr",parameters);
      JSONObject response = this.session.sendAndReceive(request);
      if(response==null){
    	  this.error="节点服务内部错误:"+this.session.getError();
    	  return "";
      }
      this.error="";
      return response.getString("result");
    }
    catch (Exception e)
    {
    	this.error="getprimeaddr异常:"+e.getMessage();  
    	return null;
    }
  }
  
  /**
   * 生成新的根地址
   * @return
   */
  public String getPrimeAddress()
  {
    try
    {
      JSONObject request = createRequest("getprimeaddr");
      JSONObject response = this.session.sendAndReceive(request);
      if(response==null){
    	  this.error="节点服务内部错误:"+this.session.getError();
    	  return null;
      }
      this.error="";
      return response.getString("result");
    }
    catch (Exception e)
    {
    	this.error="getprimeaddr异常:"+e.getMessage();  
    	return null;
    }
  }
  
  /**
   * 执行根地址授权操作，返回JSON字符串
   * @param signprogram
   * @param address
   * @return
   */
  public String authPrimeAddr(String signprogram,String address)
  {
    try
    {
      JSONObject request;
      String param="{\"signprogram\":\""+signprogram+"\",\"auth\":[\""+address+"\"]}";
	  JSONArray parameters =new JSONArray().element(param);
	  request = createRequest("operatoraction", parameters);
	
	  JSONObject response = this.session.sendAndReceive(request);
	  if(response==null){
		  return "{success:false,msg:\"钱包节点服务内部错误:"+this.session.getError()+"\"}";
	  }
	  String result = "{success:true,txid:\""+response.getString("result")+"\"}";
	  
	  return result;
    }
    catch (Exception e)
    {
    	return "{success:false,msg:\"signprogram异常:"+e.getMessage()+"\"}";
    }
  }
  
  //不在需要此函数
  //获取wizardid,wizardProgram是/home/projects/scripts/wizard.py
  public String getWizardid_del(String wizardProgram){
	  try {
			String[] strs={"python3", wizardProgram,"-n"};
			Process pr=Runtime.getRuntime().exec("python3 " + wizardProgram + " -n");//(strs);			
			String result=WwSystem.InputStreamToString(pr.getInputStream()); 
			if(result!=null&&!result.isEmpty()){
				result=result.replaceAll("\n", "");
				result=result.replaceAll("\r", "");
				return "{success:true,wizardid:\""+result+"\"}";
			}
			
			try {
				pr.waitFor();
			} catch (InterruptedException e) {
				return "{success:false,msg:\""+e.getMessage()+"\"}";
			}
			String ss=WwSystem.InputStreamToString(pr.getErrorStream());
			if(ss!=null){
				ss=ss.replaceAll("\n", "");
				ss=ss.replaceAll("\r", "");
				ss=ss.replaceAll("\"", "");
			}
			return "{success:false,msg:\"InputStream异常:"+ss+"\"}";
		} catch (Exception e) {
			return "{success:false,msg:\""+e.getMessage()+"\"}";
		}
  }
  
//不在需要此函数
  public String getWizardidFromRemote_del()
  {
	Config cfg=Config.getInst();
	try
	{
		WwHttpRequest req=new WwHttpRequest();
		String res=req.get("http://"+cfg.mainNodeIP+":10999", "");		  
		return res;
	}
	catch (Exception e)
	{
		return "{success:false,msg:\"getWizardidFromRemote异常:"+e.getMessage()+"\"}";
	}
  }
  
  public String posFromRemote()
  {
	Config cfg=Config.getInst();
	try
	{
		WwHttpRequest req=new WwHttpRequest();
		String res=req.get("http://"+cfg.mainNodeIP+":10999/pos", "");		  
		return res;
	}
	catch (Exception e)
	{
		return "{success:false,msg:\"posFromRemote异常:"+e.getMessage()+"\"}";
	}
  }
  
  public String assignWizardid_del(String signprogram,String wizardid)
  {
    try
    {
      JSONObject request;
      String param="{\"signprogram\":\""+signprogram+"\",\"wizardid\":\""+wizardid+"\"}";
	  JSONArray parameters =new JSONArray().element(param);
	  request = createRequest("operatoraction", parameters);
	
	  JSONObject response = this.session.sendAndReceive(request);
	  if(response==null){
		  return "{success:false,msg:\"钱包节点服务内部错误:"+this.session.getError()+"\"}";
	  }
	  String result = "{success:true,txid:\""+response.getString("result")+"\"}";
	  
	  return result;
    }
    catch (Exception e)
    {
    	return "{success:false,msg:\"signprogram异常:"+e.getMessage()+"\"}";
    }
  }  
  
  public String wizardTransfer(String wizardprogram,String unspentlist,String targetAddress)
  {
    try
    {
      JSONObject request;
      String param="{\"wizardprogram\":\""+wizardprogram+"\",\"unspentlist\":["+unspentlist+"],\"target\":\""+targetAddress+"\"}";
	  JSONArray parameters =new JSONArray().element(param);
	  request = createRequest("wizardtransfer", parameters);
	
	  JSONObject response = this.session.sendAndReceive(request);
	  if(response==null){
		  return "{success:false,msg:\"钱包节点服务内部错误:"+this.session.getError()+"\"}";
	  }
	  String result = "{success:true,txid:\""+response.getString("result")+"\"}";
	  
	  return result;
    }
    catch (Exception e)
    {
    	return "{success:false,msg:\"signprogram异常:"+e.getMessage()+"\"}";
    }
  }
  
  /**
   * 取消授权
   * @param signprogram
   * @param address
   * @return
   */
  public String revokePrimeAddr(String signprogram,String address)
  {
    try
    {
      JSONObject request;
      String param="{\"signprogram\":\""+signprogram+"\",\"revoke\":[\""+address+"\"]}";
	  JSONArray parameters =new JSONArray().element(param);
	  request = createRequest("operatoraction", parameters);
	
	  JSONObject response = this.session.sendAndReceive(request);
	  if(response==null){
		  return "{success:false,msg:\"钱包节点服务内部错误:"+this.session.getError()+"\"}";
	  }
	  String result = "{success:true,txid:\""+response.getString("result")+"\"}";
	  
	  return result;
    }
    catch (Exception e)
    {
    	return "{success:false,msg:\"signprogram异常:"+e.getMessage()+"\"}";
    }
  }
    
  /**
   * 发送原始交易
   * @return json字符串
   */
  public String sendrawtransaction(String hexTransaction)
  {
    try
    {
    	JSONArray parameters = new JSONArray().element(hexTransaction);
        JSONObject request = createRequest("sendrawtransaction",parameters);
        JSONObject response = this.session.sendAndReceive(request);
        if(response==null){
        	this.error="节点服务内部错误:"+this.session.getError();
        	return null;
        }
        this.error="";
        String res=response.getString("result");
        
        String result = "{success:true,txid:\""+res+"\"}";
        return result;
    }
    catch (Exception e)
    {
    	this.error="sendrawtransaction异常:"+e.getMessage();  
    	return null;
    }
  }
  
  /**
   * 解码原始交易hex
   * @return 交易json字符串
   */
  public String decoderawtransaction(String hexTransaction)
  {
    try
    {
    	JSONArray parameters = new JSONArray().element(hexTransaction);
        JSONObject request = createRequest("decoderawtransaction",parameters);
        JSONObject response = this.session.sendAndReceive(request);
        if(response==null){
        	this.error="节点服务内部错误:"+this.session.getError();
        	return null;
        }
        this.error="";
        String result=response.getString("result");
        return result;
    }
    catch (Exception e)
    {
    	this.error="decoderawtransaction异常:"+e.getMessage()+this.error;  
    	return null;
    }
  }
  
  /*****ww end*******/
  
  public List<String> getAddressesByAccount(String account)
  {
    if (account == null) {
      account = "";
    }
    try
    {
      JSONArray parameters = new JSONArray().element(account);
      JSONObject request = createRequest("getaddressesbyaccount", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      JSONArray result = (JSONArray)response.get("result");
      int size = result.size();
      
      List<String> list = new ArrayList();
      for (int i = 0; i < size; i++) {
        list.add(result.getString(i));
      }
      return list;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Got incorrect JSON for this account: " + account, e);
    }
  }
  
  public BigDecimal getBalance()
  {
    try
    {
      JSONObject request = createRequest("getbalance");
      JSONObject response = this.session.sendAndReceive(request);
      
      return getBigDecimal(response, "result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting balance", e);
    }
  }
  
  public BigDecimal getBalance(String account)
  {
    if (account == null) {
      account = "";
    }
    try
    {
      JSONArray parameters = new JSONArray().element(account);
      JSONObject request = createRequest("getbalance", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return getBigDecimal(response, "result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting balance", e);
    }
  }
  
  public int getBlockCount()
  {
    try
    {
      JSONObject request = createRequest("getblockcount");
      JSONObject response = this.session.sendAndReceive(request);
      if(response==null){
    	  error="服务器节点内部错误";
    	  return -1;
      }
      
      return response.getInt("result");
    }
    catch (Exception e)
    {
    	error=e.getMessage();
    	return -1;
    }
  }
  
  public int getBlockNumber()
  {
    try
    {
      JSONObject request = createRequest("getblocknumber");
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getInt("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the block number", e);
    }
  }
  
  //ww
  public String getBlockHash(int index)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(index);
	  JSONObject request = createRequest("getblockhash",parameters);
	  JSONObject response = this.session.sendAndReceive(request);
      
      return response.getString("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the block number", e);
    }
  }
  
  public JSONObject getBlock(String hash)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(hash);
	  JSONObject request = createRequest("getblock",parameters);
	  JSONObject response = this.session.sendAndReceive(request);
      
      return response.getJSONObject("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the block number", e);
    }
  }
  
  public int getConnectionCount()
  {
    try
    {
      JSONObject request = createRequest("getconnectioncount");
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getInt("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the number of connections", e);
    }
  }
  
  public long getHashesPerSecond()
  {
    try
    {
      JSONObject request = createRequest("gethashespersec");
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getLong("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the number of calculated hashes per second", e);
    }
  }
  
  public BigDecimal getDifficulty()
  {
    try
    {
      JSONObject request = createRequest("getdifficulty");
      JSONObject response = this.session.sendAndReceive(request);
      
      return getBigDecimal(response, "result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the difficulty", e);
    }
  }
  
  public boolean getGenerate()
  {
    try
    {
      JSONObject request = createRequest("getgenerate");
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getBoolean("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting whether the server is generating coins or not", e);
    }
  }
  
  public void setGenerate(boolean isGenerate, int processorsCount)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(isGenerate).element(processorsCount);
      JSONObject request = createRequest("setgenerate", parameters);
      this.session.sendAndReceive(request);
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when setting whether the server is generating coins or not", e);
    }
  }
  
  public ServerInfo getServerInfo()
  {
    try
    {
      JSONObject request = createRequest("getinfo");
      JSONObject response = this.session.sendAndReceive(request);
      JSONObject result = (JSONObject)response.get("result");
      
      ServerInfo info = new ServerInfo();
      info.setBalance(getBigDecimal(result, "balance"));
      info.setBlocks(result.getLong("blocks"));
      info.setConnections(result.getInt("connections"));
      info.setDifficulty(getBigDecimal(result, "difficulty"));
      info.setHashesPerSecond(result.getLong("hashespersec"));
      info.setIsGenerateCoins(result.getBoolean("generate"));
      info.setUsedCPUs(result.getInt("genproclimit"));
      info.setVersion(result.getString("version"));
      
      return info;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the server info", e);
    }
  }
  
  public String getAccount(String address)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(address);
      JSONObject request = createRequest("getaccount", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getString("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the account associated with this address: " + address, e);
    }
  }
  
  public void setAccountForAddress(String address, String account)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(address).element(account);
      JSONObject request = createRequest("setaccount", parameters);
      this.session.sendAndReceive(request);
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when setting the account associated with a given address", e);
    }
  }
  
  public String getAccountAddress(String account)
  {
    if (account == null) {
      account = "";
    }
    try
    {
      JSONArray parameters = new JSONArray().element(account);
      JSONObject request = createRequest("getaccountaddress", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getString("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the new bitcoin address for receiving payments", e);
    }
  }
  
  public BigDecimal getReceivedByAddress(String address, long minimumConfirmations)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(address).element(minimumConfirmations);
      JSONObject request = createRequest("getreceivedbyaddress", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return getBigDecimal(response, "result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the total amount received by bitcoinaddress", e);
    }
  }
  
  public BigDecimal getReceivedByAccount(String account, long minimumConfirmations)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(account).element(minimumConfirmations);
      JSONObject request = createRequest("getreceivedbyaccount", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return getBigDecimal(response, "result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the total amount received for account: " + account, e);
    }
  }
  
  public String help(String command)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(command);
      JSONObject request = createRequest("help", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getString("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting help for a command", e);
    }
  }
  
  public List<AddressInfo> listReceivedByAddress(long minimumConfirmations, boolean includeEmpty)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(minimumConfirmations).element(includeEmpty);
      JSONObject request = createRequest("listreceivedbyaddress", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      JSONArray result = response.getJSONArray("result");
      int size = result.size();
      List<AddressInfo> list = new ArrayList();
      for (int i = 0; i < size; i++)
      {
        AddressInfo info = new AddressInfo();
        JSONObject jObject = result.getJSONObject(i);
        info.setAddress(jObject.getString("address"));
        info.setAccount(jObject.getString("account"));
        info.setAmount(getBigDecimal(jObject, "amount"));
        info.setConfirmations(jObject.getLong("confirmations"));
        list.add(info);
      }
      return list;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting info about all received transactions by address", e);
    }
  }
  
  public List<AccountInfo> listReceivedByAccount(long minimumConfirmations, boolean includeEmpty)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(minimumConfirmations).element(includeEmpty);
      JSONObject request = createRequest("listreceivedbyaccount", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      JSONArray result = response.getJSONArray("result");
      int size = result.size();
      
      List<AccountInfo> list = new ArrayList(size);
      for (int i = 0; i < size; i++)
      {
        AccountInfo info = new AccountInfo();
        JSONObject jObject = result.getJSONObject(i);
        info.setAccount(jObject.getString("account"));
        info.setAmount(getBigDecimal(jObject, "amount"));
        info.setConfirmations(jObject.getLong("confirmations"));
        list.add(info);
      }
      return list;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting the received amount by account", e);
    }
  }
  
  public List<TransactionInfo> listTransactions(String account, int count)
  {
    if (account == null) {
      account = "";
    }
    if (count <= 0) {
      throw new BitcoinClientException("count must be > 0");
    }
    try
    {
      JSONArray parameters = new JSONArray().element(account).element(count);
      JSONObject request = createRequest("listtransactions", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      JSONArray result = response.getJSONArray("result");
      int size = result.size();
      
      List<TransactionInfo> list = new ArrayList(size);
      for (int i = 0; i < size; i++)
      {
        JSONObject jObject = result.getJSONObject(i);
        TransactionInfo info = parseTransactionInfoFromJson(jObject);
        list.add(info);
      }
      return list;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting transactions for account: " + account, e);
    }
  }
  
  public TransactionInfo getTransaction(String txId)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(txId);
      JSONObject request = createRequest("gettransaction", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      JSONObject result = (JSONObject)response.get("result");
      
      return parseTransactionInfoFromJson(result);
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting transaction info for this id: " + txId, e);
    }
  }
  
  private TransactionInfo parseTransactionInfoFromJson(JSONObject jObject)
    throws Exception
  {
    TransactionInfo info = new TransactionInfo();
    
    info.setAmount(getBigDecimal(jObject, "amount"));
    if (jObject.has("category")) {
      info.setCategory(jObject.getString("category"));
    }
    if (jObject.has("fee")) {
      info.setFee(getBigDecimal(jObject, "fee"));
    }
    if (jObject.has("message")) {
      info.setMessage(jObject.getString("message"));
    }
    if (jObject.has("to")) {
      info.setTo(jObject.getString("to"));
    }
    if (jObject.has("confirmations")) {
      info.setConfirmations(jObject.getLong("confirmations"));
    }
    if (jObject.has("txid")) {
      info.setTxId(jObject.getString("txid"));
    }
    if (jObject.has("otheraccount")) {
      info.setOtherAccount(jObject.getString("otheraccount"));
    }
    if (!jObject.has("time")) {
      info.setTime(jObject.getLong("time"));
    }
    return info;
  }
  
  public List<TransactionInfo> listTransactions(String account)
  {
    return listTransactions(account, 10);
  }
  
  public List<TransactionInfo> listTransactions()
  {
    return listTransactions("", 10);
  }
  
  public WorkInfo getWork()
  {
    try
    {
      JSONObject request = createRequest("getwork");
      JSONObject response = this.session.sendAndReceive(request);
      JSONObject result = (JSONObject)response.get("result");
      
      WorkInfo info = new WorkInfo();
      info.setMidstate(result.getString("midstate"));
      info.setData(result.getString("data"));
      info.setHash1(result.getString("hash1"));
      info.setTarget(result.getString("target"));
      
      return info;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when getting work info", e);
    }
  }
  
  public boolean getWork(String block)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(block);
      JSONObject request = createRequest("getwork", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getBoolean("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when trying to solve a block with getwork", e);
    }
  }
  
  public String sendToAddress(String bitcoinAddress, BigDecimal amount, String comment, String commentTo)
  {
    amount = checkAndRound(amount);
    try
    {
      JSONArray parameters = new JSONArray().element(bitcoinAddress).element(amount).element(comment).element(commentTo);
      
      JSONObject request = createRequest("sendtoaddress", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getString("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when sending bitcoins", e);
    }
  }
  
  public String sendFrom(String account, String bitcoinAddress, BigDecimal amount, int minimumConfirmations, String comment, String commentTo)
  {
    if (account == null) {
      account = "";
    }
    if (minimumConfirmations <= 0) {
      throw new BitcoinClientException("minimumConfirmations must be > 0");
    }
    amount = checkAndRound(amount);
    try
    {
      JSONArray parameters = new JSONArray().element(account).element(bitcoinAddress).element(amount).element(minimumConfirmations).element(comment).element(commentTo);
      
      JSONObject request = createRequest("sendfrom", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getString("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when sending bitcoins with sendFrom()", e);
    }
  }
  
  public boolean move(String fromAccount, String toAccount, BigDecimal amount, int minimumConfirmations, String comment)
  {
    if (fromAccount == null) {
      fromAccount = "";
    }
    if (toAccount == null) {
      toAccount = "";
    }
    if (minimumConfirmations <= 0) {
      throw new BitcoinClientException("minimumConfirmations must be > 0");
    }
    amount = checkAndRound(amount);
    try
    {
      JSONArray parameters = new JSONArray().element(fromAccount).element(toAccount).element(amount).element(minimumConfirmations).element(comment);
      
      JSONObject request = createRequest("move", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      
      return response.getBoolean("result");
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when moving " + amount + " bitcoins from account: '" + fromAccount + "' to account: '" + toAccount + "'", e);
    }
  }
  
  private BigDecimal checkAndRound(BigDecimal amount)
  {
    if (amount.compareTo(new BigDecimal("0.01")) < 0) {
      throw new BitcoinClientException("The current machinery doesn't support transactions of less than 0.01 Bitcoins");
    }
    if (amount.compareTo(new BigDecimal("21000000")) > 0) {
      throw new BitcoinClientException("Sorry dude, can't transfer that many Bitcoins");
    }
    amount = roundToTwoDecimals(amount);
    return amount;
  }
  
  public void stop()
  {
    try
    {
      JSONObject request = createRequest("stop");
      this.session.sendAndReceive(request);
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when stopping the bitcoin server", e);
    }
  }
  
  public ValidatedAddressInfo validateAddress(String address)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(address);
      JSONObject request = createRequest("validateaddress", parameters);
      JSONObject response = this.session.sendAndReceive(request);
      JSONObject result = (JSONObject)response.get("result");
      
      ValidatedAddressInfo info = new ValidatedAddressInfo();
      info.setIsValid(result.getBoolean("isvalid"));
      if (info.getIsValid())
      {
        info.setIsMine(result.getBoolean("ismine"));
        info.setAddress(result.getString("address"));
      }
      return info;
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when validating an address", e);
    }
  }
  
  public void backupWallet(String destination)
  {
    try
    {
      JSONArray parameters = new JSONArray().element(destination);
      JSONObject request = createRequest("backupwallet", parameters);
      this.session.sendAndReceive(request);
    }
    catch (Exception e)
    {
      throw new BitcoinClientException("Exception when backing up the wallet", e);
    }
  }  
  
  
  protected static BigDecimal roundToTwoDecimals(BigDecimal amount)
  {
    BigDecimal amountTimes100 = amount.multiply(new BigDecimal(100)).add(new BigDecimal("0.5"));
    BigDecimal roundedAmountTimes100 = new BigDecimal(amountTimes100.intValue());
    BigDecimal roundedAmount = roundedAmountTimes100.divide(new BigDecimal(100.0D));
    
    return roundedAmount;
  }
  
  private JSONObject createRequest(String functionName, JSONArray parameters)
    throws Exception
  {
    JSONObject request = new JSONObject();
    request.put("jsonrpc", "2.0");
    request.put("id", UUID.randomUUID().toString());
    request.put("method", functionName);
    request.put("params", parameters);
    
    return request;
  }
  
  private JSONObject createRequest(String functionName)
    throws Exception
  {
    return createRequest(functionName, new JSONArray());
  }
}

