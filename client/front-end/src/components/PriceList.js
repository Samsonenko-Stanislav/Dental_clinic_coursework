import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Table } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const PriceList = () => {
    const [services, setServices] = useState([]);

    useEffect(() => {
        axios
            .get('/price')
            .then((response) => setServices(response.data))
            .catch(() => {
                toast.error('Ошибка при загрузке списка услуг!');
                setServices([
                    { service: 'Лечение кариеса', price: 2000 },
                    { service: 'Удаление зуба', price: 3000 },
                    { service: 'Протезирование зубов', price: 5000 },
                    { service: 'Ортодонтическое лечение', price: 7000 },
                    { service: 'Имплантация зубов', price: 10000 },
                    { service: 'Отбеливание зубов', price: 1500 },
                    { service: 'Снятие зубного налета', price: 500 },
                    { service: 'Гигиеническое чистка зубов', price: 1000 },
                    { service: 'Общий осмотр стоматолога', price: 500 },
                    { service: 'Рентген зубов', price: 800 },
                ]);
            });
    }, []);

    return (
        <Container>
            <ToastContainer />
            <Table striped bordered hover responsive>
                <thead>
                <tr>
                    <th>Услуга</th>
                    <th>Цена, руб.</th>
                </tr>
                </thead>
                <tbody>
                {services.map((service, index) => (
                    <tr key={index}>
                        <td>{service.service}</td>
                        <td>{service.price}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default PriceList;
