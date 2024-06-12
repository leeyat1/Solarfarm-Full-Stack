import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";

const SolarPanelForm = ({ existingSolarPanel, handleSubmit }) => {    
    const INITIAL_FORM_STATE = {
        section: "",
        row: 0,
        column: 0,
        yearInstalled: 0,
        material: "A_SI",
        tracking: false,
    };

    const [solarPanel, setSolarPanel] = useState(INITIAL_FORM_STATE)

    const navigate = useNavigate()

    useEffect(() => {
        const initialFormState = existingSolarPanel || INITIAL_FORM_STATE
        setSolarPanel(initialFormState)
    }, [existingSolarPanel])

    const handleChange = (event) => {
        let newValue = event.target.value;
        if (event.target.type === "checkbox") {
            newValue = event.target.checked;
        }

        const newSolarPanel = { ...solarPanel, [event.target.name]: newValue }
        setSolarPanel(newSolarPanel)
    }

    return (
        <>
            <form onSubmit={(event) => handleSubmit(event, solarPanel)}>
                <fieldset>
                    <div>
                        <label htmlFor="section-input">Section:</label>
                        <input type="text" id="section-input" name="section" required value={solarPanel.section} onChange={handleChange} />
                    </div>

                    <div>
                        <label htmlFor="row-input">Row:</label>
                        <input type="number" id="row-input" name="row" min={1} max={250} value={solarPanel.row} onChange={handleChange} />
                    </div>

                    <div>
                        <label htmlFor="column-input">Column:</label>
                        <input type="number" id="column-input" name="column" min={1} max={250} value={solarPanel.column} onChange={handleChange} />
                    </div>

                    <div>
                        <label htmlFor="yearInstalled-input">Year Installed:</label>
                        <input type="number" id="yearInstalled-input" name="yearInstalled" value={solarPanel.yearInstalled} onChange={handleChange} />
                    </div>

                    <div>
                        <label htmlFor="material-input">Material:</label>
                        <select id="material-input" name="material" value={solarPanel.material} onChange={handleChange}>
                            <option>A_SI</option>
                            <option>POLY_SI</option>
                            <option>CIGS</option>
                        </select>
                    </div>

                    <div>
                        <label htmlFor="tracking-input">Tracking?:</label>
                        <input type="checkbox" id="tracking-input" name="tracking" checked={solarPanel.tracking} onChange={handleChange} />
                    </div>

                    <button type="submit">{ existingSolarPanel ? "Update!" : "Create!" }</button>
                    <button type="button" onClick={() => navigate("/allPanels")}>Cancel</button>
                </fieldset>
            </form>
        </>
    )
}

export default SolarPanelForm