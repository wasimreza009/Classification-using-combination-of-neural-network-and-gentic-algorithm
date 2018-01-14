/****************************************************************************************************
 *Class preProcessDataset
 *This class will do various preprocessing of dataset before MOGA
 *****************************************************************************************************/ 
public class preProcessDataset 
{
    /****************************************************************************************************
    *eliminateUnnessesaryAttribute method
    *This method will eliminate attribute if that attribute having missing values for all records
     *Example: Anneal
    *****************************************************************************************************/ 
    public String[] eliminateUnnessesaryAttribute(String records[][],String valuesOfAttributes[],String attributesInformations[][])
    {
        System.out.println("No of records="+records.length);//for testing        
        int numberOfAttributes=valuesOfAttributes.length;        
        boolean gavingValidValueOrNot[]=new boolean[numberOfAttributes];
        for(int counter=0;counter<=valuesOfAttributes.length-1;counter++)
        {            
            if(valuesOfAttributes[counter].equals(""))
            {                
                gavingValidValueOrNot[counter]=false;
            }//end of if
            else
            {
                gavingValidValueOrNot[counter]=true;
            }//end of else
        }//end of for         
        int numberOfRecords=records.length;
        String afterEliminatingInvalidAttributes[]=new String[numberOfRecords];        
        for(int outerCounter=0;outerCounter<=numberOfRecords-1;outerCounter++)
        {
            afterEliminatingInvalidAttributes[outerCounter]="";
            for(int innerCounter=0;innerCounter<=numberOfAttributes-1;innerCounter++)
            {
                if(gavingValidValueOrNot[innerCounter]==true)
                {
                    if(records[outerCounter][innerCounter].contains(".,"))
                    {
                        records[outerCounter][innerCounter]=records[outerCounter][innerCounter].substring(0,records[outerCounter][innerCounter].indexOf('.'));
                        afterEliminatingInvalidAttributes[outerCounter]=afterEliminatingInvalidAttributes[outerCounter].concat(records[outerCounter][innerCounter]);
                        afterEliminatingInvalidAttributes[outerCounter]=afterEliminatingInvalidAttributes[outerCounter].concat(",");
                    }//end of if                    
                    else
                    {
                        afterEliminatingInvalidAttributes[outerCounter]=afterEliminatingInvalidAttributes[outerCounter].concat(records[outerCounter][innerCounter]);
                        afterEliminatingInvalidAttributes[outerCounter]=afterEliminatingInvalidAttributes[outerCounter].concat(",");
                    }//end of else
                }//end of if                           
            }//end of for            
            afterEliminatingInvalidAttributes[outerCounter]=afterEliminatingInvalidAttributes[outerCounter].substring(0,afterEliminatingInvalidAttributes[outerCounter].length()-1);            
        }//end of for
        return afterEliminatingInvalidAttributes;
    }//end of method
    /***************************************************************************************************/
    /****************************************************************************************************
    *shieftingClassLabel method
    *This method will shieft classLabel from first to last colume
    *****************************************************************************************************/ 
    public String[] shieftingClassLabel(String records[][])
    {
        int numberOfRecords=records.length;
        int numberOfAttributes=records[0].length;
        String afterShieftingClassLabel[]=new String[numberOfRecords];        
        for(int outerCounter=0;outerCounter<=numberOfRecords-1;outerCounter++)
        {
            afterShieftingClassLabel[outerCounter]="";
            for(int innerCounter=1;innerCounter<=numberOfAttributes-1;innerCounter++)
            {              
                afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(records[outerCounter][innerCounter]);
                afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(",");          
            }//end of for            
            afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(records[outerCounter][0]);
        }//end of for
        return afterShieftingClassLabel;
    }//end of method
    /***************************************************************************************************/
    /****************************************************************************************************
    *shieftingClassLabel method
    *This method will shieft classLabel from last to first colume
    *****************************************************************************************************/ 
    public String[] shieftingClassLabel2(String records[][])
    {
        int numberOfRecords=records.length;
        int numberOfAttributes=records[0].length;
        String afterShieftingClassLabel[]=new String[numberOfRecords];        
        for(int outerCounter=0;outerCounter<=numberOfRecords-1;outerCounter++)
        {
            afterShieftingClassLabel[outerCounter]="";
            afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(records[outerCounter][numberOfAttributes-1]);
            afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(","); 
            for(int innerCounter=0;innerCounter<=numberOfAttributes-2;innerCounter++)
            {              
                afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(records[outerCounter][innerCounter]);
                if(innerCounter!=numberOfAttributes-2)
                    afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(",");          
            }//end of for             
        }//end of for
        return afterShieftingClassLabel;
    }//end of method
    /***************************************************************************************************/
    /****************************************************************************************************
    *eliminatingGap method
    *This method will eliminate gap in between attributes in PreprocessedOriginal.data file
    *****************************************************************************************************/     
    public String[] eliminatingGap(String records[][])
    {
        int numberOfRecords=records.length;
        System.out.println(numberOfRecords);//for testing
        int numberOfAttributes=records[0].length;
        String afterEliminatingGap[]=new String[numberOfRecords];        
        for(int outerCounter=0;outerCounter<=numberOfRecords-1;outerCounter++)
        {
            afterEliminatingGap[outerCounter]="";
            for(int innerCounter=0;innerCounter<=numberOfAttributes-1;innerCounter++)
            {              
                //if(innerCounter==numberOfAttributes-1)
                //{
                    //records[outerCounter][innerCounter]=records[outerCounter][innerCounter].substring(0,records[outerCounter][innerCounter].indexOf('.'));
                    afterEliminatingGap[outerCounter]=afterEliminatingGap[outerCounter].concat(records[outerCounter][innerCounter]);
                    afterEliminatingGap[outerCounter]=afterEliminatingGap[outerCounter].concat(",");
                //}//end of if                    
                /*else
                {
                    afterEliminatingGap[outerCounter]=afterEliminatingGap[outerCounter].concat(records[outerCounter][innerCounter]);
                    afterEliminatingGap[outerCounter]=afterEliminatingGap[outerCounter].concat(",");
                }//end of else   */      
            }//end of for            
            //afterShieftingClassLabel[outerCounter]=afterShieftingClassLabel[outerCounter].concat(records[outerCounter][0]);
        }//end of for
        return afterEliminatingGap;
    }//end of method
    /***************************************************************************************************/
   /***************************************************************************************************
     * writeParetoRulestoFile method writes records in a file
     *****************************************************************************************************/ 
    public void writeRecordstoFile(String afterEliminatingInvalidAttributes[],String fileName)
    {        
        FileUtil fileRecords=new FileUtil(fileName);        
        fileRecords.writeFile();          
        for(int counter=0;counter<=afterEliminatingInvalidAttributes.length-1;counter++)
        {            
            fileRecords.writeString(afterEliminatingInvalidAttributes[counter]);
        }//end of for        
    }//end of writeRecordstoFile
    /***********************************************************************************************************
     *Main method of generateDataFiles class
     *For checking this class
     ***********************************************************************************************************/
    public static void main( String args[])
    {
        preProcessDataset PPD=new preProcessDataset();
    }
}//end of class
