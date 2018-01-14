
import java.util.*;
import java.lang.Math;
import java.text.DecimalFormat;


public class BackPropagation 
{
    InitialNodes initial_nodes[];
    Nodes hidden_nodes[];
    Nodes output_nodes[];
    double hidden_nodes_weight[][];
    double output_nodes_weight[][];
    double output_node_error[];
    double hidden_node_error[];
    double output_node_bais[];
    double hidden_node_bais[];
    double testing_hidden_bias[];
    double testing_output_bais[];
    double po_delta=0,pob_delta=0,ph_delta=0,phb_delta=0;
    
    
    
    /* --------------------------------BackPropagation class construstor------------------------------------------------*/                                
       
    BackPropagation(Dataset training_DataSet,/*Dataset testing_DataSet*/Chromosome chromo,int initial_hidden_nodes)                                 
    {   //chromo.Show_chromosome();
        //System.out.println();
        //System.out.println("-----------------------------------");
        //int initial_hidden_nodes=(training_DataSet.noOfAttributes-1)*training_DataSet.noOfClasses;
        //chromo.Show_chromosome();  
        //System.out.println(initial_hidden_nodes);
        //calling create_initial_node function
        initial_nodes =create_initial_node(training_DataSet);
        //chromo.Show_chromosome();
        //System.out.println();
        //calling create_hidden_node function
        hidden_nodes =create_hidden_node(training_DataSet,initial_hidden_nodes);
        //chromo.Show_chromosome();
        //System.out.println();
        //calling create_output_node function
        output_nodes =create_output_node(training_DataSet);
        //chromo.Show_chromosome();
        //System.out.println();
        //calling create_input_hidden_weight_matrix function
        create_input_hidden_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
        //chromo.Show_chromosome();
        //System.out.println();
        //calling create_hidden_output_weight_matrix function
        create_hidden_output_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
        //chromo.Show_chromosome();
        //System.out.println();
        //calling bais function
        bais(training_DataSet,chromo,initial_hidden_nodes);
        //chromo.Show_chromosome();
        //System.out.println();
        //calling training fuction
        train_bpn (training_DataSet,initial_hidden_nodes);
        //chromo.Show_chromosome();
        //System.out.println();
        //calling removing unneccessary weights
        Removing_unnecessary_weight(training_DataSet,/*testing_DataSet*/initial_hidden_nodes,chromo);
        //chromo.Show_chromosome();
        //System.out.println();
        //System.out.println("-----------------------------------------------------");
        

    }    

    /*****************************************************************************************************                                                                                                   *
    *      This method is used to create number of input layer nodes                                     *                              *
    ******************************************************************************************************/      
    public InitialNodes[] create_initial_node(Dataset training_DataSet)
    {
        //allocating input nodes
        InitialNodes nodes[] = new InitialNodes[training_DataSet.noOfAttributes-1]; 
        //creating input nodes
        for (int i=0; i < training_DataSet.noOfAttributes -1; i++ )
        {
            nodes[i]=new InitialNodes();
        }
        return nodes;
    }
             
    /*******************************************************************************************************
     *                 This method is used to create number of hidden layer nodes                          *
     *******************************************************************************************************/            
     public Nodes[] create_hidden_node(Dataset training_DataSet,int initial_hidden_nodes)
    {
        //allocating hidden nodes
        Nodes nodes[] = new Nodes[initial_hidden_nodes];
        //creating hidden nodes
        for (int i=0;i<initial_hidden_nodes;i++ )
        {
            nodes[i]=new Nodes();
        }   
        return nodes;           
    }

    /*******************************************************************************************************
     *                          This method is to create no of output layer nodes                          *
     *******************************************************************************************************/
                           
