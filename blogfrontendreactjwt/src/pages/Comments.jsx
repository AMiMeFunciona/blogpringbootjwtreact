import { useState, useEffect, useReducer } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';

const PAGE_SIZE = 10;

const paginationReducer = (state, action) => {
    switch (action.type) {
        case 'SET_PAGE':
            return { ...state, page: action.payload };
        case 'SET_TOTAL_PAGES':
            return { ...state, totalPages: action.payload };
        case 'NEXT_PAGE':
            return state.page < state.totalPages - 1 
                ? { ...state, page: state.page + 1 }
                : state;
        case 'PREVIOUS_PAGE':
            return state.page > 0 
                ? { ...state, page: state.page - 1 }
                : state;
        default:
            return state;
    }
};

const Comments = () => {
    const { postId } = useParams();
    const [comments, setComments] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const [pagination, dispatch] = useReducer(paginationReducer, {
        page: 0,
        totalPages: 1
    });

    useEffect(() => {
        const fetchComments = async () => {
            try {
                const response = await api.get(`/comment/comments/${postId}`, {
                    params: {
                        page: pagination.page,
                        size: PAGE_SIZE,
                    }
                });
                setComments(response.data);
                dispatch({ type: 'SET_TOTAL_PAGES', payload: response.data.totalPages }); 
            } catch (error) {
                console.error('Error fetching comments:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchComments();
    }, [postId, pagination.page]);

    const handleDelete = async (commentId) => {
        try {
            await api.delete(`/comment/delete/${commentId}`);
            setComments(prevComments => ({
                ...prevComments,
                content: prevComments.content.filter(comment => comment.id !== commentId)
            })); 
        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    };

    const handleEdit = (commentId) => {
        navigate(`/comments/edit/${commentId}`);
    }
    const handleCreateComment = () => {
        navigate(`/comments/new/${postId}`);
    };

    if (loading) {
        return <p>Cargando datos...</p>;
    }
    
    return (
        <div>
            <h2>Comments</h2>
            <ul>
                {comments.content !== undefined && comments.content.map(comment => (
                    <li key={comment.id}>
                        <p>{comment.body}</p>
                        <button onClick={() => handleEdit(comment.id)}>Ver comentario</button>
                        <button onClick={() => handleDelete(comment.id)}>Eliminar</button>
                    </li>
                ))}
            </ul>
            <button onClick={handleCreateComment}>Crear Nuevo Comment</button>
            <button onClick={() => navigate('/blog')}>Regresar</button>

            <div>
                <button 
                    onClick={() => dispatch({ type: 'PREVIOUS_PAGE' })} 
                    disabled={pagination.page === 0}
                >
                    Anterior
                </button>
                <span>Página {pagination.page + 1} de {pagination.totalPages}</span>
                <button 
                    onClick={() => dispatch({ type: 'NEXT_PAGE' })} 
                    disabled={pagination.page === pagination.totalPages - 1}
                >
                    Siguiente
                </button>
            </div>

        </div>
    );
};

export default Comments;
