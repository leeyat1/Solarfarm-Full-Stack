import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import Errors from "../components/Errors"
import SolarPanelForm from "../components/SolarPanelForm"

const Edit = ({ user, setUser }) => {
    const navigate = useNavigate()

    // const params = useParams()
    // const id = params.id
    const { id } = useParams()

    const [solarPanel, setSolarPanel] = useState(null)
    const [errors, setErrors] = useState([])

    useEffect(() => {
        if (id) {
            fetch(`http://localhost:8080/api/solarpanel/${id}`)
            .then(response => response.json())
            .then(json => setSolarPanel(json))
        }
    }, [id])

    const handleSubmit = (event, solarPanel) => {
        event.preventDefault()
        fetch(`http://localhost:8080/api/solarpanel/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
                Authorization: user.jwt,
            },
            body: JSON.stringify(solarPanel)
        })
        .then(response => {
            if (response.status === 204) {
                navigate("/allPanels")
            // } else if (response.status === 400 || response.status === 404 || response.status === 409) {
            } else if (response.status === 403) {
                setUser(null)
                localStorage.removeItem("user")
            } else if (response.status >= 400 && response.status < 499) {
                // handle validation errors
                response.json()
                .then(json => {
                    setErrors(json)
                })
            } else {
                return Promise.reject();
            }
        })
        .catch(err => {
            setErrors(["Something went wrong!"]);            
        })
    }


    return (
        <>
            <h3>Edit a panel</h3>
            <Errors errors={errors} />
            <SolarPanelForm existingSolarPanel={solarPanel} handleSubmit={handleSubmit} />
        </>
    )
}

export default Edit