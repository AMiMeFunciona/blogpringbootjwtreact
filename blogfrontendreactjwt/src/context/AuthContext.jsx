import { createContext, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const login = async (email, password) => {
        try {
            const response = await api.post("/auth/authlogin", { email, password });
            localStorage.setItem("jwtToken", response.data.token);
            setUser({ email });
            navigate("/blog");
        } catch (err) {
            setError("Credenciales incorrectas");
        }
    };

    const handleAuthError = (message) => {
        setError(message);
        logout(false);
    };

    const logout = (clearError = true)  => {
        if (clearError) {
            setError("");
        }
        localStorage.removeItem("jwtToken");
        setUser(null);
        navigate("/login");
    };

    return <AuthContext.Provider value={{ user, error, login, logout, handleAuthError }}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);
