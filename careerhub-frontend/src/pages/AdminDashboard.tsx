import { useEffect, useState } from 'react';
import { getAdminStats, AdminStats } from '../api/adminApi';

export default function AdminDashboard(){
const [stats,setStats] = useState<AdminStats|null>(null);

useEffect(()=>{ (async()=>{ setStats(await getAdminStats()); })(); },[]);

if(!stats) return <p>Loading...</p>;

return (
<div>
<h2>Admin Dashboard</h2>
<p>Users: {stats.userCount}</p>
<p>Recruiters: {stats.recruiterCount}</p>
<p>Interviews: {stats.interviewCount}</p>

<h4>Top Skills</h4>
<ul>
{stats.topSkills.map(s => (
<li key={s.skill}>{s.skill} ({s.count})</li>
))}
</ul>
</div>
);
}
