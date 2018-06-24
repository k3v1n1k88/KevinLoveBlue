package Client;


import Client.ClientCalculator;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
public class ThoughtDisplay extends TimerTask{
    private static final Logger LOGGER = LoggerFactory.getLogger(ThoughtDisplay.class);
    @Override
    public void run() {
        System.out.println("Send: "+ClientCalculator.getNumsOfSend());
        System.out.println("Get:  "+ClientCalculator.getNumsOfGet());
        LOGGER.info("Throught rate :"+(ClientCalculator.getNumsOfGet()*1.0/ClientCalculator.getNumsOfSend())*100);
    }  
}
