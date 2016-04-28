import java.awt.List;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
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
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import XMLParser.*;

public class XMLChecker {
	public void check(){
		Thread thread =  new Thread(new Runnable() {
			public void run() {
				System.out.print("线程启动---");
				
				ArrayList<String> findAllXMLFilePathes = findAllXMLFilePathes();
				
				System.out.print("xml 数量"+findAllXMLFilePathes.size());
				
				checkFilePathes(findAllXMLFilePathes, 0);
			}
		});
		thread.start();
		

	}
	
	
	
	/*
	public ArrayList<String> findAllLanuageDir(){
		
	}
	*/
	
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
						String filePath = languageDir+fileList[j];
						allXMLFilePaths.add(filePath);
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
	//http://xuelianbobo.iteye.com/blog/2152238   xml解析
	//http://www.oschina.net/code/snippet_155593_49684  xml解析
	public void checkFileWithPath(String path){
//		XStream magicApi = new XStream();
//		magicApi.registerConverter(new MapEntryConverter());
//		Map map = (Map) magicApi.fromXML(path);
		String xmlString = XMLChecker.stringFromFilePath(path);
		Map map = XMLParser.XMLObjectFromString(xmlString);
		
		checkMap(map);
	}
	
	public static String stringFromFilePath(String path){
		return null;
	}
	
	//递归遍历XML结构,并检查语法是否正确
	public boolean checkMap(Object xml){
		//if map ,遍历
		//if String ,判断语法
		//if ArrayList,遍历
		//其他,通过
		return false;
	}
	
	

}












