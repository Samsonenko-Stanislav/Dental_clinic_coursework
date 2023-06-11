import React, { useEffect, useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../UserContext';

const Appointments = () => {
  const { user, role } = useContext(UserContext);

  const [appointmentsDoctor, setAppointmentsDoctor] = useState([]);
  const [appointmentsClient, setAppointmentsClient] = useState([]);
  const [withArchived, setWithArchived] = useState(false);

  useEffect(() => {
    const effect = async () => {
      const response = await fetch('https://jsonplaceholder.typicode.com/photos?_start=0&_limit=5');
      const data = await response.json();
      setAppointmentsClient(data);
    };

    effect();

    // fetchAppointmentsDoctor().then((data) => setAppointmentsDoctor(data));
    // fetchAppointmentsClient().then((data) => setAppointmentsClient(data));
  }, []);

  return (
    <div>
      {role === 'doctor' && (
        <div className="row mb-2">
          <div className="col-12">
            <h3>{withArchived ? 'Предыдущие записи к вам:' : 'Ближайшие записи к вам:'}</h3>
          </div>
          {appointmentsDoctor.map((appointment) => (
            <div className="col-md-4" key={appointment.id}>
              <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <div className="col p-4 d-flex flex-column position-static">
                  <strong className="d-inline-block mb-2 text-primary">{appointment.name}</strong>
                  <div className="mb-1 text-muted">{appointment.date}</div>
                  <Link href={`/appointments/${appointment.id}/edit`} className="stretched-link">
                    Подробнее
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {role === 'user' && appointmentsClient.length > 0 && (
        <div className="row mb-2">
          <div className="col-12">
            <h3>{withArchived ? 'Вы были у врачей:' : 'Вы записаны к врачам:'}</h3>
          </div>
          {appointmentsClient.map((appointment) => (
            <div className="col-md-4" key={appointment.id}>
              <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <div className="col p-4 d-flex flex-column position-static">
                  <strong className="d-inline-block mb-2 text-primary">{appointment.name}</strong>
                  <h3 className="mb-0">{appointment.name}</h3>
                  <div className="mb-1 text-muted">{appointment.name}</div>
                  <Link to={`/appointments/edit/${appointment.id}`} className="stretched-link">
                    Подробнее
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {role === 'user' && (
        <Link to="/appointments/add" className="btn btn-primary">
          Записаться
        </Link>
      )}

      {!withArchived ? (
        <button className="btn btn-primary mx-4" onClick={() => setWithArchived(true)}>
          Показать предыдущие записи
        </button>
      ) : (
        <button onClick={() => setWithArchived(false)} className="btn btn-primary  mx-4">
          Показать текущие записи
        </button>
      )}
    </div>
  );
};

export default Appointments;
