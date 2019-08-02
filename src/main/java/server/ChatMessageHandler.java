package server;

import static org.mockito.Matchers.startsWith;
import java.sql.Timestamp;
/**
 * Created by Java_1 on 01.08.2019.
 */
public class ChatMessageHandler {
    private String initialMessage;
    private Timestamp time;
    private CommandType type;
    String userName = "";
    public ChatMessageHandler(String message){
        time = new Timestamp(System.currentTimeMillis());
        boolean isSnd = message.startsWith("/snd");
        if(isSnd){
            type = CommandType.SND;
            initialMessage = message.substring(4);
        }
        else
            if(message.startsWith("/hist")){
                type = CommandType.HIST;
                initialMessage = "";
            }
            else
                if(message.startsWith("/child")) {
                type = CommandType.CHILD;
                userName =  message.substring(6);
                }
                else
                    type = CommandType.NONE;

    }

    public String toString(){
        if(type == CommandType.HIST){
            return MultithreadedServer.logger.getHistory();
        }
        return getInfoMessage();
    }

    public String getInfoMessage(){
        return userName + " : "+ time + " : " + initialMessage;
    }
    public CommandType getType(){
        return type;
    }
}
