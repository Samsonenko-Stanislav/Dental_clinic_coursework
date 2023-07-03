import React, { useContext, useEffect, useMemo, useState } from 'react';
import { UserContext } from '../context/UserContext';
import { cancelAppointment, editAppointments, getSoloAppointments, nullifyAppointment } from '../store/slice/AppoimentsSlice';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { requestGoods } from '../store/slice/GoodsSlice';
import moment from 'moment';
import 'moment/locale/ru';
import NotFound from "../components/NotFoundComponent/NotFound";
import {showNotification} from "../App";
moment.locale('ru');

const AppointmentsEdit = () => {
  const dispatch = useDispatch();
  const params = useParams();
  const { role } = useContext(UserContext);
  const {user} = useContext(UserContext)
  const appointmentStore = useSelector((state) => state.appointments.appointment);
  const goodsStore = useSelector((state) => state.goods.goods);
  const checkLines = appointmentStore.checkLines || [];
  const { setLoading } = useContext(UserContext);
  const readOnly = useMemo(() => {
    return appointmentStore.readOnly;
  }, [appointmentStore.readOnly]);

  const navigate = useNavigate();
  const [conclusion, setConclusion] = useState('');
  const [currentCheck, setCurrentCheck] = useState([{ id: 0, good: null, price: null, qty: null, total: null, goodId: null }]);
  const [total, setTotal] = useState(0);


  const goods = useMemo(() => {
    return goodsStore.filter((user) => user.active) || [];
  }, [goodsStore]);

  const appointment = useMemo(() => {
    return appointmentStore?.appointment || {};
  }, [appointmentStore]);

  useEffect(() => {
    const totalSum = readOnly
      ? checkLines.reduce((sum, item) => {
          return sum + (item.qty * item.price || 0);
        }, 0)
      : currentCheck.reduce((sum, item) => {
          return sum + (item.total || 0);
        }, 0);

    setTotal(totalSum);
  }, [currentCheck, checkLines, readOnly]);

  useEffect(() => {
    dispatch(getSoloAppointments({ newData: { id: params.id } }));
    dispatch(requestGoods({}));
    return () => {
      dispatch(nullifyAppointment());
    };
  }, [dispatch, params.id]);

  useEffect(() => {
    if (Object.keys(appointment).length) {
      setConclusion(appointment.conclusion);
    }
  }, [appointment]);

  const handleInputChange = (e) => {
    const good = JSON.parse(e.target.value);
    setCurrentCheck((prevState) => {
      return prevState.map((item) => {
        if (item.id === good.lineId) {
          return {
            ...item,
            price: good.price,
            total: good.price * 1,
            qty: 1,
            goodId: good.id,
          };
        }
        return item;
      });
    });
  };

  const handlePriceChange = (id, value) => {
    setCurrentCheck((prevState) => {
      return prevState.map((item) => {
        if (item.id === id) {
          return {
            ...item,
            qty: value,
            total: value * item.price,
          };
        }
        return item;
      });
    });
  };

  const addFieldHandler = () => {
    setCurrentCheck((prevState) => {
      return [...prevState, { id: prevState.length, good: null, price: null, qty: null, total: null, goodId: null }];
    });
  };

  const handleSubmit = async (e) => {
    setLoading(true);
    e.preventDefault();
    const response = await dispatch(
      editAppointments({
        newData: {
          conclusion,
          appointmentId: params.id,
          checks: currentCheck.filter((item) => item.goodId).map((item) => ({ goodId: item.goodId, price: item.price, qty: item.qty })),
        },
      })
    );
    if (response?.type?.includes('fulfilled')) {
      navigate('/appointments');
      showNotification('success', 'Заключение успешно оставлено!', 'Запись')
    }
    setLoading(false);
  };

  const cancelHandler = async (e) => {
    e.preventDefault();
    setLoading(true);
    const response = await dispatch(cancelAppointment({ newData: { id: params.id } }));

    if (response?.type?.includes('fulfilled')) {
      navigate('/appointments');
      showNotification('success', 'Запись успешно отменена', 'Запись')
    }
    if (response?.type?.includes('rejected')){
      showNotification('error', 'Вы не можете отменить запись, так как не являетесь в ней пациентом', 'Запись');
    }
    setLoading(false);
  };

  if (!Object.keys(appointmentStore).length) {
    return <NotFound/>;
  }
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
                  <h5>{moment(appointment.date).format('llll')}</h5>
                </div>
                <h5 className="mb-1">
                  <h5 className="mb-1">Номер записи: {appointment.id}</h5>
                  Доктор: {appointment?.doctor?.fullName} {appointment?.doctor?.jobTitle}
                </h5>
                <h5 className="mb-1">Пациент: {appointment?.client?.fullName}</h5>
                <h5 className="mb-1">Амбулаторная карта: {appointment?.client?.id}</h5>
                <h5 className="mb-1">Email пациента: {appointment?.client?.email}</h5>
                <h5 className="mb-1">{appointment?.client?.gender === 'MALE' ? 'Пол пациента: Мужской' : 'Пол пациента: Женский'}</h5>
              </div>
            </div>
            <div className="row col-12 justify-content-between">
              <div className="row col-4">
                <h4>Заключение</h4>
                <textarea required rows="15" value={conclusion} disabled={readOnly} name="conclusion" onChange={(e) => setConclusion(e.target.value)} />
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
                        <>
                          {checkLines.map((checkLine) => (
                              <tr key={checkLine.id}>
                                <td className="w-25">{checkLine.good.name}</td>
                                <td>
                                  <input type="number" disabled step="0.01" name="price" className="form-control price" value={checkLine.good.price} />
                                </td>
                                <td>
                                  <input type="number" disabled step="1" name="qty" className="form-control qty" value={checkLine.qty} />
                                </td>
                                <td>
                                  <input type="number" disabled step="0.01" name="total" className="form-control total" value={checkLine.good.price * checkLine.qty} />
                                </td>
                              </tr>
                          ))}
                        </>
                    ) : (
                        <>
                          {currentCheck.map((item, index) => {
                            return (
                                <tr>
                                  <td className="w-25">
                                    <select className="form-select good" name="good" onChange={handleInputChange}>
                                      <option value="0" />
                                      {goods.map((good) => (
                                          <option key={good.id} value={JSON.stringify({ ...good, lineId: index })}>
                                            {good.name}
                                          </option>
                                      ))}
                                    </select>
                                  </td>
                                  <td>
                                    <input type="number" value={item.price} disabled step="0.01" name="price" className="form-control price" />
                                  </td>
                                  <td>
                                    <input
                                        type="number"
                                        value={item.qty}
                                        step="1"
                                        name="qty"
                                        min={0}
                                        className="form-control qty"
                                        onChange={(e) => handlePriceChange(index, e.target.value)}
                                    />
                                  </td>
                                  <td>
                                    <input type="number" value={item.total} disabled step="0.01" name="total" className="form-control total" />
                                  </td>
                                </tr>
                            );
                          })}

                          <button className="btn btn-primary my-2" type={'button'} onClick={addFieldHandler}>
                            Добавить поле
                          </button>
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
            {readOnly ? null : (
                <button className="w-100 my-3 btn btn-primary btn-lg" type="submit">
                  Сохранить
                </button>
            )}
            {role.includes('USER') && readOnly && !checkLines.length && (
                <button className="btn btn-primary" type={'button'} onClick={cancelHandler}>
                  Отменить запись
                </button>
            )}
          </form>
        </div>
      </div>
  );


};

export default AppointmentsEdit;
