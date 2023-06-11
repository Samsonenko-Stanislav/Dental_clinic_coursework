import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import Footer from "./components/Footer.js";
import Home from "./pages/Home";
import Login from "./authorization/Login";
import {ToastContainer} from "react-toastify";


const App = () => {

  return (
      <div>

        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
              <Route path="/sign_in" element={<Login />} />
          </Routes>
        </BrowserRouter>
          <Footer/>
          <ToastContainer />
      </div>

  );
}

export default App;
