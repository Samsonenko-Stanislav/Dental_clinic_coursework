import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
class Footer extends React.Component {
    render() {
        return (
            <footer className="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
                <div className="col-md-4 d-flex align-items-center ms-5">
                    <span className="mb-3 mb-md-0 text-muted">© 2023 Самсоненко Станислав</span>
                </div>

            </footer>
        );
    }
}

export default Footer;