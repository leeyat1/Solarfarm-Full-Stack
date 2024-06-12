import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"

const ConfirmDelete = ({ user }) => {
    const params = useParams()
    const id = params.id

    const navigate = useNavigate()

    const [solarPanel, setSolarPanel] = useState({})

    useEffect(() => {
        fetch(`http://localhost:8080/api/solarpanel/${id}`)
        .then(response => response.json())
        .then(json => setSolarPanel(json))
    }, [])

    const handleDelete = () => {
        fetch(`http://localhost:8080/api/solarpanel/${id}`, {
            method: "DELETE",
            headers: {
                Authorization: user.id
            }
        })
        .then(response => {
            if (response.status === 204) {
                navigate("/allPanels")
            } else if (response.status === 404) {
                navigate("/allPanels")
            } else {
                return Promise.reject()
            }
        })
        .catch(err => console.log(err))
    }
    
    return (
        <div>
            <h3>Are you sure you want to delete panel #{id}?</h3>
            <p>Section: {solarPanel.section}</p>
            <p>Row: {solarPanel.row}</p>
            <p>Column: {solarPanel.column}</p>
            <p>Year Installed: {solarPanel.yearInstalled}</p>
            <p>Material: {solarPanel.material}</p>
            <p>Tracking?: {solarPanel.tracking ? "Yes" : "No"}</p>
        
            <button onClick={handleDelete}>Delete</button>
            
            <button onClick={() => navigate("/allPanels")}>Cancel</button>
        </div>
    )
}

export default ConfirmDelete