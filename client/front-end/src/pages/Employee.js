import React, { useContext, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import { useDispatch, useSelector } from 'react-redux';
import { requestEmployee } from '../store/slice/EmployeeSlice';

const Employee = () => {
  const dispatch = useDispatch();
  const employees = useSelector((state) => state.employee.employees);
  const { setLoading } = useContext(UserContext);

  useEffect(() => {
    setLoading(true);
    dispatch(requestEmployee({}));
    setLoading(false);
  }, [dispatch, setLoading]);

  return (
    <>
      {employees.map((employee) => (
        <div className="col-md-12" key={employee.id}>
          <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
            <div className="col p-4 d-flex flex-column position-static">
              <strong className="d-inline-block mb-2 text-primary">{`${employee.id}:${employee.name}`}</strong>
              <h3 className="mb-0">{employee.email}</h3>
              <div className="mb-1 text-muted">{`${employee.workStart}-${employee.workEnd}`}</div>
              <Link to={`/employee/edit/${employee.id}`} className="stretched-link">
                Редактировать
              </Link>
            </div>
          </div>
        </div>
      ))}

      <Link to="/employee/new" className="btn btn-primary">
        Создать нового
      </Link>
    </>
  );
};

export default Employee;
