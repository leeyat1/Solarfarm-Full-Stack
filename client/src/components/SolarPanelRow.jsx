import { Link } from "react-router-dom";

const SolarPanelRow = ({ solarPanel, user }) => {    
    return (
        <tr >
            <td>{solarPanel.section}</td>
            <td>{solarPanel.row}</td>
            <td>{solarPanel.column}</td>
            <td>{solarPanel.yearInstalled}</td>
            <td>{solarPanel.material}</td>
            <td>{solarPanel.tracking ? 'Yes' : 'No'}</td>
            <td>
                { user && user.id === solarPanel.userId &&
                    <>
                        <Link to={`/edit/${solarPanel.id}`} className='btn btn-warning me-2 mb-2'>
                            Edit
                        </Link>
                        <Link to={`/delete/${solarPanel.id}`} className='btn btn-danger me-2 mb-2'>
                            Delete
                        </Link>
                    </>
                }
            </td>
        </tr>
    )

}

export default SolarPanelRow