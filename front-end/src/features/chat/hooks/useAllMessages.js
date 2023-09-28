import { useCallback, useEffect, useState } from 'react';
import {over} from 'stompjs';
import SockJS from 'sockjs-client';

export const useAllMessage = () => {
    const [connected, setConnected] = useState(false);
    const [stompClient, setStompClient] = useState(null);
    const [receivedPayload, setReceivedPayload] = useState([]);

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
            stompClient.subscribe('/topic/all-messages', onMessageReceived);
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

    const getAllMessages = useCallback(() => {
        if (connected) {
            stompClient.send("/app/all",{}, {})
        }
        
    }, [stompClient, connected])


    return {
        connected,
        receivedPayload,
        getAllMessages
    }
}