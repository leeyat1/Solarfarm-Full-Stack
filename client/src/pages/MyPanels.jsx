import { useEffect, useState } from "react";
import SolarPanelTable from "../components/SolarPanelTable";

const MyPanels = ({ user }) => {
	const [solarPanels, setSolarPanels] = useState([])

	useEffect(() => {
		fetch("http://localhost:8080/api/solarpanel/personal", {
            headers: {
                Authorization: user.id
            }
        })
		.then(response => response.json())
		.then(json => {
			setSolarPanels(json)
		})
	}, [])

    if (solarPanels.length === 0) {
        return (<div>Looks like there aren't any panels</div>)
    }

    return (
        <SolarPanelTable panels={solarPanels} user={user} />
    )
}

export default MyPanels