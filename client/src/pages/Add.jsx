import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import Errors from "../components/Errors"
import SolarPanelForm from "../components/SolarPanelForm"

const Add = () => {
    const navigate = useNavigate()

    // const params = useParams()
    // const id = params.id
    const { id } = useParams()

    const [errors, setErrors] = useState([])

    const handleSubmit = (event, solarPanel) => {
        event.preventDefault()
        fetch("http://localhost:8080/api/solarpanel/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
            },
            body: JSON.stringify(solarPanel)
        })
        .then(response => {
            if (response.status === 201) {
                navigate("/allPanels")
            } else if (response.status === 400) {
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
            <h3>Add a panel</h3>
            <Errors errors={errors} />
            <SolarPanelForm existingSolarPanel={null} handleSubmit={handleSubmit} />
        </>
    )
}

export default Add