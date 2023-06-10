import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Employee = () => {
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
    fetchEmployees();
  }, []);

  const fetchEmployees = async () => {
    try {
      const response = await fetch('https://jsonplaceholder.typicode.com/users');
      const data = await response.json();
      setEmployees(data);
    } catch (error) {
      console.error('Error fetching employees:', error);
    }
  };

  return (
    <>
        {employees.map((employee) => (
          <div className='col-md-12' key={employee.id}>
            <div
              className='row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative'>
              <div className='col p-4 d-flex flex-column position-static'>
                <strong className='d-inline-block mb-2 text-primary'>
                  {`${employee.id}:${employee.name}`}
                </strong>
                <h3 className='mb-0'>{employee.email}</h3>
                <div className='mb-1 text-muted'>{`${employee.workStart}-${employee.workEnd}`}</div>
                <Link to={`/employee/${employee.id}`} className='stretched-link'>Редактировать</Link>
              </div>
            </div>
          </div>
        ))}

      <Link to='/employee/new' className='btn btn-primary'>Создать нового</Link>
    </>
  );
};


export default Employee;