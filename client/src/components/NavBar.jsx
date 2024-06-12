import { Link } from "react-router-dom"

import NavLink from "./NavLink"



const NavBar = ({ setUser, user }) => {

    const handleLogout = () => {
        setUser(null);
        localStorage.removeItem('user');
    };

    const logOutLink = 
    <li className='nav-item'>
        <span className='nav-link' onClick={handleLogout}>
            Log Out
        </span>
    </li>

    return (
        <ul className='navbar-nav'>
            {/* always visible */}
            <NavLink name="Home" to="/"/>
            <NavLink name="Contact" to="/contact"/>
           
            {/* TODO: refactor routes into /solarPanel/add and /user/add */}

            {/* visible only when logged out */}
            { user === null && <NavLink name="Sign Up" to="/signup" /> }
            { user === null && <NavLink name="Log In" to="/login" /> }
                        
            {/* visible only when logged in */}
            { user !== null && <NavLink name="All Panels" to="/allPanels"/> }
            { user !== null && <NavLink name="Add" to="/add" /> }
            { user !== null && <NavLink name="My Panels" to="/personal" /> }
            { user !== null && logOutLink }
        </ul>
    )
}

export default NavBar