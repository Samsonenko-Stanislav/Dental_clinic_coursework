import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import Main_page from "./Components/Main.js";
import Footer from "./Components/Footer.js";


const App = () => {

  return (
      <div>

        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Main_page />} />
          </Routes>
        </BrowserRouter>
          <Footer/>
      </div>

  );
}

export default App;