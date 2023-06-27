import React from 'react';
import searchFail from '../../assets/search-fail.png';
import './styles.css';

const EmptyComponent = () => {
  return (
    <article className="empty">
      <img src={searchFail} alt="Logo" />
    </article>
  );
};

export default EmptyComponent;
