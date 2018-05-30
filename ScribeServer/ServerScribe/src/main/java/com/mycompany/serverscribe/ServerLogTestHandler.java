/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import k3v1n1k88.TestLogScribe.Item;
import k3v1n1k88.TestLogScribe.Result;
import k3v1n1k88.TestLogScribe.Status;
import k3v1n1k88.TestLogScribe.TPlayer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class ServerLogTestHandler implements k3v1n1k88.TestLogScribe.LogTest.Iface {

    private static TPlayer mPlayer = null;
    
    private static final Log logInit = LogFactory.getLog(ServerLogTestHandler.class + " Init");
    private static final Log logLogin = LogFactory.getLog(ServerLogTestHandler.class + " Login");
    private static final Log logLogout = LogFactory.getLog(ServerLogTestHandler.class + " Logout");
    private static final Log logChangeItem = LogFactory.getLog(ServerLogTestHandler.class + " ChangeItem");
    private static final Log logSendLog = LogFactory.getLog(ServerLogTestHandler.class + " SendLog ");
    private static final Log logGetStatus = LogFactory.getLog(ServerLogTestHandler.class + " GetStatus");
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
    private static Calendar calendar = Calendar.getInstance();
    @Override
    public Result init(TPlayer player) throws TException {
        if (mPlayer != null) {
            logInit.info(dateFormat.format(calendar.getTime())+":"
                    +"Fail");
            return Result.FAIL;
        }
        mPlayer = player;
        logInit.debug(dateFormat.format(calendar.getTime())+":\n"
                + "Catch init:"+"\n"
                + "PlayerID: " + String.valueOf(player.playerID)+"\n"
                + "Status: " + String.valueOf(player.currentStatus)+"\n"
                + "Current Item: " + String.valueOf(player.currentItem)
        );
        return Result.SUCCESS;
    }

    @Override
    public Result logIn(int playerID) throws TException {
        if (mPlayer == null) {
            logLogin.info(dateFormat.format(calendar.getTime())+":"
                    +"Fail");
            return Result.FAIL;
        }
        if (mPlayer.getPlayerID() == playerID) {
            logLogin.info(dateFormat.format(calendar.getTime())+":"
                    +"Fail");
            return Result.FAIL;
        }

        mPlayer.currentStatus = Status.Login;

        logLogin.info(dateFormat.format(calendar.getTime())+":"
                +"Catch login:" + "\n"
                + "PlayerID: " + String.valueOf(playerID)
                + "Status: " + String.valueOf(1)
        );
        return Result.SUCCESS;
    }

    @Override
    public Result logOut(int playerID) throws TException {
        if (mPlayer == null) {
            logLogin.info(dateFormat.format(calendar.getTime())+":"
                    +"Fail");
            return Result.FAIL;
        }
        if (mPlayer.getPlayerID() != playerID) {
            logLogin.info(dateFormat.format(calendar.getTime())+":"
                    +"Fail");
            return Result.FAIL;
        }
        return Result.SUCCESS;
    }

    @Override
    public Result changeItem(Item Item) throws TException {
        if (mPlayer == null) {
            logChangeItem.info(dateFormat.format(calendar.getTime())+":"
                    +"Fail");
            return Result.FAIL;
        }
        mPlayer.setCurrentItem(Item);
        logChangeItem.info(dateFormat.format(calendar.getTime())+":"
                +"Success change item to"+String.valueOf(Item));
        return Result.SUCCESS;
    }

    @Override
    public Result sendLog(String time, String err) throws TException {
        logSendLog.info(dateFormat.format(calendar.getTime())+":"
                +time+":"+err);
        return Result.SUCCESS;
    }

    @Override
    public TPlayer getStatusPlayer() throws TException {
        if (mPlayer == null) {
            logGetStatus.info(dateFormat.format(calendar.getTime())+":"
                    +"Fail");
            return null;
        }
        TPlayer player = new TPlayer(mPlayer);
        logGetStatus.info(dateFormat.format(calendar.getTime())+":"
                +"Success");
        return player;
    }

}
