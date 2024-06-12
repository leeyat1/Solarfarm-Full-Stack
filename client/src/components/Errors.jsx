const Errors = ({ errors }) => {
    if (!errors) {
        return null
    }
    
    return (
        <ul>
            { errors.map(error => <li key={error} className="text-danger">{error}</li>)}
        </ul>
    )
}

export default Errors