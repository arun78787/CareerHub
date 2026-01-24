
import React, { createContext, useContext, useEffect, useState } from 'react';
import { apiFetch } from '../api/api';

export type User = {
  id: number;
  email: string;
  fullName: string;
  role: 'USER' | 'RECRUITER' | 'ADMIN';
};

interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  loading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  // Try restoring session on page refresh
  useEffect(() => {
    async function loadMe() {
      try {
        const me = await apiFetch('/auth/me');
        setUser(me);
      } catch (err) {
        setUser(null);
      } finally {
        setLoading(false);
      }
    }

    loadMe();
  }, []);

  async function login(email: string, password: string) {
    const res = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password }),
    });

    if (!res.ok) throw new Error('Invalid credentials');

    const data = await res.json();
    // backend returns: { accessToken, refreshToken?, user }
    localStorage.setItem('ch_access_token', data.accessToken);
    setUser(data.user);
  }

  function logout() {
    localStorage.removeItem('ch_access_token');
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider');
  return ctx;
}