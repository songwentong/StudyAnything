package XMLParser;
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.List; 
import java.util.Map; 
import org.dom4j.Document; 
import org.dom4j.Element; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import Main.*;
public class DomXMLParser { 
	
	private static String currentPath;
	
	public static Map<String, Object> XMLObjectFromPath(String path){
//		System.out.println("\n开始扫描文件:"+path);
		currentPath = path;
		Document doc = DomXMLParser.docFormPath(path);
		Map<String, Object> xmlObj = DomXMLParser.Dom2Map(doc);
		return xmlObj;
	}
	
	public static Document docFormPath(String path){
		FileInputStream fis;
		Document doc = null;
		try {
			fis = new FileInputStream(path);
			byte[] b = new byte[fis.available()];
			  fis.read(b);
			  String str = new String(b);
			  
			  doc = DocumentHelper.parseText(str);
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		return doc;
	}
	
	
  @SuppressWarnings("unchecked")  
    public static Map<String, Object> Dom2Map(Document doc){ 
        Map<String, Object> map = new HashMap<String, Object>(); 
        if(doc == null) 
            return map; 
        Element root = doc.getRootElement(); 
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) { 
            Element e = (Element) iterator.next(); 
            List list = e.elements(); 
            if(list.size() > 0){ 
                map.put(e.getName(), Dom2Map(e)); 
            }else {
            	//得到的每一个字符串
            	String string = e.getText();
//            	System.out.println("\nDomXMLParser 57:"+string+"");
            	boolean result = checkString(string);
            	if(result == false){
            	System.out.println("current Path:"+currentPath+"\n"+"string may have grammatical errors \nkey: "+e.attributeValue("name")+"\nvalue: "+e.getText());	
            	}
            	
                map.put(e.getName(), e.getText());
            }
        } 
        return map; 
    } 
  
  public static boolean checkString (String string){
	  XMLChecker.checkTime ++;
	  
		boolean result = true;
		if(string != null){
			//为了加快效率,这里先进行一次筛选
			if( string.indexOf("%") >= 0){
				String temp = string;
				//去掉双百分号
				temp = temp.replaceAll("%%", "");
				temp = temp.replace("%d", "");
				temp = temp.replace("%s", "");
				temp = temp.replace("%f", "");
				temp = temp.replace("%1$s", "");
				temp = temp.replace("%2$s", "");
				temp = temp.replace("%3$s", "");
				temp = temp.replace("%1$d", "");
				temp = temp.replace("%2$d", "");
				temp = temp.replace("%3$d", "");
				
				for (int i=0;i<=100;i++){
					String percent = i+"%";
					temp = temp.replace(percent, "");
					percent = i + " %";
					temp = temp.replace(percent, "");
					percent = i+" %";
					temp = temp.replace(percent, "");
					percent = "%"+i;
					temp = temp.replace(percent, "");
					percent = " %"+i;
					temp = temp.replace(percent, "");
					percent = " % "+i;
					temp = temp.replace(percent, "");
				}
				
				//这里第二次判断一下就可以确定结果了
				if( temp.indexOf("%") >= 0){
					
//					System.out.println("string may have grammatical errors: "+temp);
					result = false;
					
				}else{
					//不包含
//					System.out.println("string dont have grammatical errors: "+temp);
				}
			}
			
			
		}
		return result;
		
	}
  
     @SuppressWarnings("unchecked")
    public static Map Dom2Map(Element e){ 
        Map map = new HashMap(); 
        List list = e.elements(); 
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList(); 
                 
                if(iter.elements().size() > 0){ 
                    Map m = Dom2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(m); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), m); 
                } 
                else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(iter.getText()); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), iter.getText()); 
                } 
            } 
        }else 
            map.put(e.getName(), e.getText()); 
        return map; 
    } 
} 