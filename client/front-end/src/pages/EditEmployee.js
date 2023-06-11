import React, { useState } from 'react';
import { useParams } from 'react-router-dom';

const EditEmployee = (props) => {
  const params = useParams()
  const [employee, setEmployee]= useState({})

  const handleSubmit = (event) => {
    event.preventDefault();

    // Perform form submission logic here
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
                <input type="text" name="fullName" className="form-control" id="fullName" required value={employee.fullName} />
              </div>
              <div className="col-6">
                <label htmlFor="jobTitle" className="form-label">
                  Должность
                </label>
                <input type="text" name="jobTitle" className="form-control" id="jobTitle" value={employee.jobTitle} />
              </div>
            </div>
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="workStart" className="form-label">
                  Старт рабочего дня
                </label>
                <input type="time" name="workStart" className="form-control" id="workStart" value={employee.workStart} />
              </div>
              <div className="col-6">
                <label htmlFor="workEnd" className="form-label">
                  Конец рабочего дня
                </label>
                <input type="time" name="workEnd" className="form-control" id="workEnd" value={employee.workEnd} />
              </div>
              <div className="col-6">
                <label htmlFor="durationApp" className="form-label">
                  Время приема (минут)
                </label>
                <input type="text" name="durationApp" className="form-control" id="durationApp" value={employee.durationApp} />
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
}


export default EditEmployee;