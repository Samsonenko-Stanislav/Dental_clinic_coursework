import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Main_pict from "../assets/main.jpg";

const Home = () => {
  (function (p, o, s, t, m, a, n
  ) {
    !p[s] && (p[s] = function () {
      (p[t] || (p[t] = [])).push(arguments);
    });
    !o.getElementById(s+t) && o.getElementsByTagName("head")[0].appendChild((
        (n = o.createElement("script")),
            (n.id = s+t), (n.async = 1), (n.src = m), n
    ));
  }(window, document, "_pm", "PostmanRunObject", "https://run.pstmn.io/button.js"
  ))
  ;
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
        <p>Мы открыты ежедневно с 8:00 до 21:00</p>
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
      <div className="postman-run-button content padding-site m-3"
           data-postman-action="collection/fork"
           data-postman-visibility="private"
           data-postman-var-1="20983878-56711c53-7817-443a-b940-cb80c5e5b15b"
           data-postman-collection-url="entityId=20983878-56711c53-7817-443a-b940-cb80c5e5b15b&entityType=collection&workspaceId=63e788f6-1c0b-437d-8635-9e5a5aa63865">
      </div>
    </div>
  );
};

export default Home;
