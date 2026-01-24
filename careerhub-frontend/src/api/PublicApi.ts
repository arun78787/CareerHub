export type PublicProfile = {
id: number;
fullName: string;
skills: string[];
projects: { title:string; repoUrl?:string }[];
};

export async function searchCandidates(query: string, apiKey: string){
const res = await fetch(`http://localhost:8080/api/public/candidates?q=${query}`,{
headers: { 'X-API-KEY': apiKey }
});
if(!res.ok) throw new Error('Rate limited or unauthorized');
return res.json() as Promise<PublicProfile[]>;
}