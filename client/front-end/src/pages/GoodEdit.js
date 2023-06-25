import React, { useEffect, useState, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { editGoods, getGood, nullifyDataGoods } from '../store/slice/GoodsSlice';
import { UserContext } from '../context/UserContext';
import { showNotification } from '../App';
import NotFound from "../components/NotFoundComponent/NotFound";

const GoodEdit = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = useParams();
  const good = useSelector((state) => state.goods.good);
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [active, setActive] = useState(false);
  const { setLoading } = useContext(UserContext);


  useEffect(() => {
    dispatch(getGood({ newData: { id: params.id } }));

    return () => {
      dispatch(nullifyDataGoods({}));
    };
  }, [dispatch, params.id]);

  useEffect(() => {
    if (Object.keys(good).length) {
      setPrice(good.price);
      setName(good.name);
      setActive(good.active);
    }
  }, [good]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const response = await dispatch(editGoods({ newData: { price, name, active, id: params.id } }));

    if (response?.type?.includes('fulfilled')) {
      showNotification('success', 'Вы успешно отредактировали услугу', 'Услуга');

      navigate('/good');
    }
    setLoading(false);
  };
if (good.id){
  return (
      <div className="col-md-7 col-lg-8 mt-4">
        <h4 className="mb-3">Редактировать услугу</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="col-3">
              <input type="checkbox" name="active" id="active" value={active} checked={active} onChange={(e) => setActive(e.target.checked)} />
              <label htmlFor="active" className="form-label">
                Активная
              </label>
            </div>
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="name" className="form-label">
                  Название
                </label>
                <input type="text" name="name" className="form-control" id="name" required defaultValue={good.name} value={name} onChange={(e) => setName(e.target.value)} />
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
  );
}
else {
  return <NotFound/>
}

};

export default GoodEdit;
