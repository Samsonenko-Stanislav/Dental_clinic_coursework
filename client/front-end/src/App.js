import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import Main_page from "./components/Main.js";
import Footer from "./components/Footer.js";
import Home from "./pages/Home";
import Login from "./authorization/Login";


const App = () => {

  return (
      <div>

        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
          </Routes>
        </BrowserRouter>
          <Footer/>
      </div>

  );
}

export default App;