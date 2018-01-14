/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author wasim-pc
 */
public class Population_of_ANN 
{
    int population_size_of_Ann;
    BackPropagation bpn[];
    //BackPropagation1 bpn[];
   Population_of_ANN(Population combination_population,Dataset training_DataSet,Dataset testing_DataSet,int initial_hidden_nodes)
   {
       
       population_size_of_Ann=combination_population.pop_size;
       bpn=new BackPropagation[combination_population.pop_size];
       
       //invoking backpropogation class
       //System.out.println("entering bpn");
       for (int counter=0;counter<=combination_population.pop_size-1;counter++)
        {
            bpn[counter]=new BackPropagation(training_DataSet,/*testing_DataSet*/combination_population.chromo[counter],initial_hidden_nodes);
        }
       
    
    }
  
   
}

