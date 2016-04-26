import java.io.File;
import java.util.ArrayList;

public class XMLChecker {
	public void check(){
		ArrayList<String> findAllXMLFilePathes = findAllXMLFilePathes();
		
		System.out.println(findAllXMLFilePathes + "");
	}
	
	
	//找到所有的XML目录
	public ArrayList<String> findAllXMLFilePathes(){
		File file = new File("/Users/songwentong/Desktop/xml");
		String [] list = file.list();
		
		ArrayList<String> allXMLFilePaths = new ArrayList<>();
		
		//从1开始是为了去掉.DS_Store
		for (int i=0;i<list.length;i++){
			System.out.println(list[i] + "");
			
			if(list[i].equals(".DS_Store")){
				//如果是DS_Store文件就什么都不做
			}else{
				//获取某个语言的路径
				String languageDir = file.getAbsolutePath() +"/" + list[i];
				File languageFile = new File(languageDir);
				//当前目录下所有的文件
				String []fileList = languageFile.list();
				for(int j=0;j<fileList.length;j++){
					String filePath = languageDir+fileList[j];
					allXMLFilePaths.add(filePath);
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
	
	public void checkFileWithPath(String path){
		
	}
	

}

