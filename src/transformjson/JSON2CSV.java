package transformjson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSON2CSV {
  public static void main(String[] myHelpers) throws Exception{
	    //load and read the named-entity data from local file, read those file one by one
	  int filenamecount=0;
	    boolean fileexist=true;
	    do{	 String filename="nerdata_"+filenamecount;
	    File sourcefile=new File("dealeddatabyner/"+filename+".txt");
	   if(!sourcefile.exists()){
		  fileexist=false;
		  System.err.println("All file read.");
	    }else{

	    BufferedReader readdata=new BufferedReader( new FileReader(sourcefile) );
	     String tem;
	  	String dataString="";
	     while( (tem=readdata.readLine())!=null){
	    	           dataString=dataString+tem;
	  }
	     readdata.close();
//remove the unnecessary information in the data
	   dataString = dataString.substring(dataString.indexOf("\"docs\""),dataString.indexOf("}]}")+3);
	   dataString="{"+dataString;	     
//compile the data in Json format
     JSONObject output = new JSONObject(dataString);
	 JSONArray docs = output.getJSONArray("docs");
	 String csv = CDL.toString(docs);
 //create the csv files in local place,use the name nerdatacsv_number, if the name is exist,
     //then replace it with a new data information. Next, create a new file for next source data
	 boolean doccreate=false;
	 String filename2="nerdatacsv_"+filenamecount;
     File file=new File("csvfile/"+filename2+".csv");
     do{
  		if(createFile(file)){
  			doccreate=true;
  			 System.out.println("File "+filename2+" is created successfully.");
  		}else{
  			file.delete();
  			file = new File("csvfile/"+filename2+".csv");
  		 }
  	   }while(doccreate==false);
 	//store the new data information in local csv file
     FileUtils.writeStringToFile(file, csv);
     filenamecount++;
     }}while(fileexist==true);
  }
	//the method to create a new file
  public static boolean createFile(File fileName)throws Exception{  
	  boolean flag=false;  
	  try{  
	   if(!fileName.exists()){  
	    fileName.createNewFile();  
	    flag=true;  
	   }  
	  }catch(Exception e){  
	   e.printStackTrace();  
	  }  
	  return flag;  
	 } 
}