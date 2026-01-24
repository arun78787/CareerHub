import React, { useEffect, useState } from 'react';
import ResumeUploader from '../components/ResumeUploader';
import { getMyResume, getSignedDownloadUrl } from '../api/resumeApi';

export default function ResumePage(){
  const [resume, setResume] = useState<any>(null);

  useEffect(()=>{ (async ()=>{ try{ const r = await getMyResume(); setResume(r); }catch(e){} })(); },[]);

  async function handleUploaded(r:any){ setResume(r); }

  async function download(){
    if(!resume) return;
    try{
      const signed = await getSignedDownloadUrl(resume.id);
      if(signed?.url) window.open(signed.url, '_blank');
      else alert('No download url returned');
    }catch(err:any){ alert('Download failed: '+(err.message||String(err))); }
  }

  return (
    <div style={{padding:16}}>
      <h2>Resume</h2>
      <ResumeUploader onUploaded={handleUploaded} />

      {resume && (
        <div style={{marginTop:12}}>
          <p>Uploaded at: {new Date(resume.uploadedAt).toLocaleString()}</p>
          <p>Parsed skills: {resume.parsedSkills?.join(', ')}</p>
          <button onClick={download}>Download (signed url)</button>
        </div>
      )}
    </div>
  );
}
