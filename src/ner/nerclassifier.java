package ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;

public class nerclassifier {

  public static void main(String[] args) throws Exception {

	  //load the seven classes recognize named-entity classifier from local file
    String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";

   AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
    
    
    //load and read the processed data from local file, read those file one by one
    int filenamecount=0;
    boolean fileexist=true;

   do{	 String filename="removestopworddata_"+filenamecount;
    File sourcefile=new File("removestopword/"+filename+".txt");
   if(!sourcefile.exists()){
	  fileexist=false;
	  System.err.println("All file read.");
    }else{
//collect those data
    BufferedReader readdata=new BufferedReader( new FileReader(sourcefile) );
     String tem;
  	String datastring="";
     while( (tem=readdata.readLine())!=null){
    	           datastring=datastring+tem;
  }
     readdata.close();
     //here to analysis the data and get the named-entity
     String[] data ={datastring};
    String nerstring="";
      for (String str : data) {
    	  nerstring=classifier.classifyWithInlineXML(str);
      }
 
    
    //create a new file to store those information   
      	 String filename2="nerdata_"+filenamecount;
         File fcreatednewdoc2 = new File("dealeddatabyner/"+filename2+".txt");
         boolean doccreate=false;
         //create a new file, use the name dealeddatabyner_number, if the name is exist,
         //then replace it with a new data information. Then create a new file for next source data
      	 do{
      		if(createFile(fcreatednewdoc2)){
      			doccreate=true;
      			 System.out.println("File "+filename2+" is created successfully.");
      		}else{
      		 fcreatednewdoc2.delete();
      		 fcreatednewdoc2 = new File("dealeddatabyner/"+filename2+".txt");
      		 }
      	   }while(doccreate==false);
      	//write the data into the file created above
      	 writeTxtFile(nerstring, fcreatednewdoc2);
      	 filenamecount++;
    } }while(fileexist==true);
      	
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
