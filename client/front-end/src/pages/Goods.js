import React, { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { requestGoods } from '../store/slice/GoodsSlice';
import EmptyComponent from '../components/EmptyComponent/EmptyComponent';
import { Checkbox } from 'antd';

const Goods = () => {
  const dispatch = useDispatch();
  const [withArchived, setWithArchived] = useState(true);
  const goodsStore = useSelector((state) => state.goods.goods);

  const goods = useMemo(() => {
    return withArchived ? goodsStore.filter((user) => user.active) : goodsStore.filter((user) => !user.active);
  }, [goodsStore, withArchived]);

  useEffect(() => {
    dispatch(requestGoods({}));
  }, [dispatch]);

  const toggleArchived = () => {
    setWithArchived((prevValue) => !prevValue);
  };

  return (
    <>
      <div className="row my-4 ">
        {goods.length ? (
          <div className="list">
            {[...goods].map((good) => (
              <div key={good.id} className="col-md-12">
                <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                  <div className="col p-4 d-flex flex-column position-static">
                    <div className="col-3">
                      <Checkbox checked={good.active}> Активная</Checkbox>
                    </div>
                    <strong className="d-inline-block my-2 text-primary">{good.name}</strong>
                    <div className="mb-1 text-muted">{good.price}</div>
                    <Link to={`/good/edit/${good.id}`} className="stretched-link">
                      Редактировать
                    </Link>
                  </div>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <EmptyComponent />
        )}
        <div className="my-4">
          {!withArchived ? (
            <button className="btn btn-primary mx-1" onClick={toggleArchived}>
              Показать активные услуги
            </button>
          ) : (
            <button className="btn btn-primary mx-1" onClick={toggleArchived}>
              Показать архивные услуги
            </button>
          )}
          <Link to="/good/new" className="btn btn-primary mx-1 my-4">
            Создать новую
          </Link>
        </div>
      </div>
    </>
  );
};

export default Goods;
