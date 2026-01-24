import { useEffect, useState } from 'react';
import { connectWS, disconnectWS } from '../services/ws';

export default function useNotifications(){
const [messages, setMessages] = useState<any[]>([]);

useEffect(()=>{
const client = connectWS((m:any)=> setMessages(prev => [m, ...prev]));
return ()=>{ disconnectWS(); };
},[]);

return { messages };
}
