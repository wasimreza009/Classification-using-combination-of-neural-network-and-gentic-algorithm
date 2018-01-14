import java.io.*;
import java.util.*;
public class IOUtil
{  
    String IOString;
    StringTokenizer Tk;
    String delim;
    String S="";
    /**************************************************************
     *Constructor
     ***************************************************************/
    public IOUtil(String s) 
    {    
        this.IOString = s ; 
    }//end of constructor
    /***************************************************************/
    /***************************************************************
     *method readString(). Return next token 
     ***************************************************************/
    public String readString() throws IOException
    {  
        if (Tk==null) 
            Tk = new StringTokenizer (IOString,delim);   
        while(true)
        {    
            try
            {       
                return Tk.nextToken();    
            }//end of try    
            catch (NoSuchElementException e1)
            { 
                throw new IOException(); 
            }//end of catch   
        }//end of while 
    }//end of readString()  
    /***************************************************************/
    /***************************************************************
     *method findNextWord() This method will return next word of a word 
     * by using readString() method 
     ***************************************************************/
    public String findNextWord(String s)
    {    
        do 
        {  
            try 
            {	
                S = readString();    
            }//end of try  
            catch (IOException io)
            {	
                System.out.println("Input Error"); 
            }//end of catch           
        }while(!S.equals(s) && !S.equals(""));   
        if(!S.equals(""))
        {      
            try 
            {	
                S = readString();    
            }//end of try 
            catch (IOException io) 
            {
                System.out.println("Input Error");      
            }//end of catch      
        }//end of if   
        return S;  
    }//end of findNextWord()  
    /***************************************************************/
    /***************************************************************
     *method getNext(). This method will return next string by using 
     *readString() method 
     ***************************************************************/
    public String getNext() 
    {    
        try 
        {   
            S = readString();  
        }//end of try 
        catch (IOException io) 
        {    
            //System.out.println("Input Error");
            S=null;
        }//end of catch       
        return(S); 
    }//end of getNext() 
    /***************************************************************/
    /***************************************************************
     *method init_delim(). This will set delimiter for string by which 
     *we can separate string from each other. We have to supply delimiter. 
     ***************************************************************/
     public void init_delim(String s) 
    {   
        delim = s;  
    }//end of init_delim()
     /***************************************************************/
      /***************************************************************
     *method main(). For testing
     ***************************************************************/
     public static void main(String args[])
     {
         IOUtil IO=new IOUtil("My name is Dipankar Dutta");
         IO.init_delim(" ");
         for(int i=0;i<4;i++)
         {
            System.out.println(IO.getNext());
         }//end of for
         //System.out.println(IO.findNextWord("My"));
     }//end of main()     
}//end of IOUtil
