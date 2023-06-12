import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Main_pict from "../main.jpg";

const Home = () => {
  return (
    <div>
      <div className="content m-5">
        <p>
          <b>О стоматологической клинике "Улыбка премиум"</b>
        </p>
        <p>
          В нашей стоматологической клинике работают настоящие профессионалы, которые безболезненно лечат любые стоматологические заболевания с использованием только лучшего
          оборудования и материалов
        </p>
        <p>Мы открыты со вторника по субботу с 8:00 до 21:00</p>
      </div>
      <div className="content padding-site m-3">
        <div className="container">
          <div className="row">
            <div className="col-4 text-center">
              <img alt={'Main_picture'} className="main_pic" src={Main_pict} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
