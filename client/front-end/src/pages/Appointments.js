import React, { useEffect, useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import { useDispatch, useSelector } from 'react-redux';
import { requestAppointments, requestAppointmentsDoctors } from '../store/slice/AppoimentsSlice';

const Appointments = () => {
  const dispatch = useDispatch();
  const appointmentsDoctor = useSelector((state) => state.appointments.appointmentsDoctor);
  const appointmentsClient = useSelector((state) => state.appointments.appointmentsClient);
  const { role } = useContext(UserContext);
  const [withArchived, setWithArchived] = useState(false);

  useEffect(() => {
    dispatch(requestAppointments({}));
    dispatch(requestAppointmentsDoctors({}));
  }, [dispatch]);

  return (
    <div>
      {role === 'doctor' && (
        <div className="row mb-2">
          <div className="col-12">
            <h3>{withArchived ? 'Предыдущие записи к вам:' : 'Ближайшие записи к вам:'}</h3>
          </div>
          {role.includes('DOCTOR') &&
            appointmentsDoctor.map((appointment) => (
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

      {role.includes('USER') && appointmentsClient.length > 0 && (
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

      {role.includes('USER') && (
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
