package server;

import java.sql.Timestamp;
/**
 * Created by Java_1 on 01.08.2019.
 */
public class ChatMessageHandler {
    private boolean commandType;
    private String initialMessage;
    private Timestamp time;
    public ChatMessageHandler(String message){
        time = new Timestamp(System.currentTimeMillis());
        boolean isSnd = message.startsWith("/snd");
        if(isSnd){
            commandType = false;
            initialMessage = message.substring(4);
        }
        else
            if(message.startsWith("/hist")){
                commandType = true;
                initialMessage = message.substring(5);
            }
    }
    public String getInfoMessage(){
        return time + " : " + initialMessage;
    }
}