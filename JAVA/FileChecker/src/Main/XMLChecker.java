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
import java.util.Set;

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
		super();
		checkTime = 0;
	}

	public static boolean isMac(){
		String os = System.getProperty("os.name");
		// 是mac电脑
		boolean isMac = false;
		if (os.indexOf("Mac") != -1) {
			isMac = true;
		} else {

		}
		return isMac;
	}
	
	// 根路径 相当于home下的桌面下的xml 就是home/desktop/xml
	public static String homeDirectory() {
		String os = System.getProperty("os.name");
		// 是mac电脑
		boolean isMac = false;
		if (os.indexOf("Mac") != -1) {
			isMac = true;
		} else {

		}

		FileSystemView fsv = FileSystemView.getFileSystemView();
		// 将桌面的那个文件目录赋值给file
		// File desktop=fsv.getHomeDirectory();
		String path = fsv.getHomeDirectory().getAbsolutePath();
		if (isMac) {
			path = path + "/Desktop/xml";
		} else {
			path = path + "\\xml";
		}

		return path;
	}

	// 检查桌面下名字为xml的文件夹下所有的xml文件,遍历筛选字符串
	public void check() {
		Thread thread = new Thread(new Runnable() {
			private ArrayList<String> findAllXMLFilePathes;

			public void run() {
				Date date1 = new Date();
				startDate = date1;
				System.out.print("check thread start 开始检查占位符语法是否正确 " + date1.toString() + "\n");

				String home = XMLChecker.homeDirectory();
				ArrayList<String> findAllXMLFilePathes = findAllXMLFilePathesAtPath(home);
				this.findAllXMLFilePathes = findAllXMLFilePathes;
				System.out.print("number of xml files: " + findAllXMLFilePathes.size() + "\n");
				// System.out.print("all pathes:"+findAllXMLFilePathes);
				// System.out.println("start check");

				// checkFilePathes(this.findAllXMLFilePathes, 0);
				for (int i = 0; i < findAllXMLFilePathes.size(); i++) {
					checkFileWithPath(findAllXMLFilePathes.get(i));
				}

				date1 = new Date();
				endDate = date1;
				long useTime = (endDate.getTime() - startDate.getTime()) / 1000;
				System.out.println(
						"---------------------------------------------------------------------------------------------");
				System.out.println("done, number of lines:" + checkTime + " \nerror count: " + errorCount + "\n"
						+ date1.toString() + " use time(s): " + useTime);
			}
		});
		thread.start();

	}

	// 检查占位符是否和英文的一样
	public void checkPlaceHolders() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// 开始检查占位符是否相等
				String homeDirectory = XMLChecker.homeDirectory();
				System.out.println("start checking place holders,home directory:" + homeDirectory);
				File homeFile = new File(homeDirectory);
				String[] homeFilelist = homeFile.list();

				ArrayList<String> englishPathes = findAllXMLFilePathesAtPath(homeDirectory + "/en");
				System.out.println("get the number of xml files at english path:" + englishPathes.size());
				System.out.println("get all languages:");
				for (int a = 0; a < englishPathes.size(); a++) {

					String englishPath = englishPathes.get(a);
					for (int i = 0; i < homeFilelist.length; i++) {

						// current language
						String currentLanguage = homeFilelist[i];
						// if get .SD_Store or get English path,do nothing,find
						// others
						if (!currentLanguage.equals(".DS_Store") || currentLanguage.equals("en")) {
							// System.out.println("language:"+currentLanguage);
							String currentPath = englishPath.replace("/en", "/" + currentLanguage);
							if (XMLChecker.isMac()) {
								currentPath = englishPath.replace("/en", "/" + currentLanguage);
							}else{
								currentPath = englishPath.replace("\\en", "\\" + currentLanguage);
							}
							// System.out.println("English file
							// path:"+englishPath +" current language path:"+
							// currentPath);
							Map<String, Object> englishFile = DomXMLParser.Dom2MapFromPath(englishPath);
							Map<String, Object> currentFile = DomXMLParser.Dom2MapFromPath(currentPath);

							for (Map.Entry<String, Object> entry : englishFile.entrySet()) {
								String key = entry.getKey();
								Object object1 = entry.getValue();
								Object object2 = currentFile.get(entry.getKey());
								if (object1 instanceof String && object2 instanceof String) {

									String v1 = (String) entry.getValue();
									String v2 = (String) currentFile.get(entry.getKey());
									
									HashMap<String, Integer> map1 = DomXMLParser.findPlaceholdersInString(v1);
									HashMap<String, Integer> map2 = DomXMLParser.findPlaceholdersInString(v2);
									
									Set<String>ssssss =  map1.keySet();
									Boolean equal = true;
									for (String str : ssssss) {  
										Integer vv1 = map1.get(str);
										Integer vv2 = map2.get(str);
										if (vv1 != vv2) {
											equal = false;
										}
									}  
									if (!equal) {
										System.out.println("strings have different place holders found \nthe english path is:" + englishPath + "\ncurrent path is:"
												+ currentPath + "\nkey: " + key + "\nenglish value: " + v1
												+ "\ncurrent value: " + v2);
									}
							
									
									

								} else {
									//System.out.print("找到不是字符串类型的数据:" + entry.getKey());
								}

							}
							// System.out.println(englishFile+currentFile);

						}
					}
				}
				System.out.println("find place holder has finished");
			}
		});
		t.start();
	}

	public ArrayList<String> findAllXMLFilePathesAtPath(String path) {

		// 找到桌面的文件夹 /Users/songwentong/Desktop/xml
		File file = new File(path);
		// 获取文件列表
		String[] list = file.list();

		// 所有的XML文件路径
		ArrayList<String> allXMLFilePaths = new ArrayList<>();

		// 如果文件不存在
		if (list == null) {
			// XML路径找不到
			return allXMLFilePaths;
		} else {
			// 从1开始是为了去掉.DS_Store
			for (int i = 0; i < list.length; i++) {

				if (list[i].equals(".DS_Store")) {
					// 如果是DS_Store文件就什么都不做
				} else {
					// 获取某个语言的路径
					String languageDir = file.getAbsolutePath() + "/" + list[i];
					// 语言文件的文件夹
					File languageFile = new File(languageDir);
					if (languageFile.isDirectory()) {
						allXMLFilePaths.addAll(findAllXMLFilePathesAtPath(languageDir));

					} else {
						if (languageDir.indexOf(".xml") != -1) {
							allXMLFilePaths.add(languageDir);
						}

					}

				}
			}
		}

		return allXMLFilePaths;
	}

	// 递归读取文件,这样做的目的是防止for循环导致卡死
	public void checkFilePathes(ArrayList<String> paths, int fromIndex) {
		checkFileWithPath(paths.get(fromIndex));
		if (fromIndex != paths.size() - 1) {
			// 如果不是最后一个,就读取下一个
			checkFilePathes(paths, fromIndex + 1);
		}
	}

	// 检查单个文件
	public void checkFileWithPath(String path) {
		// 读取出字符串

		// String xmlString = XMLChecker.stringFromFilePath(path);
		Map<String, Object> map = null;
		try {
			// 解析得到一个map,在解析的过程中调用一下检查吧
			map = DomXMLParser.XMLObjectFromPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// checkMap(map);
	}

	public boolean checkString(String string) {
		boolean result = true;
		if (string != null) {

			// 判断是否包含%
			if (string.indexOf("%%") >= 0) {
				// 包含
				System.out.println(string);
			} else {
				// 不包含
			}
		}
		return result;

	}

}
