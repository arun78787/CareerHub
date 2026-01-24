import { apiFetch } from './api';

export type AdminStats = {
userCount: number;
recruiterCount: number;
interviewCount: number;
topSkills: { skill:string; count:number }[];
};

export function getAdminStats(){
return apiFetch('/admin/stats') as Promise<AdminStats>;
}