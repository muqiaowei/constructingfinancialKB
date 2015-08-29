package preprocess;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class newyorktimedata {

public static void main(String[] args) throws IOException,Exception{
	//start to read the financial word list from local file
 File filefinancialword=new File("common financial word list.txt");
 if(!filefinancialword.exists()){
     System.err.println("can read source words");
 }
	 BufferedReader readfianacialword=new BufferedReader( new FileReader(filefinancialword) );
	 List<String> financialwordlist=new ArrayList<String>();
	  String readfinancialwordline;
		
	  while( (readfinancialwordline=readfianacialword.readLine())!=null){
	 	 
		  financialwordlist.add(readfinancialwordline);		    
	    
	}
	  readfianacialword.close();
	//finish reading the financial words  
	  
 //set the format to satisfy the structure of URL
	  String queryword="";
	  for (int i = 0; i < financialwordlist.size(); i++) {
		  queryword=financialwordlist.get(i);
	      queryword=queryword.replace(" ", "%20");
	      
			//define the URL, use this URL to download information from website
String apikey= "&api-key=b9c6e23367fc8f81792e95c389b1337e:19:72473377";
String baseURL="http://api.nytimes.com/svc/search/v2/articlesearch";
String responseformats=".json";
String querytermString="?fq="+queryword+"&news_desk=Finance&begin_date=20140101&end_date=20150101";

// load data from the New York Times using those URL
URL url = new URL(baseURL+responseformats+querytermString+apikey); 
Reader loaddata = new InputStreamReader(new BufferedInputStream(url.openStream())); 
int a; 
String dataString="";
while ((a = loaddata.read()) != -1) { 
    dataString=dataString+(char)(a);
} 
loaddata.close(); 

//create a new file to store downloaded data    
int filenamecount=0;
	 String filename="sourcedata_"+filenamecount;
   File fcreatednewdoc = new File("sourceinformation/"+filename+".txt");
   boolean doccreate=false;
	 do{
		 //create a new file, use the sourcedata_number, if the name is exist, then create a new one with number add 1
		if(createFile(fcreatednewdoc)){
			doccreate=true;
			 System.out.println("File "+filename+" is created successfully.");
		}else{
		 filenamecount++;
		 filename="sourcedata_"+filenamecount;
		 fcreatednewdoc = new File("sourceinformation/"+filename+".txt");
		
		 }
	   }while(doccreate==false);
	 //write the data into the file created above
	 writeTxtFile(dataString, fcreatednewdoc);
	 if(i==financialwordlist.size()-1){
   	  System.err.println("All data obtained.");
     }
	  }
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
	// the method to write data into an exist file
	public static boolean writeTxtFile(String content,File  FileName)throws Exception{   
		  boolean flag=false;  		    
		  try {  
			  FileOutputStream o = new FileOutputStream(FileName,true);  
		      o.write(content.getBytes());  		      
		      o.close();  	
		   flag=true;  
		  } catch (Exception e) {  
		   e.printStackTrace();  
		  } 
		  return flag;  
		 }  

}


