import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import NotFound_pict from "../../assets/404-dental.png";
import "./NotFound.css"

const NotFound = () => {
    return (
        <div className="notFound">
            <img alt={'NotFound_pict'} className="NotFound_pict" src={NotFound_pict} />
        </div>
    );
};

export default NotFound;
