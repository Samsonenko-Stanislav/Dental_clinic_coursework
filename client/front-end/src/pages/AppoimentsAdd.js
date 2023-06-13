import React, { useState } from 'react';
import { addAppointments } from '../store/slice/AppoimentsSlice';
import { useDispatch } from 'react-redux';

const AppointmentsAdd = () => {
  const dispatch = useDispatch();
  const [selectedDoctorId, setSelectedDoctorId] = useState('');
  const [selectedAppointmentDate, setSelectedAppointmentDate] = useState('');
  const [doctors, setDoctors] = useState([]);

  const handleSetDoctor = (doctorId, date, time, button) => {
    document.querySelectorAll('button').forEach(function (element) {
      element.disabled = false;
    });
    setSelectedDoctorId(doctorId);
    setSelectedAppointmentDate(`${date}T${time}`);
    button.disabled = true;
    document.getElementById('add_appointment').scrollIntoView();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(addAppointments({}));
  };

  return (
    <>
      <div className="row mx-2 mt-4">
        {doctors.map((doctor) => (
          <div key={doctor.key.id} className="col-md-12">
            <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
              <div className="col p-4 d-flex flex-column position-static">
                <h3 className="mb-2">{`${doctor.key.employee.jobTitle} ${doctor.key.employee.fullName}`}</h3>
                <div className="row m-0">
                  {Object.entries(doctor.value).map(([date, times]) => (
                    <div key={date} className="col-md-4 border rounded">
                      <div className="ps-2 pt-2">{date}</div>
                      <div className="p-2">
                        {times.map((time) => (
                          <button
                            key={time}
                            className="mt-1 ms-1"
                            data-date={date}
                            data-time={time}
                            onClick={(e) => handleSetDoctor(doctor.key.id, e.target.getAttribute('data-date'), e.target.getAttribute('data-time'), e.target)}
                          >
                            {time}
                          </button>
                        ))}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <form onSubmit={handleSubmit}>
        <input type="hidden" value={selectedDoctorId} name="doctorId" placeholder="Доктор" />
        <input type="hidden" value={selectedAppointmentDate} name="dateStr" placeholder="Время" />
        <button className="btn btn-primary btn-lg" id="add_appointment" type="submit">
          Добавить запись
        </button>
      </form>
    </>
  );
};

export default AppointmentsAdd;
