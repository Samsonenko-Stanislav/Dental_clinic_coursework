import React ,{ useState }from 'react';
import { useParams } from 'react-router-dom';
import axiosApi from '../axiosApi';

const GoodEdit = () => {
  const params = useParams()
  const [good, setGood]= useState({})

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosApi.post("/login", {

      });
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
        <h4 className="mb-3">Редактировать услугу</h4>
        <form  onSubmit={handleSubmit}>
          <div className="row">
            <div className="col-3">
              <input
                type="checkbox"
                name="active"
                id="active"
                defaultChecked={good.active}
              />
              <label htmlFor="active" className="form-label">
                Активная
              </label>
            </div>
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="name" className="form-label">
                  Название
                </label>
                <input
                  type="text"
                  name="name"
                  className="form-control"
                  id="name"
                  required
                  defaultValue={good.name}
                />
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
}


export default GoodEdit;