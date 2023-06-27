import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { requestPrice } from '../store/slice/PriceSlice';
import EmptyComponent from '../components/EmptyComponent/EmptyComponent';

const Price = () => {
  const dispatch = useDispatch();
  const goods = useSelector((state) => state.price.prices);

  useEffect(() => {
    dispatch(requestPrice({}));
  }, [dispatch]);

  return (
    <div className={'my-4'}>
      {goods.length ? (
        goods.map((good) => (
          <div className="col-md-12" key={good.id}>
            <div className="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
              <div className="col p-4 d-flex flex-column position-static">
                <strong className="d-inline-block mb-2 text-primary">{good.name}</strong>
                <div className="mb-1 text-muted">Цена: {good.price}</div>
              </div>
            </div>
          </div>
        ))
      ) : (
        <EmptyComponent />
      )}
    </div>
  );
};

export default Price;
