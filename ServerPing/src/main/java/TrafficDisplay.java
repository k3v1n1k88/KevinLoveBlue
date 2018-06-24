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
public class TrafficDisplay extends TimerTask{
    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficDisplay.class);
    @Override
    public void run() {
        LOGGER.info(Server.getNumsOfRequest()+" request/s");
        Server.setNumsOfRequest(0);
    }
}
