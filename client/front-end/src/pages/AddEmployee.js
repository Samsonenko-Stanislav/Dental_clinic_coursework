import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addEmployee } from '../store/slice/EmployeeSlice';

const AddEmployee = () => {
  const dispatch = useDispatch();
  const [fullName, setFullName] = useState('');
  const [jobTitle, setJobTitle] = useState('');
  const [workStart, setWorkStart] = useState('');
  const [workEnd, setWorkEnd] = useState('');
  const [durationApp, setDurationApp] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(addEmployee({ newData: { fullName, jobTitle, workStart, workEnd, durationApp } }));
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
        <h4 className="mb-3">Создать нового сотрудника</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="fullName" className="form-label">
                  ФИО
                </label>
                <input type="text" name="fullName" className="form-control" required id="fullName" value={fullName} onChange={(e) => setFullName(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="jobTitle" className="form-label">
                  Должность
                </label>
                <input type="text" name="jobTitle" className="form-control" required id="jobTitle" value={jobTitle} onChange={(e) => setJobTitle(e.target.value)} />
              </div>
            </div>
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="workStart" className="form-label">
                  Старт рабочего дня
                </label>
                <input type="time" name="workStart" className="form-control" required id="workStart" value={workStart} onChange={(e) => setWorkStart(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="workEnd" className="form-label">
                  Конец рабочего дня
                </label>
                <input type="time" name="workEnd" className="form-control" required id="workEnd" value={workEnd} onChange={(e) => setWorkEnd(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="durationApp" className="form-label">
                  Время приема (минут)
                </label>
                <input type="text" name="durationApp" className="form-control" required id="durationApp" value={durationApp} onChange={(e) => setDurationApp(e.target.value)} />
              </div>
            </div>
            <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
              Создать
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddEmployee;
