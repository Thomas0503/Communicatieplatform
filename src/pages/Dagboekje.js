import React, { useEffect, useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth, db, storage } from "../firebase";
import { useHistory } from "react-router-dom";
import {
  collection,
  query,
  where,
  onSnapshot,
  addDoc,
  Timestamp,
  orderBy,
  setDoc,
  doc,
  getDoc,
  updateDoc,
} from "firebase/firestore";
import { ref, getDownloadURL, uploadBytes } from "firebase/storage";
import { Server } from "net";

const Dagboekje = () => {
  const [checked1, setChecked1] = useState(false);
  const [checked2, setChecked2] = useState(false);
 
  const handleChangeCheck1 = () => {
    setChecked1(!checked1);
  };

  const handleChangeCheck2 = () => {
    setChecked2(!checked2);
  };


  const [data, setData] = useState({
    desc: "",
    password: "",
    error: null,
    loading: false,
  });

  const [selectValue, setState] = useState("");
  const [img, setImg] = useState("");

  const history = useHistory();

  const user = auth.currentUser.uid;


  const { desc, stress, error, loading } = data;

  const handleDropdownChange = (e) => {
    setState(e.target.value);
  };
  

  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  const overzicht = (e) => {
    console.log("overzicht")
    history.replace("/D2");
  };


  

  var dlUrl = "";
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(desc)
    if (img) {
      const imgRef = ref(
        storage,
        `dagboekje/${new Date().getTime()} - ${img.name}`
      );
      const snap = await uploadBytes(imgRef, img);
      console.log("toegevoegd");
      dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));

      
      
    }

    await addDoc(collection(db, "dagboekje", "dagboekje", user), {
      "desc": desc,
      "uid": user,
      createdAt: Timestamp.fromDate(new Date()),
      stressniveau: stress,
      link: dlUrl,
      stresssignalen: {"Bevriezen": checked1.toString(), "Gapen": checked2.toString()},
      oefening: selectValue
      
    });
    setData({
      email: "",
      password: "",
      error: null,
      loading: false,
    });
    history.replace("/D2");
  
  
  };

  

 

  
  return (
    <div>
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
        <input
        onChange={(e) => setImg(e.target.files[0])}
        type="file"
        id="img"
        accept="image/*"
        style={{ display: "box" }}
      />

        
    <div>
      <label>
        <input
          type="checkbox"
          checked={checked1}
          onChange={handleChangeCheck1}
        />
        Bevriezen
      </label>
 
    </div>
    <div>
      <label>
        <input
          type="checkbox"
          checked={checked2}
          onChange={handleChangeCheck2}
        />
        Gapen
      </label>
    </div>
    <select id = "dropdown" onChange={handleDropdownChange}>
      <option value="0">Kies oefening</option>
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <option value="4">4</option>
</select>
        
        <div>
        <input type="number" max="100"placeholder = "Stressnivea"
        name="stress"
        value={stress}
        onChange={handleChange}/>
        </div>
        <div className="btn_container">
          <button className="btn" disabled={loading}>
            {loading ? "Aan het toevoegen ..." : "toevoegen"}
          </button>
        </div>
        
      </form>
      
    </section>
    <div className="btn_container">
      <button className="btn" onClick={overzicht}>Bekijk het overzicht</button>
      </div>
      </div>
  );
};

export default Dagboekje;
