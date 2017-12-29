package messanger.handler;

import messanger.message.Message;

/**
 * Created by Artem on 28.12.2017.
 */
public class Messanger implements IMessanger {
    private IHandler mHandler;

    public Messanger(IHandler handler) {
        mHandler = handler;
    }

    @Override
    public void send(Message message) {
        mHandler.accept(message);
    }
}
