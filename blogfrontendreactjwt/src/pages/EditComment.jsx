import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';

const EditComment = () => {
    const { commentId } = useParams();
    const navigate = useNavigate();
    const [comment, setComment] = useState({});
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        const fetchComment = async () => {
            try {
                const response = await api.get(`/comment/comments/detail/${commentId}`);
                setComment(response.data);
            } catch (error) {
                console.error('Error fetching comment:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchComment();
    }, [commentId]);

    const handleUpdate = async () => {
        try {
            await api.put(`/comment/update/${commentId}`, { body: comment.body });
            navigate(-1);
        } catch (error) {
            console.error('Error updating comment:', error);
        }
    };

    const handleDelete = async () => {
        try {
            await api.delete(`/comment/delete/${commentId}`);
            navigate(-1);
        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    };

    if (loading) {
        return <p>Cargando datos...</p>;
    }
    
    return (
        <div>
            <h2>Comentario Detallado</h2>
            <label>
                Cuerpo:
                <textarea 
                    value={comment.body || ''} 
                    onChange={(e) => setComment(prevComment => ({
                        ...prevComment,
                        body: e.target.value
                    }))} 
                    disabled={!isEditing}
                />
            </label>
            <p>Date: {comment.publicationDate ? new Date(comment.publicationDate).toLocaleString() : ''}</p>
            <p>Author: {comment.userEmail}</p>
            <button onClick={() => isEditing ? handleUpdate() : setIsEditing(true)}>
                {isEditing ? "Guardar" : "Actualizar"}
            </button>
            <button onClick={() => handleDelete()}>Eliminar</button>
            <button onClick={() => navigate(-1)}>Cancelar</button>
        </div>
    );
};

export default EditComment;
