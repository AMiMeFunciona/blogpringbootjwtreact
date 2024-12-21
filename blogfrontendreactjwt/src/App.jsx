import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { AuthProvider } from './context/AuthContext';
import Login from './pages/Login';
import Blog from "./pages/Blog";
import Register from './pages/Register';
import RootRedirect from './pages/RootRedirect';
import Comments from './pages/Comments';
import EditPost from './pages/EditPost';
import NewComment from './pages/NewComment';
import NewPost from './pages/NewPost';
import EditComment from './pages/EditComment';
import "./App.css";

function App() {
  return (
    <>
      <Router>
        <AuthProvider>
          <Routes>
            <Route path="/" element={<RootRedirect />} />
            <Route path="/login" element={<Login />} />
            <Route path="/blog" element={<Blog />} />
            <Route path="/register" element={<Register />} />
            <Route path="/comments/:postId" element={<Comments />} />
            <Route path="/posts/edit/:postId" element={<EditPost />} />
            <Route path="/comments/new/:postId" element={<NewComment />} />
            <Route path="/posts/new" element={<NewPost />} />
            <Route path="/comments/edit/:commentId" element={<EditComment />} />
          </Routes>
        </AuthProvider>
      </Router>
    </>
  );
}

export default App;
