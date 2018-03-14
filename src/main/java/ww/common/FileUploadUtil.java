package ww.common;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import sun.text.normalizer.CharTrie.FriendAgent;

/**
 * 文件上传工具类
 * @ClassName FileUploadUtil
 * @author YIN huafei
 * @date 2015年8月31日
 */
public class FileUploadUtil {
	
	public static class UploadResult{
		public boolean success=false;
		public String message="";
		public String srcFileName="";
		public String newFileName="";
		public String exName=""; //扩展名
		public long size=0;
		
		public UploadResult(boolean success,String message,String srcFileName,String newFileName,String exName,long fileSize){
			this.success=success;
			this.message=message;
			this.srcFileName=srcFileName;
			this.newFileName=newFileName;
			this.exName=exName;
			this.size=fileSize;
		}
		
		public UploadResult(boolean success,String message){
			this.success=success;
			this.message=message;
		}
		
		public UploadResult(boolean success,String srcFileName,String newFileName,String exName,long fileSize){
			this.success=success;
			this.srcFileName=srcFileName;
			this.newFileName=newFileName;
			this.exName=exName;
			this.size=fileSize;
		}
		
	}
	
	public static boolean isMultipartContent(HttpServletRequest request){
		String content_type=request.getContentType();
		if(content_type.toLowerCase().startsWith("multipart")){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 判断文件是否是图片<br>
	 * 图片格式限定为:jpg、png、jpeg
	 * @Author YIN huafei
	 * @Date 2015年8月31日
	 * @param fileName 文件名称
	 * @return boolean 若判断的文件是图片，返回true，否则返回false
	 */
	public static boolean isImage(String fileName) {
		if (fileName == null) {
			return false;
		}
		String[] imgs = new String[]{"png","jpg","jpeg"};
		boolean flag = Arrays.toString(imgs).contains(fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length()));
		return flag ? true : false;
	}
	
	public static UploadResult updateOneImage(HttpServletRequest request,String savePath,String fileElementName){
		DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
		// 得到上传的文件
		MultipartFile mFile1 = multipartRequest.getFile(fileElementName);
		
		if(savePath==null||savePath.isEmpty())
		{
			savePath="/public/file_list/images/";//默认保存路径
		}
		
		if(mFile1.getSize()>0){
			// 得到上传服务器的路径
			String path = request.getSession().getServletContext().getRealPath(savePath);			
			path=path.replaceAll("\\\\", "/");//主要这里是正则表达式\\\\代表一个\
		    // 得到上传的文件的文件名
			String filename = mFile1.getOriginalFilename();
			String exname = getExName(filename);//  filename = filename.substring(filename.lastIndexOf("."));//截取文件类型
		    
		    String newfilename = "img_"+WwSystem.UUID()+"."+exname;//修改文件名
		    String pathfile = path + "/"+newfilename;
		    pathfile=pathfile.replaceAll("//", "/");
		    try {
				FileCopyUtils.copy(mFile1.getBytes(), new File(pathfile));
			} catch (IOException e) {
				e.printStackTrace();
				return new UploadResult(false,e.getMessage());
			}
		    return new UploadResult(true,filename, newfilename, exname,mFile1.getSize());
		}
		
		return new UploadResult(false, "上传的文件没有数据");
	}
	
	
	/**
	 *"/"代表web根目录,savePath:默认保存路径："/public/file_list/images/"
	 * @param request
	 * @param savePath
	 * @param fileElementName
	 * @return
	 */
	public static UploadResult updateOneImage(HttpServletRequest request,String savePath,String fileElementName,int maxWidth){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 得到上传的文件
		MultipartFile mFile1 = multipartRequest.getFile(fileElementName);
		
		if(savePath==null||savePath.isEmpty())
		{
			savePath="/public/file_list/images/";//默认保存路径
		}
		
		if(mFile1.getSize()>0){
			// 得到上传服务器的路径
			String path = request.getSession().getServletContext().getRealPath(savePath);			
			path=path.replaceAll("\\\\", "/");//主要这里是正则表达式\\\\代表一个\
		    // 得到上传的文件的文件名
			String filename = mFile1.getOriginalFilename();
			String exname = getExName(filename);//  filename = filename.substring(filename.lastIndexOf("."));//截取文件类型
		    
		    String newfilename = "img_"+WwSystem.UUID()+"."+exname;//修改文件名
		    String pathfile = path + "/"+newfilename;
		    pathfile=pathfile.replaceAll("//", "/");
		    try {
				FileCopyUtils.copy(mFile1.getBytes(), new File(pathfile));
			} catch (IOException e) {
				e.printStackTrace();
				return new UploadResult(false,e.getMessage());
			}
		    if (maxWidth>0) {
		    	try {		    		
		    		newfilename=CreateThumbnail(request,path,newfilename,maxWidth);
		    		exname="jpg";
		    		} catch (Exception e) {
			    		e.printStackTrace();
			    		return new UploadResult(false,e.getMessage());
		    		}
			}
		    return new UploadResult(true,filename, newfilename, exname,mFile1.getSize());
		}
		
		return new UploadResult(false, "上传的文件没有数据");
	}
	
	
	public static  String CreateThumbnail(HttpServletRequest request,String path,String newfilename,int maxWidth) throws Exception{
	    // fileExtNmae是图片的格式 gif JPG 或png
	    // String fileExtNmae="";
	    double Ratio = 0.0;
	    File srcFile = new File(path, newfilename);
	    if ( !srcFile.isFile() )
	        throw new Exception(srcFile+" is not image file error in CreateThumbnail!");
	   
	    BufferedImage Bi = ImageIO.read(srcFile);
	    //假设图片宽 高 最大为100 100
	    Image Itemp = Bi.getScaledInstance (maxWidth,maxWidth,Bi.SCALE_SMOOTH);
	    if ((Bi.getHeight()>maxWidth) || (Bi.getWidth()>maxWidth)){
	    	if ((Bi.getHeight()>maxWidth*10) || (Bi.getWidth()>maxWidth*10)) 
				 throw new Exception(srcFile+" is image size error in CreateThumbnail!");
		        if (Bi.getHeight()>Bi.getWidth())
		        	Ratio = maxWidth*10/Bi.getHeight();
		        else if(Bi.getHeight()<Bi.getWidth())
		        	Ratio = maxWidth*10/Bi.getWidth();
		        else
		        	Ratio = Bi.getHeight()/Bi.getWidth();
	        }else
	        	if (Bi.getHeight()>Bi.getWidth())
	            	Ratio = maxWidth/Bi.getHeight();
	            else if(Bi.getHeight()<Bi.getWidth())
	            	Ratio = maxWidth/Bi.getWidth();
	            else
	            	Ratio = Bi.getHeight()/Bi.getWidth();
	    if (Bi.getHeight()==Bi.getWidth()) 
	    	Ratio = Bi.getHeight()/Bi.getWidth();
		
	    AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Ratio, Ratio), null);
	    Itemp = op.filter(Bi, null);
	    String newfilename2="img_"+WwSystem.UUID()+".jpg";
	    try {
	    	
	    	File newFile = new File( path, newfilename2);
	        ImageIO.write((BufferedImage)Itemp, "jpg", newFile);
	       
	        srcFile.delete();
	    }
	    catch (Exception ex) {
	        throw new Exception(" ImageIo.write error in CreatThum.: "+ex.getMessage());
	        }
	    return newfilename2;
	}
	
	/**
	 *"/"代表web根目录,savePath:默认保存路径："/public/file_list/files/"
	 * @param request
	 * @param savePath 默认保存路径："/public/file_list/files/"
	 * @param fileElementName 
	 * @return 返回保存的新文件名
	 */
	public static UploadResult updateOneFile(HttpServletRequest request,String savePath,String fileElementName){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 得到上传的文件
		MultipartFile mFile1 = multipartRequest.getFile(fileElementName);
		
		if(savePath==null||savePath.isEmpty())
		{
			savePath="/public/file_list/files/";//默认保存路径
		}
		
		if(mFile1.getSize()>0){
			// 得到上传服务器的路径
			String path = request.getSession().getServletContext().getRealPath(savePath);
			path=path.replaceAll("\\\\", "/");//主要这里是正则表达式\\\\代表一个\
		    // 得到上传的文件的文件名
			String filename = mFile1.getOriginalFilename();
		    String exname = getExName(filename);//  filename.substring(filename.lastIndexOf("."));//截取文件类型(扩展名)
		    
		    String newfilename = "file_"+WwSystem.UUID()+"."+exname;//新文件名
		    path += "/"+newfilename;
		    path=path.replaceAll("//", "/");
		    try {
				FileCopyUtils.copy(mFile1.getBytes(), new File(path));
			} catch (IOException e) {
				e.printStackTrace();
				return new UploadResult(false,e.getMessage());
			}
		    
		    return new UploadResult(true,filename, newfilename, exname,mFile1.getSize());
		}
		
		return new UploadResult(false, "上传的文件没有数据");
	}
	
	public static String getExName(String fileName){
		String exname = fileName.substring(fileName.lastIndexOf(".")+1);//截取文件类型
		return exname;
	}
	
}
