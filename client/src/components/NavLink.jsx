import { Link } from "react-router-dom"

const NavLink = (props) => {
    return (
        <li className='nav-item'>
            <Link className='nav-link' to={props.to}>
                {props.name}
            </Link>
        </li>
    )
}

export default NavLink