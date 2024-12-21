import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

const Blog = () => {
    const [data, setData] = useState({ posts: [], totalViews: 0 });
    const [loading, setLoading] = useState(true);
    const { logout, handleAuthError } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [postsResponse, incrementResponse] = await Promise.all([
                    api.get('/blog/getall'),
                    api.put('/api/views/increment')
                ]);

                setData({
                    posts: postsResponse.data,
                    totalViews: incrementResponse.data
                });
            } catch (error) {
                console.error('Error fetching data:', error);
/*                 if (error.response?.status === 403) {
                    handleAuthError("Usuario no autenticado");
                } */
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    const handleCreatePost = () => {
        navigate('/posts/new');
    };

    const handleCreateComment = (postId) => {
        navigate(`/comments/new/${postId}`);
    };

    if (loading) {
        return <p>Cargando datos...</p>;
    }

    return (
        <div>
        <h2>Blog Posts</h2>
            <p>Total Views: {data.totalViews}</p>
            <ul>
                {data.posts.length > 0 && data.posts.map((post) => (
                    <li key={post.id}>
                        <Link to={`/posts/edit/${post.id}`}>
                            <h3>{post.title}</h3>
                        </Link>
                        <p>{post.body}</p>
                        <p>Date: {new Date(post.publicationDate).toLocaleString()}</p>
                        <p>
                            <Link to={`/comments/${post.id}`}>
                                Comments: {post.comments ? post.comments.length : (post.commentCount ?? 0)}
                            </Link>
                        </p>
                        <p>{post.userEmail}</p>
                        <button onClick={() => handleCreateComment(post.id)}>Crear Nuevo Comment</button>
                    </li>
                ))}
            </ul>
            <button onClick={handleCreatePost}>Crear Nuevo Post</button>
            <button onClick={logout}>Logout</button>
        </div>
    );
};

export default Blog;
