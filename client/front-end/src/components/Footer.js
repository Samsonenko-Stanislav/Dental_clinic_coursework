import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container} from "react-bootstrap";
class Footer extends React.Component {
    render() {
        return (
        <footer className="py-4 bg-dark text-white text-center">
            <Container>
                <span className="mb-3 mb-md-0 text-muted">© 2023 Самсоненко Станислав</span>
                <h4 className="mb-3 mb-md-0 text-muted">График работы</h4>
                <p className="mb-3 mb-md-0 text-muted">Вторник - Суббота:</p>
                <p className="mb-3 mb-md-0 text-muted">8:00 - 21:00</p>
            </Container>
        </footer>
        );
    }
}

export default Footer;
