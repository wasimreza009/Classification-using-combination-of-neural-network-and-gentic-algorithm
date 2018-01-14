import java.io.*;
import java.util.*;
public class FileUtil extends IOUtil
{
    static private BufferedReader buf;    
    static private BufferedWriter outbuf;
    private FileReader infile;
    private FileWriter outfile;
    String FileName;
    /**************************************************************
     *Constructor. Filename to be passed
     ***************************************************************/
    public FileUtil(String file) 
    {   
        super("FILE");//call of superclass i.e. IOUtil class constructor
        this.FileName = file;        
    }//end of constructor 
    /***************************************************************/
    /***************************************************************
     *method readFile(). 
     ***************************************************************/
    public void readFile()
    { 
        try 
        {      
            infile = new FileReader(FileName);
        }//end of try
        catch (FileNotFoundException nf) 
        {     
            System.out.println("File Not Found"); 
        }//end of catch   
        buf = new BufferedReader(infile);  
    }//end of readFile
    /***************************************************************/
    /***************************************************************
     *method writeFile(). 
     ***************************************************************/
    public void writeFile() 
    {   
        try 
        {     
            outfile = new FileWriter(FileName);  
        }//end of try 
        catch (IOException er)
        {    
            System.out.println("Write Error"); 
        }//end of catch     
        outbuf = new BufferedWriter(outfile);
    } //end of writeFile
    /***************************************************************/
    /***************************************************************
     *method readString(). 
     ***************************************************************/
    public String readString() throws IOException 
    {   
        if (Tk==null) refresh(); 
        while(true) 
        {     
            try 
            {    
                return Tk.nextToken();
            }//end of try    
            catch (NoSuchElementException e1)
            {
                refresh();            
            }//end of catch      
        }//end of while
    }//end of readString
    /***************************************************************/
    /***************************************************************
     *method writeString(). 
     ***************************************************************/
    public void writeString(String WS)
    {   
        try 
        {             
            outbuf.write(WS);             
            outbuf.newLine(); 
            outbuf.flush();            
        }//end of try  
        catch (IOException io) 
        {
            System.out.println(io);        
            System.out.println("WRITE ERROR");            
        }//end of catch          
    }//end of writeString 
    /***************************************************************/
    /***************************************************************
     *method finalize(). 
     ***************************************************************/
    protected void finalize() throws IOException 
    {    
        try 
        {    
            super.finalize(); 
        }//end of try  
        catch (Throwable t) 
        {
            ;
        }//end of catch      
        //if(outbuf != null) outbuf.close();
        //if(buf != null) buf.close();
    }//end of finalize
    /***************************************************************/
    /***************************************************************
     *method refresh(). 
     ***************************************************************/
    private void refresh () throws IOException 
    {   
        S = buf.readLine();
        if (S==null) throw new EOFException();    
        Tk = new StringTokenizer (S,delim);  
    }//end of refresh
    /***************************************************************/
    /***************************************************************
     *method name(). 
     ***************************************************************/
    public String name() 
    {  
        return FileName; 
    }//end of name
    /***************************************************************/
    /***************************************************************
     *method readLine(). This method will return a specified line from 
     *a line and return that as string.
     ***************************************************************/
    public String readLine(int lineNo,int noOfRecords)
    {
        String aLine=null;        
        this.readFile();
        for(int i=1;i<=noOfRecords;i++)
        {
            if(i==lineNo)
            {
                try
                {
                    aLine=this.buf.readLine();
                    //System.out.println(lineNo);//for testing
                }//end of try
                catch (Throwable t)
                {
                    //System.out.println("catch:"+t);//for testing;
                    //System.out.println("readline not performed");//for testing;
                }//end of catch
            }//end of if
            else
            {
                try
                {
                    this.buf.readLine();    
                }//end of try
                catch (Throwable t)
                {
                    ;
                }//end of catch
            }//end of else
        }//end of for        
        return aLine;
    }//end of readLine
    /***************************************************************
     
    /********************************************************************
     *method deleteLine(). This file delete a line from a file specified
     *by lineNo. After deleting the line this method will rewrite the file.
     *********************************************************************/
    public FileUtil deleteLine(int lineNo,int noOfRecords)
    {        
        String str="";        
        this.readFile();
        //System.out.println("FileName="+this.FileName);//for testing
        //System.out.println("noOfRecords="+noOfRecords);//for testing
        //System.out.println("lineNo="+lineNo);//for testing       
        for(int j=1;j<=noOfRecords;j++)
        {
            //System.out.println("j="+j);//for testing               
            if(j==lineNo)
            {
                //do nothing
                //System.out.println("jj="+j);//for testing
                try
                { 
                    this.buf.readLine();
                }//end of try
                catch (Throwable t)
                {
                    System.out.println("catch:"+t);//for testing;
                }//end of catch 
            }//end of if
            else
            {
                try
                {    
                    str=str.concat(this.buf.readLine());
                    //System.out.println("j="+j);//for testing      
                    //System.out.println(str);//for testing
                    str=str.concat("\n");
                }//end of try
                catch (Throwable t)
                {
                    System.out.println("catch:"+t);//for testing;
                }//end of catch                    
            }//end of else  
        }//end of for        
        //System.out.println(str);//for testing
        //return str;
        this.writeFile();
        this.writeString(str);
        return this;
    }//end of deleteLine
    /***************************************************************
   /***************************************************************
     *method noOfLines(). This method will return no of records in a file
     ***************************************************************/
    public int noOfLines()
    {
            int noOfRecords=0;
            try
            {
                this.readFile();
                
                while(this.buf.readLine()!=null)
                {
                    noOfRecords++;
                }//end of while
            }//end of try
            catch (Throwable t)
            {
                ;
            }//end of catch        
        return noOfRecords;
    }//end of noOfLines
    /***************************************************************
    /***************************************************************
     *method main()
     ***************************************************************/
     public static void main(String args[])
     {
         try 
         {
             FileUtil fileOriginal=new FileUtil("C:/Users/wasim/Documents/NetBeansProjects/bpn/src/files/Original.data");
             for(int i=1;i<=14;i++)
             {
                 System.out.println(fileOriginal.readLine(i,14));
             }//end of for
            //FileUtil fileWrite=new FileUtil("D:/PhD/FileHandling/src/OUTPUT_FILES/Dest_iris.data");
            //fileOriginal.readFile(); 
            //to find out no of lines in a file
            //int noOfRecords=fileOriginal.noOfLines();
            //System.out.println(noOfRecords);//for testing
            //String str="";            
            //for taking 10% random records into str
            //for(int i=1;i<=15/*noOfRecords/10*/;i++)
            //{
                //int randomNumber=(int)(Math.random()*noOfRecords);
                //int ran=randomNumber;
                //System.out.println(ran);//for testing
                //fileOriginal.readFile();
                //str=str.concat(fileOriginal.readLine(ran,noOfRecords));
                //str=str.concat("\n");
                //fileOriginal=fileOriginal.deleteLine(ran,noOfRecords);
                //fileOriginal.writeFile();
                //fileOriginal.writeString(strWrite);                
                //System.out.println(str);//for testing                
            //}//end of for
            //fileWrite.writeFile();
            //fileWrite.writeString(str);
          }//end of try
          catch (Throwable t)
          {
             ;
          }//end of catch          
     }//end of main()
}//end of FileUtil
