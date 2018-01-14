
import java.text.DecimalFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author wasim-pc
 */
public class Gene 
{
    double gene_value;
    DecimalFormat df2 = new DecimalFormat("#.##");
    /*******************************************************************************************************
     *                This constructor is to generate random numbers range between(-0.1 to 0.1)            *
     *******************************************************************************************************/
    Gene()
    {
        gene_value=(double)(((Math.random()*2.0)-1.0)*0.1);
        
    }
    Gene(String gene_value1)
    {
        //System.out.println(gene_value1);
        gene_value=(double)Double.parseDouble(gene_value1);
        //System.out.println(gene_value);
    }
    /*******************************************************************************************************
     *                This method shows the gene value                                                     *
     *******************************************************************************************************/
    public void Show_gene()
    {
        
        System.out.print(df2.format(gene_value));
    }
}
