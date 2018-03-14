package ww.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import ww.common.FileUploadUtil.UploadResult;

/**
 * content-type:multipart/form-data
 * @author admin
 *
 */
public class Multipart_form_data {
//	public HashMap<String,String> parameters=new HashMap<String,String>();
	
	public String message="";
	public ArrayList<String> newFileNames=new ArrayList<String>();
	
//	public String getParameter(String key){
//		if(this.parameters.containsKey(key)){
//			return this.parameters.get(key);
//		}
//		return null;
//	}
	
	public static boolean isMultipartContent(HttpServletRequest request){
		String content_type=request.getContentType();
		if(content_type.toLowerCase().startsWith("multipart")){
			return true;
		}else{
			return false;
		}
		//return ServletFileUpload.isMultipartContent(request);
	}
	
	public UploadResult updateImage(HttpServletRequest request,String savePath,int maxWidth){
		DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
		Iterator<String> it= multipartRequest.getFileNames();
		String fname="";
		if(it.hasNext()){
			fname=it.next();
		}else{
			return new UploadResult(false,"没上传文件");
		}
		// 得到上传的文件
		MultipartFile mFile1 = multipartRequest.getFile(fname);
		
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
	
//	public void uploadFile(HttpServletRequest request) 
//			throws ServletException, IOException {
//		
//		InputStream is = request.getInputStream();
//		StringBuffer sb = new StringBuffer();
//		int count = 0;
//		while(true){
//			int a = is.read();
//			sb.append((char)a);
//			if(a == '\r')
//				count++;
//			if(count==4){
//				is.read();
//				break;
//			}
//				
//		}
//		String title = sb.toString();
//		System.out.println(title);
//		String[] titles = title.split("\r\n");
//		String fileName = titles[1].split(";")[2].split("=")[1].replace("\"","");
//		System.out.println(fileName);
//		FileOutputStream os = new FileOutputStream(new File("d:\\"+fileName));
//		byte[] ob = new byte[1024];
//		int length = 0;
//		while((length = is.read(ob, 0, ob.length))>0){
//			os.write(ob,0,length);
//			os.flush();
//		}
//		os.close();
//		is.close();
//	}
//	
//	public boolean loadParameters(HttpServletRequest request){ 
//        String message = "";
//        try{
//            //使用Apache文件上传组件处理文件上传步骤：
//            //1、创建一个DiskFileItemFactory工厂
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
//            factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
//            //设置上传时生成的临时文件的保存目录
//            //factory.setRepository(tmpFile);
//            //2、创建一个文件上传解析器
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            //监听文件上传进度
//            upload.setProgressListener(new ProgressListener(){
//                public void update(long pBytesRead, long pContentLength, int arg2) {
//                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
//                    /**
//                     * 文件大小为：14608,当前已处理：4096
//                        文件大小为：14608,当前已处理：7367
//                        文件大小为：14608,当前已处理：11419
//                        文件大小为：14608,当前已处理：14608
//                     */
//                }
//            });
//             //解决上传文件名的中文乱码
//            upload.setHeaderEncoding("UTF-8"); 
//            //3、判断提交上来的数据是否是上传表单的数据
//            if(!ServletFileUpload.isMultipartContent(request)){
//                //按照传统方式获取数据    
//            	message="请按照传统方式获取数据";
//                return false;
//            }
//            
//            //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
//            upload.setFileSizeMax(1024*1024*3);
//            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
//            upload.setSizeMax(1024*1024*10);
//            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
//            List<FileItem> list = upload.parseRequest(request);
//            for(FileItem item : list){
//                //如果fileitem中封装的是普通输入项的数据
//                if(item.isFormField()){
//                    String name = item.getFieldName();
//                    //解决普通输入项的数据的中文乱码问题
//                    String value = item.getString("UTF-8");
//                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
//                    //System.out.println(name + "=" + value);
//                    parameters.put(name, value);
//                }
//            }
//        }catch (FileUploadBase.FileSizeLimitExceededException e) {
//            e.printStackTrace();
//            message="单个文件超出最大值:"+e.getMessage();
//            return false;
//        }catch (FileUploadBase.SizeLimitExceededException e) {
//            e.printStackTrace();
//            message="上传文件的总的大小超出限制的最大值:"+e.getMessage();
//            return false;
//        }catch (Exception e) {
//            message= "获取数据失败："+e.getMessage();
//            e.printStackTrace();
//            return false;
//        }
//        
//        return true;
//    }
//	
//	public boolean UpdateFiles(HttpServletRequest request,String savePath){ 
//        String message = "";
//        try{
//            //使用Apache文件上传组件处理文件上传步骤：
//            //1、创建一个DiskFileItemFactory工厂
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
//            factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
//            //设置上传时生成的临时文件的保存目录
//            //factory.setRepository(tmpFile);
//            //2、创建一个文件上传解析器
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            //监听文件上传进度
//            upload.setProgressListener(new ProgressListener(){
//                public void update(long pBytesRead, long pContentLength, int arg2) {
//                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
//                    /**
//                     * 文件大小为：14608,当前已处理：4096
//                        文件大小为：14608,当前已处理：7367
//                        文件大小为：14608,当前已处理：11419
//                        文件大小为：14608,当前已处理：14608
//                     */
//                }
//            });
//             //解决上传文件名的中文乱码
//            upload.setHeaderEncoding("UTF-8"); 
//            //3、判断提交上来的数据是否是上传表单的数据
//            if(!ServletFileUpload.isMultipartContent(request)){
//                //按照传统方式获取数据    
//            	message="请按照传统方式获取数据";
//                return false;
//            }
//            
//            //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
//            upload.setFileSizeMax(1024*1024*3);
//            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
//            upload.setSizeMax(1024*1024*10);
//            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
//            List<FileItem> list = upload.parseRequest(request);
//            for(FileItem item : list){
//                //如果fileitem中封装的是普通输入项的数据
//                if(item.isFormField()){
//                    String name = item.getFieldName();
//                    //解决普通输入项的数据的中文乱码问题
//                    String value = item.getString("UTF-8");
//                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
//                    //System.out.println(name + "=" + value);
//                    parameters.put(name, value);
//                }else{//如果fileitem中封装的是上传文件
//                    //得到上传的文件名称，
//                    String filename = item.getName();
//                    System.out.println(filename);
//                    if(filename==null || filename.trim().equals("")){
//                        continue;
//                    }                            
//                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
//                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
//                    filename = filename.substring(filename.lastIndexOf("\\")+1);
//                    //得到上传文件的扩展名
//                    String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
//                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
//                    //System.out.println("上传的文件的扩展名是："+fileExtName);
//                    
//                    // 得到上传服务器的路径
//        			String path = request.getSession().getServletContext().getRealPath(savePath);			
//        			path=path.replaceAll("\\\\", "/");//主要这里是正则表达式\\\\代表一个\
//     		        //保存的文件名
//        		    String newfilename = "img_"+WwSystem.UUID()+"."+fileExtName;//修改文件名
//        		    String pathfile = path + "/"+newfilename;
//        		    pathfile=pathfile.replaceAll("//", "/");
//                    this.newFileNames.add(newfilename);
//                    
//                    //获取item中的上传文件的输入流
//                    InputStream in = item.getInputStream();
//                    //创建一个文件输出流
//                    FileOutputStream out = new FileOutputStream(pathfile);
//                    //创建一个缓冲区
//                    byte buffer[] = new byte[1024];
//                    //判断输入流中的数据是否已经读完的标识
//                    int len = 0;
//                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
//                    while((len=in.read(buffer))>0){
//                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
//                        out.write(buffer, 0, len);
//                    }
//                    //关闭输入流
//                    in.close();
//                    //关闭输出流
//                    out.close();
//                    //删除处理文件上传时生成的临时文件
//                    //item.delete();
//                    message = "文件上传成功！";
//                }
//            }
//        }catch (FileUploadBase.FileSizeLimitExceededException e) {
//            e.printStackTrace();
//            message="单个文件超出最大值:"+e.getMessage();
//            return false;
//        }catch (FileUploadBase.SizeLimitExceededException e) {
//            e.printStackTrace();
//            message="上传文件的总的大小超出限制的最大值:"+e.getMessage();
//            return false;
//        }catch (Exception e) {
//            message= "获取数据失败："+e.getMessage();
//            e.printStackTrace();
//            return false;
//        }
//        
//        return true;
//    }
	
	
	public static String getExName(String fileName){
		String exname = fileName.substring(fileName.lastIndexOf(".")+1);//截取文件类型
		return exname;
	}

}
