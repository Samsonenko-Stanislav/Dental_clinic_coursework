import React, { memo, useContext, useEffect, useState } from "react";
import { addAppointments, getAddAppointments } from "../store/slice/AppoimentsSlice";
import { useDispatch, useSelector } from "react-redux";
import moment from "moment";
import { UserContext } from "../context/UserContext";
import "moment/locale/ru";
import { useNavigate } from "react-router-dom";
import { showNotification } from "../App";
import employee from "./Employee";
import EmptyComponent from "../components/EmptyComponent/EmptyComponent";

moment.locale('ru');

const AppointmentsAdd = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [currentNameDoctor, setCurrentNameDoctor] = useState('');
  const [selectedDoctorId, setSelectedDoctorId] = useState('');
  const [selectedAppointmentDate, setSelectedAppointmentDate] = useState('');
  const doctors = useSelector((state) => state.appointments.doctors) || [];
  const { setLoading } = useContext(UserContext);
  const handleSetDoctor = (id, time, name) => {
    setSelectedDoctorId(id);
    setSelectedAppointmentDate(time);
    setCurrentNameDoctor(name);
  };

  useEffect(() => {
    dispatch(getAddAppointments({}));
  }, [dispatch]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    setLoading(true);
    const response = await dispatch(addAppointments({ newData: { doctorId: selectedDoctorId, date: selectedAppointmentDate } }));
    if (response?.type?.includes('fulfilled')) {
      navigate('/appointments');
      showNotification('success', 'Вы успешно записаны', 'Запись');

      dispatch(getAddAppointments({}));
    }
    setLoading(false);
  };

  return (
    <>
      <div className="row mx-2 mt-4 list">
        {doctors.length
          ? doctors.map((doctor, index) => (
              <div key={index} className="col-md-12">
                <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                  <div className="col p-4 d-flex flex-column position-static">
                    <h3 className="mb-2">{`${doctor.fullName} ${doctor.jobTitle}`}</h3>
                    <div className="row m-0">
                      {doctor?.timetable?.length
                        ? doctor?.timetable.map((date, index) => (
                            <div key={index} className="col-md-4 border rounded">
                              <div className="ps-2 pt-2">{moment(date.day).format('ll')}</div>
                              <div className="p-2">
                                {date?.times?.length ? date.times.map((time) => (
                                  <button key={time} className="mt-1 ms-1" onClick={() => handleSetDoctor(doctor.id, date.day + 'T' + time, doctor.fullName)}>
                                    {time}
                                  </button>
                                )):<a>На данную дату нет свободных талонов!!!!</a>}
                              </div>
                            </div>
                          ))
                        : (<a>На ближайшие 14 дней нет свободных талонов!!!!</a>)}
                    </div>
                  </div>
                </div>
              </div>
            ))
          : (
                <EmptyComponent />
            )}
      </div>

      {currentNameDoctor && (
        <form onSubmit={handleSubmit} className="mt-2">
          <div>Выбранный врач: {currentNameDoctor}</div>
          <div>Выбранная дата: {moment(selectedAppointmentDate).format('LLLL')}</div>
          <button className="btn btn-primary btn-lg my-1" id="add_appointment" type="submit">
            Добавить запись
          </button>
        </form>
      )}
    </>
  );
};

export default memo(AppointmentsAdd);
