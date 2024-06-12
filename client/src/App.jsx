import { useState } from 'react';
import NavBar from './components/NavBar';
import SolarPanelForm from './components/SolarPanelForm';
import SolarPanelTable from './components/SolarPanelTable';
import logo from './assets/logo.png';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home'
import Contact from './pages/Contact'
import ConfirmDelete from './pages/ConfirmDelete';
import AllPanels from './pages/AllPanels';
import Add from './pages/Add';
import Edit from './pages/Edit';

function App() {
	return (
		<BrowserRouter>
			<div className='container'>
				<header className='mb-3'>
					<nav className='navbar navbar-expand'>
						<div className='d-flex'>
							<a className='navbar-brand' href='/'>
								<img src={logo} alt='Solar Farm' width='150' />
							</a>
							<NavBar />
						</div>
					</nav>
				</header>
				<main>
					<h1 className='mb-3'>Solar Panels</h1>
					<Routes>
						<Route path="/" element={<Home />} />
						<Route path="/contact" element={<Contact />} />
						<Route path="/allPanels" element={<AllPanels />} />
						<Route path="/add" element={<Add />} />
						<Route path="/edit/:id" element={<Edit />} />				
						<Route path="/delete/:id" element={<ConfirmDelete />} />												
						<Route path="*" element={<div>Page not found, 404</div>} />
					</Routes>
				</main>
			</div>
		</BrowserRouter>
	);
}


export default App
