import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import Footer from "./components/Footer.js";
import Home from "./pages/Home";
import Login from "./authorization/Login";
import Navigation from "./components/Navigation";
import Registration from "./authorization/Register";
import PriceList from "./components/PriceList";
import {ToastContainer} from "react-toastify";


const App = () => {

  return (
      <div>

        <BrowserRouter>
            <Navigation role="USER"/>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/price" element={<PriceList />} />
              <Route path="/sign_in" element={<Login />} />
              <Route path="/sign_up" element={<Registration />} />
          </Routes>
        </BrowserRouter>
          <Footer/>
          <ToastContainer />
      </div>

  );
}

export default App;
