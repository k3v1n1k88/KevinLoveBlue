package CreateClient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class define constant values
 *
 * @author k3v1n1k88
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
    }

    public enum Server {
        ABSTRACTNONBLOCKING,
        TSIMPLE,
        TTHREADPOOL,
        TSELECTOR,
    }

    public enum Operation {
        ADD,
        SUB,
        DIV,
        MUL,
    }
}
