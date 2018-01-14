public class buildInitialPopulation
{     
    /******************************************************************************************************
     *method buildTwoDimentionalArrayOfData(). This method will take data from original data table 
     *and return a two dimentional array of data.
     ******************************************************************************************************/
    public String[][] buildTwoDimentionalArrayOfData(FileUtil fileOriginal,String dlimiter)
    {         
        fileOriginal.readFile();
        int noOfRecords=fileOriginal.noOfLines()-1;        
        fileOriginal.readFile();
        String firstLine=fileOriginal.readLine(1,noOfRecords);       
        IOUtil FL=new IOUtil(firstLine);
        FL.init_delim(dlimiter);
        int noOfAttributes=0;
        while(FL.getNext()!=null)
        {
            noOfAttributes++;                      
        }//end of while        
        String records[][]=new String[noOfRecords][noOfAttributes];
        //System.out.println("noOfRecords"+noOfRecords);//for testing
        //System.out.println("noOfAttributes"+noOfAttributes);//for testing
        for(int outerLoopCounter=0;outerLoopCounter<=noOfRecords-1;outerLoopCounter++)
        {
            //System.out.println("outerLoopCounter"+outerLoopCounter);//for testing
            String record=fileOriginal.readLine(outerLoopCounter+1,noOfRecords);            
            IOUtil IO=new IOUtil(record);
            IO.init_delim(dlimiter);      
            for(int innerLoopCounter=0;innerLoopCounter<=noOfAttributes-1;innerLoopCounter++)
            {                          
                records[outerLoopCounter][innerLoopCounter]=IO.getNext();                       
            }//end of innerloop for
        }//end of outerloop for
        return records;        
    }//end of buildTwoDimentionalArrayOfData
    /******************************************************************************************************
     * method buildInitialPopulation(). This method will construct initial population from original database
     ******************************************************************************************************/
    public String[] buildInitialPopulation(String records[][],String attributeInformation[][],String valuesOfAttributes[])
    {   
        int populationSize=(int)records.length/10;   
        //int populationSize=2;  
        String initialPopulation[]=new String[populationSize];
        int noOfAttributes=records[0].length;        
        int noOfRecords=records.length;        
        int j=0;
        do
        {       
            /******************************************************************************************************
            * to generate a random number between 0 and maximum number of records-1 to select a record randomly 
            ******************************************************************************************************/
            int randomNumber1=(int)(noOfRecords*Math.random());               
            String classLabel1=records[randomNumber1][noOfAttributes-1];
            /******************************************************************************************************
            * to generate a random number between 0 and maximum number of records-1 to select a record randomly 
            ******************************************************************************************************/
            int randomNumber2=(int)(noOfRecords*Math.random());
            String classLabel2=records[randomNumber2][noOfAttributes-1];            
            if(classLabel1.equals(classLabel2))            
            {                
                for(int i=0;i<=noOfAttributes-2;i++)                
                {                    
                    String typeOfAttribute=attributeInformation[1][i];                   
                    /******************************************************************************************************
                    * For "Boolean" OR "Integer" OR "Float" type of attributes we will take minimum and maximum 
                     *values from two records. Format |min,max|. If one value is missing then we are taking same min 
                     *and max value. If two values are missing we are taking random values from possible values 
                     * of that attribute. For "Boolean" OR "Integer" we will consider only Integer values.
                    ******************************************************************************************************/
                    if(typeOfAttribute.equals("Boolean")||typeOfAttribute.equals("Integer"))
                    {
                        int attributeValue1=0;
                        int attributeValue2=0;
                        if(records[randomNumber1][i].equals("?")&!records[randomNumber2][i].equals("?"))
                        {
                            attributeValue1=Integer.parseInt(records[randomNumber2][i]);
                            attributeValue2=Integer.parseInt(records[randomNumber2][i]);                            
                        }//end of if
                        else if(records[randomNumber2][i].equals("?")&!records[randomNumber1][i].equals("?"))
                        {
                            attributeValue1=Integer.parseInt(records[randomNumber1][i]);
                            attributeValue2=Integer.parseInt(records[randomNumber1][i]);                            
                        }//end of if
                        else if(records[randomNumber1][i].equals("?")&records[randomNumber2][i].equals("?"))
                        {
                            String allValues=valuesOfAttributes[i];                            
                            int counter1=0;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter1++;                    
                                }//end of if
                            }//end of for                            
                            int randomNo2=(int)(Math.random()*counter1);                             
                            int startIndex3=0;
                            int endIndex3=0;   
                            int counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo2)
                                    {                 
                                        startIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for
                            counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo2+1)
                                    {                 
                                        endIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for                            
                            String substitute1="";
                            if(randomNo2==(counter1-1))
                            {
                                substitute1=substitute1.concat(allValues.substring(startIndex3+1));                                
                            }//end of if
                            else
                            {
                                substitute1=substitute1.concat(allValues.substring(startIndex3+1,endIndex3));                                
                            }//end of else                            
                            attributeValue1=Integer.parseInt(substitute1);                  
                            counter1=0;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter1++;                    
                                }//end of if
                            }//end of for                            
                            int randomNo3=(int)(Math.random()*counter1);                             
                            startIndex3=0;
                            endIndex3=0;   
                            counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo3)
                                    {                 
                                        startIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for
                            counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo3+1)
                                    {                 
                                        endIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for                            
                            String substitute2="";
                            if(randomNo3==(counter1-1))
                            {
                                substitute2=substitute2.concat(allValues.substring(startIndex3+1));                                
                            }//end of if
                            else
                            {
                                substitute2=substitute2.concat(allValues.substring(startIndex3+1,endIndex3));                                
                            }//end of else                            
                            attributeValue2=Integer.parseInt(substitute1);                            
                        }//end of if                       
                        else
                        {
                            attributeValue1=Integer.parseInt(records[randomNumber1][i]);                            
                            attributeValue2=Integer.parseInt(records[randomNumber2][i]);                              
                        }//end of else                        
                        if(attributeValue1<=attributeValue2)
                        {                    
                            if(initialPopulation[j]==null)
                            {
                                initialPopulation[j]=Integer.toString(attributeValue1);//records[randomNumber1][i];
                            }//end of if
                            else
                            {
                                initialPopulation[j]=initialPopulation[j].concat(Integer.toString(attributeValue1));
                            }//end of else
                            initialPopulation[j]=initialPopulation[j].concat(",");                    
                            initialPopulation[j]=initialPopulation[j].concat(Integer.toString(attributeValue2));
                            initialPopulation[j]=initialPopulation[j].concat("|"); 
                        }//end of if                        
                        else
                        {
                            if(initialPopulation[j]==null)
                            {
                                initialPopulation[j]=Integer.toString(attributeValue2);//records[randomNumber2][i];
                            }//end of if
                            else
                            {
                                initialPopulation[j]=initialPopulation[j].concat(Integer.toString(attributeValue2));
                            }//end of else
                            initialPopulation[j]=initialPopulation[j].concat(",");                    
                            initialPopulation[j]=initialPopulation[j].concat(Integer.toString(attributeValue1));
                            initialPopulation[j]=initialPopulation[j].concat("|"); 
                        }//end of else  
                    }//end of if
                    
                    
                    else if(typeOfAttribute.equals("Float"))
                    {
                        float attributeValue1=0;
                        float attributeValue2=0;
                        if(records[randomNumber1][i].equals("?")&!records[randomNumber2][i].equals("?"))
                        {
                            attributeValue1=Float.parseFloat(records[randomNumber2][i]);
                            attributeValue2=Float.parseFloat(records[randomNumber2][i]);
                        }//end of if
                        else if(records[randomNumber2][i].equals("?")&!records[randomNumber1][i].equals("?"))
                        {
                            attributeValue1=Float.parseFloat(records[randomNumber1][i]);
                            attributeValue2=Float.parseFloat(records[randomNumber1][i]);
                        }//end of if
                        else if(records[randomNumber1][i].equals("?")&records[randomNumber2][i].equals("?"))
                        {
                            String allValues=valuesOfAttributes[i];                            
                            int counter1=0;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter1++;                    
                                }//end of if
                            }//end of for                            
                            int randomNo2=(int)(Math.random()*counter1);                             
                            int startIndex3=0;
                            int endIndex3=0;   
                            int counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo2)
                                    {                 
                                        startIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for
                            counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo2+1)
                                    {                 
                                        endIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for                            
                            String substitute1="";
                            if(randomNo2==(counter1-1))
                            {
                                substitute1=substitute1.concat(allValues.substring(startIndex3+1));                                
                            }//end of if
                            else
                            {
                                substitute1=substitute1.concat(allValues.substring(startIndex3+1,endIndex3));                                
                            }//end of else                            
                            attributeValue1=Float.parseFloat(substitute1);                  
                            counter1=0;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter1++;                    
                                }//end of if
                            }//end of for                            
                            int randomNo3=(int)(Math.random()*counter1);                             
                            startIndex3=0;
                            endIndex3=0;   
                            counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo3)
                                    {                 
                                        startIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for
                            counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo3+1)
                                    {                 
                                        endIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for                            
                            String substitute2="";
                            if(randomNo3==(counter1-1))
                            {
                                substitute2=substitute2.concat(allValues.substring(startIndex3+1));                                
                            }//end of if
                            else
                            {
                                substitute2=substitute2.concat(allValues.substring(startIndex3+1,endIndex3));                                
                            }//end of else                            
                            attributeValue2=Float.parseFloat(substitute1);
                        }//end of if                       
                        else
                        {
                            attributeValue1=Float.parseFloat(records[randomNumber1][i]);                            
                            attributeValue2=Float.parseFloat(records[randomNumber2][i]);                            
                        }//end of else                        
                        if(attributeValue1<=attributeValue2)
                        {                    
                            if(initialPopulation[j]==null)
                            {
                                initialPopulation[j]=Float.toString(attributeValue1);//records[randomNumber1][i];
                            }//end of if
                            else
                            {
                                initialPopulation[j]=initialPopulation[j].concat(Float.toString(attributeValue1));
                            }//end of else
                            initialPopulation[j]=initialPopulation[j].concat(",");                    
                            initialPopulation[j]=initialPopulation[j].concat(Float.toString(attributeValue2));
                            initialPopulation[j]=initialPopulation[j].concat("|"); 
                        }//end of if                        
                        else
                        {
                            if(initialPopulation[j]==null)
                            {
                                initialPopulation[j]=Float.toString(attributeValue2);//records[randomNumber2][i];
                            }//end of if
                            else
                            {
                                initialPopulation[j]=initialPopulation[j].concat(Float.toString(attributeValue2));
                            }//end of else
                            initialPopulation[j]=initialPopulation[j].concat(",");                    
                            initialPopulation[j]=initialPopulation[j].concat(Float.toString(attributeValue1));
                            initialPopulation[j]=initialPopulation[j].concat("|"); 
                        }//end of else  
                    }//end of if
                    /******************************************************************************************************
                    * For "String" type of attributes we will take two values seperated by * if two records contains 
                     * different values, format value1*value2. If two records contains same value, we will take that value
                    ******************************************************************************************************/
                    else if(typeOfAttribute.equals("String"))
                    {                        
                        String attributeValue1=records[randomNumber1][i];                        
                        String attributeValue2=records[randomNumber2][i];                        
                        if(attributeValue1.equals("?")&attributeValue2.equals("?"))
                        {
                            String allValues=valuesOfAttributes[i];
                            
                            int counter1=0;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter1++;                    
                                }//end of if
                            }//end of for                            
                            int randomNo2=(int)(Math.random()*counter1);                             
                            int startIndex3=0;
                            int endIndex3=0;   
                            int counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo2)
                                    {                 
                                        startIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for
                            counter3=-1;
                            for(int counter=0;counter<=allValues.length()-1;counter++)
                            {
                                if(allValues.charAt(counter)==',')
                                {
                                    counter3++;  
                                    if(counter3==randomNo2+1)
                                    {                 
                                        endIndex3=counter;
                                        break;
                                    }//end of if
                                }//end of if
                            }//end of for                            
                            String substitute="";
                            if(randomNo2==(counter1-1))
                            {
                                substitute=substitute.concat(allValues.substring(startIndex3+1));                                
                            }//end of if
                            else
                            {
                                substitute=substitute.concat(allValues.substring(startIndex3+1,endIndex3));                                
                            }//end of else                   
                            if(initialPopulation[j]==null)
                            {
                                initialPopulation[j]=substitute;
                            }//end of if
                            else
                            {
                                initialPopulation[j]=initialPopulation[j].concat(substitute);
                            }//end of else                            
                        }//end of if
                        else if(attributeValue1.equals("?")&!attributeValue2.equals("?"))
                        {
                            if(initialPopulation[j]==null)
                            {
                                initialPopulation[j]=attributeValue2;
                            }//end of if
                            else
                            {
                                initialPopulation[j]=initialPopulation[j].concat(attributeValue2);
                            }//end of else
                        }//end of if
                        else if(!attributeValue1.equals("?")&attributeValue2.equals("?"))
                        {
                            if(initialPopulation[j]==null)
                            {
                                initialPopulation[j]=attributeValue1;
                            }//end of if
                            else
                            {
                                initialPopulation[j]=initialPopulation[j].concat(attributeValue1);
                            }//end of else
                        }//end of if
                        else if(!attributeValue1.equals("?")&!attributeValue2.equals("?"))
                        {
                            if(attributeValue1.equals(attributeValue2))
                            {
                                if(initialPopulation[j]==null)
                                {
                                    initialPopulation[j]=attributeValue1;
                                }//end of if
                                else
                                {
                                    initialPopulation[j]=initialPopulation[j].concat(attributeValue1);                                    
                                }//end of else
                            }//end of if
                            else
                            {
                                if(initialPopulation[j]==null)
                                {
                                    initialPopulation[j]=attributeValue1;
                                }//end of if
                                else
                                {
                                    initialPopulation[j]=initialPopulation[j].concat(attributeValue1);
                                    initialPopulation[j]=initialPopulation[j].concat("*");                    
                                    initialPopulation[j]=initialPopulation[j].concat(attributeValue2);
                                }//end of else
                            }//end of if
                        }//end of if                      
                        initialPopulation[j]=initialPopulation[j].concat("|");             
                    }//end of if
                }//end of for                
                j++;
            }//end of if            
        }while(j<=populationSize-1);       
        return initialPopulation;
    }//end of buildInitialPopulation   
}//end of buildInitialPopulation