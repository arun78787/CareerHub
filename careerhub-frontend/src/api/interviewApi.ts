import { apiFetch } from './api';

export type Interview = {
id: number;
organizerId: number;
candidateId: number;
startTime: string;
endTime: string;
status: 'SCHEDULED'|'CANCELLED'|'COMPLETED';
};

export function scheduleInterview(payload: { candidateId:number; startTime:string; endTime?:string; organizerName?:string }){
return apiFetch('/interviews', { method: 'POST', body: JSON.stringify(payload) }) as Promise<Interview>;
}

export function getMyInterviews(){
return apiFetch('/interviews') as Promise<Interview[]>;
}

export function cancelInterview(id: number){
return apiFetch(`/interviews/${id}/cancel`, { method: 'POST' });
}