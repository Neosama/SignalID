package com.tortillum.salome.window;

/**
 * Created by aalle on 19/07/2017.
 */

public class Hamming {
    public static double[] toHammingWindow(double[] values,int windowSize)
    {
        double[] hammingWindow=new double[windowSize];
        double[] newFrames=new double[values.length];
        for(int i=0;i<windowSize;i++)
        {
            hammingWindow[i]=0.54-0.46*Math.cos(2*Math.PI*i/(windowSize-1));
            //System.out.println("Hamming window "+i+": "+hammingWindow[i]);
        }
        double sumHamming=0;
        for(int i=0;i<windowSize;i++)
        {
            sumHamming+=hammingWindow[i];
        }
        //System.out.println(sumHamming);
        for(int i=0;i<windowSize;i++)
        {
            hammingWindow[i]=hammingWindow[i]/windowSize;
            //System.out.println("Hamming window "+i+": "+hammingWindow[i]);
        }
        for(int i=0;i<values.length;i++)
        {
            //System.out.println("Incomming value: "+values[i]);
            //System.out.println("Old frame: "+fileFrames[i]);
            /*for(int j=0;j<windowSize;j++)
            {
                int diff=i-j;
                double sampleFrame;
                if(diff<0)
                    sampleFrame=0;
                else
                    sampleFrame=values[diff];
                newFrames[i]+=sampleFrame*hammingWindow[j]*2;
            }
            //System.out.println("New Frames (before clip check)"+i+": "+newFrames[i]);
            if(newFrames[i]>=1)
                newFrames[i]=0.99;
            else if (newFrames[i]<=-1)
                newFrames[i]=-0.99;*/
            //System.out.println("New Frames "+i+": "+newFrames[i]);
            newFrames[i]=(values[i]*hammingWindow[i%windowSize]);
        }
        return newFrames;
    }
    public static double getHammingSquareSum(int windowSize)
    {
        double hammingSquareSum=0;
        for(int i=0;i<windowSize;i++)
        {
            hammingSquareSum+=Math.pow(0.54-0.46*Math.cos(2*Math.PI*i/(windowSize-1)),2);
            //System.out.println("Hamming window "+i+": "+hammingWindow[i]);
        }
        return hammingSquareSum;
    }
}
