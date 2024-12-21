import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';

const NewPost = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [body, setBody] = useState('');

    const handleCreate = async () => {
        try {
            await api.post('/blog/new', { title, body });
            navigate('/blog');
        } catch (error) {
            console.error('Error creating post:', error);
        }
    };

    return (
        <div>
            <h2>Crear Nuevo Post</h2>
            <label>
                Título:
                <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
            </label>
            <label>
                Cuerpo:
                <textarea value={body} onChange={(e) => setBody(e.target.value)} />
            </label>
            <button onClick={handleCreate}>Crear Post</button>
            <button onClick={() => navigate('/blog')}>Cancelar</button>
        </div>
    );
};

export default NewPost;
