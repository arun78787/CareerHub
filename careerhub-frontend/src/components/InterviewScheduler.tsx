import React, { useState } from 'react';
import { scheduleInterview } from '../api/interviewsApi';
import { localToUtcIso } from '../utils/time';

export default function InterviewScheduler({ onScheduled }:{ onScheduled?: ()=>void }){
const [candidateId, setCandidateId] = useState('');
const [start, setStart] = useState('');

async function submit(e: React.FormEvent){
e.preventDefault();
if(!candidateId || !start) return alert('fill fields');

await scheduleInterview({
candidateId: Number(candidateId),
startTime: localToUtcIso(start),
});

alert('Interview scheduled (UTC-safe)');
onScheduled && onScheduled();
}

return (
<form onSubmit={submit}>
<input placeholder="Candidate ID" value={candidateId} onChange={e=>setCandidateId(e.target.value)} />
<input type="datetime-local" value={start} onChange={e=>setStart(e.target.value)} />
<button>Schedule</button>
</form>
);
}

