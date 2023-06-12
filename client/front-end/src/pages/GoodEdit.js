import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { editGoods, getGood } from '../store/slice/GoodsSlice';

const GoodEdit = () => {
  const dispatch = useDispatch();
  const params = useParams();
  const good = useSelector((state) => state.goods.good);
  const [title, setTitle] = useState('');
  const [price, setPrice] = useState('');
  const [active, setActive] = useState(false);

  useEffect(() => {
    dispatch(getGood({ newData: { id: params.id } }));
  }, [dispatch, params.id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(editGoods({}));
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
        <h4 className="mb-3">Редактировать услугу</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="col-3">
              <input type="checkbox" name="active" id="active" defaultChecked={good.active} value={active} onChange={(e) => setActive(e.target.checked)} />
              <label htmlFor="active" className="form-label">
                Активная
              </label>
            </div>
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="name" className="form-label">
                  Название
                </label>
                <input type="text" name="name" className="form-control" id="name" required defaultValue={good.name} value={title} onChange={(e) => setTitle(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="price" className="form-label">
                  Цена
                </label>
                <input
                  type="number"
                  step="0.01"
                  name="price"
                  className="form-control"
                  id="price"
                  required
                  defaultValue={good.price}
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                />
              </div>
            </div>
            <input type="hidden" name="goodId" value={good.id} />
            <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
              Сохранить
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default GoodEdit;
