import "./App.css";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Profile from './pages/Profile'
import AuthProvider from "./context/auth";
import PrivateRoute from "./components/PrivateRoute";
import Dagboekje from "./pages/Dagboekje";
import Dagboekje_get from "./pages/Dagboekje_get";
import Dagboekje_get_trainer from "./pages/Dagboekje_get_trainer";
import Chat_trainer from "./pages/Chat_trainer";
import Calender from "./pages/Calender";
import Formulier from "./pages/Formulier";
import D2 from "./pages/D2";
import Cal2 from "./pages/Cal2";
import CalT from "./pages/Cal_trainer";
import Chat_met_trainer from "./pages/Chat_met_trainer";
import Form2 from "./pages/Form2";
import Dagboek2 from "./pages/Dagboek2";
import D3 from "./pages/D3";
import DocTrainer from "./pages/DocTrainer";
import Form3 from "./pages/Form3";
import DocTrainer2 from "./pages/DocTrainer2";
//import 'bootstrap/dist/css/bootstrap.min.css';


function App() {





  
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <Switch>
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
          <PrivateRoute exact path="/profile" component={Profile} />
          <PrivateRoute exact path="/dagboekje" component={Dagboekje} />
          <PrivateRoute exact path="/dagboekje_get" component={Dagboekje_get} />
          <PrivateRoute exact path="/dagboekje_get_trainer" component={Dagboekje_get_trainer} />
          <PrivateRoute exact path="/chat_trainer" component={Chat_trainer} />
          <PrivateRoute exact path="/chat" component={Home} />
          <PrivateRoute exact path="/calender" component={Calender} />
          <PrivateRoute exact path="/formulier" component={Formulier} />
          <PrivateRoute exact path="/D2" component={D2} />
          <PrivateRoute exact path="/calT" component={CalT} />
          <PrivateRoute exact path="/form2" component={Form2} />
          <PrivateRoute exact path="/chat_met_trainer" component={Chat_met_trainer} />
          <PrivateRoute exact path="/dagboek2" component={Dagboek2} />
          <PrivateRoute exact path="/D3" component={D3} />
          <PrivateRoute exact path="/Doctrainer" component={DocTrainer} />
          <PrivateRoute exact path="/Form3" component={Form3} />
          <PrivateRoute exact path="/DocTrainer2" component={DocTrainer2} />
        </Switch>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
