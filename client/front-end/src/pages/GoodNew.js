import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addGoods } from '../store/slice/GoodsSlice';

const GoodNew = () => {
  const dispatch = useDispatch();
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(addGoods({ newData: { price: parseInt(price), name } }));
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
        <h4 className="mb-3">Создать новую услугу</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="name" className="form-label">
                  Название
                </label>
                <input type="text" name="name" className="form-control" id="name" required value={name} onChange={(e) => setName(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="price" className="form-label">
                  Цена
                </label>
                <input type="number" step="0.01" name="price" className="form-control" id="price" required value={price} onChange={(e) => setPrice(e.target.value)} />
              </div>
            </div>
            <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
              Создать
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default GoodNew;
