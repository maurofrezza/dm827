import java.io.IOException;

import net.tinyos.message.*;
import net.tinyos.packet.*;
import net.tinyos.util.*;

import java.util.ArrayList;

public class Sink implements MessageListener {
    static Window w;
    static ArrayList list;

    private MoteIF moteIF;

    public Sink(MoteIF moteIF) {
        this.moteIF = moteIF;
        this.moteIF.registerListener(new AggregateMsg(), this);
    }

    public int rssiToDbm(int rssi){
        return rssi - 45; //45 might not be correct...
    }

    public void messageReceived(int to, Message message) {
        AggregateMsg msg = (AggregateMsg)message;
        if(msg.get_from() < 3 && msg.get_from() >= 0) {
            ((Mote)list.get( msg.get_from())).dist = 128 + msg.getElement_motes_rssi(0);
        } else {
            System.out.println("" + msg.get_from());
        }
        w.drawMotes(list);

        System.out.println(
                "From=" + msg.get_from() + 
                " msgNr=" + msg.get_counter() + 
                " id=" + msg.getElement_motes_id(0) + 
                " count=" + msg.getElement_motes_count(0) +
                " rssi=" + msg.getElement_motes_rssi(0));
    }

    private static void usage() {
        System.err.println("usage: Sink [-comm <source>]");
    }

    public static void main(String[] args) throws Exception {
        list = new ArrayList();
        list.add(new Mote(100,100,100));
        list.add(new Mote(100,50,100));
        list.add(new Mote(50,50,100));
        w = new Window();

        String source = null;
        if (args.length == 2) {
            if (!args[0].equals("-comm")) {
                usage();
                System.exit(1);
            }
            source = args[1];
        }
        else if (args.length != 0) {
            usage();
            System.exit(1);
        }

        PhoenixSource phoenix;

        if (source == null) {
            phoenix = BuildSource.makePhoenix(PrintStreamMessenger.err);
        }
        else {
            phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
        }

        MoteIF mif = new MoteIF(phoenix);
        Sink sink = new Sink(mif);
    }
}
