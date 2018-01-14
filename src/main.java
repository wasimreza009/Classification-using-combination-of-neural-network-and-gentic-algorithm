
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;



public class main 
{
    public static void main(String args[]) throws Exception
    {
        //for(int counter0=0;counter0<10;counter0++)
        //{
            
        //buildInitialPopulation DRFCR=new buildInitialPopulation();
        /*******************************************************************************************************
        *all dataset
        *****************************************************gg***************************************************/
        
        //String dataset="BREAST";
        //String dataset="CREDIT";
        String dataset="DIABETES";
        //String dataset="GLASS";
        //String dataset="HEART";
        //String dataset="IRIS";
        //String dataset="LABOR";
        //String dataset="SONAR";
        //String dataset="WINE";
        //String dataset="ZOO";
        //String dataset="LETTER";
        //String dataset="YEAST";
        //String dataset="AUTO";
        //String dataset="ANNEAL";
        
        System.out.println(dataset);//for testing
        //String pathOriginal="C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/files/"+dataset+".data";
        //FileUtil fileOriginal=new FileUtil(pathOriginal);        
        //String records[][]=DRFCR.buildTwoDimentionalArrayOfData(fileOriginal,",");
        //copyString CS=new copyString();
        //String records1[][]=CS.copyTwoDimentionalArray(records);
        //generateDataFiles GDF=new generateDataFiles();//calling constructar
        //String testRecords[][]=GDF.generateTestDataFiles(records,dataset);        
        //GDF.generateTrainingDataFiles(testRecords,dataset);
        
        
        
        File file = new File("C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/output/"+dataset+"output.txt");
        if (!file.exists()) 
        {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
       
        int population_size=20;
        int fold=10;
        double avg_training_accuracy=0;
        double avg_testing_accuracy=0;
        double voting_acc=0.00;
        double avg_voting_acc=0;
        double acc=0;
        System.out.println(dataset);//for testing
        //dataset path and attribute path
        for(int counter=1;counter<=fold;counter++)
        {
            System.out.println(counter+"fold-----------------------------------------------------------");
            String path_Training_DataSet="C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/files/"+dataset+"training1.data";
            String path_Testing_DataSet="C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/files/"+dataset+"test1.data";
            String path_Attribute_Information="C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/files/"+dataset+"Attribute.data";
            String dlimiter=",";
            //invoke DataSet constructor ---> class DataSet
            Dataset training_DataSet= new Dataset(path_Training_DataSet,dlimiter,path_Attribute_Information,dlimiter);
            Dataset testing_DataSet= new Dataset(path_Testing_DataSet,dlimiter,path_Attribute_Information,dlimiter);
            //Dataset training_DataSet= new Dataset(path_Training_DataSet,dlimiter,path_Attribute_Information,dlimiter);            
            //invoke BackPropagation constructor ---> class BackPropagation
            //BackPropagation bpn= new BackPropagation(training_DataSet);
            //calling train_bpn method -----> class BackPropagation
            //bpn.train_bpn(training_DataSet);
            float crossover_probability=(float)0.8;
            float mutation_probability=(float)0.1;
            int number_of_generation=100;
            int number_of_hidden_nodes=(training_DataSet.noOfAttributes-1)*(training_DataSet.noOfClasses);
            MOGA_ANN moga_ann= new MOGA_ANN(training_DataSet,testing_DataSet,population_size,crossover_probability,mutation_probability,number_of_generation,number_of_hidden_nodes);
            //System.out.println("population size="+moga_ann.pareto_pop.pop_size);
            //System.out.println("for testing-----------------------------------------------------------------");
            //moga_ann.pareto_pop.Show_population();
            double accuracy=moga_ann.pop_ANN.bpn[0].Classification(training_DataSet, testing_DataSet,moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.fit_chromo],(training_DataSet.noOfAttributes-1)*(training_DataSet.noOfClasses));
            
            /*******************************************************for testing*******************************************/ 
            /*for(int counter1=0;counter1<moga_ann.pareto_pop.pop_size;counter1++)
            {
                moga_ann.pop_ANN.bpn[counter1].pareto_voting(training_DataSet, testing_DataSet,moga_ann.pareto_pop.chromo[counter1], number_of_hidden_nodes);
            double accuracy=moga_ann.pop_ANN.bpn[counter1].Classification(training_DataSet, testing_DataSet,moga_ann.pareto_pop.chromo[counter1],(training_DataSet.noOfAttributes-1)*(training_DataSet.noOfClasses));
            System.out.print("training accuracy="+moga_ann.pareto_pop.chromo[counter1].fitness);
            
            System.out.print("\ttesting accuracy="+accuracy);
            System.out.print("\tno of zeros="+moga_ann.pareto_pop.chromo[counter1].num_of_zero);
            System.out.println();
            }*/
            
            
            //voting method
            for(int counter1=0;counter1<moga_ann.pareto_pop.pop_size;counter1++)
            {
                moga_ann.pop_ANN.bpn[counter1].pareto_voting(training_DataSet, testing_DataSet,moga_ann.pareto_pop.chromo[counter1],(training_DataSet.noOfAttributes-1)*(training_DataSet.noOfClasses));
            }
                acc=0.0;
                int vote_count[]=new int[training_DataSet.noOfClasses];
                String fired_nodes[]=new String[testing_DataSet.noOfRecords];
            
               
                for(int counter2=0;counter2< testing_DataSet.noOfRecords;counter2++)
                {
                    for(int counter3=0;counter3<training_DataSet.noOfClasses;counter3++)
                    {
                        int count=0;
                        for(int counter4=0;counter4<moga_ann.pareto_pop.pop_size;counter4++)
                        {
                            if(moga_ann.pareto_pop.chromo[counter4].vote[counter2].equals(training_DataSet.classLevels[counter3]))
                            {
                                count++;
                                //vote_count[counter3]=count;
                                //System.out.print(moga_ann.pareto_pop.chromo[counter4].vote[counter2]+"=");
                                //System.out.print(training_DataSet.classLevels[counter3]);
                                //System.out.println();
                            }
                        }
                        vote_count[counter3]=count;
                    }
                    
                    int max=0;
                    for(int counter5=0;counter5<training_DataSet.noOfClasses;counter5++)
                    {
                        //System.out.println(vote_count[counter5]);
                        if(vote_count[max]<vote_count[counter5])
                        {
                            max=counter5;
                        }
                    }
                    fired_nodes[counter2]=training_DataSet.classLevels[max];
                    //System.out.println(max);
                }
                for(int counter6=0;counter6<testing_DataSet.noOfRecords;counter6++)
                {
                    if(fired_nodes[counter6].equals(testing_DataSet.dataSet[counter6][training_DataSet.noOfAttributes-1]))
                    {
                        
                        acc++;

                    }
                }
                //System.out.println(acc);
                voting_acc=(acc/testing_DataSet.noOfRecords)*100;
                avg_voting_acc+=voting_acc;  
                avg_testing_accuracy+=accuracy;
                avg_training_accuracy+=moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.fit_chromo].fitness;
                
                bw.write("voting accuracy="+Double.toString(voting_acc));
                bw.write("\ttraining accuracy="+Double.toString(moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.fit_chromo].fitness));
                bw.write("\ttesting accuracy="+Double.toString(accuracy));
                bw.write("\tno of zeros="+Double.toString(moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.max_zero_chromo].num_of_zero));
		bw.newLine();
                        
                    System.out.print("training accuracy="+moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.fit_chromo].fitness);
                    System.out.print("\ttesting accuracy="+accuracy);
                    System.out.print("\tno of zeros="+moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.max_zero_chromo].num_of_zero);
                System.out.println();
        }
        System.out.println("avg testing accuracy"+avg_testing_accuracy/10);
        System.out.println("avg voting accuracy"+avg_voting_acc/10);
        bw.newLine();
        bw.write("avg training accuracy"+Double.toString(avg_training_accuracy/10));
        bw.newLine();
        bw.write("avg testing accuracy"+Double.toString(avg_testing_accuracy/10));
        bw.newLine();
        bw.write("avg voting accuracy"+Double.toString(avg_voting_acc/10));
        bw.close();
        }
        
    
       public static double Calculate_testing_accuracy(Dataset training_DataSet,Dataset testing_DataSet,Chromosome chromo)
        {
            double accuracy=0.0;
            
            //BackPropagation bpn=new BackPropagation(training_DataSet,chromo);
            //double training_accuracy=bpn.testing(training_DataSet,training_DataSet.noOfAttributes-1*training_DataSet.noOfClasses);
            //System.out.println("training accuracy 2="+training_accuracy);
            //accuracy= bpn.Classification(training_DataSet,testing_DataSet,training_DataSet.noOfAttributes-1*training_DataSet.noOfClasses);
            return accuracy;
            
        }

        //double y = (0.1*((Math.random()*2.0)-1.0));
        //double x = (0.1+((Math.random()*2.0)-1.0));
        //System.out.println(x+"___"+y);
        //System.out.println(( 0.9 - Math.exp(-(2*x)))/(0.9 + Math.exp(-(2*x))));
        //System.out.println(( Math.exp(-(2*x))-0.9)/(Math.exp(-(2*x))+0.9));
        //     System.out.println(test_DataSet);
    //   BackPropagation bpn= new BackPropagation();
      
        /********************************************************************************************************/
        /*********************************************^**********************************************************
        *calling buildTwoDimentionalArrayOfData method to build two dimentional array of records from Original.data file
        *******************************************************************************************************/
        
       //String resultForTrainingData[]=new String[36];
        //float confidenceForTrainingData[]=new float[10];
        //float coverageForTrainingData[]=new float[10];
        //int noOfCSRsForTrainingData[]=new int[10];

        //String resultForTestData[]=new String[36];
        //float confidenceForTestData[]=new float[10];
        //float coverageForTestData[]=new float[10];
        //int noOfCSRsForTestData[]=new int[10];

        //String rules[]=new String[10];
        //for(int number=0;number<=9;number++)//for ten fold CV
        //{           
          //System.out.println("Fold:"+number);//for testing
            //String path_Training_DataSet="C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/INPUT_FILES/Training.data";//for ten fold CV
            //String path_Test_DataSet="C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/files/Original.data";
            //String path_Attribute_Information="C:/Users/wasim-pc/Documents/NetBeansProjects/BPN/src/files/Attribute_information.data";
            //String dlimiter=",";
            //Dataset training_DataSet= new Dataset(path_Training_DataSet,dlimiter,path_Attribute_Information,dlimiter);            
            //String path_Test_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Test"+number+".data";//for ten fold CV
            //String path_Test_DataSet="C:/Users/Dipankar/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Original.data";//for 100% training and testing
            //Dataset test_DataSet= new Dataset(path_Test_DataSet,dlimiter,path_Attribute_Information,dlimiter);
        
            /*******************************************************************************************************
            *calling buildInitialPopulation method
            ********************************************************************************************************/
            /*float fractionOfTrainingData=(float)0.1;//fraction of training data used for building initial population            
            Population initialPopulation=new Population(training_DataSet,fractionOfTrainingData);
            //initialPopulation.showDnaOfPopulation();//for testing
            //System.out.println("____________________________");//for testing
            /*******************************************************************************************************
            *calling cromosomeForGA method
            ********************************************************************************************************/
           /* int numberOfGenerationofBPMOGA=1000;
            int numberOfGenerationofP1MOGA=50;
            int numberOfGenerationofP2MOGA=50;
            int sizeOfInitialPopulationOfP2MOGA=20;
            float minCrossProbP1=(float)0.3;
            float maxCrossProbP1=(float)0.5;
            float minMuProbP1=(float)0.1;
            float maxMuProbP1=(float)0.9;
            float minRuleProbP2=(float)0.5;
            float maxRuleProbP2=(float)0.5;
            float minCrossProbP2=(float)0.05;
            float maxCrossProbP2=(float)0.45;
            float minMuProbP2=(float)0.1;
            float maxMuProbP2=(float)0.2;
            biPhasedMOGA BPMOGA=new biPhasedMOGA(training_DataSet,initialPopulation,numberOfGenerationofBPMOGA,numberOfGenerationofP1MOGA,numberOfGenerationofP2MOGA,sizeOfInitialPopulationOfP2MOGA,fractionOfTrainingData,minCrossProbP1,maxCrossProbP1,minMuProbP1,maxMuProbP1,minRuleProbP2,maxRuleProbP2,minCrossProbP2,maxCrossProbP2,minMuProbP2,maxMuProbP2);
            //BPMOGA.ParetoCRsFromBPMA.showPopulationForP2();//for testing
            //accuracyChecking ACForTrainingData=new  accuracyChecking(training_DataSet,BPMOGA.ParetoCRsFromBPMA,training_DataSet);//constructor called
            confidenceForTrainingData[number]=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].totalConfidence;
            coverageForTrainingData[number]=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].totalCoverage;
            noOfCSRsForTrainingData[number]=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].numberOfCSRs;
            PopulationForP2 sortedPopulationFromBPMOGA=BPMOGA.ParetoCRsFromBPMA.sortingChromoOfP2();            
            accuracyChecking ACForTestData=new accuracyChecking(test_DataSet,sortedPopulationFromBPMOGA.chromoForP2[0],training_DataSet);//constructor called
            confidenceForTestData[number]=ACForTestData.confidenceOfTestDataSet;
            coverageForTestData[number]=ACForTestData.coverageOfTestDataSet;
            noOfCSRsForTestData[number]=ACForTestData.numberOfCSRs;
            rules[number]="";
            int ruleCounter=1;
            for(int loopCounter=0;loopCounter<=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.populationSize-1;loopCounter++)
            {
                if(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].DnaForP2.charAt(loopCounter)=='1')
                {
                    String rule="IF ".concat(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].Dna)
                            .concat(" THEN ").concat(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].classLabel)
                            .concat(" With Confidence=").concat(String.valueOf(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].confidence))
                            .concat(" and Coverage=").concat(String.valueOf(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].coverage));
                    //System.out.println(rule);//for testing
                    //System.out.println("____________________________");//for testing
                    rules[number]=rules[number].concat(String.valueOf(ruleCounter)).concat(":").concat(rule).concat("\n");
                    ruleCounter++;
                }//end of if                
            }//end of for
            //System.out.println(rules[number]);//for testing
            //System.out.println("____________________________");//for testing
        }//end of for
        float averageConfidenceFor10FoldForTrainingData=(float)0.0;
        float averageCoverageFor10FoldForTrainingData=(float)0.0;
        float averageCSRsFor10FoldForTrainingData=(float)0.0;
        float averageConfidenceFor10FoldForTestData=(float)0.0;
        float averageCoverageFor10FoldForTestData=(float)0.0;
        float averageCSRsFor10FoldForTestData=(float)0.0;
        int counter=0;
        resultForTrainingData[counter]="Confidence";
        resultForTrainingData[counter+12]="Coverage";
        resultForTrainingData[counter+24]="No of CSRs";
        resultForTestData[counter]="Confidence";
        resultForTestData[counter+12]="Coverage";
        resultForTestData[counter+24]="No of CSRs";
        counter++;
        for(int number=0;number<=9;number++)
        {
            averageConfidenceFor10FoldForTrainingData=averageConfidenceFor10FoldForTrainingData+confidenceForTrainingData[number];
            resultForTrainingData[counter]=/*"Fold  "+number+":  ".concat(*///String.valueOf(confidenceForTrainingData[number]*100)/*)*/;
            //System.out.println("resultForTrainingData["+counter+"]="+resultForTrainingData[counter]);//for testing
            /*averageCoverageFor10FoldForTrainingData=averageCoverageFor10FoldForTrainingData+coverageForTrainingData[number];
            resultForTrainingData[counter+12]=/*"Fold  "+number+":  ".concat(*///String.valueOf(coverageForTrainingData[number]*100)/*)*/;
            //System.out.println(result[counter+number+10]);//for testing
            /*averageCSRsFor10FoldForTrainingData=averageCSRsFor10FoldForTrainingData+noOfCSRsForTrainingData[number];
            resultForTrainingData[counter+24]=/*"Fold  "+number+":  ".concat(*///String.valueOf(noOfCSRsForTrainingData[number])/*)*/;
            //System.out.println(result[counter+number+20]);//for testing

            /*averageConfidenceFor10FoldForTestData=averageConfidenceFor10FoldForTestData+confidenceForTestData[number];
            resultForTestData[counter]=/*"Fold  "+number+":  ".concat(*///String.valueOf(confidenceForTestData[number]*100)/*)*/;
            //System.out.println(result[counter+number]);//for testing
            /*averageCoverageFor10FoldForTestData=averageCoverageFor10FoldForTestData+coverageForTestData[number];
            resultForTestData[counter+12]=/*"Fold  "+number+":  ".concat(*///String.valueOf(coverageForTestData[number]*100)/*)*/;
            //System.out.println(result[counter+number+10]);//for testing
            /*averageCSRsFor10FoldForTestData=averageCSRsFor10FoldForTestData+noOfCSRsForTestData[number];
            resultForTestData[counter+24]=/*"Fold  "+number+":  ".concat(*///String.valueOf(noOfCSRsForTestData[number])/*)*/;
            //System.out.println(result[counter+number+20]);//for testing
            /*counter++;
        }//end of for
        /*for(int number=0;number<=32;number++)
        {
            System.out.println(number+"      "+result[number]);//for testing
        }//end of for*/
       /* averageConfidenceFor10FoldForTrainingData=(float)averageConfidenceFor10FoldForTrainingData/10;
        averageCoverageFor10FoldForTrainingData=(float)averageCoverageFor10FoldForTrainingData/10;
        averageCSRsFor10FoldForTrainingData=(float)averageCSRsFor10FoldForTrainingData/10;
        resultForTrainingData[11]="Average Confidence:  ".concat(String.valueOf(averageConfidenceFor10FoldForTrainingData));
        resultForTrainingData[23]="Average Coverage:  ".concat(String.valueOf(averageCoverageFor10FoldForTrainingData));
        resultForTrainingData[35]="Average No of CSRs:  ".concat(String.valueOf(averageCSRsFor10FoldForTrainingData));
        averageConfidenceFor10FoldForTestData=(float)averageConfidenceFor10FoldForTestData/10;
        averageCoverageFor10FoldForTestData=(float)averageCoverageFor10FoldForTestData/10;
        averageCSRsFor10FoldForTestData=(float)averageCSRsFor10FoldForTestData/10;
        resultForTestData[11]="Average Confidence:  ".concat(String.valueOf(averageConfidenceFor10FoldForTestData));
        resultForTestData[23]="Average Coverage:  ".concat(String.valueOf(averageCoverageFor10FoldForTestData));
        resultForTestData[35]="Average No of CSRs:  ".concat(String.valueOf(averageCSRsFor10FoldForTestData));
        System.out.println("averageConfidenceFor10FoldForTrainingData="+averageConfidenceFor10FoldForTrainingData*100);//for testing
        System.out.println("averageCoverageFor10FoldForTrainingData="+averageCoverageFor10FoldForTrainingData*100);//for testing
        System.out.println("averageCSRsFor10FoldForTrainingData="+averageCSRsFor10FoldForTrainingData);//for testing
        System.out.println("averageConfidenceFor10FoldForTestData="+averageConfidenceFor10FoldForTestData*100);//for testing
        System.out.println("averageCoverageFor10FoldForTestData="+averageCoverageFor10FoldForTestData*100);//for testing
        System.out.println("averageCSRsFor10FoldForTestData="+averageCSRsFor10FoldForTestData);//for testing
        String pathResultForTrainingData="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/OUTPUT_FILES/ResultForTrainingData.data";
        String pathResultForTestData="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/OUTPUT_FILES/ResultForTestData.data";
        String pathRules="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/OUTPUT_FILES/Rules.data";
        generateResultFile GRFForTrainingData=new generateResultFile();
        generateResultFile GRFForTestData=new generateResultFile();
        generateResultFile GRFForRules=new generateResultFile();
        //result[0]=String.valueOf(averageConfidenceFor10Fold*100).concat("    ").concat(String.valueOf(averageCoverageFor10Fold*100)).concat("    ").concat(String.valueOf(averageCSRsFor10Fold));
        //result[1]=String.valueOf(averageCoverageFor10Fold*100);
        //result[2]=String.valueOf(averageCSRsFor10Fold);
        GRFForTrainingData.generateResultFile(resultForTrainingData, pathResultForTrainingData);
        GRFForTestData.generateResultFile(resultForTestData, pathResultForTestData);
        GRFForRules.generateResultFile(rules, pathRules);*/
    //end of main()
}//end of Repeat
