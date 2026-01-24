import SockJS from 'sockjs-client';
import { Client, Message } from '@stomp/stompjs';

let client: Client | null = null;

export function connectWS(onMessage: (msg:any)=>void){
if(client && client.active) return client;
const base = (import.meta.env.VITE_API_BASE || 'http://localhost:8080/api').replace('/api','');
const socket = new SockJS(base + '/ws');
client = new Client({
webSocketFactory: () => socket as any,
reconnectDelay: 5000,
onConnect: () => {
// subscribe to user queue
client?.subscribe('/user/queue/notifications', (m: Message) => {
try{ onMessage(JSON.parse(m.body)); }catch(e){ onMessage(m.body); }
});
},
onStompError: (frame) => {
console.error('STOMP error', frame);
}
});
client.activate();
return client;
}

export function disconnectWS(){ client?.deactivate(); client = null; }
