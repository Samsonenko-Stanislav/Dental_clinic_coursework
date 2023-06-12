import React, { useState, useContext } from "react";
import { UserContext } from "../context/UserContext";
import axiosApi from "../axiosApi";

const AppointmentsEdit = ({
  appointment = {},
  message,
  goods = [],
  checkLines = [],
}) => {
  const [total, setTotal] = useState(0);
  const { role } = useContext(UserContext);
  const readOnly = role !== "doctor";

  const handleInputChange = (e) => {
    // Обработчик изменения значения в полях чека
    // Реализация обработчика зависит от требуемой логики
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosApi.post("/login", {});
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="container">
      <div className="col-12">
        {message && <div>{message}</div>}
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12 justify-content-between">
              <div className="col-3">
                <input
                  type="checkbox"
                  id="active"
                  disabled
                  checked={appointment.active}
                />
                <label htmlFor="active" className="form-label">
                  Активная
                </label>
              </div>
              <div className="col-3 mb-1 text-muted">{appointment.date}</div>
              <h3 className="mb-0">Доктор: </h3>
              <h3 className="mb-0">Пациент: {appointment.fullName}</h3>
              <h3 className="mb-0">Амбулаторная карта: {appointment.id}</h3>
              <h3 className="mb-0">Email пациента: {appointment.email}</h3>
              <h3 className="mb-0">
                {true ? "Пол пациента: Мужской" : "Пол пациента: Женский"}
              </h3>
            </div>
          </div>
          <div className="row col-12 justify-content-between">
            <div className="row col-4">
              <h4>Заключение</h4>
              <textarea
                rows="15"
                value={appointment.conclusion}
                disabled={readOnly}
                name="conclusion"
                onChange={handleInputChange}
              />
            </div>
            <div className="row col-7">
              <h4>Чек</h4>
              <div className="col-12 table-responsive h-100">
                <table className="table table-striped table-sm">
                  <thead>
                    <tr>
                      <th scope="col">Услуга</th>
                      <th scope="col">Стоимость</th>
                      <th scope="col">Кол-во</th>
                      <th scope="col">Итог</th>
                    </tr>
                  </thead>
                  <tbody id="check">
                    {readOnly ? (
                      checkLines.map((checkLine) => (
                        <tr key={checkLine.id}>
                          <td className="w-25">{checkLine.name}</td>
                          <td>{checkLine.price}</td>
                          <td>{checkLine.qty}</td>
                          <td>{checkLine.price * checkLine.qty}</td>
                        </tr>
                      ))
                    ) : (
                      <>
                        <tr>
                          <td className="w-25">
                            <select
                              className="form-select good"
                              name="good"
                              onChange={handleInputChange}
                            >
                              <option value="0" />
                              {goods.map((good) => (
                                <option key={good.id} value={good.id}>
                                  {good.name}
                                </option>
                              ))}
                            </select>
                          </td>
                          <td>
                            <input
                              type="number"
                              disabled
                              step="0.01"
                              name="price"
                              className="form-control price"
                              onChange={handleInputChange}
                            />
                          </td>
                          <td>
                            <input
                              type="number"
                              step="1"
                              name="qty"
                              className="form-control qty"
                              onChange={handleInputChange}
                            />
                          </td>
                          <td>
                            <input
                              type="number"
                              disabled
                              step="0.01"
                              name="total"
                              className="form-control total"
                              onChange={handleInputChange}
                            />
                          </td>
                        </tr>
                        {checkLines.map((checkLine) => (
                          <tr key={checkLine.id}>
                            <td className="w-25">{checkLine.good.name}</td>
                            <td>
                              <input
                                type="number"
                                disabled
                                step="0.01"
                                name="price"
                                className="form-control price"
                                value={checkLine.price}
                              />
                            </td>
                            <td>
                              <input
                                type="number"
                                disabled
                                step="1"
                                name="qty"
                                className="form-control qty"
                                value={checkLine.qty}
                              />
                            </td>
                            <td>
                              <input
                                type="number"
                                disabled
                                step="0.01"
                                name="total"
                                className="form-control total"
                                value={checkLine.price * checkLine.qty}
                              />
                            </td>
                          </tr>
                        ))}
                      </>
                    )}
                  </tbody>
                </table>
                <div className="row col-12 justify-content-end">
                  <div className="col-1 me-4">Итого:</div>
                  <input
                    id="total"
                    type="number"
                    disabled
                    step="0.01"
                    className="col-4"
                    value={total}
                  />
                </div>
              </div>
              <div id="goodsPrices" className="d-none">
                {goods.map((good) => (
                  <div key={good.id}>
                    <input
                      type="number"
                      step="0.01"
                      name="price"
                      className="form-control price"
                      value={good.price}
                    />
                  </div>
                ))}
              </div>
              <input
                className="d-none"
                type="text"
                name="checkJson"
                id="checkJson"
              />
            </div>
          </div>
          <input type="hidden" name="appointmentId" value={appointment.id} />
          {role !== "doctor" && readOnly ? null : (
            <button className="w-100 my-3 btn btn-primary btn-lg" type="submit">
              Сохранить
            </button>
          )}
          {role === "user" && (
            <button className="btn btn-primary">Отменить запись</button>
          )}
        </form>
      </div>
    </div>
  );
};

export default AppointmentsEdit;
