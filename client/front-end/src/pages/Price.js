import React, { useEffect, useState } from 'react';

const Price = () => {
  const [goods, setGoods] = useState([]);

  useEffect(() => {
    const fetchGoods = async () => {
      try {
        const response = await fetch('https://jsonplaceholder.typicode.com/comments');
        const data = await response.json();
        setGoods(data);
      } catch (error) {
        console.error('Ошибка при загрузке товаров:', error);
      }
    };

    fetchGoods();
  }, []);

  return (
      <div className='container my-4'>
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
      </div>
  );
};


export default Price;