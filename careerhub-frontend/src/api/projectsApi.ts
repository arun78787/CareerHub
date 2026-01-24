import { apiFetch } from './api';

export type Project = {
  id: number;
  title: string;
  description: string;
  repoUrl?: string;
  demoUrl?: string;
  techStack?: string[];
};

export type Page<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number; // current page
};

export function getMyProjects(page = 0, size = 5) {
  return apiFetch(`/projects/me?page=${page}&size=${size}`) as Promise<Page<Project>>;
}

export function createProject(project: Omit<Project, 'id'>) {
  return apiFetch('/projects', {
    method: 'POST',
    body: JSON.stringify(project),
  });
}