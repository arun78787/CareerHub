import React, { useEffect, useState } from 'react';
import { getMyInterviews, cancelInterview } from '../api/interviewsApi';
import InterviewScheduler from '../components/InterviewScheduler';

export default function InterviewsPage(){
const [list, setList] = useState<any[]>([]);

async function load(){ try{ const data = await getMyInterviews(); setList(data); }catch(e){} }
useEffect(()=>{ load(); },[]);

async function handleCancel(id:number){ try{ await cancelInterview(id); load(); }catch(e:any){ alert('Cancel failed: '+(e.message||String(e))); } }

return (
<div style={{padding:16}}>
<h2>Interviews</h2>
<InterviewScheduler onScheduled={load} />

<ul>
{list.map(i=> (
<li key={i.id}>
{i.organizerName || ('organizer:'+i.organizerId)} — {new Date(i.startTime).toLocaleString()} — {i.status}
{i.status === 'SCHEDULED' && <button onClick={()=>handleCancel(i.id)}>Cancel</button>}
</li>
))}
</ul>
</div>
);
}