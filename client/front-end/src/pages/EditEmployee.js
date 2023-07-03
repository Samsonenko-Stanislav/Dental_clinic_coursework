import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { editEmployee, nullifyDataEmployee, requestSoloEmployee } from '../store/slice/EmployeeSlice';
import { showNotification } from '../App';
import NotFound from "../components/NotFoundComponent/NotFound";

const EditEmployee = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = useParams();
  const employee = useSelector((state) => state.employee.employee);
  const [fullName, setFullName] = useState('');
  const [jobTitle, setTitle] = useState('');
  const [durationApp, setTimeReception] = useState('');
  const [workStart, setTimeStart] = useState('');
  const [workEnd, setTimeEnd] = useState('');

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

  useEffect(() => {
    if (Object.keys(employee).length) {
      setTimeEnd(employee.workEnd);
      setTimeStart(employee.workStart);
      setIsMonday(employee.workDays.includes('MONDAY'));
      setIsTuesday(employee.workDays.includes('TUESDAY'));
      setIsWednesday(employee.workDays.includes('WEDNESDAY'));
      setIsThursday(employee.workDays.includes("THURSDAY"));
      setIsFriday(employee.workDays.includes('FRIDAY'));
      setIsSaturday(employee.workDays.includes('SATURDAY'));
      setIsSunday(employee.workDays.includes('SUNDAY'));
      setFullName(employee.fullName);
      setTitle(employee.jobTitle);
      setTimeReception(employee.durationApp);
    }
  }, [employee]);

  useEffect(() => {
    dispatch(requestSoloEmployee({ newData: { id: params.id } }));

    return () => {
      dispatch(nullifyDataEmployee());
    };
  }, [dispatch, params.id]);

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
    const response = await dispatch(editEmployee(
        { newData:
              {
                id: params.id,
                workEnd,
                workStart,
                jobTitle,
                workDays,
                durationApp,
                fullName
              }
        })
    );
    if (response?.type?.includes('fulfilled')) {
      navigate('/employee');
      showNotification('success', 'Вы успешно изменили сотрудника', 'Изменение Сотрудника');
    }
  };
if (employee.id){
  return (
      <div className="col-md-7 col-lg-8 mt-4">
        <h4 className="mb-3">Редактировать сотрудника {params.id}</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="fullName" className="form-label">
                  ФИО
                </label>
                <input type="text" name="fullName" className="form-control" id="fullName" required value={fullName} onChange={(e) => setFullName(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="jobTitle" className="form-label">
                  Должность
                </label>
                <input type="text" required name="jobTitle" className="form-control" id="jobTitle" value={jobTitle} onChange={(e) => setTitle(e.target.value)} />
              </div>
            </div>
            <div className="row col-12 my-4">
              <div className="col-6">
                <label htmlFor="workStart" className="form-label">
                  Старт рабочего дня
                </label>
                <input type="time" required name="workStart" className="form-control" id="workStart" value={workStart} onChange={(e) => setTimeStart(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="workEnd" className="form-label">
                  Конец рабочего дня
                </label>
                <input type="time" required name="workEnd" className="form-control" id="workEnd" value={workEnd} onChange={(e) => setTimeEnd(e.target.value)} />
              </div>
              <div className="col-6 mt-2">
                <label htmlFor="durationApp" className="form-label">
                  Время приема (минут)
                </label>
                <input type="text" required name="durationApp" className="form-control" id="durationApp" value={durationApp} onChange={(e) => setTimeReception(e.target.value)} />
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
            <button className="btn btn-primary btn-lg my-4" type="submit">
              Сохранить
            </button>
          </div>
        </form>
      </div>
  );
}
else {
  return <NotFound/>
}

};

export default EditEmployee;
