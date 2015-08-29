package preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class removestopword extends newyorktimedata{

	public static void main(String[] args) throws Exception {

		   //load and read the file collecting the data source from website.
		// read those files one by one.
	    int filenamecount=0;
	    boolean fileexist=true;
	    
	    do{	 String filename="sourcedata_"+filenamecount;
	     File fileSrc=new File("sourceinformation/"+filename+".txt");
	    if(!fileSrc.exists()){
	 	  fileexist=false;
	 	  System.err.println("All file read.");
	     }else{
	//read the stop word list from local file
 File filestopword=new File("stopwords.txt");
 if(!filestopword.exists()){
     System.err.println("can not delete stopwords");
 }

 BufferedReader readstopword=new BufferedReader( new FileReader(filestopword) );
 List<String> stopwordlist=new ArrayList<String>();
  String readstopwordline;
	
  while( (readstopwordline=readstopword.readLine())!=null){
 	 
        stopwordlist.add(readstopwordline);		    
    
}
  readstopword.close();
	//store the data information into string "preprocessdata"
  String line;
  String preprocessdata="";
  BufferedReader buffer=new BufferedReader( new FileReader(fileSrc) ); 
 while( (line=buffer.readLine())!=null){
	 preprocessdata=preprocessdata+line;
	                 
 }
 buffer.close();
 
 //replace some expressions to reduce compiler error when match the stop words
 preprocessdata=preprocessdata.replace("\\\"", "");
 preprocessdata=preprocessdata.replace("(", "");
 preprocessdata=preprocessdata.replace(")", "");
 
 //use this regular expression to get the data information surround by quotation mark.
		Pattern pattern = Pattern.compile("\"(.*?)\"");
		Matcher matcher = pattern.matcher(preprocessdata);
		while(matcher.find()){
			String undealsentence =matcher.group(1);
			
			 //replace some expressions to reduce compiler error when match the stop words
		String dealingsentence=undealsentence.replace("."," .");
	        dealingsentence=dealingsentence.replace(","," ,");
	     
			
			//match the data source and stop word, if they match , then remove stop word from data source
			String undealword[] = dealingsentence.split(" ");
			String dealsentence="";
			for (int i = 0; i < undealword.length; i++) {		

			   	 boolean isstopword=false;
			   	 for (int j = 0; j <stopwordlist.size(); j++) {
					if(undealword[i].equals(stopwordlist.get(j))){
							isstopword=true;
						}				
					}
		   	 if(isstopword==false){
		   		dealsentence=dealsentence+undealword[i]+" ";	   		
			   	 }
		}
			if(!dealsentence.isEmpty()){
			dealsentence=dealsentence.substring(0,dealsentence.length()-1);	
		}
			//change some expressions back to original format.
			dealsentence=dealsentence.replace(" .",".");
			dealsentence=dealsentence.replace(" ,",",");
			
			preprocessdata=preprocessdata.replaceFirst(undealsentence, dealsentence);
						
		}
		
		 //create a new file to store those processed data 
     	 String filename2="removestopworddata_"+filenamecount;
        File fcreatednewdoc2 = new File("removestopword/"+filename2+".txt");
        boolean doccreate=false;
		 //create a new file, use the removestopworddata_number, if the name is exist,
        //then replace it with a new data information. Then create a new file for next source data
     	 do{
     		if(createFile(fcreatednewdoc2)){
     			doccreate=true;
     			 System.out.println("File "+filename2+" is created successfully.");
     		}else{
     		 fcreatednewdoc2.delete();
     		 fcreatednewdoc2 = new File("removestopword/"+filename2+".txt");
     		 }
     	   }while(doccreate==false);
     	//write the data into the file created above
     	 writeTxtFile(preprocessdata, fcreatednewdoc2);    	 
		filenamecount++;
	     }}while(fileexist==true);		
	}
}
