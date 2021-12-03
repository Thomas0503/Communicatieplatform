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

const Dagboek2 = () => {
  const [checked1, setChecked1] = useState(false);
  const [checked2, setChecked2] = useState(false);
  const [checked3, setChecked3] = useState(false);
  const [checked4, setChecked4] = useState(false);
  const [checked5, setChecked5] = useState(false);
  const [checked6, setChecked6] = useState(false);
  const [checked7, setChecked7] = useState(false);
  const [checked8, setChecked8] = useState(false);
  const [checked9, setChecked9] = useState(false);
  const [checked10, setChecked10] = useState(false);

  const handleChangeCheck1 = () => {
    setChecked1(!checked1);
  };

  const handleChangeCheck2 = () => {
    setChecked2(!checked2);
  };
  const handleChangeCheck3 = () => {
    setChecked3(!checked3);
  };
  const handleChangeCheck4 = () => {
    setChecked4(!checked4);
  };
  const handleChangeCheck5 = () => {
    setChecked5(!checked5);
  };
  const handleChangeCheck6 = () => {
    setChecked6(!checked6);
  };
  const handleChangeCheck9 = () => {
    setChecked9(!checked9);
  };
  const handleChangeCheck7 = () => {
    setChecked7(!checked7);
  };
  const handleChangeCheck8 = () => {
    setChecked8(!checked8);
  };
  
  const handleChangeCheck10 = () => {
    setChecked10(!checked10);
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
    history.replace("/D3");
  };




  var dlUrl = "";
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(desc)
    setData({
      email: "",
      password: "",
      error: null,
      loading: true,
    });
    if (img) {
      const imgRef = ref(
        storage,
        `dagboekje/${new Date().getTime()} - ${img.name}`
      );
      const snap = await uploadBytes(imgRef, img);
      console.log("toegevoegd");
      dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));



    }
    var signl = []
    const sign = { "Bevriezen": checked1.toString(), "Gapen": checked2.toString(), "Hijgen": checked3.toString(), "Krabben": checked4.toString(), "Oogwit": checked5.toString(), "Tongelen": checked6.toString(), "Trillen": checked7.toString(), "Uitschudden": checked8.toString(), "Wegkijken": checked9.toString(), "Overige": checked10.toString() }
    for (const [key, value] of Object.entries(sign)) {
      if (value == "true") {
        signl.push(key)
      }
    }

    await addDoc(collection(db, "dagboekje", "dagboekje", user), {
      "desc": desc,
      "uid": user,
      createdAt: Timestamp.fromDate(new Date()),
      stressniveau: parseInt(stress),
      link: dlUrl,
      stresssignalen: signl,
      oefening: selectValue

    });
    setData({
      email: "",
      password: "",
      error: null,
      loading: false,
    });
    history.replace("/D3");


  };






  return (
    <div>
      <section>
        <h3>Dagboek</h3>
        <form className="form" onSubmit={handleSubmit}>
          <div className="input_container">
            <label htmlFor="text">Beschrijving</label>
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
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked3}
                onChange={handleChangeCheck3}
              />
              Hijgen
            </label>

          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked4}
                onChange={handleChangeCheck4}
              />
              Krabben
            </label>

          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked5}
                onChange={handleChangeCheck5}
              />
              Oogwit
            </label>

          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked6}
                onChange={handleChangeCheck6}
              />
              Tongelen
            </label>

          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked7}
                onChange={handleChangeCheck7}
              />
              Trillen
            </label>

          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked8}
                onChange={handleChangeCheck8}
              />
              Uitschudden
            </label>

          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked9}
                onChange={handleChangeCheck9}
              />
              Wegkijken
            </label>

          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={checked10}
                onChange={handleChangeCheck10}
              />
              Overige
            </label>

          </div>
          
          
          <select id="dropdown" onChange={handleDropdownChange}>
            <option value="0" disabled="true" selected="selected">Kies oefening</option>
            <option value="Bench">Bench</option>
            <option value="Houdigen">Houdigen</option>
            <option value="Kinderen">Kinderen</option>
            <option value="Korte lijn zonder prikkels">Korte lijn zonder prikkels</option>
            <option value="Korte lijn met prikkels">Korte lijn met prikkels</option>
            <option value="Mee naar het werk">Mee naar het werk</option>
            <option value="Op dingen springen / onder dingen kruipen">Op dingen springen / onder dingen kruipen</option>
            <option value="Stofzuiger">Stofzuiger</option>
            <option value="Tafeloefening">Tafeloefening</option>
            <option value="Voedselweigering">Voedselweigering</option>
            <option value="Vreemde ondergronden">Vreemde ondergronden</option>
            <option value="Privé vervoer">Privé vervoer</option>
            <option value="Openbaar vervoer">Openbaar vervoer</option>
            <option value="Openbare plaatsen">Openbare plaatsen</option>
            <option value="Shopping">Shopping</option>
            <option value="Horeca">Horeca</option>
          </select>

          <div>
            <input type="number" max="100" placeholder="Stressniveau"
              name="stress"
              value={stress}
              onChange={handleChange} />
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

export default Dagboek2;
