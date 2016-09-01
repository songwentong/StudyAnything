package Main;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import XMLParser.DomXMLParser;

public class Main{

	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				XMLChecker checker = new XMLChecker();
				checker.printDate();
				checker.check();
				checker.checkPlaceHolders();
				checker.printDate();
			}
		});
		t.start();
		
//		String s1 = "aaaa";
//		String s2 = "a";
		
//	ArrayList<Integer> result = DomXMLParser.findOccrrenceFormString("dasdsadsa%asdsadas", "%");
//	System.out.println(result);
	}
	
	
	



}

