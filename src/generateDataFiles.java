/****************************************************************************************************
 *Class generateDataFiles 
 *This class will do various data file generation operation for training and testing for 10 fold cross
 *validation operation
 *****************************************************************************************************/ 
public class generateDataFiles
{   
    /***************************************************************************************************
     * generateTestDataFiles method will generate 10 test data files from original data files for 10 fold 
     *cross validation
     *****************************************************************************************************/ 
    public String[][] generateTestDataFiles(String records[][],String dataset)
    {        
        int noOfRecords=records.length;
        //System.out.println("noOfRecords="+noOfRecords);//for testing
        int noOfAttributes=records[0].length;//including classLevel
        //System.out.println("noOfAttributes="+noOfAttributes);//for testing
        int noOfRecordsInEachTestFile=(int)(noOfRecords/10);
        //System.out.println("noOfRecordsInEachTestFile="+noOfRecordsInEachTestFile);//for testing
        int remainderRecords=noOfRecords%10;
        //System.out.println("remainderRecords="+remainderRecords);//for testing
        String testRecords[][]=new String[10][];
        for(int outerCounter=0;outerCounter<=10-1;outerCounter++)
        {            
            int modifiedNoOfRecordsInEachTestFile=noOfRecordsInEachTestFile;
            if(outerCounter<=remainderRecords-1)
            {
                modifiedNoOfRecordsInEachTestFile++;
            }//end of if
            String noOfLines[]=new String[modifiedNoOfRecordsInEachTestFile+1];            
            //testRecords[outerCounter]=new String[modifiedNoOfRecordsInEachTestFile+1];
            int counter=0;
            for(int innerCounter=0;innerCounter<=modifiedNoOfRecordsInEachTestFile-1;innerCounter++)
            {   
                noOfLines[counter]="";
                int randomNo=(int)(Math.random()*noOfRecords);                
                while(records[randomNo]==null)
                {
                    randomNo=(int)(Math.random()*noOfRecords); 
                }//end of while
                //System.out.println("randomNo="+randomNo);//for testing
                for(int innerCounter1=0;innerCounter1<=noOfAttributes-1;innerCounter1++)
                {  
                    noOfLines[counter]=noOfLines[counter].concat(records[randomNo][innerCounter1]);
                    noOfLines[counter]=noOfLines[counter].concat(",");
                }//end of for
                records[randomNo]=null;                
                //System.out.println("noOfLines[counter]="+noOfLines[counter]);//for testing
                counter++;
            }//end of for
            noOfLines[counter]="";
            //System.out.println(noOfLines[counter-1]);//for testing              
            /*for(int counter1=0;counter1<=counter-1;counter1++)
            {  
                testRecords[outerCounter][counter1]=noOfLines[counter1];
            }//end of for*/
            testRecords[outerCounter]=noOfLines;
            preProcessDataset PPD=new preProcessDataset();
            String recordsFileName="D:/StUdY/BPN/src/files/"+dataset+"test"+outerCounter+".data";
            PPD.writeRecordstoFile(noOfLines,recordsFileName);
            //System.out.println("Test"+outerCounter+"="+(testRecords[outerCounter].length-1));//for testing
        }//end of for  
        //System.out.println("Test file prepaired");//for testing
        return testRecords;
    }//end of generateTestDataFiles
    /******************************************************************************************************* 
    /***************************************************************************************************
     * generateTrainingDataFiles method will generate 10 training data files from test data files for 10 fold 
     *cross validation
     *****************************************************************************************************/ 
    public void generateTrainingDataFiles(String oneDimentionalTestRecords[][],String dataset)
    {        
        int noOfTestRecords=oneDimentionalTestRecords[0].length-1;
        //System.out.println("noOfTestRecords="+noOfTestRecords);//for testing
        //buildInitialPopulation DRFCR=new buildInitialPopulation();  
        //DRFCR.buildOneDimentionalArrayOfDataFromArray(oneDimentionalTestRecords[0][0],',');
        //int noOfAttributes=oneDimentionalTestRecords[0][0].length();//including classLevel
        //System.out.println("noOfAttributes="+noOfAttributes);//for testing
        String trainingRecords1[]=new String[noOfTestRecords*10];
        int counter=0;       
        for(int outerCounter1=0;outerCounter1<=10-1;outerCounter1++)
        {              
            int counter1=0;
            for(int outerCounter=0;outerCounter<=10-1;outerCounter++)
            {       
                //FileUtil fileTest=new FileUtil("C:/Documents and Settings/Administrator/cuscadingGA/src/"+dataset+"/INPUT_FILES/Test"+outerCounter+".data");
                //System.out.println("Test"+outerCounter);//for testing
                //buildInitialPopulation DRFCR=new buildInitialPopulation();
                //String testRecords[][]=DRFCR.buildTwoDimentionalArrayOfData(fileTest,","); 
                if(counter!=outerCounter)
                {
                    for(int innerCounter=0;innerCounter<=oneDimentionalTestRecords[outerCounter].length-2;innerCounter++)
                    {  
                        //for(int innerCounter1=0;innerCounter1<=oneDimentionalTestRecords[0].length-1;innerCounter1++)
                        //{ 
                            trainingRecords1[counter1]=oneDimentionalTestRecords[outerCounter][innerCounter];
                        //}//end of for 
                        counter1++;
                    }//end of for 
                    //System.out.println("counter1="+counter1);//for testing
                }//end of if
            }//end of for
            int counter2=0;
            for(int innerCounter=0;innerCounter<=trainingRecords1.length-1;innerCounter++)
            {       
                if(trainingRecords1[innerCounter]==null)
                    break;
                counter2++;
            }//end of for         
            //System.out.println("Train"+outerCounter1+"="+counter2);//for testing
            //String noOfLines[]=new String[counter2+1];
            //int counter3=0;
            String trainingRecords[]=new String[counter2+1];   
            /*for(int outerCounter=0;outerCounter<=counter2-1;outerCounter++)
            {       
                noOfLines[counter3]="";
                for(int innerCounter=0;innerCounter<=noOfAttributes-1;innerCounter++)
                {  
                    noOfLines[counter3]=noOfLines[counter3].concat(trainingRecords[outerCounter][innerCounter]);
                    noOfLines[counter3]=noOfLines[counter3].concat(",");
                }//end of for
                counter3++;
            }//end of for 
            noOfLines[counter3]="";*/
            counter2=0;
            for(int innerCounter=0;innerCounter<=trainingRecords1.length-1;innerCounter++)
            {       
                if(trainingRecords1[innerCounter]==null)
                    break;
                else
                {
                    trainingRecords[counter2]=trainingRecords1[innerCounter];
                    counter2++;
                }//end of else
            }//end of for
            trainingRecords[counter2]="";
            preProcessDataset PPD=new preProcessDataset();
            String recordsFileName="D:/StUdY/BPN/src/files/"+dataset+"training"+outerCounter1+".data";
            PPD.writeRecordstoFile(trainingRecords,recordsFileName);
            //System.out.println("Train"+outerCounter1+"="+trainingRecords[outerCounter1].length());//for testing
            counter++;
        }//end of for
        //System.out.println("Training file prepaired");//for testing        
    }//end of generateTestDataFiles
    /*******************************************************************************************************
     /***********************************************************************************************************
     *Main method of generateDataFiles class 
     *For checking this class
     ***********************************************************************************************************/
    public static void main( String args[])
    {
        buildInitialPopulation DRFCR=new buildInitialPopulation();
        /*******************************************************************************************************
        *all dataset
        ********************************************************************************************************/
        //String dataset="BREAST";
        //String dataset="CREDIT";
        //String dataset="DIADETES";
        //String dataset="GLASS";
        //String dataset="HEART";
        //String dataset="IRIS";
        //String dataset="LABOR";
        //String dataset="SONAR";
        //String dataset="WINE";
        //String dataset="ZOO";
        String dataset="YEAST";
        //String dataset="BREAST";
        //String dataset="HEPATITIS";
        //String dataset="LABOR";
        //String dataset="CREDIT";
        //String dataset="AUTO";
        //String dataset="ANNEAL";
        //String dataset="GERMAN";
        //String dataset="HORSE";//??
        //String dataset="CLEVE";//??
        //String dataset="SICK";   
        //String dataset="AUSTRAL";
        //String dataset="VEHICLE";
        //String dataset="HEART";
        //String dataset="HYPO";
        //String dataset="SONAR";
        //String dataset="LYMPHOGRAPHY";
        //String dataset="LED7";
        //String dataset="DIADETES";
          //String dataset="APPENDICITIS";
        //String dataset="NURSERY";
        //String dataset="HEART_C";
        //String dataset="TEST";
       System.out.println(dataset);//for testing
        String pathOriginal="D:/StUdY/BPN/src/files/"+dataset+".data";
        FileUtil fileOriginal=new FileUtil(pathOriginal);        
        String records[][]=DRFCR.buildTwoDimentionalArrayOfData(fileOriginal,",");
        //copyString CS=new copyString();
        //String records1[][]=CS.copyTwoDimentionalArray(records);
        generateDataFiles GDF=new generateDataFiles();//calling constructar
        String testRecords[][]=GDF.generateTestDataFiles(records,dataset);        
        GDF.generateTrainingDataFiles(testRecords,dataset);
    }//end of main       
}//end of class
