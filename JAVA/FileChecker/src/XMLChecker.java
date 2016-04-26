import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

public class XMLChecker {
	public void check(){
		
		FileSystemView fsv=FileSystemView.getFileSystemView();
		  //将桌面的那个文件目录赋值给file
		File file=fsv.getHomeDirectory();
		System.out.println(file.getAbsolutePath());
		
		
		ArrayList<String> findAllXMLFilePathes = findAllXMLFilePathes();
		
		System.out.println(findAllXMLFilePathes + "");
	}
	
	
	//找到所有的XML目录
	public ArrayList<String> findAllXMLFilePathes(){
		//找到桌面的文件夹
		File file = new File("/Users/songwentong/Desktop/xml");
		//获取文件列表
		String [] list = file.list();
		
		//所有的XML文件路径
		ArrayList<String> allXMLFilePaths = new ArrayList<>();
		
		//如果文件不存在
		if(list == null){
			System.out.println("找不到XML路径");
			return allXMLFilePaths;
		}else{
			//从1开始是为了去掉.DS_Store
			for (int i=0;i<list.length;i++){
				System.out.println(list[i] + "");
				
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
			checkFileWithPath(paths.get(fromIndex-1));
		}
	}
	//http://xuelianbobo.iteye.com/blog/2152238   xml解析
	//http://www.oschina.net/code/snippet_155593_49684  xml解析
	public void checkFileWithPath(String path){
		
	}
	

}

