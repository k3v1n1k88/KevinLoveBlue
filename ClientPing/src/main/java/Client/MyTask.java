package Client;


import Client.ClientCalculator;
import CreateClient.Type;
import Service2.Calculator;
import Service2.CalculatorException;
import java.util.Random;
import java.util.logging.Level;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class is created for tasking running
 *
 * @author k3v1n1k88
 */
public class MyTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyTask.class);
    private Type.Operation mOp;
    private Calculator.Client mClient;
    int num1, num2;

    public MyTask(Calculator.Client client) {
        super();
        mClient = client;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            calculate();
        }
    }

    void calculate() {
        Random random = new Random();
        int num1 = random.nextInt();
        int num2 = random.nextInt();
        int temp = random.nextInt();
        int pos = temp % 4;
        pos = pos >= 0 ? pos : pos + 3;
        Type.Operation op = Type.Operation.values()[pos];
        int res = 0;
        ClientCalculator.increaseNumsOfSend();
        try {
            switch (op) {
                case ADD:
                    
                    res = mClient.add(num1, num2);
                    //System.out.println("Result add:"+ res);
                    break;
                case SUB:
                    res = mClient.subtract(num1, num2);
                    //System.out.println("Result sub:"+ res);
                    break;
                case DIV:
                    res = mClient.div(num1, num2);
                    //System.out.println("Result div:"+ res);
                    break;
                case MUL:
                    res = mClient.mutiple(num1, num2);
                    //System.out.println("Result multiple:"+ res);
                    break;
                default:
                    break;
            }
            ClientCalculator.increaseNumsOfGet();
            //LOGGER.info("Perform {}: num1 = {} num2 = {}",op.toString(),num1,num2);

        } catch (TTransportException ex) {
            LOGGER.error("Error transport: " + ex);
        } catch (CalculatorException ex) {
            LOGGER.error("Error when calculating:" + ex);
        } catch (TApplicationException ex) {
            //LOGGER.error("Error application: "+ex);
        } catch (TException ex) {
            LOGGER.error("Error: " + ex);
        }
    }
}
