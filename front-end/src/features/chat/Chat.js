import React from 'react';
import { useSendMessage } from './hooks/useSendMessage';
import { useAllMessage } from './hooks/useAllMessages';

export const Chat = () => {
    const {
        connected,
        sendMessage
    } = useSendMessage();
    const {
        getAllMessages,
        receivedPayload
    } = useAllMessage();

    return (
        <div style={{ display: 'flex', flexDirection: 'column-reverse'}}>
            {connected ? 'Connected' : 'Not connected'}
            <button onClick={() => {sendMessage("Hello World");getAllMessages();} }>Send Hello World!</button>
            {
                receivedPayload && (
                    receivedPayload.map(message => (
                        <div>
                        <span>{message.content}</span>
                        <span>{message.sender}</span>
                        </div>
                    ))
                )
            }
        </div>
    )
}