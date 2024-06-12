import { useEffect, useState } from "react";
import SolarPanelTable from "../components/SolarPanelTable";

const AllPanels = ({ user }) => {
	const [solarPanels, setSolarPanels] = useState([])

	useEffect(() => {
		fetch("http://localhost:8080/api/solarpanel")
		.then(response => response.json())
		.then(json => {
			setSolarPanels(json)
		})
	}, [])

    return (
        <SolarPanelTable panels={solarPanels} user={user} />
    )
}

export default AllPanels