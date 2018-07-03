package CreateServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author root
 */
public class Type {

    public enum Protocol {
        TBINARY,
        TCOMPACT,
        TJSON,
        TSIMPLEJSON,
    }

    public enum Transport {
        TFASTFRAMED,
        TFRAMED,
        THTTPCLIEN,
        TMEMORYBUFFER,
        TFILE,
        TSOCKET,
        TNONBLOCKING
    }

    public enum Server {
        TSIMPLE,
        TTHREADPOOL,
        TTHREADEDSELECTOR,
        TNONBLOCKING,
        THSHA
    }
}
