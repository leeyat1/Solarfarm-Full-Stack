import NavLink from "./NavLink"

const NavBar = () => {
    return (
        <ul className='navbar-nav'>
            <NavLink name="Home" to="/"/>
            <NavLink name="Contact" to="/contact"/>
            <NavLink name="All Panels" to="/allPanels"/>
            <NavLink name="Add" to="/add" />
        </ul>
    )
}

export default NavBar