package messanger.handler;

import messanger.message.Message;

/**
 * Created by Артём on 28.12.2017.
 */
public interface IHandler {

    IMessanger getMessanger();

    void handle();

    void handle(Message message);

    void accept(Message message);

    void waitMessage();
}
