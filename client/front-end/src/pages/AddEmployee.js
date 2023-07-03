import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addEmployee } from '../store/slice/EmployeeSlice';
import { useNavigate } from 'react-router-dom';
import { showNotification } from "../App";

const AddEmployee = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [fullName, setFullName] = useState('');
  const [jobTitle, setJobTitle] = useState('');
  const [workStart, setWorkStart] = useState('');
  const [workEnd, setWorkEnd] = useState('');
  const [durationApp, setDurationApp] = useState('');

  const [isMonday, setIsMonday] = useState(false);
  const [isTuesday, setIsTuesday] = useState(false);
  const [isWednesday, setIsWednesday] = useState(false);
  const [isThursday, setIsThursday] = useState(false);
  const [isFriday, setIsFriday] = useState(false);
  const [isSaturday, setIsSaturday] = useState(false);
  const [isSunday, setIsSunday] = useState(false);

  const handleMondayChange = (event) => {
    setIsMonday(event.target.checked)
  }

  const handleTuesdayChange = (event) => {
    setIsTuesday(event.target.checked)
  }
  const handleWednesdayChange = (event) => {
    setIsWednesday(event.target.checked)
  }
  const handleThursdayChange = (event) => {
    setIsThursday(event.target.checked)
  }
  const handleFridayChange = (event) => {
    setIsFriday(event.target.checked)
  }
  const handleSaturdayChange = (event) => {
    setIsSaturday(event.target.checked)
  }
  const handleSundayChange = (event) => {
    setIsSunday(event.target.checked)
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    const workDays = [];

    if(isMonday) workDays.push('MONDAY');
    if(isTuesday) workDays.push('TUESDAY');
    if(isWednesday) workDays.push('WEDNESDAY');
    if(isThursday) workDays.push('THURSDAY');
    if(isFriday) workDays.push('FRIDAY');
    if(isSaturday) workDays.push('SATURDAY');
    if(isSunday) workDays.push('SUNDAY');

    const response = await dispatch(addEmployee(
        { newData:
              { fullName,
                jobTitle,
                workStart: workStart + ':00',
                workEnd: workEnd + ':00',
                workDays,
                durationApp: parseInt(durationApp)
              }
        })

    );

    if (response?.type?.includes('fulfilled')) {
      showNotification('success', 'Вы успешно создали сотрудника', 'Создание Сотрудника');
      navigate("/employee");
    }
  };

  return (
      <div className="col-md-7 col-lg-8 mt-4">
        <h4 className="mb-3">Создать нового сотрудника</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="fullName" className="form-label">
                  ФИО
                </label>
                <input required type="text" name="fullName" className="form-control" required id="fullName" value={fullName} onChange={(e) => setFullName(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="jobTitle" className="form-label">
                  Должность
                </label>
                <input required type="text" name="jobTitle" className="form-control" required id="jobTitle" value={jobTitle} onChange={(e) => setJobTitle(e.target.value)} />
              </div>
            </div>
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="workStart" className="form-label">
                  Старт рабочего дня
                </label>
                <input required type="time" name="workStart" className="form-control" id="workStart" value={workStart} onChange={(e) => setWorkStart(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="workEnd" className="form-label">
                  Конец рабочего дня
                </label>
                <input required type="time" name="workEnd" className="form-control" id="workEnd" value={workEnd} onChange={(e) => setWorkEnd(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="durationApp" className="form-label">
                  Время приема (минут)
                </label>
                <input required type="text" name="durationApp" className="form-control" required id="durationApp" value={durationApp} onChange={(e) => setDurationApp(e.target.value)} />
              </div>
              <div className="col">
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="MONDAY" id="MONDAY" checked={isMonday} onChange={handleMondayChange} />
                  <label htmlFor="MONDAY" className="form-label">
                    Понедельник
                  </label>
                </div>
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="TUESDAY" id="TUESDAY" checked={isTuesday} onChange={handleTuesdayChange} />
                  <label htmlFor="TUESDAY" className="form-label">
                    Вторник
                  </label>
                </div>
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="WEDNESDAY" id="WEDNESDAY" checked={isWednesday} onChange={handleWednesdayChange} />
                  <label htmlFor="WEDNESDAY" className="form-label">
                    Среда
                  </label>
                </div>
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="THURSDAY" id="THURSDAY" checked={isThursday} onChange={handleThursdayChange} />
                  <label htmlFor="THURSDAY" className="form-label">
                    Четверг
                  </label>
                </div><div className="col-md-5 my-2">
                <input type="checkbox" name="FRIDAY" id="FRIDAY" checked={isFriday} onChange={handleFridayChange} />
                <label htmlFor="FRIDAY" className="form-label">
                  Пятница
                </label>
              </div><div className="col-md-5 my-2">
                <input type="checkbox" name="SATURDAY" id="SATURDAY" checked={isSaturday} onChange={handleSaturdayChange} />
                <label htmlFor="SATURDAY" className="form-label">
                  Суббота
                </label>
              </div><div className="col-md-5 my-2">
                <input type="checkbox" name="SUNDAY" id="SUNDAY" checked={isSunday} onChange={handleSundayChange} />
                <label htmlFor="SUNDAY" className="form-label">
                  Воскресенье
                </label>
              </div>
              </div>
            </div>
            <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
              Создать
            </button>
          </div>
        </form>
      </div>
  );
};

export default AddEmployee;
