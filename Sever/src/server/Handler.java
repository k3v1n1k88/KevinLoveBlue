/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class Handler implements Service.Iface {
    public static int request = 0;
    
    @Override
    public String ping() throws TException {
        request++;
        return "pong";
    }

    @Override
    public String getSomeThing() throws TException {
        request++;
        Random r = new Random();
        Integer a = r.nextInt()%1000;
        return a.toString();
    }

    @Override
    public int add(int num1, int num2) throws TApplicationException {
        request++;
        int sum = num1+num2;
        if (((num1&num2&~sum)|(~num1&~num2&sum))<0)
            throw new TApplicationException("Underflow");
        return sum;
    }

    @Override
    public int diff(int num1, int num2) throws TException {
        request++;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num1-num2;
    }

    @Override
    public int mutiple(int num1, int num2) throws TApplicationException {
        request++;
        int product= num1*num2;
        if(((~product&num1&num2)|(product&~num1&~num2))<0)
            throw new TApplicationException("Underlow...");
        return num1*num2;
    }

    @Override
    public int div(int num1, int num2) throws TApplicationException {
        request++;
        if(num2==0)
            throw new TApplicationException("Divide by zero");
        return num1/num2;
    }
    
}
