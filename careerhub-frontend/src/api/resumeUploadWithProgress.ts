export function uploadResumeWithProgress(
formData: FormData,
onProgress: (percent: number) => void
) {
return new Promise<any>((resolve, reject) => {
const xhr = new XMLHttpRequest();
const token = localStorage.getItem('ch_access_token');

xhr.open('POST', (import.meta.env.VITE_API_BASE || 'http://localhost:8080/api') + '/resumes');
if (token) xhr.setRequestHeader('Authorization', `Bearer ${token}`);

xhr.upload.onprogress = (e) => {
if (e.lengthComputable) {
const percent = Math.round((e.loaded / e.total) * 100);
onProgress(percent);
}
};

xhr.onload = () => {
if (xhr.status >= 200 && xhr.status < 300) {
resolve(JSON.parse(xhr.responseText));
} else {
reject(new Error(xhr.responseText));
}
};

xhr.onerror = () => reject(new Error('Network error'));
xhr.send(formData);
});
}

