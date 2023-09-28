import { useCallback, useEffect, useState } from 'react';
import {over} from 'stompjs';
import SockJS from 'sockjs-client';

export const useSendMessage = () => {
    const [connected, setConnected] = useState(false);
    const [stompClient, setStompClient] = useState(null);
    const [receivedPayload, setReceivedPayload] = useState(null);

    useEffect(() => {
        const initializeWebSocket = () => {
            let sock = new SockJS('http://localhost:8082/send');
            let stompClient = over(sock);
            if (stompClient) {
                stompClient.connect({}, () => {
                    setConnected(true);
                    setStompClient(stompClient);
                }, onError);
            }
        };

        initializeWebSocket();
    }, []);

    useEffect(() => {
        if (stompClient) {
            stompClient.subscribe('/topic/messages', onMessageReceived);
        }
    }, [stompClient]);

    const onError = () => {
        console.log('WebSocket connection error');
    }

    const onMessageReceived = (payload) => {
        console.log(payload)
        const payloadData = JSON.parse(payload.body);
        console.log(payloadData)
        setReceivedPayload(payloadData);
    }

    const sendMessage = useCallback((message) => {
        if (connected) {
            const requestBody = {
                sender: "Yassine",
                content: message
            }
            stompClient.send("/app/send",{}, JSON.stringify(requestBody))
        }
        
    }, [stompClient, connected])


    return {
        connected,
        receivedPayload,
        sendMessage
    }
}