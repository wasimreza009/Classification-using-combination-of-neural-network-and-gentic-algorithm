/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author wasim-pc
 */
import java.text.DecimalFormat;
import java.util.*;
import java.*;
//import static main.Calculate_testing_accuracy;
public class MOGA_ANN 
{
    public Population pareto_pop;
    Population_of_ANN pop_ANN;
    
    
    MOGA_ANN(Dataset training_DataSet,Dataset testing_DataSet,int population_size,float crossover_probability,float mutation_probability,int number_of_generation,int initial_hidden_nodes)
    {
        //System.out.println("population before crossover");//for testing
        Population pop=Create_initial_population_generation(training_DataSet, population_size,initial_hidden_nodes);
        //pop.Show_population();//for testing
        pareto_pop=pop;
        for(int generation=0;generation<number_of_generation;generation++)
        {
            //System.out.println("generation:"+counter+"------------------------------------------------------");
            if(generation%2!=0 ||pareto_pop.pop_size<2 )
            {
                pop=Create_initial_population_generation(training_DataSet, population_size,initial_hidden_nodes);
            }
            else if(generation%2==0 & generation!=0)
            {
               pop=pareto_pop;
            }
            //pop.Show_population();
            Population pop_after_crossover= Crossover(training_DataSet,pop,crossover_probability,initial_hidden_nodes);
            //System.out.println("pop after crossover");
            //pop.Show_population();
            //pop_after_crossover.Show_population();//for testing
            //System.out.println("population after mutation");
            //pop.Show_population();
            Population pop_after_mutation=Mutation(training_DataSet,pop,mutation_probability,initial_hidden_nodes);
            //System.out.println("pop after mutation");
            //pop_after_mutation.Show_population();
            //pop_after_mutation.Show_population();
            //System.out.println("combine population");
            Population combination_population=Combination_population(training_DataSet,pop,pop_after_crossover,pop_after_mutation,initial_hidden_nodes);
            //combination_population.Show_population();
            //System.out.println("unique population");
            //Population unique_population=Unique_population(combination_population,training_DataSet,initial_hidden_nodes);
            //calling the population ANN method
            //combination_population.Show_population();
            //System.out.println("after bpn population");
            
            
            
            
             pop_ANN=new Population_of_ANN(combination_population,training_DataSet,testing_DataSet,initial_hidden_nodes);
            //System.out.println("after bpn population");
            //combination_population.Show_population();
            //combination_population.max_fitness();
            //combination_population.max_zero();
            //System.out.println("entering unique population");
            Population unique_population=Unique_population(pareto_pop,combination_population,training_DataSet,initial_hidden_nodes);
            //unique_population.max_fitness();
            //unique_population.max_c_fitness();
            //unique_population.max_zero();
            //System.out.println("before pareto");
            //unique_population.Show_population();
            //calling the pareto front method
            pareto_pop = Pareto_front(unique_population,training_DataSet,initial_hidden_nodes,pareto_pop,generation);
            //System.out.println("max fitness after pareto");
            //pareto_pop.Show_population();
            //pareto_pop.max_fitness();
            //pareto_pop.max_c_fitness();
            //pareto_pop.max_zero();
           //if(generation==number_of_generation-1)
            //{
            //System.out.println("testing");
            //pareto_pop.Show_population();
                
                
            //}
                   //}
               
          System.out.print("generation="+generation);
         //System.out.println();
              //for(int i=0;i<pareto_pop.pop_size;i++)
             // {
                  //  System.out.println("accuracy="+pareto_pop.chromo[i].fitness+"\t"+"no of zero="+pareto_pop.chromo[i].num_of_zero);
              //  }System.out.println();
              
                System.out.print("\taccuracy="+pareto_pop.chromo[pareto_pop.fit_chromo].fitness+"\t"+"no of zero="+pareto_pop.chromo[pareto_pop.max_zero_chromo].num_of_zero);
                //pareto_pop.Show_population();
          System.out.println();
          
        }   
    }
    /*******************************************************************************************************
     *                This method is to create initial population                                          *
     *******************************************************************************************************/ 
    public Population Create_initial_population_generation (Dataset training_DataSet, int population_size,int initial_hidden_nodes) 
    {
        Population pop=new Population(training_DataSet,population_size,initial_hidden_nodes);
        return pop;
    }
    /*******************************************************************************************************
     *                              This method is for crossover                                           *
     *******************************************************************************************************/ 
    public Population Crossover(Dataset training_DataSet,Population pop,float crossover_probability,int initial_hidden_nodes)
    {
        Population pop_after_crossover=new Population(training_DataSet,pop.pop_size,initial_hidden_nodes);
        int number_of_crossover=(int)(pop.pop_size*crossover_probability*0.5);
        boolean flag[]=new boolean[pop.pop_size];
        for (int counter=0;counter<=pop.pop_size-1;counter++)
        {
            flag[counter]=true;
        }
        for (int counter=0;counter<=number_of_crossover-1;counter++)
        {
            int random1=(int)(Math.random()*pop.pop_size);
            int random2=(int)(Math.random()*pop.pop_size);
            //System.out.println(random1);//for testing
            //System.out.println(random2);//for testing
            while(random1==random2|flag[random1]==false|flag[random2]==false)
            {
                random1=(int)(Math.random()*pop.pop_size);
                random2=(int)(Math.random()*pop.pop_size);
            }
            String Dna_String_of_parent1=pop.chromo[random1].Dna_string;
            String Dna_String_of_parent2=pop.chromo[random2].Dna_string;
            flag[random1]=false;
            flag[random2]=false;
            //System.out.println("parent1"+Dna_String_of_parent1);//for testing
            //System.out.println("parent2"+Dna_String_of_parent2);//for testing
            int random3=(int)(Math.random()*pop.chromo[random1].Dna.length);//random3 is the crossover point
            //System.out.println(random3);//for testing
            String child_Dna1[]=new String[pop.chromo[random1].Dna.length];
            String child_Dna2[]=new String[pop.chromo[random2].Dna.length];
            for (int loop_counter=0;loop_counter<=pop.chromo[random1].Dna.length-1;loop_counter++)
            {
                if(loop_counter<=random3)
                    child_Dna1[loop_counter]=pop.chromo[random1].Dna[loop_counter];
                else
                    child_Dna2[loop_counter]=pop.chromo[random1].Dna[loop_counter];
            }
            for (int loop_counter=0;loop_counter<=pop.chromo[random2].Dna.length-1;loop_counter++)
            {
                if(loop_counter<=random3)
                    child_Dna2[loop_counter]=pop.chromo[random2].Dna[loop_counter];
                else
                    child_Dna1[loop_counter]=pop.chromo[random2].Dna[loop_counter];
            }
            Chromosome child_chromosome1=new Chromosome(training_DataSet,initial_hidden_nodes,child_Dna1);
            Chromosome child_chromosome2=new Chromosome(training_DataSet,initial_hidden_nodes,child_Dna2);
            pop_after_crossover.chromo[random1]=child_chromosome1;
            pop_after_crossover.chromo[random2]=child_chromosome2;
        }
        //insert the rest of the chromosome in the population 
        for(int cromo_number=0;cromo_number<pop.pop_size;cromo_number++)
        {
            if(flag[cromo_number]==true)
            {
                pop_after_crossover.chromo[cromo_number]=pop.chromo[cromo_number];
            }
        }
        return pop_after_crossover;
    }
    /*******************************************************************************************************
     *                This method is for mutation                                            *
     *******************************************************************************************************/
    public Population Mutation(Dataset training_DataSet,Population pop,float mutation_probability,int initial_hidden_nodes)
    {
        Population pop_after_mutation=new Population(training_DataSet,pop.pop_size,initial_hidden_nodes);
        for (int counter=0;counter<=pop_after_mutation.pop_size-1;counter++)
        {
            for(int counter1=0;counter1<pop.chromo[0].gene.length;counter1++)
            {
                pop_after_mutation.chromo[counter].gene[counter1].gene_value=pop.chromo[counter].gene[counter1].gene_value;
            }
        }
        for(int counter=0;counter<pop_after_mutation.pop_size;counter++)
        {
            for(int counter1=0;counter1<pop_after_mutation.chromo[0].gene.length;counter1++)
            {
                float random=(float)Math.random();
                if(random < mutation_probability)
                {
                    //System.out.println("chromo"+counter+"gene"+counter1);//for testing
                    pop_after_mutation.chromo[counter].gene[counter1].gene_value=(double)(((Math.random()*2.0)-1.0)*0.1);
                }
            }
        }
        return pop_after_mutation;
    }
    /*******************************************************************************************************
     *                This method is to combine initial, crossover, mutation population                    *
     *******************************************************************************************************/ 
    public Population Combination_population(Dataset training_DataSet,Population pop,Population pop_after_crossover,Population pop_after_mutation,int initial_hidden_nodes)
    {   
        int combination_population_size =pop.pop_size+pop_after_crossover.pop_size+pop_after_mutation.pop_size;
        //System.out.println(combination_population_size);//for testing
        Population combination_population=new Population(training_DataSet,combination_population_size,initial_hidden_nodes);
        int c_pop=0;
        while(c_pop < combination_population_size)
        {
            for (int counter=0;counter<=pop.pop_size-1;counter++)
            {
                for(int counter1=0;counter1<pop.chromo[0].gene.length;counter1++)
                {
                combination_population.chromo[c_pop].gene[counter1].gene_value=pop.chromo[counter].gene[counter1].gene_value;
                }
                c_pop++;
            }
            for (int counter=0;counter<=pop_after_crossover.pop_size-1;counter++)
            {
                for(int counter1=0;counter1<pop_after_crossover.chromo[0].gene.length;counter1++)
                {
                combination_population.chromo[c_pop].gene[counter1].gene_value=pop_after_crossover.chromo[counter].gene[counter1].gene_value;
                }
                c_pop++;
            }
            for (int counter=0;counter<=pop_after_mutation.pop_size-1;counter++)
            {
                for(int counter1=0;counter1<pop_after_mutation.chromo[0].gene.length;counter1++)
                {
                combination_population.chromo[c_pop].gene[counter1].gene_value=pop_after_mutation.chromo[counter].gene[counter1].gene_value;
                }
                c_pop++;
            }
        }
        return combination_population;
    }
    /*******************************************************************************************************
     *                This method is to remove reduandent chromosome                                       *
     *******************************************************************************************************/
    public Population Unique_population(Population pareto_pop,Population combination_population,Dataset training_DataSet,int initial_hidden_nodes)
    {
        int flag_size=0;
        String[] unique_pop=new String[combination_population.pop_size];
        DecimalFormat df2 = new DecimalFormat("#.##");
        boolean[] flag=new boolean[combination_population.pop_size];
        for(int counter=0;counter<combination_population.pop_size;counter++)
        {
            flag[counter]=true;
        }
        for(int counter=0;counter<combination_population.pop_size;counter++)
        {
            for(int counter1=0;counter1<combination_population.chromo[1].gene.length;counter1++)
            {
                //unique_population.chromo[counter].gene[counter1].gene_value=Double.valueOf(df2.format(combination_population.chromo[counter].gene[counter1].gene_value));
                combination_population.chromo[counter].Dna[counter1]=String.valueOf(/*Double.valueOf*/(df2.format(combination_population.chromo[counter].gene[counter1].gene_value))/*unique_population.chromo[counter].gene[counter1].gene_value*/);
            }
            unique_pop[counter]=String.join("|", combination_population.chromo[counter].Dna);
        }
        for(int counter=0;counter< combination_population.pop_size-1;counter++)
        {
            for(int counter1=counter+1;counter1<combination_population.pop_size;counter1++)
            {
                if((unique_pop[counter]).equals(unique_pop[counter1]))
                {
                   flag[counter1]=false;
                   //System.out.println(counter+"=="+counter1);
                }
            }
        }
        for(int counter=0;counter<combination_population.pop_size;counter++)
        {
            if(flag[counter]==true)
            {
                //System.out.println(counter);
                flag_size++;
            }
        }
        int u_pop=flag_size+pareto_pop.previous_geneation_pop_size;
        Population unique_population=new Population(training_DataSet,u_pop,initial_hidden_nodes);
        int counter1=0;
        int counter2=0;
        int loc=0;
        while(counter2<unique_population.pop_size)
        {
            if(counter2 >= flag_size)
            {
                //unique_population.chromo[counter2].c_fitness=combination_population.chromo[counter1].c_fitness;
                unique_population.chromo[counter2].fitness=pareto_pop.chromo[counter1].fitness;
                unique_population.chromo[counter2].num_of_zero=pareto_pop.chromo[counter1].num_of_zero;
                for(int counter4=0;counter4<pareto_pop.chromo[0].gene.length;counter4++)
                {
                    unique_population.chromo[counter2].gene[counter4].gene_value=pareto_pop.chromo[counter1].gene[counter4].gene_value;
                }
                //System.out.println(counter2+"="+unique_population.chromo[counter2].fitness[counter2]);
                counter1++;
            }
            for(int counter=loc;counter<combination_population.pop_size;counter++)
            {
                if(flag[counter]==true)
                {
                    //unique_population.chromo[counter2].c_fitness=combination_population.chromo[counter].c_fitness;
                    unique_population.chromo[counter2].fitness=combination_population.chromo[counter].fitness;
                    unique_population.chromo[counter2].num_of_zero=combination_population.chromo[counter].num_of_zero;
                    for(int counter4=0;counter4<combination_population.chromo[0].gene.length;counter4++)
                    {
                        unique_population.chromo[counter2].gene[counter4].gene_value=combination_population.chromo[counter].gene[counter4].gene_value;
                    }
                    //System.out.println("chromosome="+""+counter2+":fitness="+unique_population.chromo[counter2].fitness+" num of zero="+unique_population.chromo[counter2].num_of_zero);
                    loc=counter+1;
                    break;
                }
            }
            counter2++;
        }
        return unique_population;
    }
    /*******************************************************************************************************
     *                This method is for pareto selection from unique population                           *
     *******************************************************************************************************/
    public Population Pareto_front(Population unique_population,Dataset training_DataSet,int initial_hidden_nodes,Population pareto_pop,int generation)
    {
        boolean[] flag=new boolean[unique_population.pop_size];
        for(int counter=0;counter<unique_population.pop_size;counter++)
        {
            //System.out.print(unique_population.chromo[counter].fitness);
            //System.out.print("no of zero"+unique_population.chromo[counter].num_of_zero);
            //System.out.println();
            flag[counter]=true;
        }
        for(int outerloop=0;outerloop<unique_population.pop_size;outerloop++)
        {
            for(int innerloop=0;innerloop<unique_population.pop_size;innerloop++)
            {
                
                if((unique_population.chromo[outerloop].fitness<unique_population.chromo[innerloop].fitness&&unique_population.chromo[outerloop].num_of_zero<unique_population.chromo[innerloop].num_of_zero)
                    ||(unique_population.chromo[outerloop].fitness<unique_population.chromo[innerloop].fitness&&unique_population.chromo[outerloop].num_of_zero==unique_population.chromo[innerloop].num_of_zero)
                    ||(unique_population.chromo[outerloop].fitness==unique_population.chromo[innerloop].fitness&&unique_population.chromo[outerloop].num_of_zero<unique_population.chromo[innerloop].num_of_zero))
                {
                    flag[outerloop]=false;
                    break;
                }
            }
        }
        int pareto_pop_size=0;
        //System.out.println("after pareto");
        for(int counter=0;counter<unique_population.pop_size;counter++)
        {
            if(flag[counter])
            {   //System.out.print(unique_population.chromo[counter].fitness);
                //System.out.print("no of zero"+unique_population.chromo[counter].num_of_zero);
                //System.out.println();
                pareto_pop_size++;
            }
        }
        
        Population pareto_selection=new Population(training_DataSet,pareto_pop_size,initial_hidden_nodes);
        int counter4=0;
        for(int loopcounter=0;loopcounter<unique_population.pop_size;loopcounter++)
        {
            if(flag[loopcounter]==true)
            {
                pareto_selection.chromo[counter4]=unique_population.chromo[loopcounter];
                
                if(generation==9)
                {
                    //System.out.println("no of zero="+pareto_selection.chromo[counter].num_of_zero);
                //System.out.println(pareto_selection.chromo[counter4].fitness+"\t"+pareto_selection.chromo[counter4].num_of_zero);
            
                //System.out.println(counter+"="+loopcounter);
                }
                //pareto_selection.chromo[counter].num_of_zero=unique_population.chromo[loopcounter].num_of_zero;
                counter4++;
            }
        }    
        pareto_selection.previous_geneation_pop_size=pareto_pop_size;
        //pareto_pop=pareto_selection;
        pareto_selection.max_fitness_chromo();
        pareto_selection.max_zero_chromo();
        return pareto_selection;   
    }
    
}
    
    
    

