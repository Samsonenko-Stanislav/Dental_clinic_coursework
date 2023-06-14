import React, { useState, useContext, useEffect } from 'react';
import { UserContext } from '../context/UserContext';
import axiosApi from '../axiosApi';
import { editAppointments, getSoloAppointments, nullifyAppointment } from "../store/slice/AppoimentsSlice";
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { requestGoods } from '../store/slice/GoodsSlice';
import moment from 'moment';
import 'moment/locale/ru';
moment.locale('ru');

const AppointmentsEdit = ({ checkLines = [] }) => {
  const dispatch = useDispatch();
  const params = useParams();
  const { role } = useContext(UserContext);
  const appointmentStore = useSelector((state) => state.appointments.appointment);
  const appointment = appointmentStore?.appointment || {};
  const goods = useSelector((state) => state.goods.goods).filter((user) => user.active) || [];
  const readOnly = appointmentStore.readOnly;
  const [conclusion, setConclusion] = useState('');

  const [total, setTotal] = useState(0);

  useEffect(() => {
    dispatch(getSoloAppointments({ newData: { id: params.id } }));
    dispatch(requestGoods({}));
  }, [dispatch, params.id]);

  useEffect(() => {
    if (Object.keys(appointment).length) {
      setConclusion(appointment.conclusion);
    }
  }, [appointment]);

  useEffect(() => {
    return () => {
      dispatch(nullifyAppointment());
    };
  }, [dispatch]);

  const handleInputChange = (e) => {
    // Обработчик изменения значения в полях чека
    // Реализация обработчика зависит от требуемой логики
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    dispatch(editAppointments({newData:{
        conclusion,
        id:params.id
      }}))
  };

  return (
    <div className="container">
      <div className="col-12">
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12 justify-content-between">
              <div className="col-3">
                <input type="checkbox" id="active" disabled checked={appointment.active} />
                <label htmlFor="active" className="form-label">
                  Активная
                </label>
              </div>
              <div className="col-3 mb-1 text-muted">
                <h5>{moment(appointment.date).format('lll')}</h5>
              </div>
              <h5 className="mb-1">
                Доктор: {appointment?.doctor?.fullName} {appointment?.doctor?.jobTitle}
              </h5>
              <h5 className="mb-1">Пациент: {appointment?.client?.fullName}</h5>
              <h5 className="mb-1">Амбулаторная карта: {appointment.id}</h5>
              <h5 className="mb-1">Email пациента: {appointment?.client?.email}</h5>
              <h5 className="mb-1">{appointment?.client?.gender === 'MALE' ? 'Пол пациента: Мужской' : 'Пол пациента: Женский'}</h5>
            </div>
          </div>
          <div className="row col-12 justify-content-between">
            <div className="row col-4">
              <h4>Заключение</h4>
              <textarea rows="15" value={conclusion} disabled={readOnly} name="conclusion" onChange={(e) => setConclusion(e.target.value)} />
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
                            <select className="form-select good" name="good" onChange={handleInputChange}>
                              <option value="0" />
                              {goods.map((good) => (
                                <option key={good.id} value={good.id}>
                                  {good.name}
                                </option>
                              ))}
                            </select>
                          </td>
                          <td>
                            <input type="number" disabled step="0.01" name="price" className="form-control price" onChange={handleInputChange} />
                          </td>
                          <td>
                            <input type="number" step="1" name="qty" className="form-control qty" onChange={handleInputChange} />
                          </td>
                          <td>
                            <input type="number" disabled step="0.01" name="total" className="form-control total" onChange={handleInputChange} />
                          </td>
                        </tr>



                        {checkLines.map((checkLine) => (
                          <tr key={checkLine.id}>
                            <td className="w-25">{checkLine.good.name}</td>
                            <td>
                              <input type="number" disabled step="0.01" name="price" className="form-control price" value={checkLine.price} />
                            </td>
                            <td>
                              <input type="number" disabled step="1" name="qty" className="form-control qty" value={checkLine.qty} />
                            </td>
                            <td>
                              <input type="number" disabled step="0.01" name="total" className="form-control total" value={checkLine.price * checkLine.qty} />
                            </td>
                          </tr>
                        ))}
                      </>
                    )}
                  </tbody>
                </table>
                <div className="row col-12 justify-content-end">
                  <div className="col-1 me-4">Итого:</div>
                  <input id="total" type="number" disabled step="0.01" className="col-4" value={total} />
                </div>
              </div>
              <div id="goodsPrices" className="d-none">
                {goods.map((good) => (
                  <div key={good.id}>
                    <input type="number" step="0.01" name="price" className="form-control price" value={good.price} />
                  </div>
                ))}
              </div>
              <input className="d-none" type="text" name="checkJson" id="checkJson" />
            </div>
          </div>
          <input type="hidden" name="appointmentId" value={appointment.id} />
          {role !== 'doctor' && readOnly ? null : (
            <button className="w-100 my-3 btn btn-primary btn-lg" type="submit">
              Сохранить
            </button>
          )}
          {role === 'user' && <button className="btn btn-primary">Отменить запись</button>}
        </form>
      </div>
    </div>
  );
};

export default AppointmentsEdit;
