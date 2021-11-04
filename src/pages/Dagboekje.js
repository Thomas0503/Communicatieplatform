import React, { useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth, db } from "../firebase";
import { updateDoc, doc } from "firebase/firestore";
import { useHistory } from "react-router-dom";

const Dagboekje = () => {
  const [data, setData] = useState({
    desc: "",
    password: "",
    error: null,
    loading: false,
  });

  const history = useHistory();

  const { desc, password, error, loading } = data;

  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setData({ ...data, error: null, loading: true });
    
  
  };
  return (
    <section>
      <h3>Dagboekje</h3>
      <form className="form" onSubmit={handleSubmit}>
        <div className="input_container">
          <label htmlFor="text">Descr</label>
          <input
            type="text"
            name="desc"
            value={desc}
            onChange={handleChange}
          />
        </div>
        <div class="form-group">
          <input type="file" class="form-control" id="main-image">
        </input>
        </div>
        <div class = "form-group">
            <select name="" id="oef" class="form-control">
                <option value="" disabled selected>Select Oefening</option>
                <option value="1">oefening1</option>
                <option value="2">oefening2</option>
                <option value="3">oefening3</option>
            </select> 
            
        </div>
        <div class = "form-group">
            <div class="slidecontainer">
                <p>Stressniveau</p>
                <input type="range" min="1" max="100" value="50" class="slider" id="slider"/>
                <p>Value: <span id="demo"></span></p>
            </div>
            
        </div>
        <div className="btn_container">
          <button className="btn" disabled={loading}>
            {loading ? "Aan het toevoegen ..." : "toevoegen"}
          </button>
        </div>
      </form>
    </section>
  );
};

export default Dagboekje;
