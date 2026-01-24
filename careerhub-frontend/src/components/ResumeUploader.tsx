import React, { useState } from 'react';
import { uploadResumeWithProgress } from '../api/resumeUploadWithProgress';

export default function ResumeUploader({ onUploaded }:{ onUploaded?: (r:any)=>void }){
const [file, setFile] = useState<File | null>(null);
const [progress, setProgress] = useState(0);

async function submit(e: React.FormEvent){
e.preventDefault();
if(!file) return alert('Choose a PDF');

const fd = new FormData();
fd.append('file', file);

try{
const res = await uploadResumeWithProgress(fd, setProgress);
onUploaded && onUploaded(res);
alert('Upload complete');
setProgress(0);
}catch(err:any){ alert(err.message || 'Upload failed'); }
}

return (
<form onSubmit={submit}>
<input type="file" accept="application/pdf" onChange={e=>setFile(e.target.files?.[0]||null)} />
<button>Upload</button>
{progress > 0 && <progress value={progress} max={100} />}
</form>
);
}
