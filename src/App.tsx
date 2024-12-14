import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from "./AuthPage";
import Navbar from "./Navbar";
import HomePage from "./HomePage.tsx";
import ReadBlogPage from "./ReadBlogPage.tsx";

function App() {
    return (
        <Router>
            <Navbar />
            <Routes>
                {/* Blank Home Page */}
                <Route path="/" element={<HomePage/>} />

                <Route path="/auth" element={<AuthPage />} />
                <Route path="/blog" element={<ReadBlogPage />} />

            </Routes>
        </Router>
    );
}

export default App;