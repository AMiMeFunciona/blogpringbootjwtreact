import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';

const EditPost = () => {
    const { postId } = useParams();
    const navigate = useNavigate();
    const [post, setPost] = useState({});
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false); 

    useEffect(() => {
        const fetchPost = async () => {
            try {
                const response = await api.get(`/blog/posts/detail/${postId}`);
                setPost(response.data);
            } catch (err) {
                if (err.response) {
                    setError(err.response.data);
                } else {
                    setError('Error desconocido al intentar obtener los datos.');
                }
                console.error('Error fetching data:', err); 
            } finally {
                setLoading(false);
            }
        };

        fetchPost();
    }, [postId]);

    const handleUpdate = async () => {
        try {
            await api.put(`/blog/update/${postId}`, { title : post.title, body: post.body });
            navigate('/blog');
        } catch (error) {
            console.error('Error updating post:', error);
        }
    };

    const handleDelete = async () => {
        try {
            await api.delete(`/blog/delete/${postId}`);
            navigate('/blog');
        } catch (error) {
            console.error('Error deleting post:', error);
        }
    };

    if (loading) {
        return <p>Cargando datos...</p>;
    }

    return (
        <div>

            {error ? (
                <div>
                    <h3>Error:</h3>
                    <pre>{JSON.stringify(error, null, 2)}</pre>
                </div>
            ) : (
                <>
                
                <h2>Post Detallado</h2>
                    <label>
                        Título:
                        <input 
                            type="text"
                            value={post.title || ''} 
                            onChange={(e) => setPost(prevPost => ({
                                ...prevPost,
                                title: e.target.value
                            }))} 
                            disabled={!isEditing}
                        />
                    </label>
                    <label>
                        Cuerpo:
                        <textarea 
                            value={post.body || ''} 
                            onChange={(e) => setPost(prevPost => ({
                                ...prevPost,
                                body: e.target.value
                            }))} 
                            disabled={!isEditing}
                        />
                    </label>
                    <button onClick={() => isEditing ? handleUpdate() : setIsEditing(true)}>
                        {isEditing ? "Guardar" : "Actualizar"}
                    </button>
                    <button onClick={handleDelete}>Eliminar</button>
                    <button onClick={() => navigate('/blog')}>Cancelar</button>
                
                </>
            )}

        </div>
    );
};

export default EditPost;
