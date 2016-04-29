package XMLParser;
import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLParser {
	
	
	public static Map<String,String> XMLObjectFromFile(File file){
		Map< String, String> map = null;
		XStream magicApi = new XStream(new DomDriver());
        magicApi.registerConverter(new MapEntryConverter());
        magicApi.alias("root", Map.class);
//        Map fromXML =  
		Object obj =  magicApi.fromXML(file);
		System.out.println(obj.toString()+"");
		return map;
	}
	
	
	//http://xuelianbobo.iteye.com/blog/2152238   xml解析
	//http://www.oschina.net/code/snippet_155593_49684  xml解析
	//XML解析
	public static Map< String, String> XMLObjectFromString(String xmlString){
		System.out.println("解析XML");
		
		
		Map< String, String> map = null;
		XStream magicApi = new XStream(new DomDriver());
        magicApi.registerConverter(new MapEntryConverter());
        magicApi.alias("root", Map.class);
//        Map fromXML =  
		Object obj =  magicApi.fromXML(xmlString);
//		System.out.println(obj.toString()+"");
		
        
		return map;
	}
	
	
	public boolean checkString (String string){
		boolean result = true;
		if(string != null){
			
			//判断是否包含%
			if( string.indexOf("%%") >= 0){
				//包含
				System.out.println("包含百分号的字符串:"+string);
			}else{
				//不包含
			}
		}
		return result;
		
	}
	
//	public static String stringFormXMLObject(Map xmlMap){
//		return null;
//	}
	
 

}




