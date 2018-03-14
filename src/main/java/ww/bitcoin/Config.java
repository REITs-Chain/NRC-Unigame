package ww.bitcoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class Config {
	public String rootPath="";
	
	public String nodeIP="";//"127.0.0.1";
	public int nodePort=0;//8332;
	public String rpcUser="";//"ww";
	public String rpcPassword="";//"ww";
	
	public String mainNodeIP="";
    public int mainNodePort=0;
	public String mainNodeRpcUser="";
	public String mainNodeRpcPassword="";

	private static Config instance=null;
	
	public Config() {			

	}
	
	public static void load(){
		String path=ww.common.WwSystem.getRootPath();		
		String conf=path+"bitcoin.conf";		
		
		instance=new Config();
		try {
			BufferedReader bf= new BufferedReader(new FileReader(conf));
			String str=bf.readLine();
			while(str!=null){
				String[] ss=str.trim().split("=");
				String nn=ss[0].toLowerCase();
				if(nn.equals("nodeip")){
					instance.nodeIP=ss[1];
				}else if(nn.equals("nodeport")){
					instance.nodePort=Integer.parseInt(ss[1]);
				}else if(nn.equals("rpcuser")){
					instance.rpcUser=ss[1];
				}else if(nn.equals("rpcpassword")){
					instance.rpcPassword=ss[1];
				}else if(nn.equals("mainnodeip")){
					instance.mainNodeIP=ss[1];
				}else if(nn.equals("mainnodeport")){
					instance.mainNodePort=Integer.parseInt(ss[1]);
				}else if(nn.equals("mainnoderpcuser")){
					instance.mainNodeRpcUser=ss[1];
				}else if(nn.equals("mainnoderpcpassword")){
					instance.mainNodeRpcPassword=ss[1];
				}
				
				str=bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		instance.rootPath=path;
		
		System.out.println("rootPath:"+instance.rootPath);
		
		System.out.println("nodeIP:"+instance.nodeIP);
		System.out.println("nodePort:"+instance.nodePort);
		System.out.println("rpcUser:"+instance.rpcUser);
		System.out.println("rpcPassword:"+instance.rpcPassword);
		
		System.out.println("mainNodeIP:"+instance.mainNodeIP);
		System.out.println("mainNodePort:"+instance.mainNodePort);
		System.out.println("mainNodeRpcUser:"+instance.mainNodeRpcUser);
		System.out.println("mainNodeRpcPassword:"+instance.mainNodeRpcPassword);
	}
	
	public static Config getInst(){
		if(instance==null){
			load();
		}
		return instance;
	}

}
