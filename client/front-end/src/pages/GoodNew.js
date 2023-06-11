import React from 'react';
import axiosApi from '../axiosApi';

const GoodNew = () => {
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
        <h4 className="mb-3">Создать новую услугу</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="name" className="form-label">
                  Название
                </label>
                <input type="text" name="name" className="form-control" id="name" required />
              </div>
              <div className="col-6">
                <label htmlFor="price" className="form-label">
                  Цена
                </label>
                <input type="number" step="0.01" name="price" className="form-control" id="price" required />
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
}

export default GoodNew;