    public  Nodes[] create_output_node(Dataset training_DataSet)
    {
        //allocating output nodes
        Nodes nodes[] = new Nodes[training_DataSet.noOfClasses];
        //creating output nodes
        for (int i=0; i <= training_DataSet.noOfClasses-1; i++)
        {
            nodes[i]=new Nodes();
        }
        return nodes;
    }
    /*******************************************************************************************************
     *                          This method is to initialize all nodes                                     *
     *******************************************************************************************************/
    void node_initialization(Dataset training_DataSet,int initial_hidden_nodes)
    {
        //initialization of input nodes
        for (int i=0; i < training_DataSet.noOfAttributes -1; i++ )
        {
            initial_nodes[i].input=0.0;
            initial_nodes[i].output=0.0;
        }
        //initialization of hidden nodes
        for (int i=0;i<initial_hidden_nodes;i++ )
        {
            hidden_nodes[i].input=0.0;
            hidden_nodes[i].output=0.0;
            hidden_nodes[i].error=0.0;
        }
        //initialization of output nodes
        for (int i=0; i <= training_DataSet.noOfClasses-1; i++)
        {
            output_nodes[i].input=0.0;
            output_nodes[i].output=0.0;
            output_nodes[i].error=0.0;
        }
    }
    /*******************************************************************************************************
     *               This method is to create input to hidden layer weight matrix                          *
     *******************************************************************************************************/            
    public  void create_input_hidden_weight_matrix(Dataset training_DataSet,Chromosome chromo,int initial_hidden_nodes)
    {
        //allocating input to hidden layer weight matrix
        hidden_nodes_weight = new double[training_DataSet.noOfAttributes-1][initial_hidden_nodes]; 
        for(int i=0; i< training_DataSet.noOfAttributes-1 ; i++)
        {
            for(int j =0; j< initial_hidden_nodes; j++)
            {
                //creating and storing random weight between -0.1 to 0.1 into weight matrix 
                hidden_nodes_weight[i][j]= chromo.gene[i*initial_hidden_nodes+j].gene_value;
            }
        }
    }
    
