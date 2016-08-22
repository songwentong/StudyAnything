package XMLParser;
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.List; 
import java.util.Map;

import org.dom4j.Attribute;
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
			//e.printStackTrace();
		}
		  
		return doc;
	}
	public static Map<String, Object> Dom2MapFromPath(String path){
		Document doc = docFormPath(path);
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
	        	  String name = e.getName();
	        	  
	        	  Attribute a1 = e.attribute(0);
	        	  String a1v = a1.getValue();
//	        	  System.out.println("a1v:"+a1v);
	        	  String text = e.getText();
	              map.put(a1v, text);
	          }
	      } 
	      return map; 
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
            	XMLChecker.errorCount ++;
            	System.out.println("current Path:"+currentPath+"\n"+"string may have grammatical errors \nkey: "+e.attributeValue("name")+"\nvalue: "+e.getText());	
            	}
            	
                map.put(e.getName(), e.getText());
            }
        } 
        return map; 
    } 

  public static int numberOfPlaceholderInXMLString(String s){
	  int number = 0;
	  
	  return number;
  }
  
  /**
   * 所有的可能的占位符 
   */
  public static ArrayList<String> allPossibleOfPlaceholders(){
	  ArrayList<String> a = new ArrayList<String>();
	  a.add("%d");
	  a.add("%s");
	  a.add("%f");
	  a.add("%1$s");
	  a.add("%1%d");
	  a.add("%1$s");
	  a.add("%2$s");
	  a.add("%3$s");
	  a.add("%1$d");
	  a.add("%2$d");
	  a.add("%3$d");
	  return a;
  }
  
  //check the number of place holder in the string
  public static int numberOfPlaceholdersInString(String string){
	  //某个字符串中占位符的总数
	  int r = 0;
	  ArrayList<String> allPossibleOfPlaceholders = allPossibleOfPlaceholders();
	  for (int i = 0;i<allPossibleOfPlaceholders.size();i++){
		  ArrayList<Integer> temp = findOccrrenceFormString(string, allPossibleOfPlaceholders.get(i));
		  r += temp.size();	  
	  }
	  return r;
  }
  
  /*
   检查字符串是否合法
   */
  public static boolean checkString (String string){
	  XMLChecker.checkTime ++;
	  
		boolean result = true;
		if(string != null){
			
			String temp = string.replace("%%", "");
			//为了加快效率,这里先进行一次筛选
			ArrayList<Integer> results = findOccrrenceFormString(temp, "%");
			int count = results.size();
			
			switch (count){
			case 0:
			{
				//do nothing
				return true;
			}
			case 1:
			{
				//如果只有一个百分号,找到这个字符
				temp = temp.replace("%d", "");
				temp = temp.replace("%s", "");
				temp = temp.replace("%f", ""); 
				temp = temp.replace("%1$s", "");
				temp = temp.replace("%1$d", "");
			}
			break;
			default:
			{
				//如果有多个百分号,假设不超过3个,按照规则找出百分号
				temp = temp.replace("%1$s", "");
				temp = temp.replace("%2$s", "");
				temp = temp.replace("%3$s", "");
				temp = temp.replace("%1$d", "");
				temp = temp.replace("%2$d", "");
				temp = temp.replace("%3$d", "");
			}
				break;
			}
			for (int i=4;i<=100;i++){
				String percent = i+"%";
				temp = temp.replace(percent, "");
				//space1  ,note:space2 is not == space1,dont delete this line
				percent = i + " %";
				temp = temp.replace(percent, "");
				//space2  ,note:space2 is not == space1,dont delete this line
				percent = i + " %";
				temp = temp.replace(percent, "");
				percent = "%"+i;
				temp = temp.replace(percent, "");
				percent = "% "+i;
				temp = temp.replace(percent, "");
			}
			if( temp.indexOf("%") >= 0){
				
//				System.out.println("string may have grammatical errors: "+temp);
				result = false;
				
			}else{
				//不包含
//				System.out.println("string dont have grammatical errors: "+temp);
			}

			
			
		}
		return result;
		
	}
  
  
  /*
     找到string1中的所有的string2
     如果没有结果,这个数组为空
   */
  public static ArrayList<Integer> findOccrrenceFormString(String string1,String string2){
	  ArrayList<Integer> result = new ArrayList<>();
	  int fromIndex = 0;
	  
		  while(string1.indexOf(string2, fromIndex) != -1){
			  int index = string1.indexOf(string2, fromIndex);
			  result.add(new Integer(index));
			  fromIndex = index + string2.length();
		  }
	 
	  
	  
	  return result;
  } 
  
     @SuppressWarnings("unchecked")
    public static Map<String, Object> Dom2Map(Element e){ 
        Map<String, Object> map = new HashMap<String, Object>(); 
        List list = e.elements(); 
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List<Object> mapList = new ArrayList<Object>(); 
                 
                if(iter.elements().size() > 0){ 
                    Map<String, Object> m = Dom2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList<Object>(); 
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List<Object>) obj; 
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
                            mapList = new ArrayList<Object>(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List<Object>) obj; 
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