
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author wasim-pc
 */
public class Nodes 
{
    double input;
    double output;
    double bais;
    double error;
    int hidden_nodes;
    Nodes()
    {
    
    }
    /*******************************************************************************************************
     *                     Activation function(hyperbolic tangent function)                                *
     *******************************************************************************************************/
    double Hyperbolic_tangent(double x) 
    {   
        return (double)((1.0 - Math.exp(-(2*x)))/(1.0 + Math.exp(-(2*x))));
    }
    
    
    
}
