export const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080/api';

export async function apiFetch(path: string, options: RequestInit = {}) {
  const token = localStorage.getItem('ch_access_token');

  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
  };

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(API_BASE + path, {
    ...options,
    headers: {
      ...headers,
      ...(options.headers || {}),
    },
    credentials: 'include', // important if you later move refresh token to cookies
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(`${response.status} ${response.statusText}: ${text}`);
  }

  if (response.status === 204) return null;
  return response.json();
}
