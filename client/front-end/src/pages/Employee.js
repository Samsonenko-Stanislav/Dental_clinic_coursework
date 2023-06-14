import React, { useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { UserContext } from "../context/UserContext";
import { useDispatch, useSelector } from "react-redux";
import { requestEmployee } from "../store/slice/EmployeeSlice";

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
    <div className="mt-4">
      {employees.length ? (
        <div className={"list"}>
          {employees.map((employee) => (
            <div className="col-md-12" key={employee.id}>
              <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <div className="col p-4 d-flex flex-column position-static">
                  <strong className="d-inline-block mb-2 text-primary">{`${employee.fullName}`}</strong>
                  <h3 className="mb-0">Должность: {employee.jobTitle}</h3>
                  <div className="mb-1 text-muted">{`${employee.workStart}-${employee.workEnd}`}</div>
                  <Link to={`/employee/edit/${employee.id}`} className="stretched-link">
                    Редактировать
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : null}
      <Link to="/employee/new" className="btn btn-primary my-4">
        Создать нового
      </Link>
    </div>
  );
};

export default Employee;
