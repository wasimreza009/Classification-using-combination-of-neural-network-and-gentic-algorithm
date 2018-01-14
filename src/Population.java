/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author wasim-pc
 */
public class Population 
{
    int pop_size;
    Chromosome chromo[];
    double max_fitness;
    int fit_chromo;
    int max_zero_chromo;
    int fit_c_chromo;
    int max_zero;
    int previous_geneation_pop_size;
    int new_generation_pop_size;
    //this method create new chromosome and gene for the population
    Population(Dataset training_DataSet,int population_size,int initial_hidden_nodes)
    {
       pop_size=population_size;
       //System.out.println(pop_size);
       chromo=new Chromosome[population_size];
       for(int counter=0;counter<=population_size-1;counter++)
       {
           chromo[counter]=new Chromosome(training_DataSet,initial_hidden_nodes);
       }
    }
    //this method shows the population
    public void Show_population()
    {
       for(int counter=0; counter<=pop_size-1;counter++)
       {
           chromo[counter].Show_chromosome();
           System.out.println();
       }
    }
   /*******************************************************************************************************
     *                This method finds the maximum fitness                                               *
     *******************************************************************************************************/
    public double max_fitness()
    {
        int max=0;
        int counter=0;
        while( counter < pop_size )
        {
            //System.out.println(chromo[counter].fitness[counter]);
            if(chromo[max].fitness < chromo[counter].fitness)
            {
               max=counter;
            }
            counter++;
        }
        //fit_chromo=max;
        max_fitness=chromo[max].fitness;
        //System.out.print("max fitness"+max_fitness);
        return max_fitness;
    }
    /*******************************************************************************************************
     *                This method finds the maximum classification fitness                                 *
     *******************************************************************************************************/
    public int max_fitness_chromo()
    {
        int max=0;
        int counter=0;
        while( counter < pop_size )
        {
            //System.out.println(chromo[counter].c_fitness[counter]);
            if(chromo[max].fitness < chromo[counter].fitness)
            {
               max=counter;
               fit_chromo=max;
            }
            counter++;
        }
        
        return fit_chromo;
    }
    //this method return the chromosome which have the highest number of chromosome 
    public int max_zero_chromo()
    {
        int max=0;
        int counter=0;
        while( counter < pop_size)
        {
            if(chromo[max].num_of_zero < chromo[counter].num_of_zero)
            {
               max=counter;
               max_zero_chromo=max;
            }
            counter++;
        }
        //max_zero_chromo=max;
        return max_zero_chromo;
    }
    
    /*******************************************************************************************************
     *                This method finds the max number of zeros wheights in the population                 *
     *******************************************************************************************************/
    public void max_zero()
    {
        int max=0;
        int counter=0;
        while( counter < pop_size)
        {
            if(chromo[max].num_of_zero < chromo[counter].num_of_zero)
            {
               max=counter;
            }
            counter++;
        }
        System.out.print("\tmax nuber of zero"+chromo[max].num_of_zero);
        System.out.println();
    }
}
