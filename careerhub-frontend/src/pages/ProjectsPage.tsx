import { useEffect, useState } from 'react';
import { createProject, getMyProjects, Project } from '../api/projectsApi';

export default function ProjectsPage() {
  const [projects, setProjects] = useState<Project[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  useEffect(() => {
    loadProjects(page);
  }, [page]);

  async function loadProjects(p: number) {
    const res = await getMyProjects(p);
    setProjects(res.content);
    setTotalPages(res.totalPages);
  }

  async function handleCreate(e: React.FormEvent) {
    e.preventDefault();

    await createProject({ title, description });
    setTitle('');
    setDescription('');
    loadProjects(0);
  }

  return (
    <div>
      <h2>My Projects</h2>

      {/* Create Project */}
      <form onSubmit={handleCreate}>
        <input
          placeholder="Title"
          value={title}
          onChange={e => setTitle(e.target.value)}
        />
        <input
          placeholder="Description"
          value={description}
          onChange={e => setDescription(e.target.value)}
        />
        <button>Create</button>
      </form>

      <hr />

      {/* Project List */}
      <ul>
        {projects.map(p => (
          <li key={p.id}>
            <strong>{p.title}</strong> — {p.description}
          </li>
        ))}
      </ul>

      {/* Pagination */}
      <div>
        <button disabled={page === 0} onClick={() => setPage(p => p - 1)}>
          Prev
        </button>
        <span> Page {page + 1} of {totalPages} </span>
        <button disabled={page + 1 >= totalPages} onClick={() => setPage(p => p + 1)}>
          Next
        </button>
      </div>
    </div>
  );
}
