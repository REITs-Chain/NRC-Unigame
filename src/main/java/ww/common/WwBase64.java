package ww.common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Charsets;

public class WwBase64 {
	
	public static String encode(String src) { 
        byte[] b = src.getBytes(Charsets.UTF_8); 
        return encode(b);  
    }
	
	public static String encode(final byte[] bytes) {  
        return new String(Base64.encodeBase64(bytes),Charsets.UTF_8);  
    }
	
	public static String decode(String base64_str) { 
        if (base64_str != null) {  
        	byte[] b = base64_str.getBytes(Charsets.UTF_8);
        	byte[] b2 = Base64.decodeBase64(b);
        	return new String(Base64.encodeBase64(b2),Charsets.UTF_8);
        }  
        return "";
    }
	
	public static byte[] decode(final byte[] bytes) {  
        return Base64.decodeBase64(bytes);  
    }

     

	/**
	 * 编码，有访问限制
	 * @param str
	 * @return
	 */
    public static String getBase64(String src) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = src.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new sun.misc.BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
        
    /**
	 * 解码，有访问限制
	 * @param str
	 * @return
	 */
    public static String getFromBase64(String base64_str) {  
        byte[] b = null;  
        String result = null;  
        if (base64_str != null) {  
        	sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(base64_str);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  

}


