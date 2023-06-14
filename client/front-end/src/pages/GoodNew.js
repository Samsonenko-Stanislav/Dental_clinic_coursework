import React, { useState, useContext } from 'react';
import { useDispatch } from 'react-redux';
import { addGoods } from '../store/slice/GoodsSlice';
import { useNavigate } from 'react-router-dom';
import { showNotification } from '../App';
import { UserContext } from '../context/UserContext';

const GoodNew = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const { setLoading } = useContext(UserContext);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    const response = await dispatch(addGoods({ newData: { price: parseInt(price), name, active: true } }));
    if (response?.type?.includes('fulfilled')) {
      showNotification('success', 'Вы успешно создали услугу', 'Услуга');

      navigate('/good');
      setLoading(false);
    }
  };

  return (
    <div className="col-md-7 col-lg-8 mt-4">
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
              <input type="number" min={0.01} step="0.01" name="price" className="form-control" id="price" required value={price} onChange={(e) => setPrice(e.target.value)} />
            </div>
          </div>
          <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
            Создать
          </button>
        </div>
      </form>
    </div>
  );
};

export default GoodNew;
