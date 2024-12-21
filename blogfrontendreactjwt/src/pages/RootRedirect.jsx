import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const RootRedirect = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            navigate('/blog');
        } else {
            navigate('/login');
        }
    }, [navigate]);

    return null;
};

export default RootRedirect;
