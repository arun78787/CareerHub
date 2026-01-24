import { apiFetch } from './api';

export type ResumeRecord = {
  id: number;
  userId: number;
  s3Key: string;
  parsedSkills?: string[];
  uploadedAt: string;
};
// Upload using multipart/form-data. Backend endpoints vary by implementation:
// - Option A (recommended by spec): POST /api/resumes (multipart)
// - Option B (used by quick starter): POST /api/resume/upload
export async function uploadResume(formData: FormData) {
  // If your backend expects /resumes
  try {
    const token = localStorage.getItem('ch_access_token');
    const res = await fetch((import.meta.env.VITE_API_BASE || 'http://localhost:8080/api') + '/resumes', {
      method: 'POST',
      body: formData,
      headers: token ? { Authorization: `Bearer ${token}` } : undefined,
      credentials: 'include',
    });

    if (!res.ok) throw new Error(await res.text());
    return res.json() as Promise<ResumeRecord>;
  } catch (err) {
    // fallback to old endpoint used in starter
    const token = localStorage.getItem('ch_access_token');
    const res = await fetch((import.meta.env.VITE_API_BASE || 'http://localhost:8080/api') + '/resume/upload', {
      method: 'POST',
      body: formData,
      headers: token ? { Authorization: `Bearer ${token}` } : undefined,
      credentials: 'include',
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json() as Promise<ResumeRecord>;
  }
}

export function getMyResume() {
  return apiFetch('/resumes/mine').catch(() => apiFetch('/resume')).then((r) => r as ResumeRecord);
}

export function getSignedDownloadUrl(resumeId: number) {
  // backend should return { url: 'https://s3-presigned...' }
  return apiFetch(`/resumes/${resumeId}/download` as string).catch(() => apiFetch('/resume/download')) as Promise<{ url: string }>;
}