import React from 'react';
import Main_pict from '../main.jpg';
import { Container, Row, Col, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../App.css';
import { toast } from 'react-toastify';


const Main_page = () => {
    return (
        <div className="d-flex flex-column min-vh-100">
            <div
                className="d-flex flex-grow-1 position-relative"
                style={{ background: 'linear-gradient(to bottom, #000000, #2d2d2d)', backgroundImage: `url(${Main_pict})`, backgroundSize: 'cover' }}
            >
                <Container className="d-flex align-items-center justify-content-center text-white text-center">
                    <div className="mb-5">
                        <h1 className="display-4 mb-3 name">
                            Стоматологическая клиника "Улыбка Премиум"
                        </h1>
                            <div className="d-flex align-items-center justify-content-center text-white text-center " style={{ height: 'auto', backdropFilter: 'blur(5px)' }}>
                        <p className="lead discriptionhome">
                            В нашей стоматологической клинике работают настоящие профессионалы, которые безболезненно лечат любые стоматологические заболевания с использованием только лучшего оборудования и материалов.
                        </p>
                        </div>
                        <Button variant="primary" href="/price" className="mt-3">
                            Записаться на прием
                        </Button>
                    </div>
                </Container>
            </div>
            <div className="d-flex flex-grow-0 flex-shrink-0" style={{ backgroundImage: `url(${Main_pict})`, backgroundSize: 'cover', backgroundPosition: 'right' }}></div>
        </div>
    );
};

export default Main_page;
