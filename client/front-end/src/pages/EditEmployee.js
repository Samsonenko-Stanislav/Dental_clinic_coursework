import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { editEmployee, nullifyDataEmployee, requestSoloEmployee } from '../store/slice/EmployeeSlice';
import { showNotification } from '../App';

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

  useEffect(() => {
    if (Object.keys(employee).length) {
      setTimeEnd(employee.workEnd);
      setTimeStart(employee.workStart);
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
    const response = await dispatch(editEmployee({ newData: { id: params.id, workEnd, workStart, jobTitle, durationApp, fullName } }));
    if (response?.type?.includes('fulfilled')) {
      navigate('/employee');
      showNotification('success', 'Вы успешно изменили сотрудника', 'Изменение Сотрудника');
    }
  };

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
              <input type="text" name="jobTitle" className="form-control" id="jobTitle" value={jobTitle} onChange={(e) => setTitle(e.target.value)} />
            </div>
          </div>
          <div className="row col-12 my-4">
            <div className="col-6">
              <label htmlFor="workStart" className="form-label">
                Старт рабочего дня
              </label>
              <input type="time" name="workStart" className="form-control" id="workStart" value={workStart} onChange={(e) => setTimeStart(e.target.value)} />
            </div>
            <div className="col-6">
              <label htmlFor="workEnd" className="form-label">
                Конец рабочего дня
              </label>
              <input type="time" name="workEnd" className="form-control" id="workEnd" value={workEnd} onChange={(e) => setTimeEnd(e.target.value)} />
            </div>
            <div className="col-6 mt-2">
              <label htmlFor="durationApp" className="form-label">
                Время приема (минут)
              </label>
              <input type="text" name="durationApp" className="form-control" id="durationApp" value={durationApp} onChange={(e) => setTimeReception(e.target.value)} />
            </div>
          </div>
          <button className="btn btn-primary btn-lg my-4" type="submit">
            Сохранить
          </button>
        </div>
      </form>
    </div>
  );
};

export default EditEmployee;
