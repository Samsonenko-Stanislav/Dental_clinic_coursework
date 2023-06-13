import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { editEmployee, nullifyDataEmployee, requestSoloEmployee } from '../store/slice/EmployeeSlice';

const EditEmployee = () => {
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
    dispatch(editEmployee({ newData: { id: params.id, workEnd, workStart, jobTitle, durationApp, fullName } }));
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
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
            <div className="row col-12">
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
              <div className="col-6">
                <label htmlFor="durationApp" className="form-label">
                  Время приема (минут)
                </label>
                <input type="text" name="durationApp" className="form-control" id="durationApp" value={durationApp} onChange={(e) => setTimeReception(e.target.value)} />
              </div>
            </div>
            <input type="hidden" name="employeeId" value={employee.id} />
            <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
              Сохранить
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditEmployee;
