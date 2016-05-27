package Main;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.filechooser.FileSystemView;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.Dom4JXmlWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import XMLParser.*;

public class XMLChecker {
	public static long checkTime;
	public static int errorCount;
	public static ArrayList<String> findAllXMLFilePathes;
	public static Date startDate;
	public static Date endDate;
	public XMLChecker() {
		// TODO Auto-generated constructor stub
		super();
		checkTime = 0;
	}
	
	//检查桌面下名字为xml的文件夹下所有的xml文件,遍历筛选字符串
	public void check(){
		Thread thread =  new Thread(new Runnable() {
			private ArrayList<String> findAllXMLFilePathes;

			public void run() {
				Date date1 = new Date();
				
				String os = System.getProperty("os.name");
				//是mac电脑
				boolean isMac = false;
				if (os.indexOf("Mac") != -1) {
					isMac = true;
				}else{
					
				}
				
				startDate = date1;
				System.out.print("check thread start\n" + date1.toString()+"\n");
//				System.out.println("find the ");
				
				FileSystemView fsv=FileSystemView.getFileSystemView();
				 // 将桌面的那个文件目录赋值给file
//				File desktop=fsv.getHomeDirectory();
				String path = fsv.getHomeDirectory().getAbsolutePath() + "/Desktop"; 
				if (!isMac) {
					path = fsv.getHomeDirectory().getAbsolutePath()+"//xml";
				}
				
				ArrayList<String> findAllXMLFilePathes = findAllXMLFilePathesAtPath(path);
				this.findAllXMLFilePathes = findAllXMLFilePathes;
				System.out.print("number of xml files: "+findAllXMLFilePathes.size()+"\n" );
//				System.out.println("start check");
				
//				checkFilePathes(this.findAllXMLFilePathes, 0);
				for(int i=0;i<findAllXMLFilePathes.size();i++){
					checkFileWithPath(findAllXMLFilePathes.get(i));
				}
				
				date1 = new Date();
				endDate = date1;
				long useTime = (endDate.getTime() - startDate.getTime())/1000;
				System.out.println("---------------------------------------------------------------------------------------------");
				System.out.println("done, number of lines:"+checkTime +" \nerror count: "+errorCount + "\n" + date1.toString() + " use time(s): "+ useTime);
			}
		});
		thread.start();
		

	}
	
	
	
	/*
	public ArrayList<String> findAllLanuageDir(){
		
	}
	*/
	
	public ArrayList<String> findAllXMLFilePathesAtPath(String path){
		
		//找到桌面的文件夹  /Users/songwentong/Desktop/xml
		File file = new File(path);
		//获取文件列表
		String [] list = file.list();
		
		//所有的XML文件路径
		ArrayList<String> allXMLFilePaths = new ArrayList<>();
		
		//如果文件不存在
		if(list == null){
			//XML路径找不到
			return allXMLFilePaths;
		}else{
			//从1开始是为了去掉.DS_Store
			for (int i=0;i<list.length;i++){

				
				if(list[i].equals(".DS_Store")){
					//如果是DS_Store文件就什么都不做
				}else{
					//获取某个语言的路径
					String languageDir = file.getAbsolutePath() +"/" + list[i];
					//语言文件的文件夹
					File languageFile = new File(languageDir);
					if (languageFile.isDirectory()) {
						allXMLFilePaths.addAll(findAllXMLFilePathesAtPath(languageDir));
						
					}else{
						if(languageDir.indexOf(".xml") != -1){
							allXMLFilePaths.add(languageDir);
						}
						
					}
					
				}
			}
		}
		
		return allXMLFilePaths;
	}
	
	
	//找到所有的XML目录
	public ArrayList<String> findAllXMLFilePathes(){
		FileSystemView fsv=FileSystemView.getFileSystemView();
		  //将桌面的那个文件目录赋值给file
		File desktop=fsv.getHomeDirectory();
		
		//找到桌面的文件夹  /Users/songwentong/Desktop/xml
		File file = new File(desktop.getAbsolutePath()+"/Desktop/xml");
		//获取文件列表
		String [] list = file.list();
		
		//所有的XML文件路径
		ArrayList<String> allXMLFilePaths = new ArrayList<>();
		
		//如果文件不存在
		if(list == null){
			//XML路径找不到
			return allXMLFilePaths;
		}else{
			//从1开始是为了去掉.DS_Store
			for (int i=0;i<list.length;i++){

				
				if(list[i].equals(".DS_Store")){
					//如果是DS_Store文件就什么都不做
				}else{
					//获取某个语言的路径
					String languageDir = file.getAbsolutePath() +"/" + list[i];
					//语言文件的文件夹
					File languageFile = new File(languageDir);
					//当前目录下所有的文件
					String []fileList = languageFile.list();
					for(int j=0;j<fileList.length;j++){
						
						//是包含.xml的文件
						if(fileList[j].indexOf(".xml") != -1){
							String filePath = languageDir+"/"+fileList[j];
							allXMLFilePaths.add(filePath);
						}
						
						}
				}
			}
		}
		
		return allXMLFilePaths;
	}
	
	
	//递归读取文件,这样做的目的是防止for循环导致卡死
	public void checkFilePathes(ArrayList<String> paths,int fromIndex){
		checkFileWithPath(paths.get(fromIndex));
		if(fromIndex != paths.size()-1){
			//如果不是最后一个,就读取下一个
			checkFilePathes(paths,fromIndex+1);
		}
	}
	
