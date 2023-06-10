import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Goods = () => {
  const [goods, setGoods] = useState([]);
  const [withArchived, setWithArchived] = useState(false);

  useEffect(() => {
    // Выполнение запроса для получения списка услуг
    // При успешном выполнении запроса установите данные в состояние
    // setGoods(response.data);
  }, []);

  const toggleArchived = () => {
    setWithArchived((prevValue) => !prevValue);
  };

  return (
    <>
      <div className='row mb-2'>
        {goods.map((good) => (
          <div key={good.id} className='col-md-11'>
            <div
              className='row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative'>
              <div className='col p-4 d-flex flex-column position-static'>
                <div className='col-3'>
                  <input
                    type='checkbox'
                    name='active'
                    id={`active-${good.id}`}
                    checked={good.active}
                    readOnly
                  />
                  <label htmlFor={`active-${good.id}`} className='form-label'>
                    Активная
                  </label>
                </div>
                <strong className='d-inline-block mb-2 text-primary'>
                  {good.name}
                </strong>
                <div className='mb-1 text-muted'>{good.price}</div>
                <Link to={`/good/${good.id}`} className='stretched-link'>
                  Редактировать
                </Link>
              </div>
            </div>
          </div>
        ))}
      </div>

      <Link to='/good/new' className='btn btn-primary mx-1'>
        Создать новую
      </Link>
      {withArchived ? (
        <Link to='/good' className='btn btn-primary mx-1' onClick={toggleArchived}>
          Скрыть архивные услуги
        </Link>
      ) : (
        <Link to='/good?withArchived' className='btn btn-primary mx-1' onClick={toggleArchived}>
          Показать архивные услуги
        </Link>
      )}

    </>
  );
};


export default Goods;