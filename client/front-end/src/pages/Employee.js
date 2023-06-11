import React, { useEffect, useState, useContext } from "react";
import { Link } from "react-router-dom";
import { UserContext } from "../UserContext";

const Employee = () => {
  const [employees, setEmployees] = useState([]);
  const { setLoading } = useContext(UserContext);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        setLoading(true);
        const response = await fetch(
          "https://jsonplaceholder.typicode.com/users"
        );
        const data = await response.json();
        setEmployees(data);
      } catch (error) {
        setLoading(false);
      } finally {
        setTimeout(() => setLoading(false), 1000);
      }
    };
    fetchEmployees();
  }, [setLoading]);

  return (
    <>
      {employees.map((employee) => (
        <div className="col-md-12" key={employee.id}>
          <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
            <div className="col p-4 d-flex flex-column position-static">
              <strong className="d-inline-block mb-2 text-primary">
                {`${employee.id}:${employee.name}`}
              </strong>
              <h3 className="mb-0">{employee.email}</h3>
              <div className="mb-1 text-muted">{`${employee.workStart}-${employee.workEnd}`}</div>
              <Link
                to={`/employee/edit/${employee.id}`}
                className="stretched-link"
              >
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
