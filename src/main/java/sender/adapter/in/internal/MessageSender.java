package sender.adapter.in.internal;

import sender.adapter.in.dto.Message;

public interface MessageSender {

    void send(Message message);
}