    /*******************************************************************************************************
     *                This method is to create hidden to output layer weight matrix                        *
     *******************************************************************************************************/        
    public void create_hidden_output_weight_matrix(Dataset training_DataSet,Chromosome chromo,int initial_hidden_nodes)
    {
        //allocating hidden to output layer weight matrix
        output_nodes_weight=new double[initial_hidden_nodes][training_DataSet.noOfClasses];  
        int counter = (initial_hidden_nodes*(training_DataSet.noOfAttributes-1));
        for(int i=0;i<initial_hidden_nodes;i++)
        {
            for(int j=0; j< training_DataSet.noOfClasses;j++)
            {
                //System.out.println(counter);
                output_nodes_weight[i][j]= chromo.gene[counter].gene_value;
                counter++;
            }
        }
    }
   /*******************************************************************************************************
     *                This method is to create hidden to output node bais                                 *
     *******************************************************************************************************/
   public void bais(Dataset training_DataSet,Chromosome chromo,int initial_hidden_nodes)
   {
        output_node_bais=new double[training_DataSet.noOfClasses];
        hidden_node_bais=new double[initial_hidden_nodes];
        for(int counter=0;counter<initial_hidden_nodes;counter++)
        {
            hidden_node_bais[counter]=0.0;
        }
        for(int counter=0;counter<training_DataSet.noOfClasses;counter++)
        {
            output_node_bais[counter]=0.0;
        }
        int hidden_bais=0;    
        for(int counter=(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses);counter<(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes;counter++)
        {     
            hidden_node_bais[hidden_bais]=chromo.gene[counter].gene_value;
            hidden_bais++;
        }
        int output_bais=0;
        for(int counter=(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes; counter< (training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes+training_DataSet.noOfClasses;counter++)
        {    
            output_node_bais[output_bais]=chromo.gene[counter].gene_value;
            output_bais++;
            
        }
    }
    /*******************************************************************************************************
     *                This method is to train the neural network                                           *
     *******************************************************************************************************/           
    public void train_bpn (Dataset training_DataSet,int initial_hidden_nodes)  
    {   
        int fired_output_node_number;
        for(int c_epoch=0;c_epoch<10;c_epoch++)
        {   
            //random number generator between 0 to no of records without redundency
            Unique_random u_r=new Unique_random(training_DataSet.noOfRecords);
            double tcounter=0;
            for(int i=0;i<training_DataSet.noOfRecords;i++)
            {
                node_initialization(training_DataSet,initial_hidden_nodes);
                int x=u_r.d[i];
                //System.out.println(i+"-"+x);// for training
                calculating_output_of_hidden_layer(training_DataSet,x,initial_hidden_nodes);
                calculating_output_of_output_layer(training_DataSet,initial_hidden_nodes);
                fired_output_node_number=calculated_output(training_DataSet);
                if(training_DataSet.classLevels[fired_output_node_number].equals(training_DataSet.dataSet[x][training_DataSet.noOfAttributes-1]))
                {
                    tcounter++;
                }
                else
                {
                    calculating_error(training_DataSet,x,initial_hidden_nodes);
                    weight_updation(training_DataSet,initial_hidden_nodes); 
                }
            }
        }
    }
    /*******************************************************************************************************
     *                This method is to calculate the output of hidden layer nodes                        *
     *******************************************************************************************************/
    public void calculating_output_of_hidden_layer(Dataset training_DataSet,int i,int initial_hidden_nodes)
    {
        int p=0;
        
        for(int q=0;q<training_DataSet.noOfAttributes-1;q++)
        {
             
        
            if(Double.parseDouble(training_DataSet.maxValues[p])<Double.parseDouble(training_DataSet.maxValues[q]))
            {
                p=q;
            }
            
        }
        
         int max=   Integer.toString((int)Double.parseDouble(training_DataSet.maxValues[p])).length();
         //System.out.println(max);
        if( max > 2)
        {
            
        for(int k=0; k<initial_hidden_nodes;k++)
        {
            for(int j=0;j< training_DataSet.noOfAttributes-1;j++)
            {
                //initializing hidden nodes
                initial_nodes[j].input=Double.parseDouble(training_DataSet.normalizedDataSet[i][j]);
                //System.out.println(i+"="+training_DataSet.dataSet[i][j]);//for training
                initial_nodes[j].output=initial_nodes[j].input;            
                hidden_nodes[k].input+=(initial_nodes[j].output*hidden_nodes_weight[j][k]);
            }
            hidden_nodes[k].input+=hidden_node_bais[k];
            //System.out.println(hidden_nodes[k].input);//for training
            hidden_nodes[k].output=hidden_nodes[k].Hyperbolic_tangent(hidden_nodes[k].input);//calculating output of hidden nodes
        }
        }
       else
        {
            //System.out.println("hello");
           for(int k=0; k<initial_hidden_nodes;k++)
        {
            for(int j=0;j< training_DataSet.noOfAttributes-1;j++)
            {
                //initializing hidden nodes
                initial_nodes[j].input=Double.parseDouble(training_DataSet.dataSet[i][j]);
                //System.out.println(i+"="+training_DataSet.dataSet[i][j]);//for training
                initial_nodes[j].output=initial_nodes[j].input;            
                hidden_nodes[k].input+=(initial_nodes[j].output*hidden_nodes_weight[j][k]);
            }
            hidden_nodes[k].input+=hidden_node_bais[k];
            //System.out.println(hidden_nodes[k].input);//for training
            hidden_nodes[k].output=hidden_nodes[k].Hyperbolic_tangent(hidden_nodes[k].input);//calculating output of hidden nodes
        } 
        }
    }
    /*******************************************************************************************************
     *                This method is to calculate the output of output layer nodes                         *
     *******************************************************************************************************/
    public void calculating_output_of_output_layer(Dataset training_DataSet,int initial_hidden_nodes)
    {
        for(int l=0;l<training_DataSet.noOfClasses;l++)
        {
            for(int k =0; k <initial_hidden_nodes; k++)
            {
                output_nodes[l].input+=(hidden_nodes[k].output*output_nodes_weight[k][l]);
            }
            output_nodes[l].input+=output_node_bais[l];
            //calculating output of output nodes
            output_nodes[l].output=output_nodes[l].Hyperbolic_tangent(output_nodes[l].input);
        } 
    }
    /*******************************************************************************************************
     *                This method is to find the fired node                                                *
     *******************************************************************************************************/
    public int calculated_output(Dataset training_DataSet)
    {
        int max=0;
        int present_node=1;
        while( present_node < training_DataSet.noOfClasses)
        {
            if(output_nodes[max].output<output_nodes[present_node].output)
            {
                max=present_node;
            }
            present_node++;
        }
        return max;       
    }
    /*******************************************************************************************************
     *                This method is to calculate the error of the network                                 *
     *******************************************************************************************************/
    public void calculating_error(Dataset training_DataSet, int i,int initial_hidden_nodes)
    {
        double error;
        output_node_error=new double[training_DataSet.noOfClasses];
        hidden_node_error=new double[initial_hidden_nodes];
        // error in output layer
        for(int e=0;e<training_DataSet.noOfClasses;e++)
        {
            //derivative of hypertangant =(1.0 + output_nodes[e].output) * (1.0 - output_nodes[e].output)//for tsting
            if(training_DataSet.dataSet[i][training_DataSet.noOfAttributes-1].equals(training_DataSet.classLevels[e]) )
            {
                output_node_error[e] =((1.0 + output_nodes[e].output) * (1.0 - output_nodes[e].output) )* (1.0 - output_nodes[e].output );
            }
            else 
            {
                output_node_error[e] =((1.0 + output_nodes[e].output) * (1.0 - output_nodes[e].output) ) * (0.0 - output_nodes[e].output );
            }
        }
        //error in hidden layer
        for(int x=0;x<initial_hidden_nodes;x++)
        {
            error=0.0;
            for(int y=0;y<training_DataSet.noOfClasses;y++)
            {
                error+= output_node_error[y] * output_nodes_weight[x][y] ;
            }
            hidden_node_error[x]+= hidden_nodes[x].output * (1.0 - hidden_nodes[x].output) * error ;
        }
    }
    /*******************************************************************************************************
     *                This method is to update the weights                                                 *
     *******************************************************************************************************/
    public void weight_updation(Dataset training_DataSet,int initial_hidden_nodes)
    {
        double l_rate=0.04;
        double m=0.9;
        //o_delta=delta of output node   ob_delta=delta of ouput node bais
        //h_delta=delta of hidden node   hb_delta=delta of hidden node bais
        double o_delta,ob_delta,h_delta,hb_delta;
        //weight updation of ouput node
        for(int k =0; k < initial_hidden_nodes; k++)
        {
            for(int l=0;l<training_DataSet.noOfClasses;l++)
            {  
                o_delta=(double)(l_rate * output_node_error[l] * hidden_nodes[k].output);
                output_nodes_weight[k][l]+= o_delta;
                output_nodes_weight[k][l]+= m*po_delta;
                po_delta=o_delta;
            }
        }
        for(int l=0;l<training_DataSet.noOfClasses;l++)
        {
            ob_delta=(double)(l_rate * output_node_error[l]);
            output_node_bais[l]+= ob_delta;
            output_node_bais[l]+= m*pob_delta;
            pob_delta=ob_delta;
        }
        //weight updation of hidden nodes
        for(int l=0;l<training_DataSet.noOfAttributes-1;l++)
        {
            for(int k =0; k < initial_hidden_nodes; k++)
            {
                h_delta=(double)(l_rate * hidden_node_error[k] * initial_nodes[l].output);
                hidden_nodes_weight[l][k]+= h_delta ;
                hidden_nodes_weight[l][k]+= m*ph_delta;
                ph_delta=h_delta;
            }
        }
        for(int k =0; k < initial_hidden_nodes; k++)
        {
            hb_delta=l_rate * hidden_node_error[k];
            hidden_node_bais[k]+= hb_delta;
            hidden_node_bais[k]+= m*phb_delta;
            phb_delta=hb_delta;
        }
    }
    /*******************************************************************************************************
     *       This method is for testing the network using training dataset (use k-fold classification)     *
     *******************************************************************************************************/
    public double testing(Dataset training_DataSet,int initial_hidden_nodes)
    {
        int fired_output_node_number=0;
        int size =(training_DataSet.noOfRecords);
        //Unique_random u_r=new Unique_random(size);
        double counter=0;
        for(int x=0;x<size;x++)
        {
            node_initialization(training_DataSet,initial_hidden_nodes);
            //int x=u_r.d[i];
            calculating_output_of_hidden_layer(training_DataSet,x,initial_hidden_nodes);
            calculating_output_of_output_layer(training_DataSet,initial_hidden_nodes);
            fired_output_node_number=calculated_output(training_DataSet);
            if(training_DataSet.classLevels[fired_output_node_number].equals(training_DataSet.dataSet[x][training_DataSet.noOfAttributes-1]))
            {
                //System.out.println("no error");
                counter++;
            }
        }
        double accuracy=((counter/size)*100);
        //System.out.println("testing accuracy="+accuracy+"%");//for testing
        return accuracy;
    }
    /*******************************************************************************************************
     *       This method is for testing the network using testing dataset (use k-fold classification)        *
     *******************************************************************************************************/
    public double Classification(Dataset training_DataSet,Dataset testing_DataSet,Chromosome chromo,int initial_hidden_nodes)
    {
        //System.out.println("testing----------------------------------------------");
        //chromo.Show_chromosome();
        //System.out.println();
        //System.out.println("testing----------------------------------------------");
        create_input_hidden_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
        create_hidden_output_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
        bais(training_DataSet,chromo,initial_hidden_nodes);
        int fired_output_node_number=0;
        double counter=0;
        for(int loop_counter=0;loop_counter<testing_DataSet.noOfRecords;loop_counter++)
        {
           
            node_initialization(training_DataSet,initial_hidden_nodes);
            calculating_output_of_hidden_layer(testing_DataSet,loop_counter,initial_hidden_nodes);
            calculating_output_of_output_layer(training_DataSet,initial_hidden_nodes);
            fired_output_node_number=calculated_output(training_DataSet);
            //System.out.println(fired_output_node_number);
            //System.out.println(training_DataSet.classLevels[fired_output_node_number]+""+testing_DataSet.dataSet[loop_counter][testing_DataSet.noOfAttributes-1]);
            if(training_DataSet.classLevels[fired_output_node_number].equals(testing_DataSet.dataSet[loop_counter][testing_DataSet.noOfAttributes-1]))
            {
                counter++;
            }
        }
        //System.out.println(counter+""+testing_DataSet.noOfRecords);
        double c_accuracy=((counter/testing_DataSet.noOfRecords)*100);
        //System.out.println("classification accuracy="+c_accuracy+"%");//for testing
        return c_accuracy;
    }
    /*******************************************************************************************************
     *       This method is to remove unnecessary weights range(0 to 0.001  or  -0 to -0.001)
     *       and update the chromosome weight  
     *******************************************************************************************************/ 
    public void Removing_unnecessary_weight(Dataset training_DataSet,/*Dataset testing_DataSet*/int initial_hidden_nodes,Chromosome chromo)
    {
        DecimalFormat df2 = new DecimalFormat("#.##");
        int counter=0;
        //int pop_size=chromo.pop_size;
        //removing unnecessary weight from input to hidden weight matrix
        for(int k=0; k<training_DataSet.noOfAttributes-1;k++)
        {
            for(int j=0;j<initial_hidden_nodes ;j++)
            {
                if(hidden_nodes_weight[k][j]>=0 && hidden_nodes_weight[k][j]<=0.01)
                {
                    hidden_nodes_weight[k][j]=0.00;
                    counter++;
                }
                else if(hidden_nodes_weight[k][j]<=0 && hidden_nodes_weight[k][j]>=-0.01)
                {
                    hidden_nodes_weight[k][j]=0.00;
                    counter++;
                }
            }
        }
        //removing unnecessary weight from hidden to output weight matrix
        int counter2 = (initial_hidden_nodes*(training_DataSet.noOfAttributes-1));
        for(int k =0; k < initial_hidden_nodes; k++)
        {
            for(int l=0;l<training_DataSet.noOfClasses;l++)
            {
                if(output_nodes_weight[k][l]>=0 & output_nodes_weight[k][l]<=0.01)
                {
                    output_nodes_weight[k][l]=0.00;
                    counter++;
                }
                else if(output_nodes_weight[k][l]<=0 & output_nodes_weight[k][l]>=-0.01)
                {
                    output_nodes_weight[k][l]=0.00;
                    counter++;
                }
                counter2++;
            }
        }
        //updating weight of chromosome in input to hidden weight matrix
        for(int i=0; i< training_DataSet.noOfAttributes-1 ; i++)
        {
            for(int j =0; j< initial_hidden_nodes; j++)
            {
                chromo.gene[i*initial_hidden_nodes+j].gene_value=hidden_nodes_weight[i][j] ;
            }
        }
        //updating weight of chromosome in hidden to ouput weight matrix
        int counter3 = (initial_hidden_nodes*(training_DataSet.noOfAttributes-1));
        for(int i=0;i<initial_hidden_nodes;i++)
        {
            for(int j=0; j< training_DataSet.noOfClasses;j++)
            {
                chromo.gene[counter3].gene_value=output_nodes_weight[i][j];
                counter3++;
            }
        }
        int hidden_bais=0;    
        for(int counter4=(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses);counter4<(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes;counter4++)
        {     
            chromo.gene[counter4].gene_value=hidden_node_bais[hidden_bais];
            hidden_bais++;
        }
        int output_bais=0;
        for(int counter4=(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes; counter4< (training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes+training_DataSet.noOfClasses;counter4++)
        {    
            chromo.gene[counter4].gene_value=output_node_bais[output_bais];
            output_bais++;
        }
        chromo.num_of_zero=counter;
        chromo.fitness=testing(training_DataSet,initial_hidden_nodes);
        //chromo.c_fitness=Classification(testing_DataSet,initial_hidden_nodes);
        //System.out.println(chromo.fitness+"="+chromo.num_of_zero);//for testing
    }
    /*this method is used for finding  testing accuracy using voting process*/
    public void pareto_voting(Dataset training_DataSet,Dataset testing_DataSet,Chromosome chromo,int initial_hidden_nodes)
    {
        //System.out.println("--------------------------------------");
        //System.out.println(initial_hidden_nodes);
        chromo.vote=new String[testing_DataSet.noOfRecords];
        chromo.target_output=new String[testing_DataSet.noOfRecords];
        create_input_hidden_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
        create_hidden_output_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
        bais(training_DataSet,chromo,initial_hidden_nodes);
        int fired_output_node_number=0;
        for(int loop_counter=0;loop_counter<testing_DataSet.noOfRecords;loop_counter++)
        {
            
            node_initialization(training_DataSet,initial_hidden_nodes);
            calculating_output_of_hidden_layer(testing_DataSet,loop_counter,initial_hidden_nodes);
            calculating_output_of_output_layer(training_DataSet,initial_hidden_nodes);
            fired_output_node_number=calculated_output(training_DataSet);
            //if(training_DataSet.classLevels[fired_output_node_number].equals(testing_DataSet.dataSet[loop_counter][testing_DataSet.noOfAttributes-1]))
            //{
              //  counter++;
            //}
            chromo.vote[loop_counter]=training_DataSet.classLevels[fired_output_node_number];
            chromo.target_output[loop_counter]=testing_DataSet.dataSet[loop_counter][testing_DataSet.noOfAttributes-1];
            //System.out.println();
            //System.out.println(chromo.vote[loop_counter]);
            //System.out.println(chromo.target_output[loop_counter]);
        }
            //System.out.println();
            //System.out.println(chromo.vote[0]);
            //System.out.println(chromo.target_output[0]);
                //System.out.println("classification accuracy="+c_accuracy+"%");//for testing
        
    }
    
    
    
    
}