	//检查单个文件
	public void checkFileWithPath(String path){
		//读取出字符串
		
		
		
//		String xmlString = XMLChecker.stringFromFilePath(path);
		Map<String, Object> map = null;
		try{
			//解析得到一个map,在解析的过程中调用一下检查吧
			map = DomXMLParser.XMLObjectFromPath(path);
		}catch (Exception e){
			e.printStackTrace();
		}
		
//		checkMap(map);
	}
	public boolean checkString (String string){
		boolean result = true;
		if(string != null){
			
			//判断是否包含%
			if( string.indexOf("%%") >= 0){
				//包含
				System.out.println(string);
			}else{
				//不包含
			}
		}
		return result;
		
	}
	
//	
//	// 根据路径得到一个字符串文件
//	public static String stringFromFilePath(String path){
//		BufferedReader reader = null;
//        String result = "";
//        try {
//        	File file = new File(path);
//            
////            System.out.println("以行为单位读取文件内容，一次读一整行：");
//            reader = new BufferedReader(new FileReader(file));
//            String tempString = null;
////            int line = 1;
//            // 一次读入一行，直到读入null为文件结束
//            while ((tempString = reader.readLine()) != null) {
//            	result = result + tempString;
//                // 显示行号
////                System.out.println("line " + line + ": " + tempString);
////                line++;
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e1) {
//                }
//            }
//        }
//        return result;
//	}
//	
////	//递归遍历XML结构,并检查语法是否正确
////	public boolean checkMap(Object xmlObj){
//		boolean result = false;
//		if(xmlObj instanceof Map){
//			
//			
//			//如果是map类型,遍历它的数值,然后去检查
//			Map map = (Map) xmlObj;
//			Collection c = map.values();
//			Iterator iter = c.iterator();
//			Object obj;
//			while(iter.hasNext()){
//				obj = iter.next();
//				//如果检查出这个map中有错误语法
//				if (checkMap(obj) == false) {
//					result = false;
//				}
//				
//			}
//		}
//		if(xmlObj instanceof ArrayList){
//			
////			Iterator iter = 
//		}
//		if (xmlObj instanceof String){
//			String temp = (String)xmlObj;
//			System.out.println(temp);
//			//得到检查结果
//			result = checkString((String)xmlObj);
//			if (result == false) {
//				//检查出来有错误的字符 
//				System.out.println("找到语法错误的字符串" + (String)xmlObj);
//			}
//		}
//		//if map ,遍历
//		//if String ,判断语法
//		//if ArrayList,遍历
//		//其他,通过
//		return result;
//	}
//	

	

}












