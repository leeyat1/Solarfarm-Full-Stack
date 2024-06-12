import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { jwtDecode } from "jwt-decode"

import Errors from "../components/Errors"

const Login = ({ setUser }) => {
    // const setUser = props.setUser

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [errors, setErrors] = useState([])

    const navigate = useNavigate()
    
    const handleSubmit = (event) => {
        event.preventDefault();
        fetch("http://localhost:8080/api/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json"
            },
            body: JSON.stringify({ username, password })
        })
        .then(response => {
            if (response.status === 200) {
                response.json().then(json => {
                    const userObject = jwtDecode(json.jwt)
                    userObject.jwt = json.jwt
                    setUser(userObject);
                    // Save user data to local storage
                    localStorage.setItem('user', JSON.stringify(userObject));
                    navigate("/");
                });
            } else if (response.status === 404 || response.status === 403) {
                response.json().then(json => setErrors([json.message]));
            } else {
                return Promise.reject();
            }
        })
        .catch(error => {
            setErrors(["Something went wrong"]);
        });
    };

    return (
        <div>
            <h3>Log In to Your Account</h3>
            <Errors errors={errors} />
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <div>
                        <label htmlFor="username-input">Username:</label>
                        <input id="username-input" type="text" value={username} onChange={event => setUsername(event.target.value)} />
                    </div>

                    <div>
                        <label htmlFor="password-input">Password:</label>
                        <input id="password-input" type="text" value={password} onChange={event => setPassword(event.target.value)} />
                    </div>

                    <button type="submit">Log in!</button>
                </fieldset>
            </form>
        </div>
    )
}

export default Login