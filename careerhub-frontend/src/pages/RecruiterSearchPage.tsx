import { useState } from 'react';
import { searchCandidates, PublicProfile } from '../api/publicApi';

export default function RecruiterSearchPage(){
const [q,setQ] = useState('');
const [apiKey,setApiKey] = useState('');
const [results,setResults] = useState<PublicProfile[]>([]);

async function search(){
const data = await searchCandidates(q, apiKey);
setResults(data);
}

return (
<div>
<h2>Public Candidate Search</h2>
<input placeholder="API Key" value={apiKey} onChange={e=>setApiKey(e.target.value)} />
<input placeholder="Search skills" value={q} onChange={e=>setQ(e.target.value)} />
<button onClick={search}>Search</button>

<ul>
{results.map(c => (
<li key={c.id}>
<strong>{c.fullName}</strong>
<div>Skills: {c.skills.join(', ')}</div>
</li>
))}
</ul>
</div>
);
}
