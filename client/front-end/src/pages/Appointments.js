import React, { useContext, useEffect, useMemo, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import { useDispatch, useSelector } from 'react-redux';
import { requestAppointmentsClient, requestAppointmentsDoctors } from '../store/slice/AppoimentsSlice';
import moment from 'moment';
import 'moment/locale/ru';
moment.locale('ru');

const Appointments = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const appointmentsDoctorStore = useSelector((state) => state.appointments.appointmentsDoctor);
  const appointmentsClientStore = useSelector((state) => state.appointments.appointmentsClient);
  const { role } = useContext(UserContext);
  const [withArchived, setWithArchived] = useState(false);

  const appointmentsDoctor = useMemo(() => {
    if (!appointmentsDoctorStore) return [];
    else return !withArchived ? appointmentsDoctorStore.filter((user) => user.active) : appointmentsDoctorStore.filter((user) => !user.active);
  }, [withArchived, appointmentsDoctorStore]);

  const appointmentsClient = useMemo(() => {
    if (!appointmentsClientStore) return [];
    else return !withArchived ? appointmentsClientStore.filter((user) => user.active) : appointmentsClientStore.filter((user) => !user.active);
  }, [withArchived, appointmentsClientStore]);

  useEffect(() => {
    dispatch(requestAppointmentsClient({}));
    dispatch(requestAppointmentsDoctors({}));
  }, [dispatch]);

  return (
    <div className="my-4">
      {role.includes('DOCTOR') && (
        <div className="row mb-2">
          <div className="col-12">
            <h3>{withArchived ? 'Предыдущие записи к вам:' : 'Ближайшие записи к вам:'}</h3>
          </div>
        </div>
      )}

      {role.includes('DOCTOR') && (
        <div className="row mb-2 list">
          {appointmentsDoctor.map((appointment) => (
            <div className="col-md-4" key={appointment.id}>
              <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <div className="col p-4 d-flex flex-column position-static">
                  <strong className="d-inline-block mb-2 text-primary">{appointment.client.fullName}</strong>
                  <h3 className="mb-0">{appointment.name}</h3>
                  <div className="mb-1 text-muted">{moment(appointment.date).format('llll')}</div>
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
        <div className="row mb-2">
          <div className="col-12">
            <h3>{withArchived ? 'Вы были у врачей:' : 'Вы записаны к врачам:'}</h3>
          </div>
        </div>
      )}

      {role.includes('USER') && (
        <div className="row mb-2 list">
          {appointmentsClient.map((appointment) => (
            <div className="col-md-4" key={appointment.id} onClick={() => navigate(`/appointments/edit/${appointment.id}`)}>
              <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <div className="col p-4 d-flex flex-column position-static">
                  <strong className="d-inline-block mb-2 text-primary">{appointment?.doctor?.fullName}</strong>
                  <div className="mb-1 text-muted">{moment(appointment.date).format('llll')}</div>
                  <Link href={`/appointments/edit/${appointment.id}`} className="stretched-link">
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
