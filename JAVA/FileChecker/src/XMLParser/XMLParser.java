package XMLParser;
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
	public static Map< Object, Object> XMLObjectFromString(String xmlString){
		System.out.println("解析XML");
		
		
		Map map = null;
		XStream magicApi = new XStream(new DomDriver());
        magicApi.registerConverter(new MapEntryConverter());
        
        
		return map;
	}
	public static String stringFormXMLObject(Map xmlMap){
		return null;
	}
	
 

}




