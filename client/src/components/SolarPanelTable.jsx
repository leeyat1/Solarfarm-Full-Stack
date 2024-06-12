import { useEffect, useState } from "react";
import SolarPanelRow from "./SolarPanelRow";

const SolarPanelTable = ({ panels, user }) => {
    return (
        <table className='table table-striped'>
            <thead>
                <tr>
                    <th>Section</th>
                    <th>Row</th>
                    <th>Column</th>
                    <th>Year Installed</th>
                    <th>Material</th>
                    <th>Tracking Software</th>
                    <th>Modify</th>
                </tr>
            </thead>
            <tbody>
                {panels && panels.map(solarPanel => (
                    <SolarPanelRow 
                    key={solarPanel.id} solarPanel={solarPanel} user={user} />
                ))}
            </tbody>
        </table>
    )
}

export default SolarPanelTable