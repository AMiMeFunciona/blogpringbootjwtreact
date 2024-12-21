import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useLocation, useNavigate } from 'react-router-dom';

const Login = () => {
    const { login, error } = useAuth();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const urlError = queryParams.get("error");

    const handleSubmit = (e) => {
        e.preventDefault();
        if (email && password) {
            login(email, password);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            {urlError
                ? <p style={{ color: 'red' }}>{urlError}</p>
                : error && <p style={{ color: 'red' }}>{error}</p>
            }
            <form onSubmit={handleSubmit}>
                <label>
                    Email:
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Email"
                        required
                    />
                </label>
                <label>
                    Password:
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Password"
                        required
                    />
                </label>
                <button type="submit">Login</button>
            </form>
            <button onClick={() => navigate('/register')}>Registrar</button>
        </div>
    );
};

export default Login;
