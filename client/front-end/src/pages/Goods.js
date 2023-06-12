import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { requestGoods } from '../store/slice/GoodsSlice';
import EmptyComponent from '../components/EmptyComonent/EmptyComonent';

const Goods = () => {
  const dispatch = useDispatch();
  const goods = useSelector((state) => state.goods.goods);
  const [withArchived, setWithArchived] = useState(false);

  useEffect(() => {
    dispatch(requestGoods({}));
  }, [dispatch]);

  const toggleArchived = () => {
    setWithArchived((prevValue) => !prevValue);
  };

  return (
    <>
      <div className="row mb-2">
        {goods.length ? (
          <>
            {goods.map((good) => (
              <div key={good.id} className="col-md-12">
                <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                  <div className="col p-4 d-flex flex-column position-static">
                    <div className="col-3">
                      <input type="checkbox" name="active" id={`active-${good.id}`} checked={good.active} readOnly />
                      <label htmlFor={`active-${good.id}`} className="form-label">
                        Активная
                      </label>
                    </div>
                    <strong className="d-inline-block mb-2 text-primary">{good.name}</strong>
                    <div className="mb-1 text-muted">{good.price}</div>
                    <Link to={`/good/edit/${good.id}`} className="stretched-link">
                      Редактировать
                    </Link>
                  </div>
                </div>
              </div>
            ))}

            {withArchived ? (
              <Link to="/good" className="btn btn-primary mx-1" onClick={toggleArchived}>
                Скрыть архивные услуги
              </Link>
            ) : (
              <Link to="/good?withArchived" className="btn btn-primary mx-1" onClick={toggleArchived}>
                Показать архивные услуги
              </Link>
            )}
          </>
        ) : (
          <EmptyComponent />
        )}

        <Link to="/good/new" className="btn btn-primary mx-1 my-4">
          Создать новую
        </Link>
      </div>
    </>
  );
};

export default Goods;
