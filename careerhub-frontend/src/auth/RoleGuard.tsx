import { useAuth } from './AuthContext';

export default function RoleGuard({ role, children }:{ role: 'ADMIN'|'RECRUITER'|'USER'; children: JSX.Element }){
const { user } = useAuth();
if(!user) return null;
if(user.role !== role) return null;
return children;
}
