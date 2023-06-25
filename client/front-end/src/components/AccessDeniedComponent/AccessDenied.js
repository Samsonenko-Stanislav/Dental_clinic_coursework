import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import AD_pict from "../../assets/403.png";
import "./AccessDenied.css"

const AccessDenied = () => {
    return (
        <div className="accessDenied">
            <img alt={'AD_pict'} className="Ad_pict" src={AD_pict} />
        </div>
    );
};

export default AccessDenied;