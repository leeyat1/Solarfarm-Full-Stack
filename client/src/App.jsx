import { useEffect, useState } from 'react';
import NavBar from './components/NavBar';
import logo from './assets/logo.png';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Home from './pages/Home'
import Contact from './pages/Contact'
import ConfirmDelete from './pages/ConfirmDelete';
import AllPanels from './pages/AllPanels';
import Add from './pages/Add';
import Edit from './pages/Edit';
import Signup from './pages/Signup';
import Login from './pages/Login';
import MyPanels from './pages/MyPanels';

function App() {
	const initialUser = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
	const [user, setUser] = useState(initialUser)

	const navigateFromProtectedRoute = <Navigate to="/" />

	return (
		<BrowserRouter>
			<div className='container'>
				<header className='mb-3'>
					<nav className='navbar navbar-expand'>
						<div className='d-flex'>
							<a className='navbar-brand' href='/'>
								<img src={logo} alt='Solar Farm' width='150' />
							</a>
							<NavBar setUser={setUser} user={user} />
						</div>
					</nav>
				</header>
				<main>
					<h1 className='mb-3'>Solar Panels</h1>
					{ user !== null ? <h2>Welcome, {user.username}!</h2> : null }
					<Routes>
						{/* visible always */}
						<Route path="/" element={<Home />} />
						<Route path="/contact" element={<Contact />} />
						<Route path="/allPanels" element={<AllPanels user={user} />} />
						<Route path="*" element={<div>Page not found, 404</div>} />

						{/* only visible if logged in */}
						<Route path="/personal" element={
							user ? <MyPanels user={user} setUser={setUser} /> : navigateFromProtectedRoute
						} />
						<Route path="/add" element={
							user ? <Add user={user} setUser={setUser} /> : navigateFromProtectedRoute
						} />
						<Route path="/edit/:id" element={
							user ? <Edit user={user} setUser={setUser} /> : navigateFromProtectedRoute
						} />
						<Route path="/delete/:id" element={
							user ? <ConfirmDelete user={user} setUser={setUser} /> : navigateFromProtectedRoute
						} />

						{/* only visible if logged out */}
						<Route path="/signup" element={
							!user ? <Signup setUser={setUser} /> : navigateFromProtectedRoute
						} />
						<Route path="/login" element={
							!user ? <Login setUser={setUser} /> : navigateFromProtectedRoute
						} />
					</Routes>
				</main>
			</div>
		</BrowserRouter>
	);
}


export default App