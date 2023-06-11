import React, { useEffect, useState } from 'react';
import axiosApi from "../axiosApi";

const Price = () => {
  const [goods, setGoods] = useState([]);

  useEffect(() => {
    const fetchGoods = async () => {
      try {
        const response = await axiosApi.get('/price');
        setGoods(response.data?.goods);
      } catch (error) {
        console.error('Ошибка при загрузке товаров:', error);
      }
    };

    fetchGoods();
  }, []);

  return (
      <>
        {goods.map((good) => (
          <div className='col-md-12' key={good.id}>
            <div
              className='row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative'>
              <div className='col p-4 d-flex flex-column position-static'>
                <strong className='d-inline-block mb-2 text-primary'>{good.name}</strong>
                <div className='mb-1 text-muted'>{good.id}</div>
              </div>
            </div>
          </div>
        ))}
      </>
  );
};


export default Price;