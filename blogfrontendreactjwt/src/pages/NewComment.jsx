import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';

const NewComment = () => {
    const { postId } = useParams();
    const navigate = useNavigate();
    const [body, setBody] = useState('');

    const handleCreate = async () => {
        try {
            const params = new URLSearchParams();
            params.append("body", body);
            params.append("postId", postId);
            
            await api.post('/comment/add', params, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });
            navigate(`/comments/${postId}`);
        } catch (error) {
            console.error('Error creating comment:', error);
        }
    };

    return (
        <div>
            <h2>Crear Nuevo Comentario</h2>
            <label>
                Cuerpo:
                <textarea value={body} onChange={(e) => setBody(e.target.value)} />
            </label>
            <button onClick={handleCreate}>Crear Comment</button>
            <button onClick={() => navigate(`/comments/${postId}`)}>Cancelar</button>
        </div>
    );
};

export default NewComment;
