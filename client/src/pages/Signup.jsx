import { useState } from "react"
import { useNavigate } from "react-router-dom"

import Errors from "../components/Errors"

const Signup = () => {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [errors, setErrors] = useState([])

    const navigate = useNavigate()
    
    const handleSubmit = (event) => {
        event.preventDefault()
        fetch("http://localhost:8080/api/user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json"
            },
            body: JSON.stringify({ username, password })
        })
        .then(response => {
            if (response.status === 201) {
                navigate("/")
            } else if (response.status === 400) {
                response.json()
                .then(json => setErrors(json))
            } else {
                return Promise.reject()
            }
        })
        .catch(error => {
            setErrors(["Something went wrong"])
        })
    }

    return (
        <div>
            <h3>Create an Account</h3>
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
                
                    <button type="submit">Sign up!</button>
                </fieldset>
            </form>
        </div>
    )
}

export default Signup