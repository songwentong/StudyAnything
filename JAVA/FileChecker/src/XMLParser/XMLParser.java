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
	public static Map< Object, Object> xmlObjectFromString(){
		Map map = null;
		XStream magicApi = new XStream(new DomDriver());
        magicApi.registerConverter(new MapEntryConverter());
        
        
		return map;
	}
	
 

}

class MapEntryConverter implements Converter{
	public boolean canConvert(Class clazz) {
	    return AbstractMap.class.isAssignableFrom(clazz);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
	    AbstractMap<String,String> map = (AbstractMap<String,String>) value;
	    for (Entry<String, String> entry : map.entrySet()) {
	        writer.startNode(entry.getKey().toString());
	        writer.setValue(entry.getValue().toString());
	        writer.endNode();
	    }
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
	    Map<String, String> map = new HashMap<String, String>();

	    while(reader.hasMoreChildren()) {
	        reader.moveDown();
	        map.put(reader.getNodeName(), reader.getValue());
	        reader.moveUp();
	    }
	    return map;
	}
}



