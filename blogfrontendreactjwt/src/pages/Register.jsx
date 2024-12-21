import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const { register, error } = useAuth();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [selectedRoleIds, setSelectedRoleIds] = useState([]);
    const [enabled, setEnabled] = useState(false);
    const [roles, setRoles] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await api.get('/roles/getall');
                setRoles(response.data);
            } catch (error) {
                console.error("Error al cargar los roles:", error);
            }
        };

        fetchRoles();
    }, []);

    const handleRoleChange = (roleId) => {
        setSelectedRoleIds((prevSelectedRoleIds) =>
            prevSelectedRoleIds.includes(roleId)
                ? prevSelectedRoleIds.filter((id) => id !== roleId)
                : [...prevSelectedRoleIds, roleId]
        );
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const registrationData = {
            email,
            password,
            roleIds: selectedRoleIds,
            enabled: enabled
        };                                                                                                                                                                                                                                                                               
        try {
            await api.post('/auth/register', registrationData);
            navigate('/login');
        } catch (error) {
            console.error("Error al registrar el usuario:", error);
        }
    };

    return (
        <div>
            <h2>Register</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Email"
                    required
                />
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                />

                <fieldset>
                    <legend>Select Roles:</legend>
                    {roles.map((roleOption) => (
                        <label key={roleOption.id}>
                            <input
                                type="checkbox"
                                value={roleOption.id}
                                checked={selectedRoleIds.includes(roleOption.id)}
                                onChange={() => handleRoleChange(roleOption.id)}
                            />
                            {roleOption.name}
                        </label>
                    ))}
                </fieldset>

                <label>
                    <input
                        type="checkbox"
                        checked={enabled}
                        onChange={(e) => setEnabled(e.target.checked)}
                    />
                    Enabled
                </label>

                <button type="submit">Register</button>
            </form>
            <button onClick={() => navigate('/login')}>Login</button>
        </div>
    );
};

export default Register;
