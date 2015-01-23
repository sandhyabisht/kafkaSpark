package home;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.Gson;


public class ReadContent {

	/**
	 * @param args
	 */
	
	public void main1(String args) {
		// TODO Auto-generated method stub
		List<Model> modelList=new ArrayList<Model>();
		
		Properties properties=new Properties();
		 String folderPath="";
		 String ES_server="";
		 String ES_port="";
		 String ES_index="";
		 String Alert_index="";
		 String Alert_Field_value="";
		 try {
			properties.load(new FileInputStream(new File("api.properties")));
			
			folderPath=properties.getProperty("Folder");
			ES_server=properties.getProperty("ES_server");
			ES_port=properties.getProperty("ES_port");
			ES_index=properties.getProperty("ES_index");
			 Alert_index=properties.getProperty("Alert_index");
			 Alert_Field_value=properties.getProperty("Alert_Field_value");
			System.out.println("folder is "+folderPath+","+ES_index+","+ES_port+","+ES_server+","+Alert_Field_value);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		System.out.println("Reading..."+args);
       	  CSVReader reader = new CSVReader(new StringReader(args),' ');

       	  String [] tokens;
       	  try {
			while ((tokens = reader.readNext()) != null) {
			      System.out.println(tokens[0]); // value1
			      
			      Model model=new Model(tokens);

				Gson gson = new Gson();
				String json = gson.toJson(model);
				/*String modify_json="{\"data\":"+json+"}";
				System.out.println(modify_json);*/

				//Index in Elastic Search
				IndexInES es=new IndexInES(ES_server,ES_port,ES_index,json,model,Alert_Field_value,Alert_index);
			     // modelList.add(model);
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done OpenCsv...");
		//}

	}

}
